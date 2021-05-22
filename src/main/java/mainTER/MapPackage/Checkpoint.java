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
    static public Coordinate lastCheckpointCoord;

    public Checkpoint(Coordinate coordinate, String mapName) {

        this.coordinate = coordinate;
        this.mapName = mapName;

        this.defaultImage = new ImageViewSizePos("/mainTER/MapPackage/Objects/checkpointDefault.png", coordinate);
        this.activatedImage = new ImageViewSizePos("/mainTER/MapPackage/Objects/checkpointActivated.png", coordinate);
        this.currentImage = new ImageViewSizePos(defaultImage.getPathImage(), defaultImage.getCoordinate());


    }

    /**
     * Create the interaction on contact.
     */
    @Override
    public void interaction(DetectableObject detectableObject) {


        effect();
/*            for (Checkpoint checkpoint : MapFileReader.checkpointArrayList){

                if(this.coordinate.getX() != checkpoint.getX() || this.coordinate.getY() != checkpoint.getY() ){

                    checkpoint.setImage(defaultImage);

                }q
            }*/

        for (ObjectLinker objectLinker : Map.objectLinkers) {
            if (objectLinker.getDetectableObject1() instanceof Checkpoint) {
                if (this == objectLinker.getDetectableObject1() || this == objectLinker.getDetectableObject2()) {
                    setImage(activatedImage);
                }
                else {
                    ((Checkpoint)objectLinker.getDetectableObject1()).setImage(defaultImage);
                    ((Checkpoint)objectLinker.getDetectableObject2()).setImage(defaultImage);
                }
            }
        }

        //setImage(activatedImage);


    }

    /**
     * Setup the checkpointsDBManager
     */

    public void effect() {


        lastCheckpointCoord.setX(getX());
        lastCheckpointCoord.setY(getY());


    }


    public void setImage(ImageViewSizePos imageViewSizePos) {
        currentImage.getImageView().setImage(imageViewSizePos.getImageView().getImage());
    }

    @Override
    public Node getAppropriateNode() {
        return currentImage.getImageView();
    }

    @Override
    public DetectableObject clone() {
        return new Checkpoint(new Coordinate(this.getX(), this.getY()), mapName);
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
