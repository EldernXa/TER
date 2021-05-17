package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.io.File;

public class Lever extends InteractiveObject{

    InteractiveObject interactiveObject;

    public Lever(InteractiveObject interactiveObject, Coordinate coordinate){
        super(coordinate,new ImageViewSizePos("/mainTER/MapPackage/Objects/lever_left.png", coordinate));
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

    @Override
    public void actionGenuine() {  //TODO regler le pb pour les chemins generiques
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
