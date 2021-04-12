package mainTER.Menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainTER.LoadOfFXML;
import mainTER.Version;

public class MenuItem extends StackPane {

    public MenuItem(String name, Stage stage){

        LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.DARKVIOLET),
                new Stop(0.1,Color.BLACK),
                new Stop(0.9,Color.BLACK),
                new Stop(1,Color.DARKVIOLET));

        Rectangle bg = new Rectangle(200,30);
        bg.setOpacity(0.4);
        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font("Tw Cen Mt Condensed", FontWeight.SEMI_BOLD,22));


        LoadOfFXML.loadFXML("/mainTER/Menu/MainMenu.fxml", this, this);
        setMaxSize(200,30);

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg,text);



        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);
        });

        setOnMouseExited(event -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
        });

        setOnMousePressed(event -> {
            bg.setFill(Color.DARKVIOLET);
            switch (name){
                case "SINGLEPLAYER" : {
                    System.out.println("one rentre dedans");
                    Pane pane = new Pane();

                    Scene scene = new Scene(pane,300,600);
                    stage.setMaximized(false);
                        stage.setScene(scene);
                        stage.centerOnScreen();

                }
            }
        });
        setOnMouseReleased(event ->{

            bg.setFill(gradient);
        });
    }
}
