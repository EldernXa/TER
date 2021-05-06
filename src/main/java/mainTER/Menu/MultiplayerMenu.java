package mainTER.Menu;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainTER.LoadOfFXML;
import mainTER.Tools.ReturnBack;

import java.awt.*;

public class MultiplayerMenu extends StackPane {
    //@FXML
    //private Button button;
    Pane pane = new Pane();

    public MultiplayerMenu(Stage stage){

        //LoadOfFXML.loadFXML("/mainTER/Menu/FXML/MultiPlayerMenu.fxml", this, this);



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
