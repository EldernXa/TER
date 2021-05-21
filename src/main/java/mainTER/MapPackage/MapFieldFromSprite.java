package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

/**
 * Create a MapFieldForm using one picture
 */
public class MapFieldFromSprite extends MapFieldForm {

    private final ImageViewSizePos imageViewSizePos;
    private final String path;
    private final double percent;
    private String pathName;
    private String spriteName;



    public MapFieldFromSprite(String pathName,String spriteName, Coordinate coordinate, double percent) {

        super(coordinate, 0, 0);
        this.pathName = pathName;
        this.spriteName = spriteName;
        this.path = "/mainTER/MapPackage/Sprites/Front/"+ pathName +"/" + spriteName + ".png";
        this.percent = percent;
        imageViewSizePos = new ImageViewSizePos(this.path,coordinate);
        setWidth(imageViewSizePos.getImageView().getImage().getWidth());
        setHeight(imageViewSizePos.getImageView().getImage().getHeight());


        imageViewSizePos.getImageView().setFitHeight(imageViewSizePos.getImageView().getImage().getHeight() * percent / 100);
        imageViewSizePos.getImageView().setFitWidth(imageViewSizePos.getImageView().getImage().getWidth() * percent / 100);

    }



    public ImageViewSizePos getImageViewSizePos() {
        return imageViewSizePos;
    }

    public double getPercent() {
        return percent;
    }

    public String getPathName() {
        return pathName;
    }

    public String getSpriteName() {
        return spriteName;
    }

    @Override
    public double getWidth() {
        return super.getWidth();
    }

    @Override
    public void setWidth(double width) {
        super.setWidth(width);
    }


    @Override
    public double getHeight() {
        return super.getHeight();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
    }

    /**
     * Return the ImageView as a Node
     * @return ImageView
     */
    @Override
    public Node getAppropriateNode() {
        return imageViewSizePos.getImageView();
    }

    @Override
    public MapFieldFromSprite clone() {
        return new MapFieldFromSprite(this.getPathName(),this.getSpriteName(),new Coordinate(this.getX(),this.getY()),this.getPercent());
    }

    @Override
    public double getJumpMouvementSpan() {
        return 0;
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        setX(coordinate.getX());
        setY(coordinate.getY());
    }

    @Override
    public void setX(double x) {
        this.getCoordinate().setX(x);
        this.imageViewSizePos.setCoordinate(new Coordinate(getX(),getY()));
    }

    @Override
    public void setY(double y) {
        this.getCoordinate().setY(y);
        this.imageViewSizePos.setCoordinate(new Coordinate(getX(),getY()));
    }
}
