package mainTER.Menu;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mainTER.Tools.ReturnBack;

public class MultiplayerMenu extends StackPane {

    Pane pane = new Pane();

    public MultiplayerMenu(Stage stage){

        MenuBox vbox = new MenuBox(
                new MenuItem("CREER",stage),
                new MenuItem("REJOINDRE",stage)
        );
        vbox.setTranslateX(170);
        vbox.setTranslateY(250);
        vbox.setSpacing(5);
        pane.getChildren().add(vbox);
        ReturnBack.setRevenir(stage,stage.getScene(),pane);
    }


    public Pane getPane(){
        return pane;
    }
}
