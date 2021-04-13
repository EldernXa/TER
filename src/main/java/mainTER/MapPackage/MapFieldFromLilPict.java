package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainTER.CharacterGameplay.Coordinate;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

public class MapFieldFromLilPict extends MapFieldForm {
    private ImageView imageView;
    private String path;



    public MapFieldFromLilPict(String path, Coordinate coordinate, double width, double height) {

        super(coordinate, width, height);
        imageView = new ImageView(new Image(new File(path).toURI().toString())); // A améliorer

    }



    public ImageView getImageView() {
        return imageView;
    }




}
