package mainTER.Menu;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainTER.CharacterGameplay.Character;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.MapPackage.Collision;
import mainTER.MapPackage.CollisionObject;
import mainTER.MapPackage.InteractiveObject;
import mainTER.MapPackage.Map;
import mainTER.LoadOfFXML;
import mainTER.Network.GameServer;
import mainTER.Network.Player;
import mainTER.Tools.Coordinate;

import java.io.File;


public class MenuItem extends StackPane {

    @FXML
    private Rectangle rectangleText;
    
    @FXML
    private Text textMenu;
    Pane paneaa = new Pane();
    Scene sceneaa = new Scene(paneaa,500,600);

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

            switch (name){
                case "SINGLEPLAYER" : {
                    Pane pane = new Pane();


                    Scene scene = new Scene(pane,5548,788);

                    Stage mainStage = new Stage();
                    mainStage.setHeight(5548);
                    mainStage.setWidth(788);
                    mainStage.setMaximized(true);
//                    mainStage.setFullScreen(true);
                    mainStage.sizeToScene();

/*
                    mainStage.setWidth(3000);
                    mainStage.setHeight(500);*/
                    Collision collision = new Collision();
                    ImageView background = new ImageView(new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Back/Background.png").toURI().toString()));
                    Map map = new Map(collision,pane,background);
                    /*for (int i = 0; i < map.getReadFileMap().getMapFieldFormArrayList().size(); i++){
                        pane.getChildren().add(map.getReadFileMap().getMapFieldFormArrayList().get(i).getAppropriateNode());
                    }*/
                    for (CollisionObject collisionObject : map.getReadFileMap().getCollisionObjectArrayList()){
                        pane.getChildren().add(collisionObject.getAppropriateNode());
                    }

                  ;




                    Camera camera = new PerspectiveCamera();
                    camera.maxHeight(500);
                    camera.maxWidth(1300);
                    scene.setCamera(camera);
/*                    camera.setLayoutX(scene.getWidth()/2);
                    camera.setLayoutY(scene.getHeight()/2);
                    camera.setTranslateZ(-2500);
                    camera.setNearClip(1);
                    camera.setFarClip(100000);*/



                    scene.addEventHandler(KeyEvent.KEY_PRESSED, event2 ->{
                        switch (event2.getCode()){
                            case W:
                                camera.translateXProperty().set(camera.getTranslateX()+100);
                                break;
                            case S:
                                camera.translateXProperty().set(camera.getTranslateX()-100);
                                break;
                        }
                    });

                    Character character = new Character("Paladin", new Coordinate(1200, 630));
                    DisplayCharacter displayCharacter = new DisplayCharacter(scene, pane, character,collision);
                    mainStage.setScene(scene);
                    mainStage.centerOnScreen();
                    mainStage.show();



                }
                break;
                case "MULTIPLAYER": {

                    MultiplayerMenu mpm = new MultiplayerMenu(stage);
                    Scene scene = new Scene(mpm.getPane(),500,600);
                    stage.setScene(scene);


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
                    break;
                }
                case "CREER":{




                    stage.setScene(sceneaa);


                    VBox vBox = new VBox(10);
                    paneaa.getChildren().add(vBox);
                    GameServer gs = new GameServer(vBox);

                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            Platform.exit();
                        }
                    });
                    Thread t = new Thread(gs);
                    t.start();
                    break;

                }
                case "REJOINDRE" : {
                    Player p = new Player();
                    p.connectToServer(stage,sceneaa);
                    break;
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
