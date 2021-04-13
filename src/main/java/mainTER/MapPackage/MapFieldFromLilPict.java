package mainTER.MapPackage;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.io.File;

public class MapFieldFromLilPict extends MapFieldForm {


    private Image image;
    private String path;
    private Pane pane;


    public MapFieldFromLilPict(String path, Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);
        pane = new Pane();
        this.path = path;
        image = new Image(new File(path).toURI().toString());

        double tempI = 0;
        double tempJ = 0;

        for (double i = coordinate.getX(); i < (coordinate.getX() + width); i += image.getWidth()) {
            for (double j = coordinate.getY(); j < (coordinate.getY() + height); j += image.getHeight()) {

                ImageViewSizePos imageViewSizePos = new ImageViewSizePos(path, new Coordinate(i, j));

                if(i + image.getWidth() > coordinate.getX() +width){
                    System.out.println("AAAAAAAAA");
                    System.out.println((coordinate.getX() + width) +" "+ i +" "+ image.getHeight());/*
                    imageViewSizePos.getImageView().setViewport(new Rectangle2D(i,j,coordinate.getX() + width - i,image.getHeight()));*/

                    /*PixelReader reader = imageViewSizePos.getImageView().getImage().getPixelReader();
                    WritableImage newImage = new WritableImage(reader, (int)i, (int)j, (int)(coordinate.getX() + width - i), (int)(image.getHeight()));*/

                }
                if(j + image.getHeight() > coordinate.getY() +height){
                    imageViewSizePos.getImageView().setViewport(new Rectangle2D(i,j,width,coordinate.getY() +height - j));
                }


                pane.getChildren().add(imageViewSizePos.getImageView());


            }

        }


    }

    public Pane getPane() {
        return pane;
    }
}
