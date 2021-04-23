package mainTER.MapPackage;


import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import mainTER.CharacterGameplay.Character;


import java.util.ArrayList;

public class SwitchCharacter extends VBox{

    ArrayList<Character> characterList;
    ArrayList<ImageView> vBoxList = new ArrayList<>();
    public SwitchCharacter(ArrayList<Character> characterList) {
        this.characterList = characterList;




            getChildren().add(characterList.get(characterList.size()-1).getLogo().getImageView());
            getChildren().add(characterList.get(0).getLogo().getImageView());
            getChildren().add(characterList.get(1).getLogo().getImageView());
            vBoxList.add(characterList.get(characterList.size()-1).getLogo().getImageView());
            vBoxList.add(characterList.get(0).getLogo().getImageView());
            vBoxList.add(characterList.get(1).getLogo().getImageView());
            vBoxList.add(characterList.get(2).getLogo().getImageView());



            //getChildren().add(characterList.get(1).getLogo().getImageView());

    }

    public void changeToDown(){
        rotateDown();

        getChildren().clear();
        getChildren().addAll(vBoxList.get(0),vBoxList.get(1),vBoxList.get(2));

    }


    public void changeToUp(){

        rotateUp();

        getChildren().clear();
        getChildren().addAll(vBoxList.get(0),vBoxList.get(1),vBoxList.get(2));

    }

    public void rotateDown(){
        permute(1,2);
        permute(0,1);
        permute(0,3);
    }

    public void rotateUp(){
        permute(0,1);
        permute(1,2);
        permute(2,3);
    }

    public void permute(int a, int b){
        ImageView temp = vBoxList.get(a);
        vBoxList.set(a,vBoxList.get(b));
        vBoxList.set(b,temp);
    }

}
