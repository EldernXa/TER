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
import mainTER.MapPackage.Map;
import mainTER.MapPackage.SwitchCharacter;
import mainTER.Music.Music;
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

    
    private final ArrayList<Character> listCharacter;
    private Stage stage;
    private List<String> listName;

    /**
     * Constructor to create items
     *
     * @param name  is the name of the item
     * @param stage is the current stage
     */
    public MenuItem(String name, Stage stage) {

        this.stage = stage;
        LoadOfFXML.loadFXML("/mainTER/Menu/FXML/MenuItem.fxml", this, this);
        textMenu.setText(name);
        PersonDBManager personDBManager = new PersonDBManager();
        listCharacter = new ArrayList<>();
        this.listName = personDBManager.getListNameFromDatabase();
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
            Music.stopMusique();

            switch (name) {

                case "SINGLEPLAYER":{
                    MenuLevel menuLevel = new MenuLevel(stage);
                    Scene scene = new Scene( menuLevel.getPane(), 860,600);
                    stage.setScene(scene);

                }
                break;
                case "Level 1 - Forest": {
                    for(String nameCharacter : listName){
                        listCharacter.add(new Character(nameCharacter, new Coordinate(4500, 600)));
                    }
                   createLvl("Forest");
                }
                break;
                case "Level 2 - Castle": {
                    for(String nameCharacter : listName){
                        listCharacter.add(new Character(nameCharacter, new Coordinate(1985, 2200)));
                    }
                   createLvl("Castle");
                }break;
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
                    Pane pane = new Pane();
                    Scene sceneServ = new Scene(pane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

                    newStage.setScene(sceneServ);

                    GameServer gs = new GameServer();


                    Thread t = new Thread(gs);
                    t.start();
                    Player p = new Player();
                    p.connectToServer(stage,sceneServ,pane,listCharacter);
                    newStage.show();
                    newStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    break;

                }
                case "REJOINDRE": {
                    Stage newStage = new Stage();
                    Pane pane = new Pane();
                    Scene sceneCli = new Scene(pane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

                    newStage.setScene(sceneCli);

                    Player p = new Player();
                    p.connectToServer(stage,sceneCli,pane,listCharacter);
                    newStage.show();
                    break;
                }
                case "SOUND SETTINGS": {
                    MenuSound menuSound = new MenuSound(stage);
                    stage.setScene(menuSound.getScene());
                    break;
                }
                case "CONTROLS SETTINGS" : {

                    try {
                        MenuControls menuControls = new MenuControls();
                        stage.setScene(menuControls.getScene());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
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




    public void createLvl(String mapName) {
        StackPane stackPane = new StackPane();
        Pane pane = new Pane();
        stackPane.getChildren().add(pane);
        Map map =  new Map(pane,mapName);
        ImageView background = map.getBackgroundImage();



        double backtroundHeight = background.getImage().getHeight();
        if(background.getImage().getHeight() < Screen.getPrimary().getBounds().getHeight()){
            backtroundHeight =  Screen.getPrimary().getBounds().getHeight();
        }
        Scene scene = new Scene(stackPane, background.getImage().getWidth(), backtroundHeight);
        stage.close();
        stage = new Stage();
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        DisplayCharacter displayCharacter = new DisplayCharacter(scene, pane, listCharacter.get(0));
        displayCharacter.startDisplay();
        SwitchCharacter sc = new SwitchCharacter(listCharacter,displayCharacter);
        //Make the scene scale if the screen is larger
        double height = Screen.getPrimary().getBounds().getHeight();
        double h = 1;
        if(height>background.getImage().getHeight()){
            h = height/background.getImage().getHeight();
            Scale scale = new Scale(h, h, 0, 0);
            scene.getRoot().getTransforms().add(scale);
        }
        stackPane.getChildren().add(sc);
        new Camera(scene,displayCharacter,sc,listCharacter,h,background);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
