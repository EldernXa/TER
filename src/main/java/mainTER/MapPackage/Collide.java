package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;

import java.util.ArrayList;

public class Collide {

    private  ArrayList<CollideObject> collideObjectArrayList = new ArrayList<>();

    public Collide(){
    }

    public boolean verify(Image image, Coordinate coordinate){
        ImageView imageView = new ImageView(image);
        imageView.setX(coordinate.getX());
        imageView.setY(coordinate.getY());
        for(CollideObject collideObject : collideObjectArrayList){
            if(imageView.getBoundsInParent().intersects(collideObject.getAppropriateNode().getBoundsInParent())){
                return false;
            }
        }
        return true;
    }

    public void setCollisionObjectArrayList(ArrayList<CollideObject> collideObjectArrayList) {
        this.collideObjectArrayList = collideObjectArrayList;
    }
}
