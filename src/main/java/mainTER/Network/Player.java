package mainTER.Network;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import java.net.UnknownHostException;

public class Player {

    private ClientSideConnection csc;
    private int playerID;
    private int otherPlayer;






    public void connectToServer(Stage stage, Scene scene){
        csc = new ClientSideConnection(stage,scene);
    }




    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;

        public ClientSideConnection(Stage stage,Scene scene){
            System.out.println("----Client-----");
            try{
                socket = new Socket(InetAddress.getLocalHost(),5134);
                Platform.runLater(() -> stage.setScene(scene));
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                playerID = dis.readInt();
                System.out.println("Connected to Server as Player #" + playerID + ".");
                if(playerID != 4){
                    System.out.println("Waiting for other players");
                }

            } catch (IOException e) {
                System.out.println("Aucun serveur n'a été trouvé");
            }
        }
    }


}
