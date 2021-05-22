package mainTER.Network;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mainTER.CharacterGameplay.*;

import mainTER.CharacterGameplay.Character;
import mainTER.DBManage.CheckpointsDBManager;
import mainTER.DBManage.MapDBManager;
import mainTER.MapPackage.Map;
import mainTER.MapPackage.MapFileReader;
import mainTER.MapPackage.SwitchCharacter;
import mainTER.Menu.MenuItem;
import mainTER.exception.CheckpointsCharacterDoesntExistException;
import mainTER.exception.CheckpointsMapDoesntExistException;
import mainTER.exception.MapDataGetException;

import java.io.*;
import java.net.Socket;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Player {


    private int playerID;
    private ReadFromServer rfs;
    private WriteToServer wts;
    private final VBox vBox = new VBox(10);
    private ObjectInputStream objectInputStream;
    private Pane pane;
    private Stage stage;
    private DisplayCharacter me;
    private DisplayCharacter friend;
    private Map map;
    private final Button button = new Button("cliquer");
    volatile boolean finito = false;
    private ArrayList<Character> listCharacter;
    private final VBox vboxPerso = new VBox(10);
    private final Pane pane1 = new Pane();
    private Scene scene1 = new Scene(pane1);

    private final StackPane stackPane = new StackPane();
    private volatile String nameOfMe;
    volatile String nameOfMap;
    private volatile String nameOfFriend;
    private DataOutputStream dos;
    public static String multi;

    private final Button forest = new Button("Forest");
    private final Button castle = new Button("Castle");

    private final Button confirmButton = new Button("Confirm character");
    private final Button confirmMap = new Button("Confirm Map");


    /**
     *
     * @param stage allow to change the scene of the stage
     * @param pane add some object to the pane
     * @param listCharacter get the list of character to chose
     */
    public void connectToServer(Stage stage, Pane pane, ArrayList<Character> listCharacter,String ipAddr) {
        System.out.println("-----Client------------");
        try {
            multi = "a";
            Socket socket = new Socket(ipAddr, 5134);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            playerID = dis.readInt();
            dos = new DataOutputStream(socket.getOutputStream());

            this.pane = pane;
            this.stage = stage;
            this.listCharacter = listCharacter;
            vboxPerso.setTranslateX(200);
            button.setTranslateX(60);


            pane.getChildren().add(vBox);
            System.out.println("Connected to Server as Player #" + playerID + ".");

            stackPane.getChildren().add(pane1);
            button.setOnMouseClicked(mouseEvent -> {

                try {
                    dos.writeUTF("Bonjour");


                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            confirmMap.setDisable(true);
            confirmButton.setDisable(true);

            for(Character character : listCharacter){
                Button b = new Button(character.getName());
                b.setDisable(true);
                vboxPerso.getChildren().add(b);

                b.setOnMouseClicked(mouseEvent -> {
                    if(playerID == 1){
                        for(Node ba : vboxPerso.getChildren()){

                            ba.setDisable(false);
                        }
                    }else {
                        for(Node ba : vboxPerso.getChildren()){
                            ba.setDisable(nameOfFriend.contains(((Button) ba).getText()));
                        }
                    }

                        b.setDisable(true);
                        nameOfMe = character.getName();
                        confirmButton.setDisable(false);
                });
            }

            pane.getChildren().add(confirmButton);
            confirmButton.setTranslateX(300);

            if(playerID == 1){
               setDisplayForPLayer1();
            }else {
                setDisplayForPlayer2();
            }

            confirmButton.setOnMouseClicked(mouseEvent->{
                try {
                    dos.writeUTF(nameOfMe);
                    dos.flush();
                    confirmButton.setDisable(true);
                    for(Node ba : vboxPerso.getChildren()){
                        ba.setDisable(true);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            pane.getChildren().add(vboxPerso);




            listenArrayPlayers(dis);

            rfs = new ReadFromServer(dis);
            wts = new WriteToServer(dos);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDisplayForPlayer2(){
        button.setDisable(true);
    }

    /**
     * Read name of the friend
     * @param dis
     */
    public void readForCharacter(DataInputStream dis){
        Thread t = new Thread(()->{


                try {

                    dis.read();
                    String name = dis.readUTF();
                    nameOfFriend = name;

                    if (playerID == 2){

                        for(Node node :vboxPerso.getChildren()){
                            node.setDisable(name.contains(((Button) node).getText()));
                        }
                    }

                    if(playerID == 1){

                        forest.setDisable(false);
                        castle.setDisable(false);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(playerID == 2){
                    readForMap(dis);
                }
            rfs.waitForStartMsg();


        });
        t.start();
    }

    /**
     * DIsplay all the informations for player1
     */
    public void setDisplayForPLayer1(){
        button.setDisable(true);
        pane.getChildren().add(button);
        forest.setDisable(true);
        castle.setDisable(true);
        VBox vBoxMap = new VBox();
        forest.setOnMouseClicked(mouseEvent -> {
            nameOfMap = "Forest";
            forest.setDisable(true);
            castle.setDisable(false);
            confirmMap.setDisable(false);
        });
        castle.setOnMouseClicked(mouseEvent -> {
            nameOfMap= "Castle";
            castle.setDisable(true);
            forest.setDisable(false);
            confirmMap.setDisable(false);
        });

        vBoxMap.getChildren().addAll(forest,castle);
        vBoxMap.setTranslateX(200);
        vBoxMap.setTranslateY(200);
        confirmMap.setTranslateX(300);
        confirmMap.setTranslateY(200);

        confirmMap.setOnMouseClicked(mouseEvent -> {

            MenuItem.mapFileReader = new MapFileReader("./src/main/resources/mainTER/MapPackage/Files/",nameOfMap);
            confirmMap.setDisable(true);

            finito = true;

            try {
                dos.writeUTF(nameOfMap);
            } catch (IOException e) {
                e.printStackTrace();
            }


            castle.setDisable(true);
            forest.setDisable(true);

        });
        pane.getChildren().addAll(confirmMap,vBoxMap);
    }

    /**
     * get the list of players for both players
     * @param dis
     * @throws IOException
     */

   public void listenArrayPlayers(DataInputStream dis) throws IOException {

        Thread thread = new Thread(() ->{
            try {
                objectInputStream = new ObjectInputStream(dis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            AtomicReference<ArrayList<Integer>> otherPlayers = new AtomicReference<>(new ArrayList<>());


            while (otherPlayers.get().size() < 2) {
                try {

                    objectInputStream.read();
                    otherPlayers.set((ArrayList<Integer>) objectInputStream.readObject());


                    ArrayList<Integer> finalOtherPlayers = otherPlayers.get();
                    Platform.runLater(() -> {
                        vBox.getChildren().clear();
                        for (int id : finalOtherPlayers) {

                            vBox.getChildren().add(new Text("Player #" + id));
                        }


                    });
                } catch (IOException | ClassNotFoundException ignored) {
                }


            }


            if(playerID == 1){
                for(Node node :vboxPerso.getChildren()){

                    node.setDisable(false);

                }
            }
            readForCharacter(dis);



        });
        thread.start();

        }
        public void readForMap(DataInputStream dis){
            try {
                nameOfMap = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    /**
     * All that the client will read from the server
     */

    private class ReadFromServer implements Runnable{

        private final DataInputStream dis;




        public ReadFromServer(DataInputStream dataIn) throws IOException {
            dis = dataIn;
        }

        @Override
        public void run() {
            try {


                Platform.runLater(()->{
                    CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager();
                    MapDBManager mapDBManager = new MapDBManager();

                    try {
                        checkpointsDBManager.setX(mapDBManager.getInitialCoordinate(nameOfMap).getX());
                        checkpointsDBManager.setY(mapDBManager.getInitialCoordinate(nameOfMap).getY());
                        checkpointsDBManager.setMapName(nameOfMap);
                        checkpointsDBManager.setCharacterName(mapDBManager.getFirstCharacter(nameOfMap));
                    } catch (MapDataGetException | CheckpointsCharacterDoesntExistException | CheckpointsMapDoesntExistException e) {
                        e.printStackTrace();
                    }
                    map.displayMap();
                    map.addCollisionObjectNetwork(playerID == 1);
                    me.startDisplay();
                    friend.startDisplayFriend();
                    stage.setFullScreen(true);
                    stage.setResizable(false);
                    stage.sizeToScene();
                    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

                    stage.centerOnScreen();
                    stage.setScene(scene1);
                    stage.show();

                });



                while (true){
                    friend.setX(dis.readDouble());

                    friend.setY(dis.readDouble());

                    nameOfFriend = dis.readUTF();
                    int pos = dis.readInt();
                    int im = dis.readInt();
                    int skill = dis.readInt();

                    Platform.runLater(()-> {

                        Character character = new Character(nameOfFriend);
                       ActiveSkill.changeAnimateForACharacter(character,skill);

                        friend.setCharacterFriend(character,pos,im);

                    });
                    me.getKeyHandler().setNameOfFriend(nameOfFriend);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * as it's named it allow to wait the message of start
         */

        public void waitForStartMsg(){
            Thread t = new Thread(()->{
                try {
                    if(playerID == 1){
                        while (!finito) {
                            Thread.onSpinWait();

                        }
                    }


                    if(playerID == 1){
                        Platform.runLater(()-> button.setDisable(false));
                    }

                    map = new Map(pane1,nameOfMap,MenuItem.mapFileReader);

                    ImageView background = map.getBackgroundImage();



                    double backtroundHeight = background.getImage().getHeight();
                    if(background.getImage().getHeight() < Screen.getPrimary().getBounds().getHeight()){
                        backtroundHeight =  Screen.getPrimary().getBounds().getHeight();
                    }
                    scene1 = new Scene(stackPane, background.getImage().getWidth(), backtroundHeight);

                    for(Character character : listCharacter){
                        if(character.getName().equals(nameOfMe)){
                            me = new DisplayCharacter(scene1,pane1,nameOfMap,character,listCharacter,stackPane,background,stage);
                        }else if(character.getName().equals(nameOfFriend)){
                            friend = new DisplayCharacter(scene1,pane1,character,nameOfMap);
                        }
                    }




                   dis.readUTF();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            Platform.runLater(()->{
                vBox.getChildren().clear();
                pane.getChildren().remove(button);
            });

                Thread readThread = new Thread(rfs);
                Thread writeThread = new Thread(wts);

                writeThread.start();
                readThread.start();

            });
            t.start();


        }


    }

    /**
     * all that the client will send to the server
     */

    private class WriteToServer implements Runnable{

        private final DataOutputStream dos;

        public WriteToServer(DataOutputStream dataOut){
            dos = dataOut;

        }

        @Override
        public void run() {

            while (true){

                try{
                    dos.writeDouble(me.getX());
                    dos.writeDouble(me.getY());
                    dos.writeUTF(me.getCharacter().getName());
                    dos.writeInt(me.getCurrentPosition());
                    dos.writeInt(me.getCurrentImage());
                    dos.writeInt(me.getNumSkillThatIsActive());


                    dos.flush();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
