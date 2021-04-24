package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

public abstract class MapFieldForm extends CollideObject {

    private Coordinate coordinate; // repetition des coordinates dans les classe filles
    private double width;
    private double height;



    public MapFieldForm(Coordinate coordinate, double width, double height) {
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;

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
