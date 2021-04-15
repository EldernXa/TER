package mainTER.MapPackage;

import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class Crate extends InteractiveObject {
    public Crate(Coordinate coordinate ) {
        super(coordinate, new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/crate.png",coordinate));

    }



}
