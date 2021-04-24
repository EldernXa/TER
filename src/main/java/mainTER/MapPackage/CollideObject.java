package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    /**
     * Simulate the appropriate interaction between two objects
     * @param collideObject
     */
    public void interaction(CollideObject collideObject){
    }

    public boolean verify(CommingFrom commingFrom){ //C'est pas bon il faut adapter la méthode comme plus bas
        if (commingFrom == CommingFrom.LEFT) {
            this.getAppropriateNode().setTranslateX(-this.getHMouvementSpan());
        }
        if(commingFrom == CommingFrom.RIGHT){
            this.getAppropriateNode().setTranslateX(+this.getHMouvementSpan());
        }
        for(CollideObject collideObject2 : MapFileReader.collideObjectArrayList){
            if((!this.equals(collideObject2))&&(!(collideObject2 instanceof MapFieldForm))&&(this.getAppropriateNode().intersects(collideObject2.getAppropriateNode().getBoundsInParent()))){
                return false;
            }
        }
        return true;
    }
    //TODO Forsee if the the movement cause an interaction
    //TODO généraliser au CollideObjects
    //TODO arranger les clones les getter et setters de coord
}
