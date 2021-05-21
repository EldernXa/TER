package mainTER.Menu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import mainTER.CharacterGameplay.Character;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.DBManage.*;
import mainTER.LoadOfFXML;
import mainTER.MapPackage.Map;
import mainTER.Network.GameServer;
import mainTER.Network.Player;
import mainTER.Tools.Coordinate;
import mainTER.Tools.DataInsert;
import mainTER.exception.CheckpointsCharacterDoesntExistException;
import mainTER.exception.CheckpointsMapDoesntExistException;
import mainTER.exception.MapDataGetException;

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
    public static TextField pseudo;
    public static Label timerLabel = new Label();
    public static String mapName;
    public static DisplayCharacter displayCharacter;

    public static Timeline timeline = new Timeline();

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
        try {
            this.listName = personDBManager.getListNameFromDatabase();
        }catch(Exception exception){
            exception.printStackTrace();
        }
        setUpMouse(name);




    }

    /**
     * When we click on the rectangle
     *
     * @param name  name of the item
     */
    public void clickOn(String name) {

        setOnMousePressed(event -> rectangleText.setFill(Color.DARKVIOLET));


        setOnMouseClicked(event -> {
           // Music.stopMusique();

            switch (name) {
                case "BACK TO CHOICE OF MAP" :
                    displayCharacter.setCoordinate(new Coordinate(0,0));
                case "SINGLEPLAYER":
                {
                    stage.close();
                    Stage newStage = new Stage();
                    MenuLevel menuLevel = new MenuLevel(newStage);
                    Scene scene = new Scene( menuLevel.getPane(), 860,600);



                    newStage.setScene(scene);
                    newStage.show();


                }
                break;
                case "Level 1 - Forest": {
                    for(String nameCharacter : listName){
                        listCharacter.add(new Character(nameCharacter));
                    }


                    if(!pseudo.getText().equals("") && !pseudo.getText().contains(" ")){
                        mapName = "Forest";
                        createLvl("Forest");
                    }

                }
                break;
                case "Level 2 - Castle": {
                    for(String nameCharacter : listName){
                        listCharacter.add(new Character(nameCharacter));
                    }
                    if(!pseudo.getText().equals("") && !pseudo.getText().equals(" ")) {
                        mapName = "Castle";
                        createLvl("Castle");
                    }
                }break;
                case "Level 3 - City": {
                    for(String nameCharacter : listName){
                        listCharacter.add(new Character(nameCharacter));
                    }
                    if(!pseudo.getText().equals("") && !pseudo.getText().equals(" ")) {
                        mapName = "City";
                        createLvl("City");
                    }
                }break;
                case "MULTIPLAYER": {

                    MultiplayerMenu mpm = new MultiplayerMenu(stage);
                    Scene scene = new Scene(mpm.getPane(), 500, 600);
                    stage.setScene(scene);

                }
                break;
                case "SETTINGS": {
                    MenuSettings menuSettings = new MenuSettings(stage);

                    Scene scene=  new Scene(menuSettings.getPane(), Screen.getPrimary().getVisualBounds().getWidth()/2,Screen.getPrimary().getVisualBounds().getHeight()/2);

                    stage.setScene(scene);
                    stage.centerOnScreen();
                }
                break;
                case "QUIT": {
                    Platform.exit();
                    System.exit(0);
                    break;
                }
                case "CREER": {
                    for(String nameCharacter : listName){
                        listCharacter.add(new Character(nameCharacter));
                    }
                    Stage newStage = new Stage();
                    Pane pane = new Pane();
                    Scene sceneServ = new Scene(pane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

                    newStage.setScene(sceneServ);

                    GameServer gs = new GameServer();


                    Thread t = new Thread(gs);
                    t.start();
                    Player p = new Player();
                    p.connectToServer(newStage, pane,listCharacter,"localhost");
                    newStage.show();
                    newStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    break;

                }
                case "REJOINDRE": {
                    stage.close();
                    for(String nameCharacter : listName){
                        listCharacter.add(new Character(nameCharacter));
                    }
                    Stage newStage = new Stage();
                    Pane pane = new Pane();
                    Scene sceneCli = new Scene(pane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

                    TextField ipAddr = new TextField("localhost");
                    Label label = new Label("Server Ip Address");
                    Button button = new Button("Confirm");
                    newStage.setScene(sceneCli);
                    newStage.show();
                    ipAddr.setTranslateY(25);
                    button.setTranslateY(50);
                    pane.getChildren().addAll(label,ipAddr,button);

                    setButton(button,newStage,pane,ipAddr);
                    break;
                }
                case "SOUND SETTINGS": {
                    MenuSound menuSound = new MenuSound(stage);

                    stage.setScene(menuSound.getScene());
                    stage.centerOnScreen();
                    break;
                }
                case "CONTROLS SETTINGS" : {

                    try {
                        MenuControls menuControls = new MenuControls(stage);
                        stage.setScene(menuControls.getScene());
                        stage.centerOnScreen();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                }
                case "BACK TO GAME" : {
                    stage.hide();
                    break;
                }
                case "BACK TO MENU" : {
                    stage.close();
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(MainMenu.createContent(newStage)));
                    newStage.initStyle(StageStyle.UNDECORATED);
                    newStage.show();
                    break;
                }
                case "UPGRADE SKILLS" : {
                    for(String nameCharacter : listName){
                        listCharacter.add(new Character(nameCharacter));
                    }
                   UpgradeSkillMenu upgradeSkillMenu = new UpgradeSkillMenu(listCharacter,stage);
                    stage.setScene(upgradeSkillMenu.getScene());
                    break;
                }
                case "RESET THE GAME": {
                    BestProfileDBManager bestProfileDBManager = new BestProfileDBManager();
                    PointsUpgradeDBManager pointsUpgradeDBManager = new PointsUpgradeDBManager();
                    ProfileDBManager profileDBManager = new ProfileDBManager();
                    UpgradeSkillDBManager upgradeSkillDBManager = new UpgradeSkillDBManager();
                    SkillDBManager skillDBManager = new SkillDBManager();
                    CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager();
                    NbPointsDBManager nbPointsDBManager = new NbPointsDBManager();
                    nbPointsDBManager.removeTableNbPointsDBManager();
                    nbPointsDBManager.createTableNbPoints();
                    checkpointsDBManager.removeTableCheckPoints();
                    skillDBManager.removeTableSkill();
                    upgradeSkillDBManager.removeTableUpgradeSkill();
                    bestProfileDBManager.removeTableProfile();
                    pointsUpgradeDBManager.removeTablePointsUpgrade();
                    profileDBManager.removeTableProfile();
                    skillDBManager.createTableSkill();
                    DataInsert.insertSkill();
                    DataInsert.insertUpgradeSkillsValue();
                    DataInsert.insetCheckpoints();

                }
            }
        });

    }

    /**
     * Set up for the mouse controller
     *
     * @param name  is the name of the rectangle
     */
    public void setUpMouse(String name) {
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

        clickOn( name);

        setOnMouseReleased(event -> rectangleText.setFill(gradient));
    }


    public void setButton(Button button,Stage newStage,Pane pane,TextField addr){
        button.setOnMouseClicked(mouseEvent->{
            pane.getChildren().clear();
            Player p = new Player();
            p.connectToServer(newStage, pane,listCharacter,addr.getText());
            newStage.show();

        });
    }


    public void createLvl(String mapName) {

        CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager();
        MapDBManager mapDBManager = new MapDBManager();

        try {
            checkpointsDBManager.setX(mapDBManager.getInitialCoordinate(mapName).getX());
            checkpointsDBManager.setY(mapDBManager.getInitialCoordinate(mapName).getY());
            checkpointsDBManager.setMapName(mapName);
            checkpointsDBManager.setCharacterName(mapDBManager.getFirstCharacter(mapName));
        } catch (MapDataGetException | CheckpointsCharacterDoesntExistException | CheckpointsMapDoesntExistException e) {
            e.printStackTrace();
        }

        StackPane stackPane = new StackPane();
        Pane pane = new Pane();
        stackPane.getChildren().add(pane);
        Map map =  new Map(pane,mapName);
        map.displayMap();
        map.addCollisionObject();
        ImageView background = map.getBackgroundImage();
        var ref = new Object() {
            int timeSeconds = 0;
        };


        timerLabel.setFont(Font.font("Arial",30));

        timerLabel.setText(String.valueOf(ref.timeSeconds));;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            ref.timeSeconds++;
                            timerLabel.setText(
                                    String.valueOf(ref.timeSeconds));
                            if (ref.timeSeconds <= 0) {
                                timeline.stop();
                            }
                        }));

        timerLabel.setTextFill(Color.WHITE);
        timerLabel.setTranslateX(10);
        pane.getChildren().add(timerLabel);

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
        displayCharacter = new DisplayCharacter(scene, pane, mapName, listCharacter, stackPane, background,stage);
        displayCharacter.startDisplay();
        //Make the scene scale if the screen is larger

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        timeline.playFromStart();


    }
}
