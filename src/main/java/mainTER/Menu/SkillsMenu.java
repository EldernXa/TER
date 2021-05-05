package mainTER.Menu;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainTER.CharacterGameplay.Character;
import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.PersonDBManager;
import mainTER.DBManage.SkillDBManager;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;
import mainTER.exception.ControlsDataGetException;
import mainTER.exception.SkillDataGetException;

import java.awt.*;
import java.util.ArrayList;

public class SkillsMenu {
    Pane pane= new Pane();
    Text description = new Text();
    Text nameCharacter = new Text();
    PersonDBManager personDBManager = new PersonDBManager();
    ControlsDBManager controlsDBManager = new ControlsDBManager();
    SkillDBManager skillDBManager = new SkillDBManager();
    Scene scene = new Scene(pane,800,500);
    Character currentCharacter;
    Text nameSkill = new Text();
    Stage stage;
    VBox vbGen = new VBox(0);
    ArrayList<Character> listCharacter;
    ImageViewSizePos logo;



    public SkillsMenu(Stage stage, ArrayList<Character> characterArrayList,Character currentCharacter){
        this.stage = stage;
        this.currentCharacter = currentCharacter;
        listCharacter = characterArrayList;
        display();
        changePerso();

    }

    public void display(){
        pane.getChildren().clear();
        nameCharacter.setText(currentCharacter.getName());
        System.out.println(currentCharacter.getName());
        logo = new ImageViewSizePos(currentCharacter.getLogo().getPathImage(),new Coordinate(0,0));
        logo.setSize(40,40);

       // logo = currentCharacter.getLogo();
        //logo.getImageView().setX(0);
        //logo.getImageView().setY(0);


        nameCharacter.setLayoutX(300);
        nameCharacter.setLayoutY(30);

        for(int i = 1;i <= currentCharacter.getListSkill().size();i++ ){

            try {
                VBox vBox = new VBox(10);
                nameSkill.setText(skillDBManager.getNameSkill(currentCharacter.getName(),i));
                description.setText( skillDBManager.getDescription(currentCharacter.getName(),i));
                vBox.getChildren().add(nameSkill);
                vBox.getChildren().add(description);
                vbGen.getChildren().add(vBox);

            } catch (SkillDataGetException e) {
                e.printStackTrace();
            }
        }

        vbGen.setLayoutX(300);
        vbGen.setLayoutY(50);


        pane.getChildren().addAll(logo.getImageView(),nameCharacter,vbGen);


    }
    public void changePerso(){

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> {
            String event = event1.getCode().getChar().toLowerCase();
            System.out.println(event);
            System.out.println(event1);
            try {

                System.out.println(controlsDBManager.getSwitchDown());
                System.out.println(controlsDBManager.getSwitchUp());
            } catch (ControlsDataGetException e) {
                e.printStackTrace();
            }
            try {
                    if(event.equals(controlsDBManager.getSwitchDown())){
                        int k = 0;
                        for (int i = 0; i < listCharacter.size(); i++) {
                            if (listCharacter.get(i) == currentCharacter) {
                                k = i;
                            }
                        }
                        if (k == 0) {
                            currentCharacter = listCharacter.get(listCharacter.size() - 1);
                        } else {
                            currentCharacter = listCharacter.get((k - 1) % listCharacter.size());
                        }
                        display();
                    }
                    else if(event.equals(controlsDBManager.getSwitchUp())){
                        int k = 0;
                        for (int i = 0; i < listCharacter.size(); i++) {
                            if (listCharacter.get(i) == currentCharacter) {
                                k = i;
                            }
                        }
                        currentCharacter = listCharacter.get((k + 1) % listCharacter.size());
                        display();
                    }
                } catch (ControlsDataGetException e) {
                    e.printStackTrace();
                }


        });
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }
}
