package mainTER.Menu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mainTER.Tools.ReturnBack;


public class MenuSettings {

    private StackPane pane = new StackPane();





    public MenuSettings(Stage stage) {


            //scene.getStylesheets().add(new File("./ressources/style.css").toURI().toString());
            Label titre = new Label("SETTINGS");
            titre.setStyle("-fx-font-size: 30px");
            MenuBox vbox = new MenuBox(
                    new MenuItem("SOUND SETTINGS",stage),
                    new MenuItem("CONTROLS SETTINGS",stage)
            );
            pane.setStyle("-fx-background-color: lightgray");
            pane.getChildren().addAll(titre);
            vbox.setTranslateX(290);
            vbox.setTranslateY(170);
            vbox.setSpacing(5);


            pane.getChildren().add(vbox);
            StackPane.setAlignment(titre,Pos.TOP_CENTER);
            ReturnBack.setRevenir(stage,stage.getScene(),pane);


    }



    public Pane getPane() {
        return pane;
    }
}
