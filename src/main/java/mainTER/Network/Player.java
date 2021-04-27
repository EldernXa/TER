package mainTER.Network;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainTER.CharacterGameplay.Character;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.MapPackage.Collide;
import mainTER.MapPackage.Map;
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
    Collide collide;
    Button button = new Button("cliquer");
    volatile boolean finito = false;










    public void connectToServer(Stage stage, Scene scene,Pane pane, List<Character> listCharacter) {
        System.out.println("-----Client------------");
        try {

            socket = new Socket("localhost", 5134);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            playerID = dis.readInt();
            System.out.println("on lit l'id");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            this.pane = pane;
            this.stage = stage;
            this.scene = scene;

            button.setTranslateX(60);

            pane.getChildren().add(button);

            pane.getChildren().add(vBox);
            System.out.println("Connected to Server as Player #" + playerID + ".");


            collide = new Collide();



            if (playerID == 1) {
                button.setDisable(true);
                me = new DisplayCharacter(scene, pane, listCharacter.get(0), collide);
                friend = new DisplayCharacter(scene,pane,listCharacter.get(2),collide);
                System.out.println("Waiting for player 2");
            } else {
                me = new DisplayCharacter(scene, pane, listCharacter.get(2), collide);
                friend = new DisplayCharacter(scene,pane,listCharacter.get(0),collide);

                button.setDisable(true);


            }
            button.setOnMouseClicked(mouseEvent -> {

                try {
                    dos.writeUTF("Bonjour");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });





            listenArrayPlayers(dis);
            rfs = new ReadFromServer(dis);
            wts = new WriteToServer(dos);
            rfs.waitForStartMsg();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public void listenArrayPlayers(DataInputStream dis) throws IOException {

        Thread thread = new Thread(() ->{
            try {
                objectInputStream = new ObjectInputStream(dis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            AtomicReference<ArrayList<Integer>> otherPlayers = new AtomicReference<>(new ArrayList<>());

            System.out.println("on essaie de passer "+ playerID);
            while (otherPlayers.get().size() < 2) {
                try {

                    otherPlayers.set((ArrayList<Integer>) objectInputStream.readObject());

                    System.out.println("on est passé pour " + playerID);
                    ArrayList<Integer> finalOtherPlayers = otherPlayers.get();
                    Platform.runLater(() -> {
                        vBox.getChildren().clear();
                        for (int id : finalOtherPlayers) {
                            System.out.println(playerID + " ----" + id);
                            vBox.getChildren().add(new Text("Player #" + id));
                        }

                        System.out.println(otherPlayers.get().size());
                    });
                } catch (IOException | ClassNotFoundException ignored) {
                }
        }
            finito = true;
            System.out.println("ON A FINI DE LIRE LES JOUEURS");

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
                ImageView background = new ImageView(new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Back/Background-1.png").toURI().toString()));
                map = new Map(collide, pane, background);
                Platform.runLater(()->{

                    System.out.println("on crée la map avec les collisions " + playerID );


                    map.addCollisionObject();
                    
                    System.out.println(playerID +" " + pane);
                    me.startDisplay();
                    friend.startDisplayFriend();
                });



                while (true){
                    friend.setX(dis.readDouble());

                    friend.setY(dis.readDouble());

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
                    }

                    System.out.println("ON SORT DE LA BOUCLE");
                    System.out.println("on essaie de lire le message start " + playerID);

                    if(dis.available() != 0){
                        System.out.println(dis.read());
                        String startMsg = dis.readUTF();
                        System.out.println(startMsg.getBytes().length);
                        System.out.println("le message est " + startMsg + " " + playerID);
                        System.out.println(System.currentTimeMillis() /1000);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            Platform.runLater(()->{
                vBox.getChildren().clear();
                pane.getChildren().remove(button);
            });
                System.out.println("bonjour--------------");

                Thread readThread = new Thread(rfs);
                Thread writeThread = new Thread(wts);
                readThread.start();
                writeThread.start();

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
                    //System.out.println(me.getCurrentCoordinateOfTheCharacter().getX() + " " +me.getCurrentCoordinateOfTheCharacter().getY());
                    dos.flush();
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

/*
    private class ClientSideConnection{
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;
        private VBox vBox;
        private ObjectInputStream objectInputStream;

        public ClientSideConnection(VBox vBox){
            System.out.println("----Client-----");
            try{
                socket = new Socket(InetAddress.getLocalHost(),5134);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(dis);
                this.vBox = vBox;
                playerID = dis.readInt();

                System.out.println("Connected to Server as Player #" + playerID + ".");
                if(playerID != 4){
                    System.out.println("Waiting for other players");
                }
                Thread read = new Thread(()-> {

                    ArrayList<Integer> otherPlayers;

                    while (true) {
                        try {
                            otherPlayers = (ArrayList<Integer>) objectInputStream.readObject();

                            ArrayList<Integer> finalOtherPlayers = otherPlayers;
                            Platform.runLater(() -> {
                                vBox.getChildren().clear();
                                for (int id : finalOtherPlayers) {
                                   // System.out.println(playerID +" ----" +id);
                                    vBox.getChildren().add(new Text("Player #" + id));
                                }
                                //System.out.println("++++++++" + finalOtherPlayers);
                            });
                        } catch (IOException | ClassNotFoundException ignored) {

                        }
                    }

                });
                read.setDaemon(true);
                read.start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Aucun serveur n'a été trouvé");
            }
        }

    }
 */

}
