package mainTER.Network;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer implements Runnable{

    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;
    ArrayList<ServerSideConnection> players = new ArrayList<>();
    VBox vbox;



    public GameServer(VBox vbox){
        System.out.println("------Game Server ------");
        this.vbox = vbox;
        numPlayers = 0;
        maxPlayers = 4;

    }

    public void acceptConnections(){
        try{
            System.out.println("Waiting for connections...");


            while(numPlayers <= maxPlayers){
                Socket s = ss.accept();
                numPlayers++;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        vbox.getChildren().add(new Text("Player #" +numPlayers));
                    }
                });

                System.out.println("Player #" + numPlayers + " has connected.");
                ServerSideConnection ssc = new ServerSideConnection(s,numPlayers);
                players.add(ssc);

                Thread t = new Thread(ssc);
                t.start();

            }
            System.out.println("Il y a assez de joueurs");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ServerSocket getSs() {
        return ss;
    }

    @Override
    public void run() {
        try{
            ss = new ServerSocket(5134,5);
            acceptConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerSideConnection implements Runnable{

        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;
        private int playerID;


        public ServerSideConnection(Socket s, int id){
            socket = s;
            playerID = id;
            try{
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            try{
                dos.writeInt(playerID);
                dos.flush();

                while (true){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
