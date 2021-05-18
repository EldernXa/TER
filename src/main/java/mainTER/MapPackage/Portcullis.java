package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class Portcullis extends InteractiveObject{

    boolean isOpen;
    String name;

    public Portcullis(Coordinate coordinate,String name){
        super(coordinate,new ImageViewSizePos("/mainTER/MapPackage/Objects/"+ name + ".png", coordinate));
        isOpen = false;
        this.name = name;
    }

    @Override
    public void actionGenuine() {
        System.out.println("Genuine");
    }

    @Override
    public void actionTriggered() {
        if(!isOpen){
            this.setY(this.getY()-200);
            this.isOpen = true;
        }
        else{
            this.setY(this.getY()+200);
            this.isOpen = false;
        }
    }

    @Override
    public Node getAppropriateNode() {
        return this.getImageView();
    }

    @Override
    public Portcullis clone() {
        return new Portcullis(new Coordinate(this.getX(),this.getY()),name);
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }
}
