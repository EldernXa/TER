package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class Crate extends InteractiveObject {

    public Crate(Coordinate coordinate ) {
        super(coordinate, new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/crate.png",coordinate));
    }


    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }
}
