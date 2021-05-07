package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.DBManage.CheckpointsDBManager;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class Checkpoint extends CollideObject {
    private Coordinate coordinate;
    private ImageViewSizePos defaultImage;
    private ImageViewSizePos activatedImage;
    private String mapName;
    private boolean isActivated;

    public Checkpoint( Coordinate coordinate, String mapName) {

        this.coordinate = coordinate;
        this.isActivated = false;
        this.mapName = mapName;

        this.defaultImage = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/checkpointDefault.png", coordinate);
        this.activatedImage = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/checkpointActivated.png", coordinate);

    }


    public void collide(CollideObject collideObject) {
        if (collideObject.getAppropriateNode().intersects(this.getAppropriateNode().getBoundsInParent())) {
            isActivated = true;
        }
    }

    public void effect(DisplayCharacter displayCharacter) {

        CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager();
        checkpointsDBManager.setX(getX());
        checkpointsDBManager.setY(getY());
        checkpointsDBManager.setMapName(mapName);
        checkpointsDBManager.setCharacterName(displayCharacter.getCharacter().getName());

    }


    public boolean isActivated() {
        return isActivated;
    }


    @Override
    public Node getAppropriateNode() {
        return defaultImage.getImageView();
    }

    @Override
    public CollideObject clone() {
        return new Checkpoint(coordinate,mapName);
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {

    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public void setX(double x) {

    }

    @Override
    public void setY(double y) {

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
