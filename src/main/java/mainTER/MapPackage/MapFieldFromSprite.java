package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class MapFieldFromSprite extends MapFieldForm {

    private ImageViewSizePos imageViewSizePos;
    private String path;
    private Coordinate coordinate;
    private double percent;



    public MapFieldFromSprite(String spriteName, Coordinate coordinate, double percent) {

        super(coordinate, 0, 0);
        this.path = "./src/main/resources/mainTER/MapPackage/Sprites/Front/"+ spriteName +".png";
        this.coordinate = coordinate;
        this.percent = percent;
        imageViewSizePos = new ImageViewSizePos(this.path,coordinate);


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
        return this.path;
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
    public Node clone() {
        return new ImageView(imageViewSizePos.getImageView().getImage());
    }

    @Override
    public double getVMouvementSpan() {
        return 0;
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
