package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

public class UnCollideObject extends DetectableObject {
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
        System.out.println("Here");
        return detectableObject.getHMouvementSpan();
    }

    @Override
    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getHMouvementSpan();
    }

    @Override
    public double upMvt(DetectableObject detectableObject) {
        return detectableObject.getJumpMouvementSpan();
    }

    @Override
    public double downMvt(DetectableObject detectableObject) {
        return detectableObject.getFallMouvementSpan();
    }
}
