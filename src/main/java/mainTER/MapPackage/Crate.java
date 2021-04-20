package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.util.EventListener;

public class Crate extends InteractiveObject {

    public Crate(Coordinate coordinate ) {
        super(coordinate, new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/crate.png",coordinate));
    }

    public void interaction(DisplayCharacter displayCharacter) {
        if(displayCharacter.getCurrentCoordinateOfTheCharacter().getX() < super.getCoordinate().getX()){
            super.setCoordinate(new Coordinate(super.getCoordinate().getX()+displayCharacter.getCharacter().getSpeed(),super.getCoordinate().getY()));
        }
        else{
            super.setCoordinate(new Coordinate(super.getCoordinate().getX()-displayCharacter.getCharacter().getSpeed(),super.getCoordinate().getY()));
        }
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    @Override
    public CollideObject clone() {
        return null;
    }
}
