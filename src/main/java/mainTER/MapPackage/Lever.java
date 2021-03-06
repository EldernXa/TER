package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.io.File;

/**
 * Create a Lever
 */
public class Lever extends InteractiveObject{

    InteractiveObject interactiveObject;

    public Lever(InteractiveObject interactiveObject, Coordinate coordinate){
        super(coordinate,new ImageViewSizePos("/mainTER/MapPackage/Objects/lever_left.png", coordinate));
        this.interactiveObject = interactiveObject;
    }

    /**
     * Set the interactive object
     * @param interactiveObject
     */
    public void setInteractiveObject(InteractiveObject interactiveObject) {
        this.interactiveObject = interactiveObject;
    }

    /**
     * Return the object that is activated when the lever is triggered
     * @return
     */
    public InteractiveObject getInteractiveObject() {
        return interactiveObject;
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    @Override
    public Lever clone() {
        return new Lever(null, new Coordinate(super.getBaseCoordinate().getX(),super.getBaseCoordinate().getY()));
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    /**
     * Do nothing when it's touched
     * @param detectableObject
     */
    @Override
    public void interaction(DetectableObject detectableObject) {
        /*if (this.getImageView().getImage().getUrl().contains("lever_left")){
            this.getImageView().setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Objects/lever_right.png").toURI().toString()));
            interactiveObject.actionTriggered();
        }
        else{
            this.getImageView().setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Objects/lever_left.png").toURI().toString()));
            interactiveObject.actionTriggered();
        }*/
    }

    @Override
    public double rightMvt(DetectableObject detectableObject) {
        return detectableObject.getHMouvementSpan();
    }

    @Override
    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getHMouvementSpan();
    }

    @Override
    public double upMvt(DetectableObject detectableObject) {
        return detectableObject.getJumpMouvementSpan();
    }

    @Override
    public double downMvt(DetectableObject detectableObject) {
        return detectableObject.getFallMouvementSpan();
    }

    /**
     * Activate the interactiveObject's action triggered
     */
    @Override
    public void actionGenuine() {
        if (this.getImageView().getImage().getUrl().contains("lever_left")){
            this.getImageView().setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Objects/lever_right.png").toURI().toString()));
            interactiveObject.actionTriggered();
        }
        else{
            this.getImageView().setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Objects/lever_left.png").toURI().toString()));
            interactiveObject.actionTriggered();
        }
    }
}
