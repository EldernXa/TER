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

    @Override
    public double rightMvt(DetectableObject detectableObject) {
        return this.getAppropriateNode().getBoundsInParent().getMinX() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    @Override
    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    @Override
    public double upMvt(DetectableObject detectableObject) {

        return detectableObject.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    @Override
    public double downMvt(DetectableObject detectableObject) {
        System.out.println("Technique calculée");
        return this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxY();
    }
}
