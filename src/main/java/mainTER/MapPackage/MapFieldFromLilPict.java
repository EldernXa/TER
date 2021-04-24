package mainTER.MapPackage;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

import javafx.scene.paint.Color;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.io.File;

public class MapFieldFromLilPict extends MapFieldForm {


    private Image image;
    private String path;
    private Pane pane;


    public MapFieldFromLilPict(String spriteName, Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);
        this.path = "./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName + ".png";
        image = new Image(new File(this.path).toURI().toString());
        pane = new Pane();


        for (double i = coordinate.getX(); i < (coordinate.getX() + width); i += image.getWidth()) {
            for (double j = coordinate.getY(); j < (coordinate.getY() + height); j += image.getHeight()) {

                ImageViewSizePos imageViewSizePos = new ImageViewSizePos(path, new Coordinate(i, j));

                if(i + image.getWidth() > coordinate.getX() +width){

                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageViewSizePos.getImageView().getImage(), null);
                    BufferedImage dest = bufferedImage.getSubimage(0, 0, (int)(coordinate.getX() + width - i),(int)image.getHeight());
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

    public double getX(){
        return this.getCoordinate().getX();
    }

    public double getY(){
        return this.getCoordinate().getY();
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
        return pane;
    }

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

    @Override
    public void setCoordinate(Coordinate coordinate) {
        super.setCoordinate(coordinate);
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

}
