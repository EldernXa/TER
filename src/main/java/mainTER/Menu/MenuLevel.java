package mainTER.Menu;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mainTER.Tools.ReturnBack;

public class MenuLevel extends StackPane {


    private Pane pane = new Pane();

    public MenuLevel(Stage stage) {

        MenuBox vbox = new MenuBox(
                new MenuItem("Level 1 - Forest", stage),
                new MenuItem("Level 2 - Castle", stage)
        );

        vbox.setTranslateX(170);
        vbox.setTranslateY(250);
        vbox.setSpacing(5);
        pane.getChildren().add(vbox);
        ReturnBack.setRevenir(stage,stage.getScene(),pane);

    }


    public Pane getPane() {
        return pane;
    }
}
