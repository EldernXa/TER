package mainTER.MapPackage;

import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public abstract class InteractiveObject extends CollideObject {

    private Coordinate coordinate;
    private final ImageViewSizePos imageViewSizePos;

    public InteractiveObject(Coordinate coordinate, ImageViewSizePos imageViewSizePos) {
        this.imageViewSizePos = imageViewSizePos;
        this.setCoordinate(coordinate);
    }


    public ImageView getImageView() {
        return imageViewSizePos.getImageView();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void Action(){

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

    @Override
    public double getWidth() {
        return this.imageViewSizePos.getImageView().getImage().getWidth();
    }

    @Override
    public double getHeight() {
        return this.imageViewSizePos.getImageView().getImage().getWidth();
    }
}
