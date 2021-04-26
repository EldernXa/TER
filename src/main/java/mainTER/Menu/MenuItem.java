package mainTER.Menu;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import mainTER.CharacterGameplay.ActiveSkill;
import mainTER.CharacterGameplay.Camera;
import mainTER.CharacterGameplay.Character;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.DBManage.PersonDBManager;
import mainTER.LoadOfFXML;
import mainTER.MapPackage.Collide;
import mainTER.MapPackage.Map;
import mainTER.MapPackage.SwitchCharacter;
import mainTER.Network.GameServer;
import mainTER.Network.Player;
import mainTER.Tools.Coordinate;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MenuItem extends StackPane {

    @FXML
    private Rectangle rectangleText;

    @FXML
    private Text textMenu;
    Pane paneaa = new Pane();
    Scene sceneaa = new Scene(paneaa, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    private final ArrayList<Character> listCharacter;

    /**
     * Constructor to create items
     *
     * @param name  is the name of the item
     * @param stage is the current stage
     */
    public MenuItem(String name, Stage stage) {

        LoadOfFXML.loadFXML("/mainTER/Menu/FXML/MenuItem.fxml", this, this);
        textMenu.setText(name);
        PersonDBManager personDBManager = new PersonDBManager();
        listCharacter = new ArrayList<>();
        List<String> listName = personDBManager.getListNameFromDatabase();
        for(String nameCharacter : listName){
            listCharacter.add(new Character(nameCharacter, new Coordinate(1200, 630)));
        }
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

                    StackPane stackPane = new StackPane();


                    Pane pane = new Pane();


                    stackPane.getChildren().add(pane);

                    Scene scene = new Scene(stackPane, 5548, Screen.getPrimary().getBounds().getHeight());

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




                    DisplayCharacter displayCharacter = new DisplayCharacter(scene, pane, listCharacter.get(0), collide);
                    displayCharacter.startDisplay();

                    SwitchCharacter sc = new SwitchCharacter(listCharacter);

                    //Make the scene scale if the screen is larger

                    double height = Screen.getPrimary().getBounds().getHeight();
                    double h = height/background.getImage().getHeight();
                    Scale scale = new Scale(h, h, 0, 0);
                    scene.getRoot().getTransforms().add(scale);


                    sc.setTranslateY(displayCharacter.getCurrentCoordinateOfTheCharacter().getY() -Screen.getPrimary().getBounds().getHeight()/8.6* 5);
                    sc.setTranslateX(displayCharacter.getCurrentCoordinateOfTheCharacter().getX() -Screen.getPrimary().getBounds().getWidth()/2.8);
                    stackPane.getChildren().add(sc);

                    new Camera(scene,displayCharacter,sc,listCharacter,h,background);



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

                    Stage newStage = new Stage();
                    newStage.setScene(sceneaa);

                    GameServer gs = new GameServer();


                    Thread t = new Thread(gs);
                    t.start();
                    Player p = new Player();
                    p.connectToServer(stage,paneaa,listCharacter);
                    newStage.show();
                    break;

                }
                case "REJOINDRE": {
                    Stage newStage = new Stage();
                    newStage.setScene(sceneaa);


                    Player p = new Player();
                    p.connectToServer(stage,paneaa,listCharacter);
                    newStage.show();
                    break;
                }
                case "SOUND SETTINGS": {
                    MenuSound menuSound = new MenuSound(stage);
                    stage.setScene(menuSound.getScene());
                }
                case "CONTROLS SETTINGS" : {

                    try {
                        MenuControls menuControls = new MenuControls();
                        stage.setScene(menuControls.getScene());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
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

        setOnMouseReleased(event -> rectangleText.setFill(gradient));
    }
}
