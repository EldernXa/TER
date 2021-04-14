package mainTER.MapPackage;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mainTER.Tools.Coordinate;

public class MapFieldRectangle extends MapFieldForm {

    private Rectangle rectangle;


    public MapFieldRectangle(Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);
        this.rectangle = new Rectangle(coordinate.getX(),coordinate.getY(),width,height);
        this.rectangle.setFill(Color.BLACK);

        this.rectangle.setX(coordinate.getX());
        this.rectangle.setY(coordinate.getY());
        System.out.println("x : "+rectangle.getX() + " y : " +rectangle.getY()  +" w :"+rectangle.getWidth() +" h:"+rectangle.getHeight());
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}