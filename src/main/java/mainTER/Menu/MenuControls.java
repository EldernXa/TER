package mainTER.Menu;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.PersonDBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuControls {

    ControlsDBManager controlsDBManager = new ControlsDBManager();
    List<String> listControls;
    Label labelTitre = new Label();
    private final StackPane pane = new StackPane();
    private final Scene scene = new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth()/1.5,Screen.getPrimary().getVisualBounds().getHeight()- (Screen.getPrimary().getVisualBounds().getHeight()/4));;

    VBox vbox = new VBox(20);



    public MenuControls() throws SQLException {

        controlsDBManager.dropCascade();
        controlsDBManager.createTableControls();
        controlsDBManager.insertIntoTableControls("d","q", " ","a", "e");
        listControls = controlsDBManager.toArray();

        pane.getChildren().addAll(vbox);
        pane.getChildren().add(labelTitre);
        controlDisplay();

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
                    button.setText("space");
                    break;
                default:
                    button.setText(button.getText());
                    break;
            }
            hbox.getChildren().addAll(label, button);
            vbox.getChildren().addAll(hbox);
            setControls(button,label);

        }
       vbox.setAlignment(Pos.CENTER_LEFT);
    }


        public void setControls(Button button,Label label){

        button.setOnMouseClicked(mouseEvent -> {

            String text = button.getText();
            labelTitre.setText("Cliquez sur un bouton et appuyez sur une touche.");
            button.setText("");
            button.setOnKeyPressed(keyEvent -> {
                int code = keyEvent.getCode().getCode();
                String control = keyEvent.getCode().getChar();


                if(listControls.contains(control.toLowerCase())){
                    labelTitre.setText("Ce caractère est deja utilisé");
                    button.setText(text);
                }else {
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
                            button.setText("space");
                            break;
                        default:
                            if ((code <= 110 && code >= 97) || code == 10 || code == 20 || code == 9 || code == 0) {
                                labelTitre.setText("Caractère non correct");
                            } else {
                                button.setText(keyEvent.getCode().getChar().toLowerCase());
                            }

                            break;
                    }
                    if ((code <= 110 && code >= 97) || code == 10 || code == 20 || code == 9 || code == 0) {
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
