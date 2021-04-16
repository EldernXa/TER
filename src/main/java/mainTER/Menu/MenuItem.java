package mainTER.Menu;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
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
import mainTER.MapPackage.InteractiveObject;
import mainTER.MapPackage.Map;
import mainTER.LoadOfFXML;
import mainTER.Network.GameServer;
import mainTER.Network.Player;
import mainTER.Tools.Coordinate;


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
                    Camera camera = new PerspectiveCamera( true);
                    Scene scene = new Scene(pane,1300,600);
                    Stage mainStage = new Stage();
                    scene.setCamera(camera);


                    Map map = new Map();
                    for (int i = 0; i < map.getReadFileMap().getMapFieldFormArrayList().size(); i++){
                        pane.getChildren().add(map.getReadFileMap().getMapFieldFormArrayList().get(i).getAppropriateMap());
                    }
                    for (InteractiveObject interactiveObject : map.getReadFileMap().getInteractiveObjectArrayList()){

                        pane.getChildren().add(interactiveObject.getImageView());
                    }

                    camera.setLayoutX(scene.getWidth()/2);
                    camera.setLayoutY(scene.getHeight()/2);
                    camera.setTranslateZ(-1200);
                    camera.setNearClip(1);
                    camera.setFarClip(100000);

                    scene.addEventHandler(KeyEvent.KEY_PRESSED, event2 ->{
                        switch (event2.getCode()){
                            case W:
                                System.out.println("AAAAAAAAAAA");
                                camera.translateZProperty().set(camera.getTranslateZ()+100);
                                break;
                            case S:
                                System.out.println("BBBBBBBBBBBBB");
                                camera.translateZProperty().set(camera.getTranslateZ()-100);
                                break;
                        }
                    });

                    Character character = new Character("Paladin", new Coordinate(0, 0));
                    DisplayCharacter displayCharacter = new DisplayCharacter(scene, pane, character);
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
