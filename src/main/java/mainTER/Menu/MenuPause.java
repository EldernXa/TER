package mainTER.Menu;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPause {


    VBox vbox ;
     public MenuPause(Stage stage){
        vbox = new MenuBox(
                new MenuItem("BACK TO GAME",stage),
                new MenuItem("SETTINGS",stage),
                new MenuItem("BACK TO MENU",stage),
                new MenuItem("QUIT",stage)
        );
        vbox.setTranslateX(50);
        vbox.setTranslateY(250);
        vbox.setSpacing(5);
    }


    public VBox getVbox() {
        return vbox;
    }
}
