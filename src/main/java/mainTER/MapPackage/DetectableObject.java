package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import mainTER.Tools.Coordinate;

import java.util.concurrent.ExecutionException;

public abstract class DetectableObject {

    /**
     * Get the Node that corresponds to the appearance of the object
     * @return
     */
    public abstract Node getAppropriateNode();

    /**
     * Create a new Node that corresponds to the appearance of the object
     * @return Node
     */
    public abstract DetectableObject clone();

    public abstract double getHMouvementSpan();

    public double getJumpMouvementSpan(){
        return 0;
    }

    public double getFallMouvementSpan(){
        return 0;
    }

    public abstract Coordinate getCoordinate();

    public abstract void setCoordinate(Coordinate coordinate);

    public abstract double getX();
    public abstract double getY();
    public abstract void setX(double x);
    public abstract void setY(double y);

    public abstract double getWidth();
    public abstract double getHeight();

    public double getMinXBound(){
        return this.getAppropriateNode().getBoundsInParent().getMinX();
    }

    public double getMaxXBound(){
        return this.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    public double getMaxYBound(){
        return this.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    public double getMinYBound(){
        return this.getAppropriateNode().getBoundsInParent().getMinY();
    }

    public double rightMvt(DetectableObject detectableObject) {
        return this.getAppropriateNode().getBoundsInParent().getMinX() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    public double upMvt(DetectableObject detectableObject) {

        return detectableObject.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    public double downMvt(DetectableObject detectableObject) {
        return this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxY();
    }



    /**
     * Simulate the appropriate interaction between two objects
     * @param detectableObject
     */
    public void interaction(DetectableObject detectableObject){
    }

    public double calcMvt(CommingFrom commingFrom){

        Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX()-this.getAppropriateNode().getBoundsInParent().getMinX(),this.getAppropriateNode().getBoundsInParent().getMaxY()-this.getAppropriateNode().getBoundsInParent().getMinY());
        rect.setX(this.getX());
        rect.setY(this.getY());

        if (commingFrom == CommingFrom.LEFT) {
            return verifTrackRight();
        }
        if(commingFrom == CommingFrom.RIGHT){
            return verifTrackLeft();
        }
        if (commingFrom == CommingFrom.UP){
            return verifTrackDown();
        }
        if (commingFrom == CommingFrom.DOWN){
            return verifTrackUp();
        }

        System.out.println("Gros probleme");
        return 0;
    }



    private double verifTrackRight(){
        double miniRightMvt = 1000;
        boolean calc = false;
        for(int i=0; i < this.getHMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX()-this.getAppropriateNode().getBoundsInParent().getMinX(),this.getAppropriateNode().getBoundsInParent().getMaxY()-this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX()+i);

            for(DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList){
                if((!this.equals(detectableObject2)) &&(rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent())) &&(detectableObject2.getAppropriateNode().getBoundsInParent().getMinX()-this.getAppropriateNode().getBoundsInParent().getMaxX() >= 0) &&(detectableObject2.getAppropriateNode().getBoundsInParent().getMinX()-this.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan()))
                {//Prob it considers that it's true at the begening
                    calc = true;
                    detectableObject2.interaction(this);

                    multiInteractions(detectableObject2);

                    if(detectableObject2.rightMvt(this) < miniRightMvt){
                        miniRightMvt = detectableObject2.rightMvt(this);
                    }
                }
            }
            if (calc){
                return miniRightMvt;
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackLeft(){
        double miniLeftMvt = 1000;
        boolean calc = false;
        for(int i=0; i < this.getHMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX()-this.getAppropriateNode().getBoundsInParent().getMinX(),this.getAppropriateNode().getBoundsInParent().getMaxY()-this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX()-i);
            for(DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList){
                if((!this.equals(detectableObject2))&&(rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))&&(this.getAppropriateNode().getBoundsInParent().getMinX()- detectableObject2.getAppropriateNode().getBoundsInParent().getMaxX() >= 0)&&(this.getAppropriateNode().getBoundsInParent().getMinX()- detectableObject2.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan())){
                    calc = true;
                    detectableObject2.interaction(this);

                    multiInteractions(detectableObject2);

                    if(detectableObject2.leftMvt(this) < miniLeftMvt){
                        miniLeftMvt = detectableObject2.leftMvt(this);
                    }

                }
            }
            if (calc){
                return miniLeftMvt;
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackUp(){
        double miniUpMvt = 1000;
        boolean calc = false;
        for(int i=0; i < this.getJumpMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getWidth(),this.getAppropriateNode().getBoundsInParent().getHeight());
            rect.setX(this.getX());
            rect.setY(this.getY()-i);
            for(DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList){
                if((this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxY() >= 0) || (rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))){
                }
                if((!this.equals(detectableObject2))&&(rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))&&(this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxY() >= 0)/*&&(this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY() <= this.getJumpMouvementSpan())*/){ //a voir pour le -i
                    calc = true;
                    detectableObject2.interaction(this);

                    multiInteractions(detectableObject2);

                    if(detectableObject2.upMvt(this) < miniUpMvt){
                        miniUpMvt = detectableObject2.upMvt(this);
                    }
                }
            }
            if (calc){
                return miniUpMvt;
            }
        }
        return this.getJumpMouvementSpan();
    }

    private double verifTrackDown(){
        double miniDownMvt = 1000;
        boolean calc = false;
        for(int i=0; i < this.getFallMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX()-this.getAppropriateNode().getBoundsInParent().getMinX(),this.getAppropriateNode().getBoundsInParent().getMaxY()-this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY()+i);

            for(DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList){

                if((!this.equals(detectableObject2))&&(rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))/*&&((collideObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY()) >= 0)*/){
                    calc = true;
                    detectableObject2.interaction(this);
                    multiInteractions(detectableObject2);

                    if(detectableObject2.downMvt(this) < miniDownMvt){
                        miniDownMvt = detectableObject2.downMvt(this);
                    }
                }
            }
            if (calc){
                return miniDownMvt;
            }
        }
        return this.getFallMouvementSpan();
    }

    private void multiInteractions(DetectableObject detectableObject){
        for(ObjectLinker objectLinker : Map.objectLinkers){
            System.out.println("Object to be equals = " + detectableObject);
            if(objectLinker.getCollideObject1().equals(detectableObject)){
                objectLinker.detectableObject2.interaction(this);
                System.out.println("Object1 = " + objectLinker.getCollideObject1());

            }
            else if(objectLinker.getCollideObject2().equals(detectableObject)){
                objectLinker.detectableObject1.interaction(this);
                System.out.println("Object2 = " + objectLinker.getCollideObject2());

            }
            else{
                //print eror if you want
            }
        }
    }


    //TODO Forsee if the the movement cause an interaction
    //TODO généraliser au CollideObjects
    //TODO arranger les clones les getter et setters de coord
}