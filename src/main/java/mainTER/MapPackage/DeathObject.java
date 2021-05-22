package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

/**
 * Create a DeathObject item that will be placed on the map.
 * It kill the player on contact.
 */
public class DeathObject extends InteractiveObject {

    String pathName;
    Coordinate coordinate;
    boolean exist;
    String name;

    public DeathObject(String name, Coordinate coordinate,boolean exist) {
        super(coordinate,new ImageViewSizePos("/mainTER/MapPackage/Objects/"+ name + ".png",coordinate));

        this.pathName = name;
        this.coordinate = coordinate;
        this.exist = exist;
        this.name = name;

        if(!exist){
            this.getImageView().setImage(null);
            super.setCoordinate(new Coordinate(-100,-100));

        }
    }

    @Override
    public void actionTriggered() {


        if(!exist){
            this.getImageView().setImage(new ImageViewSizePos("/mainTER/MapPackage/Objects/"+ name +".png", getCoordinate()).getImageView().getImage());

            super.setCoordinate(coordinate);
            this.exist = true;
        }
    }

    /**
     * Return true if DeathObject is visible, false if invisible
     * @return
     */
    public boolean isExist() {
        return exist;
    }

    /**
     * Kill the player.
     */
    @Override
    public void interaction(DetectableObject detectableObject) {
        if(isExist()){

            ((DisplayCharacter) detectableObject).death();
        }
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    @Override
    public DeathObject clone() {
        return new DeathObject(pathName,new Coordinate(super.getBaseCoordinate().getX(),super.getBaseCoordinate().getY()),this.isExist());
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
