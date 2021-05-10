package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class ForceShield extends InteractiveObject {

    ImageViewSizePos imageView;
    boolean isOpen;
    String name;
    Coordinate coordinate;
    public ForceShield(Coordinate coordinate, String name) {
        super(coordinate,  new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/"+name +".png", coordinate));

        this.coordinate = coordinate;
        this.name = name;
        this.imageView = new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/"+name +".png", coordinate);
        isOpen =false;
    }



    public InteractiveObject clone(){
        return new ForceShield(new Coordinate(coordinate.getX(),coordinate.getY()),name);
    }


    @Override
    public void actionTriggered() {
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
