package mainTER.Network;


import mainTER.MapPackage.CollideObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class GameServer implements Runnable{

    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;
    private double p1x,p1y,p2x,p2y;
    private String name1, name2;
    private int pos1,im1,pos2,im2;
    private ArrayList<CollideObject> collideObjects1,collideObject2;

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
        name1 = "Demon";
        name2 = "Paladin";

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
                System.out.println("on écrit l'id");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(dos);
                oosList.add(objectOutputStream);
                ReadFromClient rfc =new ReadFromClient(numPlayers,dis);
                WriteToClient wtc = new WriteToClient(numPlayers,dos);

                System.out.println("on envoie  la list de joueurs");
                sendToAll(listOfPlayersID);
                if(numPlayers == 1 ){
                    player1 = s;
                    rfc1 = rfc;
                    wtc1 = wtc;

                }else{
                    player2 = s;
                    rfc2 = rfc;
                    wtc2 = wtc;
                    String o = rfc1.dis.readUTF();

                    System.out.println("Le Serveur a reçu "+o);
                    wtc1.sendStartMsg();
                    System.out.println("on envoie le premier message start");
                    wtc2.sendStartMsg();
                    System.out.println("on envoie le second message start");

                    Thread readThread1 = new Thread(rfc1);
                    Thread readThread2 = new Thread(rfc2);
                    readThread1.start();

                    readThread2.start();
                    Thread writeThread1 = new Thread(wtc1);
                    Thread writeThread2 = new Thread(wtc2);
                    writeThread1.start();
                    writeThread2.start();


                }

            }


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
            try{

                while (true){
                    if(playerID == 1){
                        p1x = dis.readDouble();
                        p1y = dis.readDouble();
                        name1 = dis.readUTF();
                        pos1 = dis.readInt();
                        im1 = dis.readInt();



                    }else{
                        p2x = dis.readDouble();
                        p2y = dis.readDouble();
                        name2 = dis.readUTF();
                        pos2 = dis.readInt();
                        im2 = dis.readInt();

                    }
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
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
            try{
                System.out.println("on écrit les coord");
                while (true){
                    if(playerID == 1){
                        dos.writeDouble(p2x);
                        dos.writeDouble(p2y);
                        dos.writeUTF(name2);
                        dos.writeInt(pos2);
                        dos.writeInt(im2);
                        dos.flush();
                    }else{
                        dos.writeDouble(p1x);
                        dos.writeDouble(p1y);
                        dos.writeUTF(name1);
                        dos.writeInt(pos1);
                        dos.writeInt(im1);
                        dos.flush();
                    }
                    try {
                        Thread.sleep(700);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        public void sendStartMsg(){
            System.out.println("on écrit le message de start");
            try{
                String a = "c'est parti";

                dos.writeUTF(a);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void writeButtonReady(){
            try{
                Thread.sleep(5000);
                dos.write(4);
            }catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
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
