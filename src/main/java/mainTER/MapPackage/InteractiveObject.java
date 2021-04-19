package mainTER.MapPackage;

import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public abstract class InteractiveObject implements CollideObject {

    private Coordinate coordinate;
    private ImageViewSizePos imageViewSizePos;

    public InteractiveObject(Coordinate coordinate, ImageViewSizePos imageViewSizePos) {
        this.coordinate = coordinate;
        this.imageViewSizePos = imageViewSizePos;
    }

    public ImageView getImageView() {
        return imageViewSizePos.getImageView();
    }
}
