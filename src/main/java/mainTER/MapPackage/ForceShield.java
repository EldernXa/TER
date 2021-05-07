package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class ForceShield extends InteractiveObject {

    ImageViewSizePos imageView;
    boolean isOpen;
    public ForceShield(Coordinate coordinate, String name) {
        super(coordinate,  new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/"+name +".png", coordinate));

        this.imageView = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/"+name +".png", coordinate);
        isOpen =false;
    }


    @Override
    public void action() {
        if(!isOpen){
           this.getImageView().setImage(null);
           //this.setY(this.getY()-20000);
           this.isOpen = true;
        }
        else{
            //this.setY(this.getY()+20000);
            this.getImageView().setImage(imageView.getImageView().getImage());

            this.isOpen = false;
        }
    }
    @Override
    public Node getAppropriateNode() {
        return this.getImageView();
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }
}
