package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.util.ArrayList;

public class Collision {

    private  ArrayList<CollisionObject> collisionObjectArrayList = new ArrayList<>();

    public Collision(){
    }

    public boolean verify(Image image, Coordinate coordinate){
        ImageView imageView = new ImageView(image);
        imageView.setX(coordinate.getX());
        imageView.setY(coordinate.getY());
        for(CollisionObject collisionObject : collisionObjectArrayList){
            if(imageView.getBoundsInParent().intersects(collisionObject.getAppropriateNode().getBoundsInParent())){
                return false;
            }
        }
        return true;
    }

    public void setCollisionObjectArrayList(ArrayList<CollisionObject> collisionObjectArrayList) {
        this.collisionObjectArrayList = collisionObjectArrayList;
    }
}
