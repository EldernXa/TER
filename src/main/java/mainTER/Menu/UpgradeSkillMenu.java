package mainTER.Menu;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mainTER.CharacterGameplay.Character;
import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.SkillDBManager;
import mainTER.exception.ControlsDataGetException;

import java.util.ArrayList;

public class UpgradeSkillMenu {

    private Pane pane = new Pane();
    private Scene scene = new Scene(pane,600,600);
    private Text nameCharacter = new Text();
    private int nbPoints = 5;
    private Button validateButton = new Button("Valider");
    private Text description = new Text();
    private ArrayList<Character> listCharacter;
    private Character currentCharacter;
    private SkillDBManager skillDBManager = new SkillDBManager();
    private ControlsDBManager controlsDBManager = new ControlsDBManager();
    private VBox vBoxSkill = new VBox(5);

    public UpgradeSkillMenu(ArrayList<Character> characterArrayList){
        listCharacter = characterArrayList;
        currentCharacter = listCharacter.get(0);
        displayMenu();
        changePerso();
    }



    public void displayMenu(){

        pane.getChildren().clear();
        vBoxSkill.getChildren().clear();

        nameCharacter = new Text(currentCharacter.getName());
        Text points = new Text("Nombre de points: " + nbPoints);
        for(int i = 1;i <= currentCharacter.getListSkill().size();i++ ){
            try {
                if(!skillDBManager.getNameSkill(currentCharacter.getName(), i).contains("ATTACK") &&
                        !skillDBManager.getNameSkill(currentCharacter.getName(), i).contains("WALL_JUMP") &&
                        !skillDBManager.getNameSkill(currentCharacter.getName(), i).contains("SHIELD")){


                    Button skill = new Button(skillDBManager.getNameSkill(currentCharacter.getName(),i));
                    eventButton(skill);
                    vBoxSkill.getChildren().add(skill);

                }
            }catch (Exception e){

            }
        }

        nameCharacter.setTranslateX(150);
        nameCharacter.setTranslateY(100);
        points.setTranslateX(400);
        points.setTranslateY(100);
        validateButton.setTranslateX(100);
        validateButton.setTranslateY(200);


        pane.getChildren().addAll(nameCharacter,vBoxSkill,validateButton,points);


    }

    public void eventButton(Button button){
        button.setOnMouseClicked(mouseEvent -> {
            nbPoints--;
            displayMenu();
        });
    }


    public void changePerso(){

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> {
            String event = event1.getCode().getChar().toLowerCase();

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
                    displayMenu();
                }
                else if(event.equals(controlsDBManager.getSwitchUp())){
                    int k = 0;
                    for (int i = 0; i < listCharacter.size(); i++) {
                        if (listCharacter.get(i) == currentCharacter) {
                            k = i;
                        }
                    }
                    currentCharacter = listCharacter.get((k + 1) % listCharacter.size());
                    displayMenu();
                }
            } catch (ControlsDataGetException e) {
                e.printStackTrace();
            }


        });
    }

    public Scene getScene() {
        return scene;
    }
}
