package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

/**
 * Class that represente all the object that stop the player when they hit it
 */
public class CollideObject extends DetectableObject{


    @Override
    public Node getAppropriateNode() {
        return null;
    }

    @Override
    public DetectableObject clone() {
        return null;
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {

    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public void setX(double x) {

    }

    @Override
    public void setY(double y) {

    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    /**
     * Return the double corresponding to the right distance calculated between this as a CollideObject and the @param detectableObject
     * @param detectableObject
     * @return
     */
    @Override
    public double rightMvt(DetectableObject detectableObject) {
        return this.getAppropriateNode().getBoundsInParent().getMinX() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    /**
     * Return the double corresponding to the left distance calculated between this as a CollideObject and the @param detectableObject
     * @param detectableObject
     * @return
     */
    @Override
    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    /**
     * Return the double corresponding to the up distance calculated between this as a CollideObject and the @param detectableObject
     * @param detectableObject
     * @return
     */
    @Override
    public double upMvt(DetectableObject detectableObject) {

        return detectableObject.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    /**
     * Return the double corresponding to the down distance calculated between this as a CollideObject and the @param detectableObject
     * @param detectableObject
     * @return
     */
    @Override
    public double downMvt(DetectableObject detectableObject) {
        return this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxY();
    }
}
