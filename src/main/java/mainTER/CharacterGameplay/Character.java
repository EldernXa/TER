package mainTER.CharacterGameplay;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;

public class Character {
    private String name;
    private Coordinate initialCoordinate;
    private AnimationCharacter Animation;
    private ArrayList<ArrayList<ImageView>> listOfPictureOfTheCharacter;

    public Character(String name, Coordinate coordinate, AnimationCharacter animation) {
        this.name = name;
        this.initialCoordinate = coordinate;
        listOfPictureOfTheCharacter = new ArrayList<>();
        try {
            URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/Paladin");
            File file2 = Paths.get(url.toURI()).toFile();
            if(file2.exists() && file2.isDirectory()) {
                // TODO modify for the multiple direction of animation.
                listOfPictureOfTheCharacter.add(new ArrayList<>());
                for (File fileForOneSprite : Objects.requireNonNull(file2.listFiles())) {
                    listOfPictureOfTheCharacter.get(0).add(new ImageView(fileForOneSprite.toURI().toString()));
                }
            }
        }catch(URISyntaxException ioException){
            ioException.printStackTrace();
        }
        Animation = animation;
    }


    public String getName() {
        return name;
    }

    public ArrayList<ArrayList<ImageView>> getListOfPictureOfTheCharacter(){
        return listOfPictureOfTheCharacter;
    }


    public Coordinate getInitialCoordinate() {
        return initialCoordinate;
    }

    public void setInitialCoordinate(Coordinate initialCoordinate) {
        this.initialCoordinate = initialCoordinate;
    }

    public AnimationCharacter getAnimation() {
        return Animation;
    }

    public void setAnimation(AnimationCharacter animation) {
        Animation = animation;
    }
}
