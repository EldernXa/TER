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
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

import mainTER.Tools.Coordinate;
import mainTER.exception.CharacterImageFileDoesntExist;

public class Character {
    private final String name;
    private final Coordinate initialCoordinate;
    private final ArrayList<ArrayList<ImageView>> listOfPictureOfTheCharacter;
    private final int speed;
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Character(String name, Coordinate coordinate) {
        this.name = name;
        this.initialCoordinate = coordinate;
        listOfPictureOfTheCharacter = new ArrayList<>();
        this.speed = 10;
        initListAnimate();
    }

    private void initListAnimate(){
        for(int i=0; i<Position.values().length; i++){
            listOfPictureOfTheCharacter.add(new ArrayList<>());
        }
        try{
            for(Position pos : Position.values()){
                URL urlCharacter = this.getClass().getResource("/mainTER/CharacterGameplay/images/"+name);
                if(urlCharacter==null){
                    throw new CharacterImageFileDoesntExist(name);
                }
                // TODO create personalized exception
                URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/" + name + "/" + pos.toString().toLowerCase().replace("_", ""));
                File file = Paths.get(url.toURI()).toFile();
                if(file.exists() && file.isDirectory()){
                    for(File fileForOneSprite : Objects.requireNonNull(file.listFiles())){
                        listOfPictureOfTheCharacter.get(pos.ordinal()).add(new ImageView(fileForOneSprite.toURI().toString()));
                    }
                }
            }
        }catch(URISyntaxException uriSyntaxException){
            uriSyntaxException.printStackTrace();
        }catch(CharacterImageFileDoesntExist characterImageFileDoesntExist){
            LOGGER.info("Le dossier du personnage n'existe pas !");
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
