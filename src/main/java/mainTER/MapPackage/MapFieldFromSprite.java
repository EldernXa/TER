package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class MapFieldFromSprite extends MapFieldForm {

    private ImageViewSizePos imageViewSizePos;
    private String path;
    private double percent;



    public MapFieldFromSprite(String spriteName, Coordinate coordinate, double percent) {

        super(coordinate, 0, 0);
        setWidth(imageViewSizePos.getImageView().getImage().getWidth());
        setHeight(imageViewSizePos.getImageView().getImage().getHeight());
        this.path = "./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName + ".png";
        this.percent = percent;
        imageViewSizePos = new ImageViewSizePos(this.path,coordinate);


        imageViewSizePos.getImageView().setFitHeight(imageViewSizePos.getImageView().getImage().getHeight() * percent / 100);
        imageViewSizePos.getImageView().setFitWidth(imageViewSizePos.getImageView().getImage().getWidth() * percent / 100);

    }



    public ImageViewSizePos getImageViewSizePos() {
        return imageViewSizePos;
    }


    public String getPath() {
        return this.path;
    }

    public double getPercent() {
        return percent;
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

    @Override
    public Node getAppropriateNode() {
        return imageViewSizePos.getImageView();
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
        super.setCoordinate(coordinate);
    }
}
