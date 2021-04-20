package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class MapFieldFromSprite extends MapFieldForm {

    private ImageViewSizePos imageViewSizePos;
    private String path;
    private Coordinate coordinate;
    private double percent;



    public MapFieldFromSprite(String path, Coordinate coordinate, double percent) {

        super(coordinate, 0, 0);
        this.path = path;
        this.coordinate = coordinate;
        this.percent = percent;
        imageViewSizePos = new ImageViewSizePos(path,coordinate);


        imageViewSizePos.getImageView().setFitHeight(imageViewSizePos.getImageView().getImage().getHeight() * percent / 100);
        imageViewSizePos.getImageView().setFitWidth(imageViewSizePos.getImageView().getImage().getWidth() * percent / 100);

    }



    public ImageViewSizePos getImageViewSizePos() {
        return imageViewSizePos;
    }

    @Override
    public Node getAppropriateNode() {
        return imageViewSizePos.getImageView();
    }

    public String getPath() {
        return path;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getX(){
        return this.getCoordinate().getX();
    }

    public double getY(){
        return this.getCoordinate().getY();
    }

    public double getPercent() {
        return percent;
    }

    @Override
    public CollideObject clone() {
        return new MapFieldFromSprite(this.getPath(),new Coordinate(getX(),getY()),this.getPercent());
    }
}
