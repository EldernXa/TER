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
    Label labelTitre = new Label();
    private final StackPane pane = new StackPane();
    private final Scene scene = new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth()/1.5,Screen.getPrimary().getVisualBounds().getHeight()- (Screen.getPrimary().getVisualBounds().getHeight()/7));

    VBox vbox = new VBox(20);
    VBox buttonVboxControl = new VBox();
    VBox vBox = new VBox(20);



    public MenuControls(Stage stage) throws SQLException {


        listControls = controlsDBManager.toArray();
        pane.getChildren().addAll(vbox,vBox, buttonVboxControl);
        pane.getChildren().add(labelTitre);
        controlDisplay();
        controlSkillDisplay();

        ReturnBack.setRevenir(stage,stage.getScene(),pane);


    }

    public void controlSkillDisplay(){
        SkillDBManager skillDBManager = new SkillDBManager();
        List<String> listNameSkillCharacterWithSkill = skillDBManager.getListNameCharacterWithSkill();
        for(String nameCharacter : listNameSkillCharacterWithSkill){
            Label name = new Label(nameCharacter);
            name.setFont(Font.font("Arial", 30));
            vBox.getChildren().add(name);
            for(int i = 0 ; i<skillDBManager.getNumberSkillActiveOfACharacter(nameCharacter);i++) {
                HBox hbox = new HBox(10);
                Label labelNameSkill = new Label();
                Button button = new Button();
                button.setStyle(" -fx-background-color: "+
                        "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
                        "        linear-gradient(#020b02, #3a3a3a)" +
                        "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%)," +
                        "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%)," +
                        "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);" +
                        "    -fx-background-insets: 0,1,4,5,6;" +
                        "    -fx-background-radius: 9,8,5,4,3;" +
                        "    -fx-padding: 15 30 15 30;" +
                        "    -fx-font-family: \"Helvetica\";" +
                        "    -fx-font-size: 5px;" +
                        "    -fx-font-weight: bold;" +
                        "    -fx-text-fill: white;" +
                        "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
                try {
                    labelNameSkill.setText(skillDBManager.getNameSkill(nameCharacter, i+1).toLowerCase());
                    button.setText(skillDBManager.getCtrlKey(nameCharacter, i+1));
                }catch(SkillDataGetException skillDataGetException){
                    skillDataGetException.printStackTrace();
                    System.out.println("Problème dans la récupération des données des compétences de " + nameCharacter + ".");
                }
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

                hbox.getChildren().addAll(labelNameSkill,button);
                vBox.getChildren().addAll(hbox);

                vBox.setTranslateX(700);
                vBox.setTranslateY(100);

                setControlsSkill(button, labelNameSkill, nameCharacter);
            }
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void controlDisplay() {

        for (int i = 0; i <listControls.size(); i++) {
            HBox hbox = new HBox(10);
            Label label = new Label();
            switch (i){
                case 1 : {
                    label.setText("left");
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
            button.setStyle(" -fx-background-color: "+
                    "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
                    "        linear-gradient(#020b02, #3a3a3a)" +
                    "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%)," +
                    "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%)," +
                    "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);" +
                    "    -fx-background-insets: 0,1,4,5,6;" +
                    "    -fx-background-radius: 9,8,5,4,3;" +
                    "    -fx-padding: 15 30 15 30;" +
                    "    -fx-font-family: \"Helvetica\";" +
                    "    -fx-font-size: 9px;" +
                    "    -fx-font-weight: bold;" +
                    "    -fx-text-fill: white;" +
                    "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
            label.setFont(Font.font("Arial", 20));
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
            hbox.getChildren().addAll(label);
            vbox.getChildren().addAll(hbox);
            buttonVboxControl.getChildren().add(button);
            setControls(button,label);

        }
        buttonVboxControl.setTranslateX(150);
        buttonVboxControl.setTranslateY(230);
        vbox.setAlignment(Pos.CENTER_LEFT);
    }

    public void setControlsSkill(Button buttonCtrlKey, Label labelNameSkill, String nameCharacter){
        SkillDBManager skillDBManager = new SkillDBManager();
        buttonCtrlKey.setOnMouseClicked(
                mouseEvent->{
                    String text = buttonCtrlKey.getText();
                    labelTitre.setText("Cliquez sur un bouton et appuyez sur une touche.");
                    buttonCtrlKey.setText("");
                    buttonCtrlKey.setOnKeyPressed(keyEvent->{
                        int code = keyEvent.getCode().getCode();
                        String control = keyEvent.getCode().getChar().toLowerCase();

                        if(skillDBManager.getCtrlKeyOfACharacter(nameCharacter).contains(control.toLowerCase())){
                            labelTitre.setText("Ce caractère est déjà utilisé.");
                            buttonCtrlKey.setText(text);
                        }else{
                            System.out.println(code);
                            boolean correctChar = (code <= 110 && code >= 97) || code == 10 || code == 20 || code == 9 || code == 0 || code == 27;
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
                                        labelTitre.setText("Caractère non correct");
                                    } else {
                                        buttonCtrlKey.setText(keyEvent.getCode().getChar().toUpperCase());
                                    }

                                    break;
                            }

                            if(correctChar){
                                labelTitre.setText("Caractère non correct.");
                            }else{
                                try {
                                    skillDBManager.modifyCtrlOfACharacter(nameCharacter, labelNameSkill.getText(), control);
                                    labelTitre.setText("");
                                }catch(SkillCtrlAlreadyUsedException skillCtrlAlreadyUsedException){
                                    labelTitre.setText("Ce caractère est déjà utilisé par le même personnage.");
                                    buttonCtrlKey.setText(text);
                                }catch(SkillCtrlAlreadyUsedByMovementControlException skillCtrlAlreadyUsedByMovementControlException){
                                    labelTitre.setText("Ce caractère est déjà utilisé par les mouvements basique du personnage.");
                                    buttonCtrlKey.setText(text);
                                }
                            }
                            buttonCtrlKey.setOnKeyPressed(null);
                        }
                    });
                }
        );
    }

        public void setControls(Button button,Label label){

        button.setOnMouseClicked(mouseEvent -> {

            String text = button.getText();
            labelTitre.setText("Cliquez sur un bouton et appuyez sur une touche.");
            button.setText("");
            button.setOnKeyPressed(keyEvent -> {
                int code = keyEvent.getCode().getCode();
                String control = keyEvent.getCode().getChar().toLowerCase();


                if(listControls.contains(control.toLowerCase())){
                    System.out.println(listControls);
                    labelTitre.setText("Ce caractère est deja utilisé");
                    button.setText(text);
                }else {
                    boolean corectChar = (code <= 110 && code >= 97) || code == 10 || code == 20 || code == 9 || code == 0 || code == 27;
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
                                labelTitre.setText("Caractère non correct");
                            } else {
                                button.setText(keyEvent.getCode().getChar().toUpperCase());
                            }

                            break;
                    }
                    if (corectChar) {
                        labelTitre.setText("Caractère non correct");

                    } else {
                        switch (label.getText()) {
                            case "Right": {
                                controlsDBManager.setRight(control);
                                try {
                                    System.out.println("on passe ici"+controlsDBManager.toArray());
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                break;
                            }
                            case "Left": {
                                controlsDBManager.setLeft(control);
                                break;
                            }
                            case "Jump": {
                                controlsDBManager.setJump(control);
                                break;
                            }
                            case "SwitchUp": {
                                controlsDBManager.setSwitchUp(control);
                                break;
                            }
                            case "SwitchDown": {
                                controlsDBManager.setSwitchDown(control);
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

        });

        }
}
