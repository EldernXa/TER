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
        setCoordinate(new Coordinate(x,getY()));
    }

    @Override
    public double getY() {
        return this.coordinate.getY();
    }

    @Override
    public void setY(double y) {
        setCoordinate(new Coordinate(getX(),y));
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.imageViewSizePos.setCoordinate(coordinate);
    }

    public Collide getCollide() {
        return collide;
    }
}
