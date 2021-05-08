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
import mainTER.CharacterGameplay.Camera;
import mainTER.CharacterGameplay.Character;
import mainTER.CharacterGameplay.DisplayCharacter;

import mainTER.MapPackage.Map;
import mainTER.MapPackage.SwitchCharacter;

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
    volatile static boolean finito = false;
    private ArrayList<Character> listCharacter;
    private final VBox vboxPerso = new VBox(10);
    private final Pane pane1 = new Pane();
    private Scene scene1 = new Scene(pane1);

    private final StackPane stackPane = new StackPane();
    private volatile String nameOfMe;
    volatile static String nameOfMap;
    private volatile String nameOfFriend;
    private DataOutputStream dos;

    private final Button forest = new Button("Forest");
    private final Button castle = new Button("Castle");

    private final Button confirmButton = new Button("Confirm character");
    private final Button confirmMap = new Button("Confirm Map");


    public void connectToServer(Stage stage, Pane pane, ArrayList<Character> listCharacter) {
        System.out.println("-----Client------------");
        try {

            Socket socket = new Socket("localhost", 5134);
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
            rfs.waitForStartMsg();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDisplayForPlayer2(){
        button.setDisable(true);
    }

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


        });
        t.start();
    }

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

    private class ReadFromServer implements Runnable{

        private final DataInputStream dis;



        public ReadFromServer(DataInputStream dataIn) throws IOException {
            dis = dataIn;
        }

        @Override
        public void run() {
            try {


                Platform.runLater(()->{
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
                    Platform.runLater(()-> friend.setCharacterFriend(new Character(nameOfFriend),pos,im));
                    /*Thread t = new Thread(()->{

                        me.eventScene(scene1,nameOfFriend,me.getKeyHandler());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    t.start();
*/

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void waitForStartMsg(){
            Thread t = new Thread(()->{
                try {
                    while (!finito) {
                        Thread.onSpinWait();

                    }

                    if(playerID == 1){
                        Platform.runLater(()-> button.setDisable(false));
                    }else {
                        nameOfMap = dis.readUTF();
                    }
                    map = new Map(pane1,nameOfMap);
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
