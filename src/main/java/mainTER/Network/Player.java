package mainTER.Network;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

import java.net.UnknownHostException;
import java.util.ArrayList;

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






    public void connectToServer(Stage stage,Pane pane){
        //csc = new ClientSideConnection(vBox);
        System.out.println("-----Client------------");
        try{

            socket = new Socket(InetAddress.getLocalHost(),5134);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            playerID = dis.readInt();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            this.pane = pane;
            this.stage = stage;

            pane.getChildren().add(vBox);
            System.out.println("Connected to Server as Player #" + playerID + ".");
            if(playerID == 1 ){
                System.out.println("Waiting for other player");
            }

            listenArrayPlayers(dis);



            rfs = new ReadFromServer(dis);
            wts = new WriteToServer(dos);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listenArrayPlayers(DataInputStream dis) throws IOException {
        objectInputStream = new ObjectInputStream(dis);
        Thread t = new Thread(()->{
            while (true) {
                try {

                    ArrayList<Integer> otherPlayers = (ArrayList<Integer>) objectInputStream.readObject();

                    System.out.println("on est passé pour " + playerID);
                    ArrayList<Integer> finalOtherPlayers = otherPlayers;
                    Platform.runLater(() -> {
                        vBox.getChildren().clear();
                        for (int id : finalOtherPlayers) {
                            System.out.println(playerID + " ----" + id);
                            vBox.getChildren().add(new Text("Player #" + id));
                        }

                    });
                } catch (IOException | ClassNotFoundException exception) {

                }
            }
        });
        t.setDaemon(true);
        t.start();
    }


    private class ReadFromServer implements Runnable{

        private DataInputStream dis;
        private ObjectInputStream ois;

        public ReadFromServer(DataInputStream dataIn){
            dis = dataIn;
            System.out.println("read from server");
        }

        @Override
        public void run() {

        }
    }

    private class WriteToServer implements Runnable{

        private DataOutputStream dos;

        public WriteToServer(DataOutputStream dataOut){
            dos = dataOut;
            System.out.println("write to server");

        }

        @Override
        public void run() {

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
