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
    private ReadFileMap readFileMap;

    public Map() {
/*        this.stage = stage;
        this.mapScene = mapScene;
        this.mapPane = mapPane;*/

        readFileMap = new ReadFileMap("./src/main/resources/mainTER/MapPackage/Files/Forest.txt");





    }

    public ReadFileMap getReadFileMap() {
        return readFileMap;
    }
}
