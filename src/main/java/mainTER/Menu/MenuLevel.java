package mainTER.Menu;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainTER.Tools.ReturnBack;

import static mainTER.Menu.MainMenu.createContent;

public class MenuLevel extends StackPane {

    TextField pseudo ;
    private final Pane pane = new Pane();

    public MenuLevel(Stage stage) {

        MenuBox vbox = new MenuBox(
                new MenuItem("Level 1 - Forest", stage),
                new MenuItem("Level 2 - Castle", stage),
                new MenuItem("Level 3 - City", stage)
        );

        vbox.setTranslateX(170);
        vbox.setTranslateY(250);
        vbox.setSpacing(5);
        if(MenuItem.pseudo == null){
            pseudo = new TextField("Pseudo");

        }else {
            pseudo = new TextField(MenuItem.pseudo.getText());
        }
        pseudo.setTranslateX(170);
        pseudo.setTranslateY(220);
        pane.getChildren().addAll(vbox,pseudo);
        stage.initStyle(StageStyle.UNDECORATED);
        ReturnBack.setRevenir(stage,new Scene(createContent(stage)),pane);

    }




    public TextField getPseudo() {
        return pseudo;
    }

    public Pane getPane() {
        return pane;
    }
}
