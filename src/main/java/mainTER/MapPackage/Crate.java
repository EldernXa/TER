package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

/**
 * Class representing a Crate, it position, it appearance, it interaction and lot more
 */
public class Crate extends InteractiveObject {

    /**
     * Class constructor
     * @param coordinate
     */

    String name;
    public Crate(Coordinate coordinate, String name ) {
        super(coordinate, new ImageViewSizePos("/mainTER/MapPackage/Objects/" + name + ".png",coordinate));
        this.name = name;
    }

    /**
     * Specifying the way the object react to a collide with an other DetectableObject
     * @param detectableObject
     */
    public void interaction(DetectableObject detectableObject) {
        if(detectableObject.getY() + detectableObject.getHeight() >= super.getY() + super.getHeight()*2/5) {
            if (detectableObject.getCoordinate().getX() < super.getCoordinate().getX()) {
                    super.setCoordinate(new Coordinate(super.getCoordinate().getX() + calcMvt(CommingFrom.LEFT), super.getCoordinate().getY()));
            } else {
                    super.setCoordinate(new Coordinate(super.getCoordinate().getX() - calcMvt(CommingFrom.RIGHT), super.getCoordinate().getY()));
            }
        }
    }

    /**
     * Return the appropriate node for a Crate (the imageView corresponding to it appearance)
     * @return
     */
    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    /**
     * Return a clone of the Crate that is totally identical but doesn't occupy the same memory location
     * @return
     */
    @Override
    public Crate clone() {
        return new Crate(new Coordinate(super.getBaseCoordinate().getX(),super.getBaseCoordinate().getY()),name);
    }

    /**
     * Return the horizontal mouvement span for a Crate that is 10
     * @return
     */
    @Override
    public double getHMouvementSpan() {
        return 10;
    }

    /**
     * Return the appropriate jump mouvement span for a Crate that is 0
     * @return
     */
    @Override
    public double getJumpMouvementSpan() {
        return 0;
    }

    /**
     * Return the Crate coordinate
     * @return
     */
    @Override
    public Coordinate getCoordinate() {
        return super.getCoordinate();
    }

    /**
     * Set the Crate coordinate with the value @param
     * @param coordinate
     */
    @Override
    public void setCoordinate(Coordinate coordinate) {
        super.setCoordinate(coordinate);
    }
}
