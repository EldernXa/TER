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
        updateNumberCD = 0;
        updateNumberTime = 1;
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
                    for (int k = 0; k < upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).size(); k++) {

                        int price = upgradeSkillDBManager.getPrice(currentCharacter.getName(), i, upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(k));
                        value = upgradeSkillDBManager.getNewValue(currentCharacter.getName(), i, upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(k));
                        String nameUpgrade = upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(k);
                        priceText = new Text("Cost : " + price);


                        if (nameUpgrade.contains("Reduce")) {
                            if (!upgradeSkillDBManager.getIsAlreadyDone(currentCharacter.getName(), i, nameUpgrade)) {

                                totalText = new Text(nameUpgrade + " from " + value + " to " + (value - 2));
                            } else
                                totalText = null;
                        } else {
                            if (!upgradeSkillDBManager.getIsAlreadyDone(currentCharacter.getName(), i, nameUpgrade)) {

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
                            if (skillDBManager.getNameSkill(currentCharacter.getName(), i).contains(nameSkill) && nbPoints > 0) {

                                float currentTime = skillDBManager.getTimeSkill(currentCharacter.getName(), i);
                                float currentCD = skillDBManager.getTimeCooldown(currentCharacter.getName(), i);

                                if ((currentCD - 2) > 0 /*&& !upgradeSkillDBManager.getIsAlreadyDone(currentCharacter.getName(), i, upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(0))*/) {
                                    int price = upgradeSkillDBManager.getPrice(currentCharacter.getName(), i, upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(0));
                                    skillDBManager.modifyTimeCooldown(currentCharacter.getName(), nameSkill, currentCD - 2);

                                    upgradeSkillDBManager.setUpgradeDone(currentCharacter.getName(), i,
                                            upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(0));
                                    upgradeSkillDBManager.insertIntoTableUpgradeSkill(currentCharacter.getName(), i, "Reduce the cooldown " + updateNumberCD, currentCD - 2, price + 1, "Reduce the cooldown2");
                                    isUpgradable = true;
                                    updateNumberCD++;
                                }
                                if (currentTime != 0 /*&& !upgradeSkillDBManager.getIsAlreadyDone(currentCharacter.getName(), i, upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(1))*/) {
                                    int price = upgradeSkillDBManager.getPrice(currentCharacter.getName(), i, upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(1));

                                    skillDBManager.modifyTimeSkill(currentCharacter.getName(), nameSkill, currentTime + 1);
                                    upgradeSkillDBManager.setUpgradeDone(currentCharacter.getName(), i,
                                            upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(1));
                                    upgradeSkillDBManager.insertIntoTableUpgradeSkill(currentCharacter.getName(), i, "Increase the duration " + updateNumberTime, currentTime - 2, price + 1, "Increase the duration2");

                                    isUpgradable = true;
                                    updateNumberTime+=1;
                                }
                                if (isUpgradable){
                                    nbPoints = nbPoints - upgradeSkillDBManager.getPrice(currentCharacter.getName(), i, upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(currentCharacter.getName(), i).get(0));

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
