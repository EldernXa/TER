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
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(dos);
                oosList.add(objectOutputStream);
                ReadFromClient rfc =new ReadFromClient(numPlayers,dis);
                WriteToClient wtc = new WriteToClient(numPlayers,dos);


                sendToAll(listOfPlayersID);
                if(numPlayers == 1 ){
                    player1 = s;
                    rfc1 = rfc;
                    wtc1 = wtc;

                }else{
                    player2 = s;
                    rfc2 = rfc;
                    wtc2 = wtc;

                    String name1 = rfc1.dis.readUTF();
                    wtc2.dos.writeUTF(name1);
                    String name2 = rfc2.dis.readUTF();
                    wtc1.dos.writeUTF(name2);
                    String map = rfc1.dis.readUTF();
                    wtc2.dos.writeUTF(map);

                    String o = rfc1.dis.readUTF();

                    wtc1.sendStartMsg();
                    wtc2.sendStartMsg();

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


        }


        @Override
        public void run() {
            try{
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

}
