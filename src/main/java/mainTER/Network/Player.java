package mainTER.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.net.UnknownHostException;

public class Player {

    private ClientSideConnection csc;
    private int playerID;
    private int otherPlayer;






    public void connectToServer(){
        csc = new ClientSideConnection();
    }




    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;

        public ClientSideConnection(){
            System.out.println("----Client-----");
            try{
                socket = new Socket("localhost",5134);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                playerID = dis.readInt();
                System.out.println("Connected to Server as Player #" + playerID + ".");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Player p = new Player();
        p.connectToServer();
    }
}
