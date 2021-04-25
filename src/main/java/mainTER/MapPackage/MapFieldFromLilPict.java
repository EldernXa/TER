package mainTER.MapPackage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
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

    /**
     *
     * @param spriteName Name of the picture you want to use
     * @param coordinate Coordinate where you want to put it
     * @param width Width
     * @param height Height
     */
    public MapFieldFromLilPict(String spriteName, Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);
        this.path = "./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName + ".png";
        Image image = new Image(new File(this.path).toURI().toString());
        pane = new Pane();


        for (double i = coordinate.getX(); i < (coordinate.getX() + width); i += image.getWidth()) {
            for (double j = coordinate.getY(); j < (coordinate.getY() + height); j += image.getHeight()) {

                ImageViewSizePos imageViewSizePos = new ImageViewSizePos(path, new Coordinate(i, j));

                if(i + image.getWidth() > coordinate.getX() +width){

                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageViewSizePos.getImageView().getImage(), null);
                    BufferedImage dest = bufferedImage.getSubimage(0, 0, (int)(coordinate.getX() + width - i),(int) image.getHeight());
                    imageViewSizePos.getImageView().setImage(SwingFXUtils.toFXImage(dest, null));

                }
                if(j + image.getHeight() > coordinate.getY() +height){
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageViewSizePos.getImageView().getImage(), null);
                    BufferedImage dest = bufferedImage.getSubimage(0, 0, (int)(imageViewSizePos.getImageView().getImage().getWidth()),(int)(coordinate.getY() + height - j));
                    imageViewSizePos.getImageView().setImage(SwingFXUtils.toFXImage(dest, null));
                }
                pane.getChildren().add(imageViewSizePos.getImageView());

            }

        }


    }

    public Pane getPane() {
        return pane;
    }

    public String getPath() {
        return this.path;
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
    public Node clone() {
        Pane pane = new Pane();
        for(Node node : this.pane.getChildren()){
            pane.getChildren().add(node);
        }
        return pane;
    }

    @Override
    public double getVMouvementSpan() {
        return 0;
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
