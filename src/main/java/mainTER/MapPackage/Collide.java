/*
package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;

import java.util.ArrayList;

public class Collide { //TODO la fusionner avec CollideObject

    public static  ArrayList<CollideObject> collideObjectArrayList = new ArrayList<>();

    public Collide(){
    }

    public boolean verify( Image image, Coordinate coordinate, DisplayCharacter displayCharacter){ //C'est âs bon il faut adapter la méthode comme plus bas
        ImageView imageView = new ImageView(image);


        imageView.setFitWidth(imageView.getImage().getWidth()/3);
        imageView.setX(coordinate.getX());
        imageView.setY(coordinate.getY());

        for(CollideObject collideObject : collideObjectArrayList){
            if(imageView.getBoundsInParent().intersects(collideObject.getAppropriateNode().getBoundsInParent())){
                collideObject.interaction(displayCharacter);
                return false;
            }
        }
        return true;
    }



    */
/*public boolean verify(CollideObject collideObject){ //C'est pas bon il faut adapter la méthode comme plus bas
        switch(collideObject.getClass()){
            case
        }
        Node node;
        node = new collideObject.getClass()
        for(CollideObject collideObject2 : collideObjectArrayList){
            if(imageView.getBoundsInParent().intersects(collideObject.getAppropriateNode().getBoundsInParent())){
                collideObject.interaction(displayCharacter);
                return false;
            }
        }
        return true;
    }*//*


    public void setCollisionObjectArrayList(ArrayList<CollideObject> collideObjectArrayList) {
        this.collideObjectArrayList = collideObjectArrayList;
    }
}
*/
