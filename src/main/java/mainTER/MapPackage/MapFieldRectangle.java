package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mainTER.Tools.Coordinate;

public class MapFieldRectangle extends MapFieldForm {

    private Rectangle rectangle;
    private Coordinate coordinate;
    private double width;
    private double height;


    public MapFieldRectangle(Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;

        this.rectangle = new Rectangle(coordinate.getX(),coordinate.getY(),width,height);
        this.rectangle.setFill(Color.BLACK);
        this.rectangle.setX(coordinate.getX());
        this.rectangle.setY(coordinate.getY());

    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public Node getAppropriateNode() {
        return rectangle;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getX(){
        return this.getCoordinate().getX();
    }

    public double getY(){
        return this.getCoordinate().getY();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Node clone() {
       return new Rectangle(this.rectangle.getWidth(),this.rectangle.getHeight(),this.rectangle.getFill());
    }

    @Override
    public double getVMouvementSpan() {
        return 0;
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
