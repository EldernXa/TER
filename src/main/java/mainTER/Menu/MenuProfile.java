package mainTER.Menu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainTER.Tools.ReturnBack;

import java.awt.*;

public class MenuProfile {

    String name,mapName;
    int time;
    Pane pane = new Pane();

    public MenuProfile(Stage stage, String name, int time, String mapName){
        this.mapName = mapName;
        this.time = time;
        this.name = name;


        display();
        MenuLevel menuLevel = new MenuLevel(stage);
        Scene scene = new Scene( menuLevel.getPane(), 860,600);
        ReturnBack.setRevenir(stage,scene,pane);
    }


    public void display(){

        Text nameText = new Text(name);

        Text timeText = new Text("Le temps est de "+ time + "secondes");
        Text mapText = new Text("Sur la map " + mapName);


        nameText.setTranslateY(50);
        timeText.setTranslateY(75);
        mapText.setTranslateY(100);
        pane.getChildren().addAll(nameText,timeText,mapText);

    }

    public Pane getPane() {
        return pane;
    }
}
