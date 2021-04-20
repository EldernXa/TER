package mainTER.Menu;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
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
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainTER.CharacterGameplay.Character;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.MapPackage.Collide;
import mainTER.MapPackage.Map;
import mainTER.LoadOfFXML;
import mainTER.Music.Music;
import mainTER.Network.GameServer;
import mainTER.Network.Player;
import mainTER.Tools.Coordinate;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class MenuItem extends StackPane {

    @FXML
    private Rectangle rectangleText;

    @FXML
    private Text textMenu;
    Pane paneaa = new Pane();
    Scene sceneaa = new Scene(paneaa, 500, 600);

    /**
     * Constructor to create items
     *
     * @param name  is the name of the item
     * @param stage is the current stage
     */
    public MenuItem(String name, Stage stage) {

        LoadOfFXML.loadFXML("/mainTER/Menu/FXML/MenuItem.fxml", this, this);
        textMenu.setText(name);
        setUpMouse(stage, name);


    }

    /**
     * When we click on the rectangle
     *
     * @param stage current stage which we are going to change his scene
     * @param name  name of the item
     */
    public void clickOn(Stage stage, String name) {
        setOnMousePressed(event -> {
            rectangleText.setFill(Color.DARKVIOLET);

        });


        setOnMouseClicked(event -> {

            switch (name) {
                case "SINGLEPLAYER": {
                    Pane pane = new Pane();


                    Scene scene = new Scene(pane, 5548, Screen.getPrimary().getBounds().getHeight());

                    Stage mainStage = new Stage();
/*                    mainStage.setHeight(Screen.getPrimary().getBounds().getHeight());
                    mainStage.setWidth(5548);*/
                    mainStage.setFullScreen(true);
                    //mainStage.setMaximized(true);
                    mainStage.setResizable(false);
                    mainStage.sizeToScene();
                    mainStage.setMinWidth(mainStage.getWidth());
                    mainStage.setMinHeight(mainStage.getHeight());
                    mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);




                    Collide collide = new Collide();
                    ImageView background = new ImageView(new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Back/Background-1.png").toURI().toString()));
                    Map map = new Map(collide, pane, background);
                    map.addCollisionObject();


                    ArrayList<Character> listCharacter = new ArrayList<>();
                    listCharacter.add(new Character("Paladin", new Coordinate(1200, 630)));
                    listCharacter.add(new Character("Serpent", new Coordinate(1200, 630)));
                    listCharacter.add(new Character("Demon", new Coordinate(1100, 530)));
                    DisplayCharacter displayCharacter = new DisplayCharacter(scene, pane, listCharacter.get(0), collide);


                    //Make the scene scale if the screen is larger

                    double height = Screen.getPrimary().getBounds().getHeight();
                    double h = height/background.getImage().getHeight();
                    Scale scale = new Scale(h, h, 0, 0);
                    scene.getRoot().getTransforms().add(scale);


                    //Set the camera on the player
                    Camera camera = new PerspectiveCamera();
                    scene.setCamera(camera);
                    camera.translateXProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getX()*h - Screen.getPrimary().getBounds().getWidth() / 2);

                    //A voir pour les Y quand la map monte ou decend
                    AtomicBoolean isZero = new AtomicBoolean(false);
                    final Coordinate saveCoord = new Coordinate(displayCharacter.getCurrentCoordinateOfTheCharacter().getX(), displayCharacter.getCurrentCoordinateOfTheCharacter().getY());



                    scene.addEventHandler(KeyEvent.KEY_PRESSED, event2 -> {

                        switch (event2.getCode()) {
                            case D:
                                if (displayCharacter.getCurrentCoordinateOfTheCharacter().getX() < saveCoord.getX()) {

                                } else {
                                    camera.translateXProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getX()*h - Screen.getPrimary().getBounds().getWidth() / 2);
                                    isZero.set(false);
                                }
                                break;

                            case Q:
                                if (camera.getTranslateX() != 0) {
                                    camera.translateXProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getX()*h - Screen.getPrimary().getBounds().getWidth() / 2);
                                } else {
                                    if (!isZero.get()) {
                                        saveCoord.setX(displayCharacter.getCurrentCoordinateOfTheCharacter().getX()*h);
                                        isZero.set(true);
                                    }
                                }
                                break;
                        }
                    });


                    mainStage.setScene(scene);
                    mainStage.centerOnScreen();
                    mainStage.show();


                }
                break;
                case "MULTIPLAYER": {

                    MultiplayerMenu mpm = new MultiplayerMenu(stage);
                    Scene scene = new Scene(mpm.getPane(), 500, 600);
                    stage.setScene(scene);


                }
                break;
                case "SETTINGS": {


                    Stage mainStage = new Stage();

                    MenuSettings menuSettings = new MenuSettings();

                    mainStage.setScene(menuSettings.getScene());
                    mainStage.centerOnScreen();
                    mainStage.show();
                }
                break;
                case "QUIT": {
                    Platform.exit();
                    break;
                }
                case "CREER": {


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
                case "REJOINDRE": {
                    Player p = new Player();
                    p.connectToServer(stage, sceneaa);
                    break;
                }
                case "SOUND SETTINGS": {
                    MenuSound menuSound = new MenuSound(stage);
                    stage.setScene(menuSound.getScene());
                }
            }
        });

    }

    /**
     * Set up for the mouse controller
     *
     * @param stage is the current stage
     * @param name  is the name of the rectangle
     */
    public void setUpMouse(Stage stage, String name) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.DARKVIOLET),
                new Stop(0.1, Color.BLACK),
                new Stop(0.9, Color.BLACK),
                new Stop(1, Color.DARKVIOLET));

        setOnMouseEntered(event -> {
            rectangleText.setFill(gradient);
            textMenu.setFill(Color.WHITE);
        });

        setOnMouseExited(event -> {
            rectangleText.setFill(Color.BLACK);
            textMenu.setFill(Color.DARKGREY);
        });

        clickOn(stage, name);

        setOnMouseReleased(event -> {

            rectangleText.setFill(gradient);
        });
    }
}
