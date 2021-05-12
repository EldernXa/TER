package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class DeathObject extends DetectableObject {

    String pathName;
    Coordinate coordinate;
    ImageViewSizePos imageViewSizePos;

    public DeathObject(String name, Coordinate coordinate) {
        this.pathName = name;
        this.coordinate = coordinate;
        imageViewSizePos = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/"+name+".png",coordinate);


    }


    @Override
    public void interaction(DetectableObject detectableObject) {
        ((DisplayCharacter) detectableObject).death();
    }

    @Override
    public Node getAppropriateNode() {
        return imageViewSizePos.getImageView();
    }

    @Override
    public DetectableObject clone() {
        return new DeathObject(pathName,coordinate);
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;

    }

    @Override
    public double getX() {
        return coordinate.getX();
    }

    @Override
    public double getY() {
        return coordinate.getY();
    }

    @Override
    public void setX(double x) {
        coordinate.setX(x);

    }

    @Override
    public void setY(double y) {
            coordinate.setY(y);
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }
}
