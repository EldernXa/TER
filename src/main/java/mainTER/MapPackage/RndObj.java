package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class RndObj extends CollideObject {

    private ImageViewSizePos imageViewSizePos;
    private String rndObjName;
    private Coordinate coordinate;



    public RndObj(String rndObjName, Coordinate coordinate) { //TODO implicite name

        this.rndObjName = rndObjName;
        this.coordinate = coordinate;
        imageViewSizePos = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Sprites/Front" + rndObjName+".png",coordinate);


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
    public double getVMouvementSpan() {
        return 0;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
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
}
