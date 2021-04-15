package mainTER.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    private ServerSocket ss;
    private int numPlayers;
    ArrayList<ServerSideConnection> players = new ArrayList<>();
    boolean accept = false;


    public GameServer(){
        System.out.println("------Game Server ------");
        numPlayers = 1;
        try{
            ss = new ServerSocket(5134);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnections(){
        try{
            System.out.println("Waiting for connections...");


            while(!accept){
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Player #" + numPlayers + " has connected.");
                ServerSideConnection ssc = new ServerSideConnection(s,numPlayers);
                players.add(ssc);
                if(numPlayers == 3){
                    accept = true;
                }
                Thread t = new Thread(ssc);
                t.start();

            }
            System.out.println("Il y a deux personnes connectées");
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

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
