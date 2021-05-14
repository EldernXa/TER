package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import mainTER.Tools.Coordinate;

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

    public double rightMvt(DetectableObject detectableObject){
        return 0;
    }

    public double leftMvt(DetectableObject detectableObject){
        return 0;
    }

    public double upMvt(DetectableObject detectableObject){
        return 0;
    }

    public double downMvt(DetectableObject detectableObject){
        return 0;
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
        for(int i=0; i < this.getHMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX()-this.getAppropriateNode().getBoundsInParent().getMinX(),this.getAppropriateNode().getBoundsInParent().getMaxY()-this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX()+i);

            for(DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList){
//                System.out.println(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()));
                if((!this.equals(detectableObject2)) &&(rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent())) &&(detectableObject2.getAppropriateNode().getBoundsInParent().getMinX()-this.getAppropriateNode().getBoundsInParent().getMaxX() >= 0) &&(detectableObject2.getAppropriateNode().getBoundsInParent().getMinX()-this.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan()))
                {//Prob it considers that it's true at the begening
//                    System.out.println("Je suis en collision avec : " + collideObject2);
//                    System.out.println("X = " + collideObject2.getX());
//                    System.out.println("Y = " + collideObject2.getY());
//                    System.out.println("Coll MinX " + collideObject2.getAppropriateNode().getBoundsInParent().getMinX());
//                    System.out.println("this X" + this.getAppropriateNode().getBoundsInParent().getMaxX());
                    detectableObject2.interaction(this);

                    multiIntersections(detectableObject2);

                    /*System.out.println("minX " + collideObject2.getAppropriateNode().getBoundsInParent().getMinX());
                    System.out.println("Droit serpent " + this.getAppropriateNode().getBoundsInParent().getMaxX());
                    System.out.println("maxX " + collideObject2.getAppropriateNode().getBoundsInParent().getMaxX());
                    System.out.println("minY " + collideObject2.getAppropriateNode().getBoundsInParent().getMinY());
                    System.out.println("maxY " + collideObject2.getAppropriateNode().getBoundsInParent().getMaxY());
                    System.out.println("Right space = " + (collideObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX()));*/
                    return detectableObject2.rightMvt(this);//detectableObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();
                }
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackLeft(){
        for(int i=0; i < this.getHMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX()-this.getAppropriateNode().getBoundsInParent().getMinX(),this.getAppropriateNode().getBoundsInParent().getMaxY()-this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX()-i);
            for(DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList){
                if((!this.equals(detectableObject2))&&(rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))&&(this.getAppropriateNode().getBoundsInParent().getMinX()- detectableObject2.getAppropriateNode().getBoundsInParent().getMaxX() >= 0)&&(this.getAppropriateNode().getBoundsInParent().getMinX()- detectableObject2.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan())){
                    detectableObject2.interaction(this);
//                    System.out.println("Collision avec : " + detectableObject2);

                    multiIntersections(detectableObject2);

//                    System.out.println("Left space = " + (this.getAppropriateNode().getBoundsInParent().getMinX() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxX()));
                    return detectableObject2.leftMvt(this);//this.getAppropriateNode().getBoundsInParent().getMinX() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxX();
                }
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackUp(){
//        System.out.println("Hauteur de saut = " + this.getJumpMouvementSpan());
        for(int i=0; i < this.getJumpMouvementSpan(); i++){
//            System.out.println(i);
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getWidth(),this.getAppropriateNode().getBoundsInParent().getHeight());
            rect.setX(this.getX());
            rect.setY(this.getY()-i);
            for(DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList){
                if((this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxY() >= 0) || (rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))){
//                    System.out.println("Min Y " + this.getAppropriateNode().getBoundsInParent().getMinY());
//                    System.out.println("Max Y " + collideObject2.getAppropriateNode().getBoundsInParent().getMaxY());
//                    System.out.println("Intersection ? = " + (rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent())) + " " + collideObject2);
//                    System.out.println("Au dessus ? " + (this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY() >= 0));
//                    System.out.println("Différence ? = " + (this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY()) + "\n");
                }
                if((!this.equals(detectableObject2))&&(rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))&&(this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxY() >= 0)/*&&(this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY() <= this.getJumpMouvementSpan())*/){ //a voir pour le -i
//                    System.out.println("je suis en collision avec" + collideObject2);
                    detectableObject2.interaction(this);

                    multiIntersections(detectableObject2);

//                    System.out.println("Hauteur de saut calculée = " + (this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY()));
                    return detectableObject2.upMvt(this);//this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxY());
                }
            }
        }
//        System.out.println("\n passe par là \n");
        return this.getJumpMouvementSpan();
    }

    private double verifTrackDown(){
        for(int i=0; i < this.getFallMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX()-this.getAppropriateNode().getBoundsInParent().getMinX(),this.getAppropriateNode().getBoundsInParent().getMaxY()-this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY()+i);

//            System.out.println("Taille de la liste d'objets " + MapFileReader.collideObjectArrayList.size());
            for(DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList){
//                System.out.println("Contact avec " + collideObject2 + " = " + rect.getBoundsInParent().intersects(collideObject2.getAppropriateNode().getBoundsInParent())); //TODO je comprends pas pourquoi ej saute pas et il consdère le saut

                if((!this.equals(detectableObject2))&&(rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))/*&&((collideObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY()) >= 0)*/){
                    detectableObject2.interaction(this);

                    multiIntersections(detectableObject2);

                    return detectableObject2.downMvt(this);//detectableObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY();
                }
            }
        }
        return this.getFallMouvementSpan();
    }

    private void multiIntersections(DetectableObject detectableObject){
        for(ObjectLinker objectLinker : Map.objectLinkers){
            if(objectLinker.getCollideObject1().equals(detectableObject)){
                objectLinker.detectableObject2.setX(detectableObject.getX());
                objectLinker.detectableObject2.setY(detectableObject.getY());


            }
            else if(objectLinker.getCollideObject2().equals(detectableObject)){
                objectLinker.detectableObject1.setX(detectableObject.getX());
                objectLinker.detectableObject1.setY(detectableObject.getY());
            }

            else{
//                            System.out.println(collideObject2);
//                            System.out.println("Cet Objet nexiste pas");
            }
        }

    }


    //TODO Forsee if the the movement cause an interaction
    //TODO généraliser au CollideObjects
    //TODO arranger les clones les getter et setters de coord
}