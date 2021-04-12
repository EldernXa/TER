package mainTER.MapPackage;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Map {
    private Stage stage;
    public static ArrayList<Object> visualObjects = new ArrayList<>();
    private Scene mapScene;
    private Pane mapPane;


    public Map(Stage stage, Scene mapScene, Pane mapPane) {
        this.stage = stage;
        this.mapScene = mapScene;
        this.mapPane = mapPane;




    }



}
