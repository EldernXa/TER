package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class MetalDoor extends InteractiveObject{

    private boolean isOpen;
    private ImageViewSizePos imageView;
    private String name;

    public MetalDoor(Coordinate coordinate, String name) {
        super(coordinate, new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/"+name +".png", coordinate));
        this.imageView = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/"+name +".png", coordinate);
        isOpen = false;
        this.name = name;
    }

    @Override
    public void actionTriggered() {
        if(!isOpen){
            this.getImageView().setImage(null);
            //this.setY(this.getY()-200);
            this.isOpen = true;
        }
        else{
            //this.setY(this.getY()+200);
            this.getImageView().setImage(imageView.getImageView().getImage());

            this.isOpen = false;
        }
    }

    @Override
    public Node getAppropriateNode() {
        return this.getImageView();
    }
    @Override
    public MetalDoor clone() {
        return new MetalDoor(new Coordinate(this.getX(),this.getY()),name);
    }
    @Override
    public double getHMouvementSpan() {
        return 0;
    }
}
