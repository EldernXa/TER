package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import mainTER.CharacterGameplay.DisplayCharacter;
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

    public abstract double getVMouvementSpan();

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
            rect.setX(rect.getX()+this.getHMouvementSpan());
        }
        if(commingFrom == CommingFrom.RIGHT){
            rect.setX(rect.getX()-this.getHMouvementSpan());
        }

        for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
            if((!this.equals(collideObject2))&&(!(collideObject2 instanceof MapFieldForm))&&(rect.intersects(collideObject2.getAppropriateNode().getBoundsInParent()))){
                if (commingFrom == CommingFrom.LEFT) {
                    collideObject2.interaction(this);
                    return collideObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();
                }
                if(commingFrom == CommingFrom.RIGHT){
                    collideObject2.interaction(this);
                    return this.getAppropriateNode().getBoundsInParent().getMinX() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxX();

                }
            }
        }
        return this.getHMouvementSpan();
    }
    //TODO Forsee if the the movement cause an interaction
    //TODO généraliser au CollideObjects
    //TODO arranger les clones les getter et setters de coord
}
