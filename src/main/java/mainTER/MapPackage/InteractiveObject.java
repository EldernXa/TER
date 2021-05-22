package mainTER.MapPackage;

import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

/**
 * Create an object that can be activated
 */
public abstract class InteractiveObject extends DetectableObject {

    private Coordinate coordinate;
    private final Coordinate baseCoordinate;
    private final ImageViewSizePos imageViewSizePos;

    public InteractiveObject(Coordinate coordinate, ImageViewSizePos imageViewSizePos) {
        this.imageViewSizePos = imageViewSizePos;
        this.baseCoordinate = coordinate;
        this.setCoordinate(coordinate);
    }

    /**
     * Get the imageview corresponding to the appearance
     * @return
     */
    public ImageView getImageView() {
        return imageViewSizePos.getImageView();
    }

    /**
     * Get the coordinate
     * @return
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Activate the undergone action
     */
    public void actionTriggered(){}

    /**
     * Activate the basic action
     */
    public void actionGenuine(){}

    @Override
    public InteractiveObject clone() { //Always redefine clone
        return null;
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

    public Coordinate getBaseCoordinate() {
        return baseCoordinate;
    }
}
