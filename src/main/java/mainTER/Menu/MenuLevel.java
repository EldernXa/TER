package mainTER.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainTER.Tools.ReturnBack;

import static mainTER.Menu.MainMenu.createContent;

public class MenuLevel extends StackPane {

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
            MenuItem.pseudo = new TextField("Pseudo");

        }else {
            MenuItem.pseudo = new TextField(MenuItem.pseudo.getText());
        }
        MenuItem.pseudo.setTranslateX(170);
        MenuItem.pseudo.setTranslateY(220);
        pane.getChildren().addAll(vbox,MenuItem.pseudo);
        stage.initStyle(StageStyle.UNDECORATED);
        ReturnBack.setRevenir(stage,new Scene(createContent(stage)),pane);

    }




    public Pane getPane() {
        return pane;
    }
}
