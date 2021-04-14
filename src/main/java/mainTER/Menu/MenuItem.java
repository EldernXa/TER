package mainTER.Menu;

import javafx.application.Platform;
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
import mainTER.MapPackage.Map;
import mainTER.MapPackage.MapFieldFromLilPict;
import mainTER.MapPackage.MapFieldFromSprite;
import mainTER.Tools.Coordinate;
import mainTER.LoadOfFXML;
import mainTER.MapPackage.MapFieldRectangle;

public class MenuItem extends StackPane {

    /**
     * Constructor to create items
     * @param name is the name of the item
     * @param stage is the current stage
     */
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

        LoadOfFXML.loadFXML("/mainTER/Menu/FXML/MainMenu.fxml", this, this);
        setMaxSize(200,30);

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg,text);

        setUpMouse(bg,stage,text,name,gradient);


    }

    /**
     * When we click on the rectangle
     * @param bg is the rectangle background
     * @param stage current stage which we are going to change his scene
     * @param name name of the item
     */
    public void clickOn(Rectangle bg,Stage stage,String name){
        setOnMousePressed(event -> {
            bg.setFill(Color.DARKVIOLET);

        });


        setOnMouseClicked(event -> {
            stage.close();
            switch (name){
                case "SINGLEPLAYER" : {
                    Pane pane = new Pane();

                    Scene scene = new Scene(pane,1300,600);
                    Stage mainStage = new Stage();
                   /* MapFieldRectangle mapFieldRectangle = new MapFieldRectangle(new Coordinate(150,150),130,130);
                    MapFieldRectangle mapFieldRectangle2 = new MapFieldRectangle(new Coordinate(10,10),32,35);
                    mapFieldRectangle2.getRectangle().setFill(Color.BLUE);*/
                    //MapFieldFromSprite mapFieldFromSprite = new MapFieldFromSprite("./src/main/resources/mainTER/MapPackage/Front/Layer_0001_8.png",new Coordinate(0,530),100);

                    //mapFieldFromSprite.getImageViewSizePos().getImageView(),

                    Map map = new Map();
                    for (int i = 0; i < map.getReadFileMap().getMapFieldFromLilPictArrayList().size(); i++){
                        pane.getChildren().add(map.getReadFileMap().getMapFieldFromLilPictArrayList().get(i).getPane());
                    }

                    for (int i = 0; i < map.getReadFileMap().getMapFieldFromSpriteArrayList().size(); i++){
                        pane.getChildren().add(map.getReadFileMap().getMapFieldFromSpriteArrayList().get(i).getImageViewSizePos().getImageView());
                    }


                   // MapFieldFromLilPict mapFieldFromLilPict = new MapFieldFromLilPict("d:/Users/user1/Documents/travaille/l3/Projet/LaveMapSprites/test/test0.png",new Coordinate(0,500),1300,100);
                    //MapFieldFromLilPict mapFieldFromLilPict2 = new MapFieldFromLilPict("./src/main/resources/mainTER/MapPackage/Sprites/Front/Layer_0001_8.png",new Coordinate(0,530),1300,100);
                    //pane.getChildren().addAll(mapFieldFromLilPict2.getPane());

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
     * @param bg is the rectangle background
     * @param stage is the current stage
     * @param text is the text that contains name
     * @param name is the name of the rectangle
     * @param gradient is the gradient color
     */
    public void setUpMouse(Rectangle bg,Stage stage, Text text, String name, LinearGradient gradient){


        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);
        });

        setOnMouseExited(event -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
        });

        clickOn(bg,stage,name);

        setOnMouseReleased(event ->{

            bg.setFill(gradient);
        });
    }
}
