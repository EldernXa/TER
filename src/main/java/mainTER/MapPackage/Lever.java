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
    public Node clone() {
        return null;
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }
}
