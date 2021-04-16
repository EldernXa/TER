package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class MapFieldFromSprite extends MapFieldForm {

    private ImageViewSizePos imageViewSizePos;
    private String path;



    public MapFieldFromSprite(String path, Coordinate coordinate, double percent) {

        super(coordinate, 0, 0);
        this.path = path;
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
}
