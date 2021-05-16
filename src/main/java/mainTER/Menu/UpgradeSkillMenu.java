package mainTER.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainTER.CharacterGameplay.Character;
import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.SkillDBManager;
import mainTER.DBManage.UpgradeSkillDBManager;
import mainTER.Tools.ReturnBack;
import mainTER.exception.SkillDataGetException;
import mainTER.exception.UpgradeSkillDataGetException;

import java.util.ArrayList;

import static mainTER.Menu.MainMenu.createContent;

/**
 * A menu that allow you to put points you earned on the game to boost the skills of characters.
 */
public class UpgradeSkillMenu {

    private final Pane pane = new Pane();
    private final Scene scene = new Scene(pane, 600, 600);
    private final Stage stage;
    private int nbPoints = 15;
    private final Button validateButton = new Button("Confirm");
    private final Text description = new Text();
    private final ArrayList<Character> listCharacter;
    private Character currentCharacter;
    private final SkillDBManager skillDBManager = new SkillDBManager();
    private final ControlsDBManager controlsDBManager = new ControlsDBManager();
    private final VBox vBoxSkill = new VBox(15);
    private String nameSkill;
    private int updateNumberCD;
    private int updateNumberTime;
    UpgradeSkillDBManager upgradeSkillDBManager;

    /**
     *
     * @param characterArrayList arrayList of charaters
     * @param stage Current Stage
     */
    public UpgradeSkillMenu(ArrayList<Character> characterArrayList, Stage stage) {
        listCharacter = characterArrayList;
        currentCharacter = listCharacter.get(0);
        upgradeSkillDBManager = new UpgradeSkillDBManager();
        updateNumberTime = 0;
        this.stage= stage;
        displayMenu();
        changePerso();
        eventButton(validateButton);

        ReturnBack.setRevenir(stage, new Scene(createContent(stage)), pane);

    }

    /**
     * Show the buttons and the text of the menu, it take ressources from upgradeSkillDBManager
     */
    public void displayMenu() {

        pane.getChildren().clear();
        vBoxSkill.getChildren().clear();

        Text nameCharacter = new Text(currentCharacter.getName());
        Text points = new Text("Points: " + nbPoints);


        Text totalText;
        Text priceText = new Text("");
        HBox hBox;
        VBox textVBox;
        for (int i = 1; i <= currentCharacter.getListSkill().size(); i++) {
            textVBox = new VBox();
            hBox = new HBox();
            try {

                if (!skillDBManager.getNameSkill(currentCharacter.getName(), i).contains("ATTACK") &&
                        !skillDBManager.getNameSkill(currentCharacter.getName(), i).contains("WALL_JUMP") &&
                        !skillDBManager.getNameSkill(currentCharacter.getName(), i).contains("SHIELD")
                ) {


                    Button skill = new Button(skillDBManager.getNameSkill(currentCharacter.getName(), i));

                    eventButton(skill);
                    for (int k = 0; k < upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(), i).size(); k++) {
                        String line = upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(), i).get(k);

                        String[] line2 = line.split("\\s+");

                        int lastChar = Integer.parseInt(line2[line2.length - 1]);

                        String nameUpgrade = line.substring(0, line.length() - 2);

                        int price = upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(), i, nameUpgrade, lastChar);


                        float value = upgradeSkillDBManager.getNewValueWithNumUpgrade(currentCharacter.getName(), i, nameUpgrade, lastChar);

                        priceText = new Text("Cost : " + price);
                        if (nameUpgrade.contains("Reduce")) {
                            if (!upgradeSkillDBManager.getIsAlreadyDoneWithNumUpgrade(currentCharacter.getName(), i, nameUpgrade, lastChar) && value > 0) {

                                totalText = new Text(nameUpgrade + " from " + value + " to " + (value - 2));
                            } else
                                totalText = null;
                        } else {
                            if (!upgradeSkillDBManager.getIsAlreadyDoneWithNumUpgrade(currentCharacter.getName(), i, nameUpgrade, lastChar)) {

                                totalText = new Text(nameUpgrade + " from " + value + " to " + (value + 1));
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
        vBoxSkill.setTranslateX(10);
        vBoxSkill.setTranslateY(90);
        nameCharacter.setTranslateX(10);
        nameCharacter.setTranslateY(80);
        nameCharacter.setFont(Font.font("Tw Cen Mt Condensed", 40));
        nameCharacter.setStyle("-fx-font-weight: bold");
        points.setTranslateX(450);
        points.setTranslateY(80);
        points.setFont(Font.font("Tw Cen Mt Condensed", 15));

        validateButton.setTranslateX(10);
        validateButton.setTranslateY(230);
        validateButton.setStyle("-fx-font-size:15");


        pane.getChildren().addAll(nameCharacter, vBoxSkill, validateButton, points);
        ReturnBack.setRevenir(stage, new Scene(createContent(stage)), pane);



    }

    /**
     * Assign task to buttons that make the skill upgrade if you have enough points.
     * @param button Button from skills and the Confirm button
     */
    public void eventButton(Button button) {


        button.setOnMouseClicked(mouseEvent -> {
            if (button.getText().equals("Confirm")) {
                if (nameSkill != null) {
                    boolean isUpgradable = false;
                    try {
                        for (int i = 1; i <= currentCharacter.getListSkill().size(); i++) {
                            updateNumberCD = upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(), i).size() - 1;

                            if (skillDBManager.getNameSkill(currentCharacter.getName(), i).contains(nameSkill) && nbPoints  > 0) {


                                float currentTime = skillDBManager.getTimeSkill(currentCharacter.getName(), i);
                                float currentCD = skillDBManager.getTimeCooldown(currentCharacter.getName(), i);

                                String lineCD = upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(), i).get(updateNumberCD);
                                int lastCharCD = Integer.parseInt(lineCD.substring(lineCD.length() - 1));
                                String nameUpgradeCD = lineCD.substring(0, lineCD.length() - 2);

                                if((nbPoints - upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(), i, nameUpgradeCD, lastCharCD)) > 0){

                                    int increaseID = upgradeSkillDBManager.getLastNumOfAUpgrade(currentCharacter.getName(), i, "Increase the duration");
                                    int lastCharTime = 0;
                                    String nameUpgradeTime = "";
                                    if (increaseID != -1) {
                                        String lineTime = upgradeSkillDBManager.getListUpgrade(currentCharacter.getName(), i).get(increaseID);
                                        lastCharTime = Integer.parseInt(lineTime.substring(lineTime.length() - 1));
                                        nameUpgradeTime = lineTime.substring(0, lineTime.length() - 2);
                                    }


                                    if ((currentCD - 2) >= 0 && !upgradeSkillDBManager.getIsAlreadyDoneWithNumUpgrade(currentCharacter.getName(), i, nameUpgradeCD, lastCharCD)) {
                                        int price = upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(), i, nameUpgradeCD, lastCharCD);
                                        skillDBManager.modifyTimeCooldown(currentCharacter.getName(), nameSkill, currentCD - 2);

                                        upgradeSkillDBManager.setUpgradeDone(currentCharacter.getName(), i,
                                                nameUpgradeCD);
                                        upgradeSkillDBManager.insertIntoTableUpgradeSkill(currentCharacter.getName(), i, "Reduce the cooldown", currentCD - 2, price + 1, "Reduce the cooldown2");
                                        isUpgradable = true;
                                    }
                                    if (currentTime != 0 && !upgradeSkillDBManager.getIsAlreadyDoneWithNumUpgrade(currentCharacter.getName(), i, nameUpgradeTime, lastCharTime)) {
                                        int price = upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(), i, nameUpgradeTime, lastCharTime);
                                        skillDBManager.modifyTimeSkill(currentCharacter.getName(), nameSkill, currentTime + 1);
                                        upgradeSkillDBManager.setUpgradeDone(currentCharacter.getName(), i,
                                                nameUpgradeTime);
                                        upgradeSkillDBManager.insertIntoTableUpgradeSkill(currentCharacter.getName(), i, "Increase the duration", currentTime + 1, price + 1, "Increase the duration2");

                                        isUpgradable = true;
                                        updateNumberTime++;
                                    }
                                    if (isUpgradable) {


                                        nbPoints = nbPoints - upgradeSkillDBManager.getPriceWithNumUpgrade(currentCharacter.getName(), i, nameUpgradeCD, lastCharCD);
                                        displayMenu();
                                    }
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

    /**
     * Funtion that swap characters with a key pressed.
     */
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

    /**
     * @return The current scene.
     */
    public Scene getScene() {
        return scene;
    }
}
