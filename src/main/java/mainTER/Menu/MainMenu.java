package mainTER.Menu;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mainTER.LoadOfFXML;
import mainTER.Version;

public class MainMenu extends StackPane {

    @FXML
    private Label lblVersion;

    @FXML
    private Button singleButton;

    public MainMenu(Stage stage){
        LoadOfFXML.loadFXML("/mainTER/Menu/MainMenu.fxml", this, this);
        lblVersion.setText(Version.getVersion());
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane,300,600);
        //double width = Screen.getPrimary().getVisualBounds().getWidth();
        //double height = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setMaximized(false);
        //pane.getChildren().add(new Rectangle(300,600, Color.BLACK));

        singleButton.setOnAction(event ->{
            stage.setScene(scene);
            stage.centerOnScreen();
        });

    }

}
