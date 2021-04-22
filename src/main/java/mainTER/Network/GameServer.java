package mainTER.Network;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer implements Runnable, Serializable{

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


                System.out.println("Player #" + numPlayers + " has connected.");
                ServerSideConnection ssc = new ServerSideConnection(s,numPlayers);
                players.add(ssc);



            }
            System.out.println("Il y a assez de joueurs");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendToAll(){
        for(ServerSideConnection ssc : players){

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

    private class ServerSideConnection {

        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;
        ObjectOutputStream oos;
        private int playerID;
        private ArrayList<Integer> playersId = new ArrayList<>();


        public ServerSideConnection(Socket s, int id){
            socket = s;
            playerID = id;
            try{
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                oos = new ObjectOutputStream(dos);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread send = new Thread(() -> {
                try{

                    dos.writeInt(playerID);


                    //oos.writeObject(playersId);
                    for(ServerSideConnection ssc : players){
                        playersId.add(ssc.getPlayerID());
                    }



                    System.out.println(playersId);
                    sendToAll(playersId);



                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            send.setDaemon(true);
            send.start();

        }

        public void write(Object obj) {
            try{
                oos.writeObject(obj);
            }
            catch(IOException e){ e.printStackTrace(); }
        }

        public void sendToAll(Object message){
            for(ServerSideConnection client : players)
                client.write(message);
        }


        public int getPlayerID() {
            return playerID;
        }


    }

}
