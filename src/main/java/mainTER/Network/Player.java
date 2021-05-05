package mainTER.Network;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mainTER.CharacterGameplay.Camera;
import mainTER.CharacterGameplay.Character;
import mainTER.CharacterGameplay.DisplayCharacter;

import mainTER.MapPackage.CollideObject;
import mainTER.MapPackage.Map;
import mainTER.MapPackage.SwitchCharacter;
import mainTER.Tools.Coordinate;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

//TODO faire le choix de personnage au début
//TODO faire 2 différentes map pour les persos
public class Player {

    //private ClientSideConnection csc;
    Socket socket;
    private int playerID;
    private int otherPlayer;
    ReadFromServer rfs;
    WriteToServer wts;
    VBox vBox = new VBox(10);
    ObjectInputStream objectInputStream;
    Pane pane;
    Stage stage;
    DisplayCharacter me;
    DisplayCharacter friend;
    Scene scene;
    Map map;
    Button button = new Button("cliquer");
    volatile static boolean finito = false;
    ArrayList<Character> listCharacter;
    VBox vboxPerso = new VBox(10);
    Pane pane1 = new Pane();
    Scene scene1 = new Scene(pane1);
    ImageView background = new ImageView(new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Back/BackgroundForest.png").toURI().toString()));
    double backtroundHeight = background.getImage().getHeight();
    StackPane stackPane = new StackPane();
    volatile String nameOfMe;
    volatile static String nameOfMap;
    volatile String nameOfFriend;

    Button forest = new Button("Forest");
    Button castle = new Button("Castle");

    Button confirmButton = new Button("Confirm character");
    Button confirmMap = new Button("Confirm Map");


    public void connectToServer(Stage stage, Scene scene,Pane pane, ArrayList<Character> listCharacter) {
        System.out.println("-----Client------------");
        try {

            socket = new Socket("localhost", 5134);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            playerID = dis.readInt();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            this.pane = pane;
            this.stage = stage;
            this.scene = scene;
            this.listCharacter = listCharacter;
            vboxPerso.setTranslateX(200);
            button.setTranslateX(60);

            pane.getChildren().add(button);
            pane.getChildren().add(vBox);
            System.out.println("Connected to Server as Player #" + playerID + ".");
            if(background.getImage().getHeight() < Screen.getPrimary().getBounds().getHeight()){
                backtroundHeight =  Screen.getPrimary().getBounds().getHeight();
            }



            stackPane.getChildren().add(pane1);


            if (playerID == 1) {
                button.setDisable(true);
            } else {
                button.setDisable(true);
            }
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
                            if(nameOfFriend.contains(((Button)ba).getText())) {
                                ba.setDisable(true);
                            }else {
                                ba.setDisable(false);
                            }
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

    public void readForCharacter(DataInputStream dis){
        Thread t = new Thread(()->{


                try {

                    dis.read();
                    String name = dis.readUTF();

                    nameOfFriend = name;

                    System.out.println(name + "   -----" + playerID);

                    if (playerID == 2){

                        for(Node node :vboxPerso.getChildren()){
                            if(name.contains(((Button)node).getText())){
                                node.setDisable(true);
                            }else {
                                node.setDisable(false);
                            }
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

        private DataInputStream dis;



        public ReadFromServer(DataInputStream dataIn) throws IOException {
            dis = dataIn;
        }

        @Override
        public void run() {
            try {



                Platform.runLater(()->{



                    double height = Screen.getPrimary().getBounds().getHeight();
                    double h = height/background.getImage().getHeight();
                    Scale scale = new Scale(h, h, 0, 0);
                    scene1.getRoot().getTransforms().add(scale);

                    stage.setFullScreen(true);
                    stage.setResizable(false);
                    stage.sizeToScene();

                    map.displayMap();
                    if(playerID == 1){
                        map.addCollisionObjectNetwork(true);
                    }else {
                        map.addCollisionObjectNetwork(false);
                    }
                    me.startDisplay();
                    friend.startDisplayFriend();

                    SwitchCharacter sc = new SwitchCharacter(listCharacter,me);

                    pane1.getChildren().add(sc);


                    new Camera(scene1,me,sc,listCharacter,h,background,stage);

                    stage.centerOnScreen();

                    stage.setScene(scene1);


                });



                while (true){
                    friend.setX(dis.readDouble());

                    friend.setY(dis.readDouble());


                    String name = dis.readUTF();
                    int pos = dis.readInt();
                    int im = dis.readInt();
                    Platform.runLater(()-> {

                        friend.setCharacterFriend(new Character(name, friend.getCurrentCoordinateOfTheCharacter()),pos,im);

                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void waitForStartMsg(){
            Thread t = new Thread(()->{
                try {
                    while (!finito){

                    }

                    if(playerID == 1){
                        Platform.runLater(()->{
                            button.setDisable(false);
                        });
                    }else {
                        nameOfMap = dis.readUTF();
                    }
                    map = new Map(pane1,nameOfMap);
                    scene1 = new Scene(stackPane, map.getBackgroundImage().getImage().getWidth(), backtroundHeight);

                   for(Character character : listCharacter){
                        if(character.getName().equals(nameOfMe)){
                            me = new DisplayCharacter(scene1,pane1,character);
                        }else if(character.getName().equals(nameOfFriend)){
                            friend = new DisplayCharacter(scene1,pane1,character);
                        }
                    }

                        String startMsg = dis.readUTF();



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

        private DataOutputStream dos;

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
