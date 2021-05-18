package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class HideOnActionObject extends InteractiveObject{

    private boolean isOpen;
    private final ImageViewSizePos imageView;
    private final String name;

    public HideOnActionObject(Coordinate coordinate, String name) {
        super(coordinate, new ImageViewSizePos("/mainTER/MapPackage/Objects/"+name +".png", coordinate));
        this.imageView = new ImageViewSizePos("/mainTER/MapPackage/Objects/"+name +".png", coordinate);//TODO can be removed
        isOpen = false;
        this.name = name;
    }

    @Override
    public void actionTriggered() {
        if(!isOpen){
            this.getImageView().setImage(null);
            this.isOpen = true;
        }
        else{
            this.getImageView().setImage(imageView.getImageView().getImage());
            this.isOpen = false;
        }
    }

    @Override
    public Node getAppropriateNode() {
        return this.getImageView();
    }
    @Override
    public HideOnActionObject clone() {
        return new HideOnActionObject(new Coordinate(this.getX(),this.getY()),name);
    }
    @Override
    public double getHMouvementSpan() {
        return 0;
    }
}
