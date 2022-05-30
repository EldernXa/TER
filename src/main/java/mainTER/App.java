package mainTER;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainTER.Tools.DataInsert;

import static mainTER.Menu.MainMenu.createContent;

/**
 * JavaFX App
 */
public class App extends Application { 



    @Override
    public void start(Stage stage) {
        DataInsert.insertControls();
        DataInsert.insertPerson();
        DataInsert.insertSkill();
        DataInsert.insertMap();
        DataInsert.insertUpgradeSkillsValue();
        DataInsert.insetCheckpoints();
        var scene = new Scene(createContent(stage));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}