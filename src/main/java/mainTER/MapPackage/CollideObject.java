package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import mainTER.Tools.Coordinate;

public abstract class CollideObject {

    /**
     * Get the Node that corresponds to the appearance of the object
     * @return
     */
    public abstract Node getAppropriateNode();

    /**
     * Create a new Node that corresponds to the appearance of the object
     * @return Node
     */
    public abstract Node clone();

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


    /**
     * Simulate the appropriate interaction between two objects
     * @param collideObject
     */
    public void interaction(CollideObject collideObject){
    }

    public double calcMvt(CommingFrom commingFrom){

        Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
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
        for(int i =0; i < this.getHMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX()+i);
            for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
                if((!this.equals(collideObject2))&&(!(collideObject2 instanceof MapFieldForm))&&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()))){
                    System.out.println("Collide with " + collideObject2);
                    System.out.println("Je vais a droite de : " + (collideObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX()));
                    System.out.println("MinX " + this.getAppropriateNode().getBoundsInParent().getMinX());
                    System.out.println("MinY " + this.getAppropriateNode().getBoundsInParent().getMinY());
                    System.out.println("MaxX " + this.getAppropriateNode().getBoundsInParent().getMaxX());
                    System.out.println("MaxY " + this.getAppropriateNode().getBoundsInParent().getMaxY());
                    System.out.println("Width " + this.getAppropriateNode().getBoundsInParent().getWidth());
                    System.out.println("Height " + this.getAppropriateNode().getBoundsInParent().getHeight());
//                    collideObject2.interaction(this);
                    return collideObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();
                }
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackLeft(){
        for(int i =0; i < this.getHMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX()-i);
            for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
                if((!this.equals(collideObject2))&&(!(collideObject2 instanceof MapFieldForm))&&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()))){
                    System.out.println("Collide with " + collideObject2);
                    System.out.println("Je vais a gauche de : " + (this.getAppropriateNode().getBoundsInParent().getMinX() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxX()));
//                    collideObject2.interaction(this);
                    return this.getAppropriateNode().getBoundsInParent().getMinX() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxX();
                }
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackUp(){
        for(int i =0; i < this.getJumpMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setY(rect.getY()-i);
            for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
                if((!this.equals(collideObject2))&&(!(collideObject2 instanceof MapFieldForm))&&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()))){
                    System.out.println("Collide with " + collideObject2);
                    System.out.println("Je monte de : " + (this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY()));
//                    collideObject2.interaction(this);
                    return this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY();
                }
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackDown(){
        for(int i =0; i < this.getFallMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setY(rect.getY()+i);
            for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
                if((!this.equals( collideObject2))&&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()))){
//                    System.out.println("Collide with " + collideObject2);
                    System.out.println("Je descend de : " + (collideObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY()));

//                    collideObject2.interaction(this);
                    return collideObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY();
                }
            }
        }
        return this.getHMouvementSpan();
    }
    //TODO Forsee if the the movement cause an interaction
    //TODO généraliser au CollideObjects
    //TODO arranger les clones les getter et setters de coord
}
