package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.DBManage.CheckpointsDBManager;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;
import mainTER.exception.CheckpointsCharacterDoesntExistException;
import mainTER.exception.CheckpointsMapDoesntExistException;

public class Checkpoint extends CollideObject {
    private Coordinate coordinate;
    private ImageViewSizePos defaultImage;
    private ImageViewSizePos activatedImage;
    private ImageViewSizePos currentImage;
    private String mapName;
    static private boolean isActivated;

    public Checkpoint( Coordinate coordinate, String mapName) {

        this.coordinate = coordinate;
        this.isActivated = false;
        this.mapName = mapName;

        this.defaultImage = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/checkpointDefault.png", coordinate);
        this.activatedImage = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/checkpointActivated.png", coordinate);
        this.currentImage = new ImageViewSizePos(defaultImage.getPathImage(),defaultImage.getCoordinate());


    }

    @Override
    public void interaction(DetectableObject detectableObject) {

        if (!isActivated) {
            effect((DisplayCharacter) detectableObject);
             for (Checkpoint checkpoint : MapFileReader.checkpointArrayList){
                 if(!this.equals(checkpoint)){
                     checkpoint.setImage(defaultImage);
                 }
             }
             setImage(activatedImage);
             isActivated =true;
        }
        else {
            isActivated = false;
        }
    }



    public void effect(DisplayCharacter displayCharacter) {

        CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager();
        checkpointsDBManager.setX(getX());
        checkpointsDBManager.setY(getY());
        try {
            checkpointsDBManager.setMapName(mapName);
            checkpointsDBManager.setCharacterName(displayCharacter.getCharacter().getName());
        } catch (CheckpointsCharacterDoesntExistException | CheckpointsMapDoesntExistException e) {
            e.printStackTrace();
        }


    }


    public boolean isActivated() {
        return isActivated;
    }


    public void setImage(ImageViewSizePos imageViewSizePos){
        currentImage.getImageView().setImage(imageViewSizePos.getImageView().getImage());
    }
    @Override
    public Node getAppropriateNode() {
        return currentImage.getImageView();
    }

    @Override
    public DetectableObject clone() {
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

    public void setIsActivated(boolean isActivated) {
        Checkpoint.isActivated = isActivated;
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
