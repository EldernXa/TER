package mainTER.MapPackage;

import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public abstract class InteractiveObject extends CollideObject {

    private Coordinate coordinate;
    private ImageViewSizePos imageViewSizePos;
    Collide collide = new Collide();

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

    @Override
    public double getX() {
        return this.coordinate.getX();
    }

    @Override
    public void setX(double x) {
        this.coordinate.setX(x);
    }

    @Override
    public double getY() {
        return this.coordinate.getY();
    }

    @Override
    public void setY(double y) {
        this.coordinate.setY(y);
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.imageViewSizePos.setCoordinate(coordinate);
    }

    public Collide getCollide() {
        return collide;
    }
}
