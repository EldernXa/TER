package mainTER.MapPackage;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import mainTER.CharacterGameplay.Character;
import mainTER.CharacterGameplay.DisplayCharacter;


import java.util.ArrayList;

public class SwitchCharacter extends VBox{

    ArrayList<Character> characterList;
    ArrayList<ImageView> vBoxList = new ArrayList<>();


    public SwitchCharacter(ArrayList<Character> characterList, DisplayCharacter displayCharacter) {
        this.characterList = characterList;


        for(int i = 0;i<characterList.size();i++){
          if(characterList.get(i).getName().equals(displayCharacter.getCharacter().getName())){
                ImageView first;
                if(i == 0){
                     first= characterList.get(characterList.size()-1).getLogo().getImageView();
                }else{
                    first= characterList.get((i-1)%characterList.size()).getLogo().getImageView();
                }

                ImageView second = characterList.get(i).getLogo().getImageView();
                ImageView third = characterList.get((i+1)%characterList.size()).getLogo().getImageView();
                ImageView fourth = characterList.get((i+2)%characterList.size()).getLogo().getImageView();

                getChildren().add(first);
                vBoxList.add(first);
                getChildren().add(second);
                vBoxList.add(second);
                getChildren().add(third);
                vBoxList.add(third);
                vBoxList.add(fourth);
                System.out.println(vBoxList);
            }
        }



            //getChildren().add(characterList.get(1).getLogo().getImageView());

    }

    /**
     * rotate the vbox to down
     */
    public void changeToDown(){
        rotateDown();

        getChildren().clear();
        getChildren().addAll(vBoxList.get(0),vBoxList.get(1),vBoxList.get(2));

    }

    /**
     * rotate the vbox to up
     */

    public void changeToUp(){

        rotateUp();

        getChildren().clear();
        getChildren().addAll(vBoxList.get(0),vBoxList.get(1),vBoxList.get(2));

    }


    /**
     * allow to rotate down the list of character
     */
    public void rotateDown(){
        permute(1,2);
        permute(0,1);
        permute(0,3);
    }
    /**
     * allow to rotate up the list of character
     */
    public void rotateUp(){
        permute(0,1);
        permute(1,2);
        permute(2,3);
    }

    /**
     *
     * @param a index of the one of two values permuted
     * @param b index of the one of two values permuted
     */

    public void permute(int a, int b){
        ImageView temp = vBoxList.get(a);
        vBoxList.set(a,vBoxList.get(b));
        vBoxList.set(b,temp);
    }

}
