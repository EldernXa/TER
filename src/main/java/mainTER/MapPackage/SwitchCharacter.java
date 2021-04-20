package mainTER.MapPackage;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import mainTER.CharacterGameplay.Character;
import mainTER.Tools.ImageViewSizePos;

import java.util.ArrayList;

public class SwitchCharacter extends VBox{

    ArrayList<Character> characterList;
    public SwitchCharacter(ArrayList<Character> characterList) {
        this.characterList = characterList;


            characterList.get(0).getLogo().getImageView().setFitHeight(100);
            characterList.get(0).getLogo().getImageView().setFitWidth(100);

            getChildren().add(characterList.get(characterList.size()-1).getLogo().getImageView());
            getChildren().add(characterList.get(0).getLogo().getImageView());
            //getChildren().add(characterList.get(1).getLogo().getImageView());




    }

    public void changeToDown(){
        Node node0 = getChildren().get(0);
        Node node1  = getChildren().get(1);

        getChildren().remove(1);
        getChildren().remove(0);
        getChildren().add(node1);
        getChildren().add(node0);

    }


    public void changeToUp(){
        Node node1 = getChildren().get(1);
        Node node0 = getChildren().get(0);



        getChildren().remove(1);
        getChildren().remove(0);
        getChildren().add(node1);
        getChildren().add(node0);

    }

}
