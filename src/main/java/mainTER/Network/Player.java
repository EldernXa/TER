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
    volatile boolean finito = false;
    volatile boolean finish = false;
    ArrayList<Character> listCharacter;
    VBox vboxPerso = new VBox(10);
    Pane pane1 = new Pane();
    Scene scene1 = new Scene(pane1);
    ImageView background = new ImageView(new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Back/Background-1.png").toURI().toString()));
    double backtroundHeight = background.getImage().getHeight();



    public void connectToServer(Stage stage, Scene scene,Pane pane, ArrayList<Character> listCharacter) {
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
            this.listCharacter = listCharacter;
            vboxPerso.setTranslateX(200);
            button.setTranslateX(60);




            pane.getChildren().add(button);
            pane.getChildren().add(vBox);
            System.out.println("Connected to Server as Player #" + playerID + ".");
            if(background.getImage().getHeight() < Screen.getPrimary().getBounds().getHeight()){
                backtroundHeight =  Screen.getPrimary().getBounds().getHeight();
            }


            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(pane1);

            scene1 = new Scene(stackPane, background.getImage().getWidth(), backtroundHeight);

            if (playerID == 1) {
                button.setDisable(true);
                System.out.println("Waiting for player 2");
                me = new DisplayCharacter(scene1, pane1, listCharacter.get(0));
                friend = new DisplayCharacter(scene1, pane1,listCharacter.get(2));
            } else {
                me = new DisplayCharacter(scene1, pane1, listCharacter.get(2));
                friend = new DisplayCharacter(scene1, pane1,listCharacter.get(0));
                button.setDisable(true);
            }
            button.setOnMouseClicked(mouseEvent -> {

                try {
                    dos.writeUTF("Bonjour");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            /*for(Character character : listCharacter){
                Button b = new Button(character.getName());
                vboxPerso.getChildren().add(b);
                b.setDisable(true);
                b.setOnMouseClicked(mouseEvent -> {
                    try {
                            me = new DisplayCharacter(scene,pane,character,collide);

                            System.out.println("voila mon perso " + me.getCharacter().getName() +" " + playerID);

                        for(Node ba : vboxPerso.getChildren()){

                            ba.setDisable(false);
                        }
                        b.setDisable(true);
                        System.out.println(b.getText());
                            dos.writeUTF(character.getName());
                            dos.flush();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
*/


            listenArrayPlayers(dis);

            rfs = new ReadFromServer(dis);
            wts = new WriteToServer(dos);
            rfs.waitForStartMsg();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public void readForCharacter(DataInputStream dis){
        Thread t = new Thread(()->{

            while (!finish){
                try {

                    String name = dis.readUTF();
                    System.out.println(name + "" + playerID);
                    for(Node node :vboxPerso.getChildren()){
                        if(name.contains(((Button)node).getText())){
                            node.setDisable(true);
                        }else {
                            //if(me.getCharacter().getName() !=((Button)node).getText())
                                node.setDisable(false);
                        }


                    }
                    for(Character character : listCharacter){
                        if(name.contains(character.getName())){
                            friend = new DisplayCharacter(scene,pane,character,collide);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        t.start();
    }
*/
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

                    objectInputStream.read();
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



            System.out.println("ON A FINI DE LIRE LES JOUEURS");
            finito = true;


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

                    System.out.println("on crée la map avec les collisions " + playerID );



                    double height = Screen.getPrimary().getBounds().getHeight();
                    double h = height/background.getImage().getHeight();
                    Scale scale = new Scale(h, h, 0, 0);
                    scene1.getRoot().getTransforms().add(scale);

/*                    mainStage.setHeight(Screen.getPrimary().getBounds().getHeight());
                    mainStage.setWidth(5548);*/
                    stage.setFullScreen(true);
                    //mainStage.setMaximized(true);
                    stage.setResizable(false);
                    stage.sizeToScene();

                    map = new Map( pane1, "forest");
                    me.startDisplay();
                    friend.startDisplayFriend();

                    SwitchCharacter sc = new SwitchCharacter(listCharacter,me);

                    pane1.getChildren().add(sc);

                    new Camera(scene1,me,sc,listCharacter,h,background);

                    System.out.println(playerID +" " + pane1.getChildren());

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



                    /*
                    for (CollideObject collideObject : map.getReadFileMap().getCollisionObjectArrayList()){
                        collideObject.setX(dis.readDouble());
                        collideObject.setY(dis.readDouble());
                    }*/

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

                    System.out.println("on passe hors de la boucle");
                    if(playerID == 1){
                        Platform.runLater(()->{
                            button.setDisable(false);
                        });
                    }

                    System.out.println("ON SORT DE LA BOUCLE");
                    System.out.println("on essaie de lire le message start " + playerID);


                    finish = true;
                    if(dis.available() != 0){
                        dis.read();
                        String startMsg = dis.readUTF();
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
                    /*for(CollideObject collideObject : map.getReadFileMap().getCollisionObjectArrayList()){
                        dos.writeDouble(collideObject.getX());
                        dos.writeDouble(collideObject.getY());
                    }*/
                    //System.out.println(me.getCurrentCoordinateOfTheCharacter().getX() + " " +me.getCurrentCoordinateOfTheCharacter().getY());
                    dos.flush();
                    try {
                        Thread.sleep(700);
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
