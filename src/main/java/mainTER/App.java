package mainTER;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mainTER.Menu.MenuBox;
import mainTER.Menu.MenuItem;
import mainTER.Menu.Title;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JavaFX App
 */
public class App extends Application {



    private Parent createContent(Stage stage){
        Pane pane = new Pane();
        Label label = new Label();
        label.setTextFill(Color.WHITE);

        label.setText(Version.getVersion());
        try(InputStream is = Files.newInputStream(Paths.get("src/main/resources/mainTER/Menu/background.png"))){
            ImageView img = new ImageView(new Image(is));
            img.setFitHeight(800);
            img.setFitWidth(1540);
            pane.getChildren().add(img);
        }catch (IOException e){
            System.out.println("ouverture d'image impossible");
        }

        Title title =  new Title("NAMELESS TITLE");
        title.setTranslateX(590);
        title.setTranslateY(100);

        MenuBox vbox = new MenuBox(
                new MenuItem("SINGLEPLAYER",stage),
                new MenuItem("MULTIPLAYER",stage),
                new MenuItem("OPTIONS",stage)
        );
        vbox.setTranslateX(610);
        vbox.setTranslateY(330);
        vbox.setSpacing(5);
        label.setTranslateX(4);
        label.setTranslateY(4);

        pane.getChildren().addAll(title,vbox,label);
        return pane;
    }
    @Override
    public void start(Stage stage) {

        //MainMenu mainMenu = new MainMenu(stage);
        var scene = new Scene(createContent(stage));
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch();
    }

}