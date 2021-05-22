package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

/**
 * Create an object that can become invisible
 */
public class HideOnActionObject extends InteractiveObject{

    private int isOpen;
    private final ImageViewSizePos imageView;
    private final String name;
    private  final  Coordinate coordinate;

    public HideOnActionObject(Coordinate coordinate, String name, int isOpen) {
        super(coordinate, new ImageViewSizePos("/mainTER/MapPackage/Objects/"+name +".png", coordinate));

        this.imageView = new ImageViewSizePos("/mainTER/MapPackage/Objects/"+name +".png", coordinate);
        this.isOpen = isOpen;
        this.name = name;
        System.out.println("Coordonnées = " + coordinate);
        this.coordinate = coordinate;

        if (isOpen==0){
            this.getImageView().setImage(null);
            this.setCoordinate(new Coordinate(-100,-100));
        }
    }

    public int getIsOpen() {
        return isOpen;
    }


    /**
     * Switch the item from visible to invisible depending on it state
     */
    @Override
    public void actionTriggered() {
        System.out.println("ACTION !!" + this);
        System.out.println(this.name);
        System.out.println(isOpen);
        System.out.println(coordinate);
        if(isOpen==0){
            this.getImageView().setImage(imageView.getImageView().getImage());
            this.setCoordinate(coordinate);
            this.isOpen = 1;
        }
        else{
            this.getImageView().setImage(null);
            this.setCoordinate(new Coordinate(-100,-100));
            this.isOpen = 0;
        }
    }

    @Override
    public Node getAppropriateNode() {
        return this.getImageView();
    }
    @Override
    public HideOnActionObject clone() {
        return new HideOnActionObject(new Coordinate(super.getBaseCoordinate().getX(), super.getBaseCoordinate().getY()),name, getIsOpen());
    }
    @Override
    public double getHMouvementSpan() {
        return 0;
    }
}
