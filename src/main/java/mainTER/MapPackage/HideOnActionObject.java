package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class HideOnActionObject extends InteractiveObject{

    private int isOpen;
    private final ImageViewSizePos imageView;
    private final String name;

    public HideOnActionObject(Coordinate coordinate, String name, int isOpen) {
        super(coordinate, new ImageViewSizePos("/mainTER/MapPackage/Objects/"+name +".png", coordinate));

        this.imageView = new ImageViewSizePos("/mainTER/MapPackage/Objects/"+name +".png", coordinate);//TODO can be removed
        this.isOpen = isOpen;
        this.name = name;

        if (isOpen==0){
            this.getImageView().setImage(null);
            this.isOpen = 1;
        }
    }

    public int getIsOpen() {
        return isOpen;
    }

    @Override
    public void actionTriggered() {
        System.out.println(name);
        System.out.println("Ouvert = " + isOpen);
        if(isOpen==0){
            this.getImageView().setImage(null);
            this.isOpen = 1;
        }
        else{
            this.getImageView().setImage(imageView.getImageView().getImage());
            this.isOpen = 0;
        }
    }

    @Override
    public Node getAppropriateNode() {
        return this.getImageView();
    }
    @Override
    public HideOnActionObject clone() {
        return new HideOnActionObject(new Coordinate(this.getX(),this.getY()),name, getIsOpen());
    }
    @Override
    public double getHMouvementSpan() {
        return 0;
    }
}
