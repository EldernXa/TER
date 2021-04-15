package mainTER.Menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
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
import mainTER.MapPackage.InteractiveObject;
import mainTER.MapPackage.Map;
import mainTER.LoadOfFXML;

public class MenuItem extends StackPane {

    @FXML
    private Rectangle rectangleText;
    
    @FXML
    private Text textMenu;

    /**
     * Constructor to create items
     * @param name is the name of the item
     * @param stage is the current stage
     */
    public MenuItem(String name, Stage stage){

        LoadOfFXML.loadFXML("/mainTER/Menu/FXML/MenuItem.fxml", this, this);
        textMenu.setText(name);
        setUpMouse(stage,name);


    }

    /**
     * When we click on the rectangle
     * @param stage current stage which we are going to change his scene
     * @param name name of the item
     */
    public void clickOn(Stage stage,String name){
        setOnMousePressed(event -> {
            rectangleText.setFill(Color.DARKVIOLET);

        });


        setOnMouseClicked(event -> {
            stage.close();
            switch (name){
                case "SINGLEPLAYER" : {
                    Pane pane = new Pane();

                    Scene scene = new Scene(pane,1300,600);
                    Stage mainStage = new Stage();


                    Map map = new Map();
                    for (int i = 0; i < map.getReadFileMap().getMapFieldFormArrayList().size(); i++){
                        pane.getChildren().add(map.getReadFileMap().getMapFieldFormArrayList().get(i).getAppropriateMap());
                    }
                    for (InteractiveObject interactiveObject : map.getReadFileMap().getInteractiveObjectArrayList()){

                        pane.getChildren().add(interactiveObject.getImageView());
                    }


                    mainStage.setScene(scene);
                    mainStage.centerOnScreen();
                    mainStage.show();



                }
                break;
                case "MULTIPLAYER": {

                }
                break;
                case "OPTIONS" : {

                    Pane pane = new Pane();

                    Scene scene = new Scene(pane,500,600);
                    Stage mainStage = new Stage();


                    mainStage.setScene(scene);
                    mainStage.centerOnScreen();
                    mainStage.show();
                }
                break;
                case "QUIT":{
                    Platform.exit();
                }
            }
        });

    }

    /**
     * Set up for the mouse controller
     * @param stage is the current stage
     * @param name is the name of the rectangle
     */
    public void setUpMouse(Stage stage, String name){
        LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.DARKVIOLET),
                new Stop(0.1,Color.BLACK),
                new Stop(0.9,Color.BLACK),
                new Stop(1,Color.DARKVIOLET));

        setOnMouseEntered(event -> {
            rectangleText.setFill(gradient);
            textMenu.setFill(Color.WHITE);
        });

        setOnMouseExited(event -> {
            rectangleText.setFill(Color.BLACK);
            textMenu.setFill(Color.DARKGREY);
        });

        clickOn(stage,name);

        setOnMouseReleased(event ->{

            rectangleText.setFill(gradient);
        });
    }
}
