package mainTER;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mainTER.Menu.MainMenu;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        MainMenu mainMenu = new MainMenu(stage);
        var scene = new Scene(mainMenu, 640, 480);
        stage.setScene(scene);
        stage.show();
        //
    }

    public static void main(String[] args) {
        launch();
    }

}