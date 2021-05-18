package mainTER.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainTER.DBManage.BestProfileDBManager;
import mainTER.DBManage.ProfileDBManager;
import mainTER.Tools.ReturnBack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static java.util.Map.Entry.comparingByValue;


public class MenuProfile {

    String name,mapName;
    int time;
    Pane pane = new Pane();
    BestProfileDBManager bestProfileDBManager = new BestProfileDBManager();
    ProfileDBManager profileDBManager = new ProfileDBManager();

    public MenuProfile(Stage stage, String name, int time, String mapName){
        this.mapName = mapName;
        this.time = time;
        this.name = name;


        display();
        setButton();
        MenuLevel menuLevel = new MenuLevel(stage);
        Scene scene = new Scene( menuLevel.getPane(), 860,600);
        ReturnBack.setRevenir(stage,scene,pane);
    }

    /**
     * display the content of the profile
     */
    public void display(){

        Text nameText = new Text(name);

        Text timeText = new Text("Your time is "+ time + " seconds");
        Text mapText = new Text("On the map " + mapName);



        nameText.setTranslateY(50);
        nameText.setTranslateX(nameText.getTranslateX()+50);
        nameText.setFont(Font.font("Tw Cen Mt Condensed", 40));
        timeText.setTranslateY(75);
        timeText.setFont(Font.font("Tw Cen Mt Condensed", 20));
        timeText.setTranslateX(timeText.getTranslateX()+150);
        mapText.setTranslateY(100);
        mapText.setFont(Font.font("Tw Cen Mt Condensed", 20));
        mapText.setTranslateX(timeText.getTranslateX());
        Text myBest = new Text("My best time on the map " + mapName + " is  " + profileDBManager.getTime(name,mapName) + " seconds");
        myBest.setTranslateY(125);
        myBest.setTranslateX(timeText.getTranslateX());
        myBest.setFont(Font.font("Tw Cen Mt Condensed", 25));
        Text bestText = new Text("The best player on the map " + mapName + " is \n" + bestProfileDBManager.getName(mapName) + " with a time of " + bestProfileDBManager.getTime(mapName) + " seconds");
        bestText.setTranslateY(300);
        bestText.setFont(Font.font("Tw Cen Mt Condensed", 30));

        pane.getChildren().addAll(nameText,timeText,mapText,bestText,myBest);

    }
    public void setButton(){
        Button button = new Button("Ranking");
        button.setTranslateY(350);
        button.setOnMouseClicked(mouseEvent->{
            displayRanking();
        });
        pane.getChildren().add(button);
    }

    public void displayRanking(){
        HashMap<String,Double>  listProfiles = profileDBManager.getRanking(mapName);

        VBox rank = new VBox(5);

        HashMap<String, Double> sortedProfiles = new LinkedHashMap<>();
        listProfiles.entrySet()
                .stream()
                .sorted(comparingByValue())
                .forEachOrdered(x -> sortedProfiles.put(x.getKey(), x.getValue()));

        int i = 1;
            for (String s : sortedProfiles.keySet()){
                double value = sortedProfiles.get(s);
                rank.getChildren().add(new Text(i +" " + s +" " + value));
                i++;

            }

        Stage stage = new Stage();
        Scene scene = new Scene(rank);
        stage.setScene(scene);
        stage.show();
    }

    public Pane getPane() {
        return pane;
    }
}
