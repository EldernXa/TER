package mainTER.Network;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class Player {

    private ClientSideConnection csc;
    private int playerID;
    private int otherPlayer;






    public void connectToServer(VBox vBox){
        csc = new ClientSideConnection(vBox);
    }




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

                    ArrayList<Integer> otherPlayers = null;

                    while (true) {
                        try {
                            otherPlayers = (ArrayList<Integer>) objectInputStream.readObject();
                            System.out.println("on passe ici");
                            ArrayList<Integer> finalOtherPlayers = otherPlayers;
                            Platform.runLater(() -> {
                                vBox.getChildren().clear();
                                for (int id : finalOtherPlayers) {

                                    vBox.getChildren().add(new Text("Player #" + id));
                                }
                                System.out.println("++++++++" + finalOtherPlayers);
                            });
                        } catch (IOException | ClassNotFoundException e) {

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


}
