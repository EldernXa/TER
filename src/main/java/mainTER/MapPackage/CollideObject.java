package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import mainTER.Tools.Coordinate;

public abstract class CollideObject extends DetectableObject{

    /**
     * Get the Node that corresponds to the appearance of the object
     * @return
     */
    public abstract Node getAppropriateNode();

    /**
     * Create a new Node that corresponds to the appearance of the object
     * @return Node
     */
    public abstract CollideObject clone();

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
        for(int i=0; i < this.getHMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX()+i);

            for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
                System.out.println(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()));
                if((!this.equals(collideObject2)) &&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent())) &&(collideObject2.getAppropriateNode().getBoundsInParent().getMinX()-this.getAppropriateNode().getBoundsInParent().getMaxX() >= 0) &&(collideObject2.getAppropriateNode().getBoundsInParent().getMinX()-this.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan()))
                {//Prob it considers that it's true at the begening
                    System.out.println("Je suis en collision avec : " + collideObject2);
                    System.out.println("X = " + collideObject2.getX());
                    System.out.println("Y = " + collideObject2.getY());
                    collideObject2.interaction(this);
                    for(ObjectLinker objectLinker : Map.objectLinkers) {//TODO faire en sort que ce soit une interaction qui se déclenche et pas seulement des coordonnées qui se modifient
                        if (objectLinker.getCollideObject1().equals(collideObject2)) {
                            objectLinker.collideObject2.setX(collideObject2.getX());
                            objectLinker.collideObject2.setY(collideObject2.getY());

                        } else if (objectLinker.getCollideObject2().equals(collideObject2)) {
                            objectLinker.collideObject1.setX(collideObject2.getX());
                            objectLinker.collideObject1.setY(collideObject2.getY());
                        } else {
//                            System.out.println(collideObject2);
//                            System.out.println("Cet Objet nexiste pas");
                        }
                    }

                    /*System.out.println("minX " + collideObject2.getAppropriateNode().getBoundsInParent().getMinX());
                    System.out.println("Droit serpent " + this.getAppropriateNode().getBoundsInParent().getMaxX());
                    System.out.println("maxX " + collideObject2.getAppropriateNode().getBoundsInParent().getMaxX());
                    System.out.println("minY " + collideObject2.getAppropriateNode().getBoundsInParent().getMinY());
                    System.out.println("maxY " + collideObject2.getAppropriateNode().getBoundsInParent().getMaxY());
                    System.out.println("Right space = " + (collideObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX()));*/
                    return collideObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();
                }
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackLeft(){
        for(int i=0; i < this.getHMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX()-i);
            for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
                if((!this.equals(collideObject2))&&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()))&&(this.getAppropriateNode().getBoundsInParent().getMinX()-collideObject2.getAppropriateNode().getBoundsInParent().getMaxX() >= 0)&&(this.getAppropriateNode().getBoundsInParent().getMinX()-collideObject2.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan())){
                    collideObject2.interaction(this);

                    for(ObjectLinker objectLinker : Map.objectLinkers){
                        if(objectLinker.getCollideObject1().equals(collideObject2)){
                            objectLinker.collideObject2.setX(collideObject2.getX());
                            objectLinker.collideObject2.setY(collideObject2.getY());

                        }
                        else if(objectLinker.getCollideObject2().equals(collideObject2)){
                            objectLinker.collideObject1.setX(collideObject2.getX());
                            objectLinker.collideObject1.setY(collideObject2.getY());
                        }

                        else{
//                            System.out.println(collideObject2);
//                            System.out.println("Cet Objet nexiste pas");
                        }
                    }
//                    System.out.println("Left space = " + (this.getAppropriateNode().getBoundsInParent().getMinX() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxX()));
                    return this.getAppropriateNode().getBoundsInParent().getMinX() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxX();
                }
            }
        }
        return this.getHMouvementSpan();
    }

    private double verifTrackUp(){
        for(int i=0; i < this.getJumpMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setY(rect.getY()-i);
            for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
                if (rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent())){
//                    System.out.println(collideObject2);
//                    System.out.println("Perso Y : " + this.getAppropriateNode().getBoundsInParent().getMinY());
//                    System.out.println("Platform Y : " + collideObject2.getAppropriateNode().getBoundsInParent().getMaxY());
                }
//                System.out.println("On rencontre un objet = " + (rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent())));
                if((!this.equals(collideObject2))&&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()))&&(this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY() >= 0)){
                    collideObject2.interaction(this);

                    for(ObjectLinker objectLinker : Map.objectLinkers){
                        if(objectLinker.getCollideObject1().equals(collideObject2)){
                            objectLinker.collideObject2.setX(collideObject2.getX());
                            objectLinker.collideObject2.setY(collideObject2.getY());

                        }
                        else if(objectLinker.getCollideObject2().equals(collideObject2)){
                            objectLinker.collideObject1.setX(collideObject2.getX());
                            objectLinker.collideObject1.setY(collideObject2.getY());
                        }

                        else{
//                            System.out.println(collideObject2);
//                            System.out.println("Cet Objet nexiste pas");
                        }
                    }

                    return this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY();
                }
            }
        }
        return this.getJumpMouvementSpan();
    }

    private double verifTrackDown(){
        for(int i=0; i < this.getFallMouvementSpan(); i++){
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInLocal().getMaxX()-this.getAppropriateNode().getBoundsInLocal().getMinX(),this.getAppropriateNode().getBoundsInLocal().getMaxY()-this.getAppropriateNode().getBoundsInLocal().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY()+i);
            for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
                if((!this.equals(collideObject2))&&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()))&&(collideObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY() >= 0)){
                    collideObject2.interaction(this);

                    for(ObjectLinker objectLinker : Map.objectLinkers){
                        if(objectLinker.getCollideObject1().equals(collideObject2)){
                            objectLinker.collideObject2.setX(collideObject2.getX());
                            objectLinker.collideObject2.setY(collideObject2.getY());

                        }
                        else if(objectLinker.getCollideObject2().equals(collideObject2)){
                            objectLinker.collideObject1.setX(collideObject2.getX());
                            objectLinker.collideObject1.setY(collideObject2.getY());
                        }

                        else{
//                            System.out.println(collideObject2);
//                            System.out.println("Cet Objet nexiste pas");
                        }
                    }

                    return (collideObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY());
                }
            }
        }
        return this.getFallMouvementSpan();
    }


    //TODO Forsee if the the movement cause an interaction
    //TODO généraliser au CollideObjects
    //TODO arranger les clones les getter et setters de coord
}