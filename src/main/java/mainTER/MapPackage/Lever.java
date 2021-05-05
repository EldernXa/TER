package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

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
}
