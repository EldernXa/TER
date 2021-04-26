package mainTER.Network;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer implements Runnable{

    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;
    //ArrayList<ServerSideConnection> players = new ArrayList<>();

    private Socket player1;
    private Socket player2;
    private ReadFromClient rfc1;
    private ReadFromClient rfc2;
    private WriteToClient wtc1;
    private WriteToClient wtc2;
    private ArrayList<Socket> listOfPlayers = new ArrayList<>();
    private ArrayList<Integer> listOfPlayersID = new ArrayList<>();
    private ArrayList<ObjectOutputStream> oosList = new ArrayList<>();

    public GameServer(){
        System.out.println("------Game Server ------");

        numPlayers = 0;
        maxPlayers = 2;

    }

    public void acceptConnections(){
        try{
            System.out.println("Waiting for connections...");


            while(numPlayers < maxPlayers){
                Socket s = ss.accept();
                numPlayers++;
                listOfPlayers.add(s);
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                listOfPlayersID.add(numPlayers);
                dos.writeInt(numPlayers);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(dos);

                oosList.add(objectOutputStream);
                ReadFromClient rfc =new ReadFromClient(numPlayers,dis);
                WriteToClient wtc = new WriteToClient(numPlayers,dos);

                if(numPlayers == 1 ){
                    player1 = s;
                    rfc1 = rfc;
                    wtc1 = wtc;

                }else{
                    player2 = s;
                    rfc2 = rfc;
                    wtc2 = wtc;
                }
                sendToAll(listOfPlayersID);



            }
            System.out.println("Il y a assez de joueurs");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void write(ObjectOutputStream oos,Object obj) {
        try{

            oos.writeObject(obj);
            oos.reset();
        }
        catch(IOException e){ e.printStackTrace(); }
    }

    public void sendToAll(Object message){

        for(ObjectOutputStream oos : oosList)
            write(oos,message);


    }



    @Override
    public void run() {
        try{
            ss = new ServerSocket(5134,2);
            acceptConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class ReadFromClient implements Runnable{


        private DataInputStream dis;
        private int playerID;


        public ReadFromClient(int pid, DataInputStream dataIn){
            this.playerID = pid;
            this.dis = dataIn;

            System.out.println("read from client create");

        }

        @Override
        public void run() {

        }
    }

    private class WriteToClient implements Runnable{
        private DataOutputStream dos;
        private int playerID;


        public WriteToClient(int pid, DataOutputStream dataOut){
            this.playerID = pid;
            this.dos = dataOut;

            System.out.println("Client thread create");

        }

        @Override
        public void run() {


        }

    }


/*
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
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(obj);
            oos.reset();
        }
        catch(IOException e){ e.printStackTrace(); }
    }

    public void sendToAll(Object message){
        for(ServerSide client : listOfPlayers){
            write(message);

        }
    }


        public int getPlayerID() {
            return playerID;
        }


    } */

}
