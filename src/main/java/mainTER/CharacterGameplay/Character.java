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

import mainTER.Tools.Coordinate;

public class Character {
    private final String name;
    private final Coordinate initialCoordinate;
    private final ArrayList<ArrayList<ImageView>> listOfPictureOfTheCharacter;
    private final int speed;

    public Character(String name, Coordinate coordinate) {
        this.name = name;
        this.initialCoordinate = coordinate;
        listOfPictureOfTheCharacter = new ArrayList<>();
        this.speed = 10;
        try {
            URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/Paladin/Walk");
            File file2 = Paths.get(url.toURI()).toFile();
            if(file2.exists() && file2.isDirectory()) {
                // TODO modify for the multiple direction of animation.
                listOfPictureOfTheCharacter.add(new ArrayList<>());
                for (File fileForOneSprite : Objects.requireNonNull(file2.listFiles())) {
                    listOfPictureOfTheCharacter.get(0).add(new ImageView(fileForOneSprite.toURI().toString()));
                }
            }

            url = this.getClass().getResource("/mainTER/CharacterGameplay/images/Paladin/ReverseWalk");
            file2 = Paths.get(url.toURI()).toFile();
            if(file2.exists() && file2.isDirectory()){
                listOfPictureOfTheCharacter.add(new ArrayList<>());
                for (File fileForOneSprite : Objects.requireNonNull(file2.listFiles())) {
                    listOfPictureOfTheCharacter.get(1).add(new ImageView(fileForOneSprite.toURI().toString()));
                }
            }
        }catch(URISyntaxException ioException){
            ioException.printStackTrace();
        }
    }

    public int getSpeed(){
        return speed;
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


}
