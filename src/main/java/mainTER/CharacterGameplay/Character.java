package mainTER.CharacterGameplay;

import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mainTER.Tools.Coordinate;
import mainTER.exception.CharacterImageFileDoesntExist;
import mainTER.exception.PositionDirectoryDoesntExist;

public class Character {
    private final String name;
    private final Coordinate initialCoordinate;
    private final ArrayList<ArrayList<ImageView>> listOfPictureOfTheCharacter;
    private final int speed;
    private final int weight;
    private final int jumpStrength;

    // TODO Put error on graphics interface.
    // TODO get characteristics from name in database.

    public Character(String name, Coordinate coordinate) {
        this.name = name;
        this.initialCoordinate = coordinate;
        listOfPictureOfTheCharacter = new ArrayList<>();
        this.speed = 10;
        this.weight = 5;
        this.jumpStrength = 20;
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
                final String replace = pos.toString().toLowerCase().replace("_", "");
                URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/" + name + "/" + replace);
                File file = Paths.get(url.toURI()).toFile();
                if(file.exists() && file.isDirectory()){
                    for(File fileForOneSprite : Objects.requireNonNull(file.listFiles())){
                        listOfPictureOfTheCharacter.get(pos.ordinal()).add(new ImageView(fileForOneSprite.toURI().toString()));
                    }
                }
                else{
                    throw new PositionDirectoryDoesntExist(replace);
                }
            }
        }catch(URISyntaxException uriSyntaxException){
            uriSyntaxException.printStackTrace();
        }catch(CharacterImageFileDoesntExist characterImageFileDoesntExist){
            System.out.println("Le dossier du personnage n'existe pas !");
        }catch(PositionDirectoryDoesntExist positionDirectoryDoesntExist){
            System.out.println(positionDirectoryDoesntExist.getMessage());
        }
    }

    public int getSpeed(){
        return speed;
    }

    public String getName() {
        return name;
    }

    public List<ArrayList<ImageView>> getListOfPictureOfTheCharacter(){
        return listOfPictureOfTheCharacter;
    }


    public Coordinate getInitialCoordinate() {
        return initialCoordinate;
    }

    public int getWeight(){
        return weight;
    }

    public int getJumpStrength(){
        return jumpStrength;
    }


}
