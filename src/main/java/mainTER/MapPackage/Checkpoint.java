package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.DBManage.CheckpointsDBManager;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;
import mainTER.exception.CheckpointsCharacterDoesntExistException;
import mainTER.exception.CheckpointsMapDoesntExistException;

/**
 * Create a checkpoint item that will be placed on the map
 */
public class Checkpoint extends UnCollideObject {
    private final Coordinate coordinate;
    private final ImageViewSizePos defaultImage;
    private final ImageViewSizePos activatedImage;
    private final ImageViewSizePos currentImage;
    private final String mapName;
    private CheckpointsDBManager checkpointsDBManager;
    static private boolean isActivated;

    public Checkpoint( Coordinate coordinate, String mapName) {

        this.coordinate = coordinate;
        this.mapName = mapName;

        this.defaultImage = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/checkpointDefault.png", coordinate);
        this.activatedImage = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/checkpointActivated.png", coordinate);
        this.currentImage = new ImageViewSizePos(defaultImage.getPathImage(),defaultImage.getCoordinate());
        checkpointsDBManager = new CheckpointsDBManager();


    }

    /**
     *  Create the interaction on contact.
     */
    @Override
    public void interaction(DetectableObject detectableObject) {

        if(checkpointsDBManager.getX() != getX() || checkpointsDBManager.getY() != getY()){
            effect((DisplayCharacter) detectableObject);
            for (Checkpoint checkpoint : MapFileReader.checkpointArrayList){
                if(!this.equals(checkpoint)){
                    checkpoint.setImage(defaultImage);
                }
            }
            setImage(activatedImage);


        }


    }

    /**
     * Setup the checkpointsDBManager
     */

    public void effect(DisplayCharacter displayCharacter) {



            checkpointsDBManager.setX(getX());
            checkpointsDBManager.setY(getY());
            try {
                checkpointsDBManager.setMapName(mapName);
                checkpointsDBManager.setCharacterName(displayCharacter.getCharacter().getName());
            } catch (CheckpointsCharacterDoesntExistException | CheckpointsMapDoesntExistException e) {
                e.printStackTrace();
            }




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
        return new Checkpoint(new Coordinate(this.getX(),this.getY()),mapName);
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
