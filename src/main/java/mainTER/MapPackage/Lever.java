package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.io.File;

public class Lever extends InteractiveObject{

    InteractiveObject interactiveObject;

    public Lever(InteractiveObject interactiveObject, Coordinate coordinate){
        super(coordinate,new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/lever_left.png", coordinate));
        this.interactiveObject = interactiveObject;
    }

    public InteractiveObject getCollideObject() {
        return interactiveObject;
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    @Override
    public Lever clone() {
        return new Lever(interactiveObject.clone(), new Coordinate(this.getX(),this.getY()));
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

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
        return this.getAppropriateNode().getBoundsInParent().getMinX() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxX();

    }

    @Override
    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();

    }

    @Override
    public double upMvt(DetectableObject detectableObject) {
        return detectableObject.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    @Override
    public double downMvt(DetectableObject detectableObject) {
        return this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    @Override
    public void actionGenuine() {
        System.out.println("Interact with " + interactiveObject);
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
