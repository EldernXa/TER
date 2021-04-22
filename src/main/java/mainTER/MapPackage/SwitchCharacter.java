package mainTER.MapPackage;


import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mainTER.CharacterGameplay.Character;

import java.util.ArrayList;

public class SwitchCharacter extends VBox{

    ArrayList<Character> characterList;
    public SwitchCharacter(ArrayList<Character> characterList) {
        this.characterList = characterList;



            getChildren().add(characterList.get(characterList.size()-1).getLogo().getImageView());
            getChildren().add(characterList.get(0).getLogo().getImageView());
            getChildren().add(characterList.get(1).getLogo().getImageView());


            //getChildren().add(characterList.get(1).getLogo().getImageView());




    }

    public void changeToDown(){
        Node node0 = getChildren().get(0);
        Node node1  = getChildren().get(1);
        Node node2 = getChildren().get(2);

        getChildren().clear();
        getChildren().add(node2);
        getChildren().add(node0);
        getChildren().add(node1);

    }


    public void changeToUp(){
        Node node1 = getChildren().get(1);
        Node node0 = getChildren().get(0);
        Node node2 = getChildren().get(2);


        getChildren().clear();
        getChildren().add(node1);
        getChildren().add(node2);
        getChildren().add(node0);

    }

}
