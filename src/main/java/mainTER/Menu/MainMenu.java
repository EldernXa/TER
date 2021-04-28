package mainTER.Menu;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import mainTER.Music.Music;
import mainTER.Version;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainMenu {




    /**
     * Creation of the content of main scene
     * @param stage is the current stage
     * @return
     */
    public static Parent createContent(Stage stage){
        Pane pane = new Pane();
        Label label = new Label();
        label.setTextFill(Color.WHITE);



        label.setText(Version.getVersion());
        try(InputStream is = Files.newInputStream(Paths.get("src/main/resources/mainTER/Menu/images/background.png"))){
            ImageView img = new ImageView(new Image(is));


            img.setFitWidth(860);
            img.setFitHeight(600);

            Rectangle rect = new Rectangle(860,600);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(5);
            rect.setStrokeType(StrokeType.INSIDE);
            rect.setFill(null);

            pane.getChildren().addAll(img,rect);
        }catch (IOException e){
            System.out.println("ouverture d'image impossible");
        }

        Title title =  new Title("NAMELESS TITLE");
        title.setTranslateX(270);
        title.setTranslateY(100);
        Music.launchMenuSound();
        MenuBox vbox = new MenuBox(
                new MenuItem("SINGLEPLAYER",stage),
                new MenuItem("MULTIPLAYER",stage),
                new MenuItem("SETTINGS",stage),
                new MenuItem("QUIT",stage)
        );
        vbox.setTranslateX(320);
        vbox.setTranslateY(250);
        vbox.setSpacing(5);
        label.setTranslateX(4);
        label.setTranslateY(4);



        pane.getChildren().addAll(title,vbox,label);
        return pane;
    }
}
