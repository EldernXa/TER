package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mainTER.Tools.Coordinate;

/**
 * Create a rectangle
 */
public class MapFieldRectangle extends MapFieldForm {

    private Rectangle rectangle;

    /**
     * Create a rectangle depending on the coordinate you choose, the width and the height
     * @param coordinate
     * @param width
     * @param height
     */
    public MapFieldRectangle(Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);

        this.rectangle = new Rectangle(coordinate.getX(),coordinate.getY(),width,height);
        this.rectangle.setFill(Color.BLACK);
        this.rectangle.setX(coordinate.getX());
        this.rectangle.setY(coordinate.getY());

    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public double getWidth() {
        return super.getWidth();
    }

    @Override
    public void setWidth(double width) {
        super.setWidth(width);
    }

    @Override
    public double getHeight() {
        return super.getHeight();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
    }

    @Override
    public Node getAppropriateNode() {
        return rectangle;
    }

    @Override
    public MapFieldRectangle clone() {
        return new MapFieldRectangle(new Coordinate(this.getX(),this.getY()),this.getWidth(),this.getHeight());
    }

    @Override
    public double getJumpMouvementSpan() {
        return 0;
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        setX(coordinate.getY());
        setY(coordinate.getY());
    }

    @Override
    public void setX(double x) {
        this.setCoordinate(new Coordinate(x, getY()));
        rectangle.setX(getX());
    }

    @Override
    public void setY(double y) {
        this.setCoordinate(new Coordinate(getX(), y));
        rectangle.setY(getY());
    }
}
