package mainTER.MapPackage;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;
import java.io.File;

public class Lever extends InteractiveObject{

    public Lever(Coordinate coordinate){
        super(coordinate,new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/lever_left.png", coordinate));
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    @Override
    public Lever clone() {
        return new Lever(new Coordinate(this.getX(),this.getY()));
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public void interaction(CollideObject collideObject) {}

    @Override
    public void Action() {
        if (this.getImageView().getImage().getUrl().contains("lever_left")){
            this.getImageView().setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Objects/lever_right.png").toURI().toString()));
        }
        else{
            this.getImageView().setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Objects/lever_left.png").toURI().toString()));
        }
    }
}
