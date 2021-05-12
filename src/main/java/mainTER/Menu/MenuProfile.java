package mainTER.Menu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainTER.DBManage.BestProfileDBManager;
import mainTER.Tools.ReturnBack;

import java.awt.*;

public class MenuProfile {

    String name,mapName;
    int time;
    Pane pane = new Pane();
    BestProfileDBManager bestProfileDBManager = new BestProfileDBManager();

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

        Text bestText = new Text("Le meilleur joueur sur la map " + mapName + " est " + bestProfileDBManager.getName(mapName) + " avec un temps de " + bestProfileDBManager.getTime(mapName) + " secondes");
        bestText.setTranslateY(150);

        pane.getChildren().addAll(nameText,timeText,mapText,bestText);

    }

    public Pane getPane() {
        return pane;
    }
}
