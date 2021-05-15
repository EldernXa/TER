package mainTER.Menu;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPause {


    VBox vbox ;
     public MenuPause(Stage stage,Stage stagea){
        vbox = new MenuBox(
                new MenuItem("BACK TO GAME",stagea),
                new MenuItem("SETTINGS",stagea),
                new MenuItem("BACK TO CHOICE OF MAP",stage),
                new MenuItem("BACK TO MENU",stage),
                new MenuItem("QUIT",stagea)
        );
        vbox.setTranslateX(50);
        vbox.setTranslateY(250);
        vbox.setSpacing(5);
    }


    public VBox getVbox() {
        return vbox;
    }
}
