package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

public abstract class MapFieldForm extends DetectableObject {

    private Coordinate coordinate; // repetition des coordinates dans les classe filles
    private double width;
    private double height;



    public MapFieldForm(Coordinate coordinate, double width, double height) {
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;

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
        return this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    public abstract Node getAppropriateNode();

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public double getX() {
        return this.coordinate.getX();
    }

    @Override
    public void setX(double x) {
        this.coordinate.setX(x);
    }

    @Override
    public double getY() {
        return this.coordinate.getY();
    }

    @Override
    public void setY(double y) {
        this.coordinate.setY(y);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
