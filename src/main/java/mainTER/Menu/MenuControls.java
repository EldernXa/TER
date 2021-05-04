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
import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.SkillDBManager;
import mainTER.exception.SkillCtrlAlreadyUsedException;
import mainTER.exception.SkillDataGetException;

import java.sql.SQLException;
import java.util.List;

public class MenuControls {

    ControlsDBManager controlsDBManager = new ControlsDBManager();
    List<String> listControls;
    Label labelTitre = new Label();
    private final StackPane pane = new StackPane();
    private final Scene scene = new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth()/1.5,Screen.getPrimary().getVisualBounds().getHeight()- (Screen.getPrimary().getVisualBounds().getHeight()/4));

    VBox vbox = new VBox(20);



    public MenuControls() throws SQLException {

        /*controlsDBManager.dropCascade();
        /controlsDBManager.createTableControls();
        //controlsDBManager.insertIntoTableControls("d","q", " ","a", "e"); */
        listControls = controlsDBManager.toArray();

        pane.getChildren().addAll(vbox);
        pane.getChildren().add(labelTitre);
        controlDisplay();
        controlSkillDisplay();

    }

    public void controlSkillDisplay(){
        SkillDBManager skillDBManager = new SkillDBManager();
        List<String> listNameSkillCharacterWithSkill = skillDBManager.getListNameCharacterWithSkill();
        for(String nameCharacter : listNameSkillCharacterWithSkill){
            vbox.getChildren().add(new Label(nameCharacter));
            for(int i = 0 ; i<skillDBManager.getNumberSkillActiveOfACharacter(nameCharacter);i++) {
                HBox hbox = new HBox(10);
                Label labelNameSkill = new Label();
                Button button = new Button();
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

                hbox.getChildren().addAll(labelNameSkill, button);
                vbox.getChildren().addAll(hbox);
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
                    label.setText("jump");
                    break;
                }
                case 3:{
                    label.setText("switchUp");
                    break;
                }
                case 4:{
                    label.setText("switchDown");
                    break;
                } case 0:{
                    label.setText("right");
                    break;
                }

            }

            Button button = new Button(listControls.get(i));
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
            hbox.getChildren().addAll(label, button);
            vbox.getChildren().addAll(hbox);
            setControls(button,label);

        }
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
                            boolean correctChar = (code <= 110 && code >= 97) || code == 10 || code == 20 || code == 9 || code == 0;
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
                    labelTitre.setText("Ce caractère est deja utilisé");
                    button.setText(text);
                }else {
                    boolean corectChar = (code <= 110 && code >= 97) || code == 10 || code == 20 || code == 9 || code == 0;
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
