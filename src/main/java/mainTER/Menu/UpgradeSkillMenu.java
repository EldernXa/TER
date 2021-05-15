package mainTER.Menu;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mainTER.CharacterGameplay.Character;
import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.SkillDBManager;
import mainTER.DBManage.UpgradeSkillDBManager;
import mainTER.exception.ControlsDataGetException;
import mainTER.exception.SkillDataGetException;
import mainTER.exception.UpgradeSkillDataGetException;

import java.util.ArrayList;

public class UpgradeSkillMenu {

    private Pane pane = new Pane();
    private Scene scene = new Scene(pane, 600, 600);
    private Text nameCharacter = new Text();
    private int nbPoints = 15;
    private Button validateButton = new Button("Confirm");
    private Text description = new Text();
    private ArrayList<Character> listCharacter;
    private Character currentCharacter;
    private SkillDBManager skillDBManager = new SkillDBManager();
    private ControlsDBManager controlsDBManager = new ControlsDBManager();
    private VBox vBoxSkill = new VBox(5);
    private Text points;
    private String nameSkill;
    private float value;
    private int updateNumberCD;
    private int updateNumberTime;
    UpgradeSkillDBManager upgradeSkillDBManager;


    public UpgradeSkillMenu(ArrayList<Character> characterArrayList) {
        listCharacter = characterArrayList;
        currentCharacter = listCharacter.get(0);
        upgradeSkillDBManager = new UpgradeSkillDBManager();

        updateNumberTime = 0;
        displayMenu();
        changePerso();
        eventButton(validateButton);
    }


    public void displayMenu() {

        pane.getChildren().clear();
        vBoxSkill.getChildren().clear();

        nameCharacter = new Text(currentCharacter.getName());
        points = new Text("Nombre de points: " + nbPoints);


        Text totalText = new Text("");
        Text priceText = new Text("");
        HBox hBox;
        VBox textVBox;
        for (int i = 1; i <= currentCharacter.getListSkill().size(); i++) {
            textVBox = new VBox();
            hBox = new HBox();
            try {

                if (!skillDBManager.getNameSkill(currentCharacter.getName(), i).contains("ATTACK") &&
                        !skillDBManager.getNameSkill(currentCharacter.getName(), i).contains("WALL_JUMP")) {


                    Button skill = new Button(skillDBManager.getNameSkill(currentCharacter.getName(), i));

                    eventButton(skill);
                    for (int k = 0; k < upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(), i).size(); k++) {
                        String line= upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(),i).get(k);

                        int lastChar = Integer.parseInt(line.substring(line.length() - 1));

                        String nameUpgrade = line.substring(0,line.length()-2);


                        int price = upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(),i,nameUpgrade,lastChar);
                        
                        
                        value =upgradeSkillDBManager.getNewValueWithNumUpgrade(currentCharacter.getName(),i,nameUpgrade,lastChar);
                        
                        priceText = new Text("Cost : " + price);
                        if (nameUpgrade.contains("Reduce")) {
                            if (!upgradeSkillDBManager.getIsAlreadyDoneWithNumUpgrade(currentCharacter.getName(), i, nameUpgrade,lastChar) && value>0) {

                                totalText = new Text(nameUpgrade + " from " + value + " to " + (value - 2));
                            } else
                                totalText = null;
                        } else {
                            if (!upgradeSkillDBManager.getIsAlreadyDoneWithNumUpgrade(currentCharacter.getName(), i, nameUpgrade,lastChar)) {

                                totalText = new Text(nameUpgrade + " from " + value + " to " + (value + 2));
                            } else
                                totalText = null;
                        }


                        if (totalText != null) {

                            textVBox.getChildren().add(totalText);
                        }


                    }
                    textVBox.getChildren().add(0, priceText);
                    hBox.getChildren().addAll(skill, textVBox);
                    vBoxSkill.getChildren().add(hBox);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        nameCharacter.setTranslateX(150);
        nameCharacter.setTranslateY(100);
        points.setTranslateX(400);
        points.setTranslateY(100);
        validateButton.setTranslateX(100);
        validateButton.setTranslateY(200);


        pane.getChildren().addAll(nameCharacter, vBoxSkill, validateButton, points);


    }


    public void eventButton(Button button) {


        button.setOnMouseClicked(mouseEvent -> {
            if (button.getText().equals("Confirm")) {
                if (nameSkill != null) {
                    boolean isUpgradable = false;
                    try {
                        for (int i = 1; i <= currentCharacter.getListSkill().size(); i++) {
                            updateNumberCD = upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(),i).size()-1;
                            if (skillDBManager.getNameSkill(currentCharacter.getName(), i).contains(nameSkill) && nbPoints > 0) {


                                float currentTime = skillDBManager.getTimeSkill(currentCharacter.getName(), i);
                                float currentCD = skillDBManager.getTimeCooldown(currentCharacter.getName(), i);


                                String lineCD= upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(),i).get(updateNumberCD);
                                int lastCharCD = Integer.parseInt(lineCD.substring(lineCD.length() - 1));
                                String nameUpgradeCD = lineCD.substring(0,lineCD.length()-2);


                                int increaseID = upgradeSkillDBManager.getLastNumOfAUpgrade(currentCharacter.getName(),i,"Increase the duration");
                                int lastCharTime=0;
                                String nameUpgradeTime="";
                                if (increaseID!=-1){
                                    String lineTime= upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(),i).get(increaseID);
                                     lastCharTime = Integer.parseInt(lineTime.substring(lineTime.length() - 1));
                                     nameUpgradeTime = lineTime.substring(0,lineTime.length()-2);
                                }



                                if ((currentCD - 2) >= 0 && !upgradeSkillDBManager.getIsAlreadyDoneWithNumUpgrade(currentCharacter.getName(), i, nameUpgradeCD,lastCharCD)) {
                                    int price =  upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(),i,nameUpgradeCD,lastCharCD);
                                    skillDBManager.modifyTimeCooldown(currentCharacter.getName(), nameSkill, currentCD - 2);

                                    upgradeSkillDBManager.setUpgradeDone(currentCharacter.getName(), i,
                                            nameUpgradeCD);
                                    upgradeSkillDBManager.insertIntoTableUpgradeSkill(currentCharacter.getName(), i, "Reduce the cooldown", currentCD - 2, price + 1, "Reduce the cooldown2");
                                    isUpgradable = true;
                                }
                                if (currentTime != 0 && !upgradeSkillDBManager.getIsAlreadyDoneWithNumUpgrade(currentCharacter.getName(), i, nameUpgradeTime,lastCharTime)) {
                                    int price =upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(),i,nameUpgradeTime,lastCharTime);
                                    skillDBManager.modifyTimeSkill(currentCharacter.getName(), nameSkill, currentTime + 1);
                                    upgradeSkillDBManager.setUpgradeDone(currentCharacter.getName(), i,
                                            nameUpgradeTime);
                                    upgradeSkillDBManager.insertIntoTableUpgradeSkill(currentCharacter.getName(), i, "Increase the duration", currentTime - 2, price + 1, "Increase the duration2");

                                    isUpgradable = true;
                                    updateNumberTime++;
                                }
                                if (isUpgradable){


                                    nbPoints = nbPoints - upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(),i,nameUpgradeCD,lastCharCD);
                                    displayMenu();
                                }

                            }

                        }
                    } catch (SkillDataGetException | UpgradeSkillDataGetException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                nameSkill = button.getText();

            }


        });
    }


    public void changePerso() {

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> {
            String event = event1.getCode().getChar().toLowerCase();

            try {

                if (event.equals(controlsDBManager.getSwitchDown())) {
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
                } else if (event.equals(controlsDBManager.getSwitchUp())) {
                    int k = 0;
                    for (int i = 0; i < listCharacter.size(); i++) {
                        if (listCharacter.get(i) == currentCharacter) {
                            k = i;
                        }
                    }
                    currentCharacter = listCharacter.get((k + 1) % listCharacter.size());
                    displayMenu();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
    }

    public Scene getScene() {
        return scene;
    }
}
