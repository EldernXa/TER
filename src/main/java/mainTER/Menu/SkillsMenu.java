package mainTER.Menu;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create a menu that display the character skills.
 */
public class SkillsMenu {
    Pane pane= new Pane();
    Text description = new Text();
    Text nameCharacter = new Text();
    PersonDBManager personDBManager = new PersonDBManager();
    ControlsDBManager controlsDBManager = new ControlsDBManager();
    SkillDBManager skillDBManager = new SkillDBManager();
    Scene scene = new Scene(pane,800,500, Color.TRANSPARENT);
    Character currentCharacter;
    Text nameSkill = new Text();
    Text cooldown = new Text();
    Stage stage;
    VBox vbGen = new VBox(20);
    VBox vbStat = new VBox();
    ArrayList<Character> listCharacter;
    ImageViewSizePos logo;




    public SkillsMenu(Stage stage, ArrayList<Character> characterArrayList,Character currentCharacter){
        this.stage = stage;
        this.currentCharacter = currentCharacter;
        listCharacter = characterArrayList;
        display();
        changeCharac();
        pane.setStyle("-fx-background-color: rgba(14, 14, 14, 0.7); -fx-background-radius: 100;");

    }

    /**
     * Put the character stats on a Vbox and show it.
     */
    public void display(){
        vbStat.getChildren().clear();
        vbGen.getChildren().clear();
        pane.getChildren().clear();
        nameCharacter.setText(currentCharacter.getName());
        logo = new ImageViewSizePos(currentCharacter.getLogo().getPathImage(),new Coordinate(10,10));
        //logo.setSize(100,100);

        nameCharacter.setLayoutX(300);
        nameCharacter.setLayoutY(60);

        nameCharacter.setFill(Color.WHITE);
        nameCharacter.setStyle("-fx-font-weight: bold");
        nameCharacter.setFont(Font.font("Tw Cen Mt Condensed",60));
        nameCharacter.setStyle("--fx-text-fill: white");

        for(int i = 1;i <= currentCharacter.getListSkill().size();i++ ){

            try {
                VBox vBox = new VBox();
                nameSkill = new Text(skillDBManager.getNameSkill(currentCharacter.getName(),i));
                description = new Text( skillDBManager.getDescription(currentCharacter.getName(),i));
                cooldown = new Text(skillDBManager.getTimeCooldown(currentCharacter.getName(), i) + " seconds");

                nameSkill.setFill(Color.WHITE);
                description.setFill(Color.WHITE);
                cooldown.setFill(Color.WHITE);

                nameSkill.setFont(Font.font("Tw Cen Mt Condensed",40));
                description.setFont(Font.font("Tw Cen Mt Condensed",30));
                cooldown.setFont(Font.font("Tw Cen Mt Condensed",20));


                vBox.getChildren().add(nameSkill);
                vBox.getChildren().add(description);
                vBox.getChildren().add(cooldown);

                vbGen.getChildren().add(vBox);


            } catch (SkillDataGetException e) {
                e.printStackTrace();
            }
        }

            try {
                List<String> stats = personDBManager.toArray(currentCharacter.getName());
                Text speed = new Text("Speed: "+ stats.get(1));
                Text weight  =new Text("Weight: "+ stats.get(2));
                speed.setFont(Font.font("Tw Cen Mt Condensed",30));
                weight.setFont(Font.font("Tw Cen Mt Condensed",30));


                speed.setFill(Color.WHITE);
                weight.setFill(Color.WHITE);
                vbStat.getChildren().add(speed);
                vbStat.getChildren().add(weight);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        vbGen.setLayoutX(300);
        vbGen.setLayoutY(70);
        vbStat.setLayoutY(300);
        pane.getChildren().addAll(vbGen,logo.getImageView(),nameCharacter,vbStat);



    }
    /**
     * Function that swap characters with a key pressed.
     */
    public void changeCharac(){

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> {
            String event = event1.getCode().getChar().toLowerCase();

            try {
                int k = 0;
                    if(event.equals(controlsDBManager.getSwitchDown())){

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
    /**
     * @return The current scene.
     */
    public Scene getScene() {
        return scene;
    }
    /**
     * @return The current pane.
     */
    public Pane getPane() {
        return pane;
    }
}
