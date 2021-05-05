package mainTER.MapPackage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Create a MapFieldForm using the same picture cloned several times in a rectangle area
 */
public class MapFieldFromLilPict extends MapFieldForm {


    private final String path;
    private final Pane pane;
    private String pathName;
    private String spriteName;

    /**
     *
     * @param spriteName Name of the picture you want to use
     * @param coordinate Coordinate where you want to put it
     * @param width Width
     * @param height Height
     */
    public MapFieldFromLilPict(String pathName, String spriteName, Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);
        this.pathName = pathName;
        this.spriteName = spriteName;
        this.path = "./src/main/resources/mainTER/MapPackage/Sprites/Front/" + pathName+ "/" + spriteName + ".png";
        Image image = new Image(new File(this.path).toURI().toString());
        pane = new Pane();
        pane.setLayoutX(coordinate.getX());
        pane.setLayoutY(coordinate.getY());
        pane.setPrefSize(width+1,height);
        pane.setBackground(new Background((new BackgroundImage(image, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,BackgroundPosition.CENTER, new BackgroundSize(image.getWidth(),
                image.getHeight(),false,false,false,false)))));
    }

    public Pane getPane() {
        return pane;
    }

    public String getPath() {
        return this.path;
    }

    public String getPathName() {
        return pathName;
    }

    public String getSpriteName() {
        return spriteName;
    }

    /**
     *
     * @return Width
     */
    @Override
    public double getWidth() {
        return super.getWidth();
    }

    /**
     * Set the rectange width
     * @param width Width
     */
    @Override
    public void setWidth(double width) {
        super.setWidth(width);
    }

    /**
     *
     * @return Height
     */
    @Override
    public double getHeight() {
        return super.getHeight();
    }

    /**
     * Set the rectange height
     * @param height Height
     */
    @Override
    public void setHeight(double height) {
        super.setHeight(height);
    }

    /**
     * Get the class Node
     * @return Pane
     */
    @Override
    public Node getAppropriateNode() {
        return pane;
    }

    /**
     * Clone the pane
     * @return Pane
     */
    @Override
    public CollideObject clone() {
        return new MapFieldFromLilPict(this.getPathName(),this.getSpriteName(),new Coordinate(this.getX(),this.getY()),this.getWidth(),this.getHeight());
    }

    @Override
    public double getJumpMouvementSpan() {
        return super.getJumpMouvementSpan();
    }

    @Override
    public double getFallMouvementSpan() {
        return super.getFallMouvementSpan();
    }

    /**
     * Set the rectangle coordinates
     * @param coordinate rectangle coordinates
     */
    @Override
    public void setCoordinate(Coordinate coordinate) {
        setX(coordinate.getX());
        setY(coordinate.getY());
    }

    /**
     * Set the X from coordinate
     * @param x x coordinate
     */
    @Override
    public void setX(double x) {
        this.getCoordinate().setX(x);
        pane.setLayoutX(getX());
    }
    /**
     * Set the y from coordinate
     * @param y y coordinate
     */
    @Override
    public void setY(double y) {
        this.getCoordinate().setY(y);
        pane.setLayoutY(getY());
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

}
