package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class RndObj extends CollideObject {

    private ImageViewSizePos imageViewSizePos;
    private String path;
    private Coordinate coordinate;



    public RndObj(String path, Coordinate coordinate) {

        this.path = path;
        this.coordinate = coordinate;
        imageViewSizePos = new ImageViewSizePos(path,coordinate);


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
