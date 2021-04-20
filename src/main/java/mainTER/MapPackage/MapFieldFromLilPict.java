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
    private Coordinate coordinate;
    private double width;
    private double height;


    public MapFieldFromLilPict(String path, Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);
        image = new Image(new File(path).toURI().toString());
        this.path = path;
        pane = new Pane();
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;


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
        return path;
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
    public Node getAppropriateNode() {
        return pane;
    }

    @Override
    public CollideObject clone() {
        MapFieldFromLilPict mapFieldFromLilPict = new MapFieldFromLilPict(this.getPath(),new Coordinate(getX(),getY()),this.getWidth(),getHeight());
        return mapFieldFromLilPict;
    }

}
