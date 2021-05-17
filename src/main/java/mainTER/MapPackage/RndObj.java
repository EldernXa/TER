package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class RndObj extends DetectableObject {

    private ImageViewSizePos imageViewSizePos;
    private String rndObjName;
    private Coordinate coordinate;
    private String pathName;



    public RndObj(String pathName,String rndObjName, Coordinate coordinate) {

        this.rndObjName = rndObjName;
        this.coordinate = coordinate;
        this.pathName = pathName;

        imageViewSizePos = new ImageViewSizePos("/mainTER/MapPackage/Sprites/Front/" + pathName +"/" + rndObjName + ".png",coordinate);
        imageViewSizePos.getImageView().setFitHeight(imageViewSizePos.getImageView().getImage().getHeight());
        imageViewSizePos.getImageView().setFitWidth(imageViewSizePos.getImageView().getImage().getWidth());

    }

    public ImageViewSizePos getImageViewSizePos() {
        return imageViewSizePos;
    }

    public String getPathName() {
        return pathName;
    }

    public String getRndObjName() {
        return rndObjName;
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public double getJumpMouvementSpan() {
        return 0;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
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

    @Override
    public Node getAppropriateNode() {
        return this.imageViewSizePos.getImageView();
    }

    @Override
    public RndObj clone() {
        return new RndObj(this.getPathName(),this.getRndObjName(),new Coordinate(this.getX(),this.getY()));
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public double getWidth() {
        return this.imageViewSizePos.getImageView().getImage().getWidth();
    }

    @Override
    public double getHeight() {
        return this.imageViewSizePos.getImageView().getImage().getHeight();
    }
}
