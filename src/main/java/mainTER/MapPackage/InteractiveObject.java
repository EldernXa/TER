package mainTER.MapPackage;

import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public abstract class InteractiveObject extends CollideObject {

    private Coordinate coordinate;
    private ImageViewSizePos imageViewSizePos;

    public InteractiveObject(Coordinate coordinate, ImageViewSizePos imageViewSizePos) {
        this.coordinate = coordinate;
        this.imageViewSizePos = imageViewSizePos;
    }


    public ImageView getImageView() {
        return imageViewSizePos.getImageView();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.imageViewSizePos.setCoordinate(coordinate);
    }
}
