package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class RndObj extends CollideObject {

    private ImageViewSizePos imageViewSizePos;
    private String rndObjName;
    private Coordinate coordinate;



    public RndObj(String pathName,String rndObjName, Coordinate coordinate) {

        this.rndObjName = rndObjName;
        this.coordinate = coordinate;

        imageViewSizePos = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Sprites/Front/" +pathName+"/" + rndObjName + ".png",coordinate);
        imageViewSizePos.getImageView().setFitHeight(imageViewSizePos.getImageView().getImage().getHeight());
        imageViewSizePos.getImageView().setFitWidth(imageViewSizePos.getImageView().getImage().getWidth());

    }

    public ImageViewSizePos getImageViewSizePos() {
        return imageViewSizePos;
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
    public Node clone() {
        return null;
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
