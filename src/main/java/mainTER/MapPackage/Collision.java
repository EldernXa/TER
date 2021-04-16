package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.util.ArrayList;

public class Collision {

    private  ArrayList<InteractiveObject> interactiveObjectArrayList = new ArrayList<>();

    public Collision(){
    }

    public boolean verify(Image image, Coordinate coordinate){
        ImageView imageView = new ImageView(image);
        imageView.setX(coordinate.getX());
        imageView.setY(coordinate.getY());
        for(InteractiveObject interactiveObject : interactiveObjectArrayList){
            if(imageView.getBoundsInParent().intersects(interactiveObject.getImageView().getBoundsInParent())){
                return false;
            }
        }
        return true;
    }

    public void setInteractiveObjectArrayList(ArrayList<InteractiveObject> interactiveObjectArrayList) {
        this.interactiveObjectArrayList = interactiveObjectArrayList;
    }

    public ArrayList<InteractiveObject> getInteractiveObjectArrayList() {
        return interactiveObjectArrayList;
    }
}
