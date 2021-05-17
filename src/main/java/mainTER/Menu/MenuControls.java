package mainTER.Menu;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.SkillDBManager;
import mainTER.Tools.ReturnBack;
import mainTER.exception.SkillCtrlAlreadyUsedByMovementControlException;
import mainTER.exception.SkillCtrlAlreadyUsedException;
import mainTER.exception.SkillDataGetException;

import java.sql.SQLException;
import java.util.List;

public class MenuControls {

    ControlsDBManager controlsDBManager = new ControlsDBManager();
    List<String> listControls;
    Label labelDesc = new Label();
    private final StackPane pane = new StackPane();
    private final Scene scene = new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth()/1.5,Screen.getPrimary().getVisualBounds().getHeight()- (Screen.getPrimary().getVisualBounds().getHeight()/7));

    VBox vbox = new VBox(20);
    VBox vBoxLabel = new VBox(10);
    VBox vBoxButton = new VBox(10);
    VBox vBoxSkill = new VBox();
    VBox vBoxButtonSkill = new VBox(10);
    VBox vBoxLabelSkill = new VBox(10);




    public MenuControls(Stage stage) throws SQLException {


        Label labelTitle = new Label("Controls");
        labelTitle.setFont(Font.font("Tw Cen Mt Condensed", 30));
        StackPane.setAlignment(labelTitle,Pos.TOP_CENTER);
        listControls = controlsDBManager.toArray();
        pane.getChildren().addAll(vbox,vBoxSkill,labelTitle);
        pane.getChildren().add(labelDesc);
        controlDisplay();
        controlSkillDisplay();

        ReturnBack.setRevenir(stage,stage.getScene(),pane);


    }

    /**
     * set the display for skills controls
     */

    public void controlSkillDisplay(){
        SkillDBManager skillDBManager = new SkillDBManager();
        List<String> listNameSkillCharacterWithSkill = skillDBManager.getListNameCharacterWithSkill();
        for(String nameCharacter : listNameSkillCharacterWithSkill){
            for(int i = 0 ; i<skillDBManager.getNumberSkillActiveOfACharacter(nameCharacter);i++) {


                HBox hbox = new HBox(10);
                Label labelNameSkill = new Label();
                Label labelSkill = new Label();
                Button button = new Button();
                try {
                    labelNameSkill.setText(skillDBManager.getNameSkill(nameCharacter, i+1) );
                    labelSkill.setText(skillDBManager.getNameSkill(nameCharacter, i+1).toLowerCase()+ " (" + nameCharacter+ ")");
                    button.setText(skillDBManager.getCtrlKey(nameCharacter, i+1));
                }catch(SkillDataGetException skillDataGetException){
                    skillDataGetException.printStackTrace();
                    System.out.println("Problème dans la récupération des données des compétences de " + nameCharacter + ".");
                }
                labelSet(hbox, labelSkill, button, vBoxButtonSkill, vBoxLabelSkill);


                vBoxSkill.getChildren().add(hbox);

                setControlsSkill(button, labelNameSkill, nameCharacter);
            }
        }
        vBoxSkill.setTranslateX(700);
        vBoxSkill.setTranslateY(300);
    }

    /**
     * set the label
     * @param hbox
     * @param labelNameSkill
     * @param button
     * @param vBoxButtonSkill
     * @param vBoxLabelSkill
     */
    private void labelSet(HBox hbox, Label labelNameSkill, Button button, VBox vBoxButtonSkill, VBox vBoxLabelSkill) {
        labelNameSkill.setFont(Font.font("Arial", 20));
        switch (button.getText()) {
            case "&":
                button.setText("↑");
                break;
            case "%":
                button.setText("←");
                break;
            case "(":
                button.setText("↓");
                break;
            case "'":
                button.setText("→");
                break;
            case " ":
                button.setText("SPACE");
                break;
            default:
                button.setText(button.getText().toUpperCase());
                break;
        }


        vBoxButtonSkill.getChildren().add(button);
        vBoxLabelSkill.getChildren().add(labelNameSkill);
        hbox.getChildren().addAll(vBoxLabelSkill, vBoxButtonSkill);
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * set the display of controls
     */
    public void controlDisplay() {

        for (int i = 0; i <listControls.size(); i++) {
            HBox hbox = new HBox(10);
            Label label = new Label();
            switch (i){
                case 1 : {
                    label.setText("Left");
                    break;
                }
                case 2: {
                    label.setText("Jump");
                    break;
                }
                case 3:{
                    label.setText("SwitchUp");
                    break;
                }
                case 4:{
                    label.setText("SwitchDown");
                    break;
                } case 0:{
                    label.setText("Right");
                    break;
                }
                case 5: {
                    label.setText("Action");
                }

            }

            Button button = new Button(listControls.get(i));
            labelSet(hbox, label, button, vBoxButton, vBoxLabel);

            vbox.getChildren().addAll(hbox);
            setControls(button,label);

        }
        vbox.setAlignment(Pos.CENTER_LEFT);
    }

    /**
     * set the control of skills
     * @param buttonCtrlKey
     * @param labelNameSkill
     * @param nameCharacter
     */
    public void setControlsSkill(Button buttonCtrlKey, Label labelNameSkill, String nameCharacter){
        SkillDBManager skillDBManager = new SkillDBManager();
        buttonCtrlKey.setOnMouseClicked(
                mouseEvent->{
                    String text = buttonCtrlKey.getText();
                    labelDesc.setText("Cliquez sur un bouton et appuyez sur une touche.");
                    buttonCtrlKey.setText("");
                    buttonCtrlKey.setOnKeyPressed(keyEvent->{
                        int code = keyEvent.getCode().getCode();
                        String control = keyEvent.getCode().getChar().toLowerCase();

                        if(skillDBManager.getCtrlKeyOfACharacter(nameCharacter).contains(control.toLowerCase())){
                            labelDesc.setText("Ce caractère est déjà utilisé.");
                            buttonCtrlKey.setText(text);
                        }else{
                            boolean correctChar = (code <= 110 && code >= 97) || code == 10 || code == 20 || code == 9 || code == 0|| code == 27;
                            switch (control) {
                                case "&":
                                    buttonCtrlKey.setText("↑");
                                    break;
                                case "%":
                                    buttonCtrlKey.setText("←");
                                    break;
                                case "(":
                                    buttonCtrlKey.setText("↓");
                                    break;
                                case "'":
                                    buttonCtrlKey.setText("→");
                                    break;
                                case " ":
                                    buttonCtrlKey.setText("SPACE");
                                    break;
                                default:
                                    if (correctChar) {
                                        labelDesc.setText("Caractère non correct");
                                    } else {
                                        buttonCtrlKey.setText(keyEvent.getCode().getChar().toUpperCase());
                                    }

                                    break;
                            }

                            if(correctChar){
                                labelDesc.setText("Caractère non correct.");
                            }else{
                                try {
                                    skillDBManager.modifyCtrlOfACharacter(nameCharacter, labelNameSkill.getText(), control);
                                    labelDesc.setText("");
                                }catch(SkillCtrlAlreadyUsedException skillCtrlAlreadyUsedException){
                                    labelDesc.setText("Ce caractère est déjà utilisé par le même personnage.");
                                    buttonCtrlKey.setText(text);
                                }catch(SkillCtrlAlreadyUsedByMovementControlException skillCtrlAlreadyUsedByMovementControlException){
                                    labelDesc.setText("Ce caractère est déjà utilisée par les mouvements basique du personnage.");
                                    buttonCtrlKey.setText(text);
                                }
                            }

                        }
                    });
                    scene.setOnMouseClicked(mouseEvent1 -> {
                        buttonCtrlKey.setText(text);
                        buttonCtrlKey.setOnKeyPressed(null);
                        scene.setOnMouseClicked(null);
                    });
                });
    }

    /**
     * set the controls
     * @param button
     * @param label
     */

    public void setControls(Button button,Label label){

        button.setOnMouseClicked(mouseEvent -> {

            String text = button.getText();
            labelDesc.setText("Cliquez sur un bouton et appuyez sur une touche.");
            button.setText("");
            button.setOnKeyPressed(keyEvent -> {
                int code = keyEvent.getCode().getCode();
                String control = keyEvent.getCode().getChar().toLowerCase();


                if(listControls.contains(control.toLowerCase())){
                    labelDesc.setText("Ce caractère est deja utilisé");
                    button.setText(text);
                }else {
                    boolean corectChar = (code <= 110 && code >= 97) || code == 10 || code == 20 || code == 9 || code == 0|| code == 27 ;
                    switch (control) {
                        case "&":
                            button.setText("↑");
                            break;
                        case "%":
                            button.setText("←");
                            break;
                        case "(":
                            button.setText("↓");
                            break;
                        case "'":
                            button.setText("→");
                            break;
                        case " ":
                            button.setText("SPACE");
                            break;
                        default:
                            if (corectChar) {
                                labelDesc.setText("Caractère non correct");
                            } else {
                                button.setText(keyEvent.getCode().getChar().toUpperCase());
                            }

                            break;
                    }
                    if (corectChar) {
                        labelDesc.setText("Caractère non correct");

                    } else {
                        switch (label.getText().toLowerCase()) {
                            case "right": {
                                controlsDBManager.setRight(control);
                                break;
                            }
                            case "left": {
                                controlsDBManager.setLeft(control);
                                break;
                            }
                            case "jump": {
                                controlsDBManager.setJump(control);
                                break;
                            }
                            case "switchUp": {
                                controlsDBManager.setSwitchUp(control);
                                break;
                            }
                            case "switchDown": {
                                controlsDBManager.setSwitchDown(control);
                                break;
                            }
                            case "action": {
                                controlsDBManager.setAction(control);
                                break;
                            }
                        }
                    }
                }
                try {
                    listControls = controlsDBManager.toArray();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                button.setOnKeyPressed(null);

            });

            scene.setOnMouseClicked(mouseEvent1 -> {
                button.setText(text);
                button.setOnKeyPressed(null);
                scene.setOnMouseClicked(null);
            });
        });



    }
}
