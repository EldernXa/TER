package mainTER.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer implements Runnable{

    public static ServerSocket ss;
    private int numPlayers;
    private final int maxPlayers;
    private double p1x,p1y,p2x,p2y;
    private String name1, name2;
    private int pos1,im1,pos2,im2;
    int skill1,skill2;
    public static String multi;


    private ReadFromClient rfc1;
    private WriteToClient wtc1;
    private final ArrayList<Socket> listOfPlayers = new ArrayList<>();
    private final ArrayList<Integer> listOfPlayersID = new ArrayList<>();
    private final ArrayList<ObjectOutputStream> oosList = new ArrayList<>();

    public GameServer(){
        System.out.println("------Game Server ------");
        multi = "a";
        numPlayers = 0;
        maxPlayers = 2;

        skill1 = -1;
        skill2 = -1;


    }

    /**
     * Accept connections for both players
     */

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
                    rfc1 = rfc;
                    wtc1 = wtc;

                }else{

                    String namePerso1 = rfc1.dis.readUTF();
                    wtc.dos.writeUTF(namePerso1);
                    name1 = namePerso1;
                    String namePerso2 = rfc.dis.readUTF();
                    wtc1.dos.writeUTF(namePerso2);
                    name2 = namePerso2;
                    String map = rfc1.dis.readUTF();
                    wtc.dos.writeUTF(map);

                    rfc1.dis.readUTF();

                    wtc1.sendStartMsg();
                    wtc.sendStartMsg();

                    Thread readThread1 = new Thread(rfc1);
                    Thread readThread2 = new Thread(rfc);
                    readThread1.start();

                    readThread2.start();
                    Thread writeThread1 = new Thread(wtc1);
                    Thread writeThread2 = new Thread(wtc);
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

    /**
     *
     * @param message the message sent to all the clients
     */
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

    /**
     * All that the server will read from the client
     */

    private class ReadFromClient implements Runnable{


        private final DataInputStream dis;
        private final int playerID;


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
                        skill1 = dis.readInt();



                    }else{
                        p2x = dis.readDouble();
                        p2y = dis.readDouble();
                        name2 = dis.readUTF();
                        pos2 = dis.readInt();
                        im2 = dis.readInt();
                        skill2 = dis.readInt();

                    }
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }

    }

    /**
     * All that the server will send to client
     */

    private class WriteToClient implements Runnable{
        private final DataOutputStream dos;
        private final int playerID;


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
                        dos.writeInt(skill2);
                    }else{
                        dos.writeDouble(p1x);
                        dos.writeDouble(p1y);
                        dos.writeUTF(name1);
                        dos.writeInt(pos1);
                        dos.writeInt(im1);
                        dos.writeInt(skill1);
                    }
                    dos.flush();
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


        /**
         * send the start message
         */

        public void sendStartMsg(){
            try{
                String a = "c'est parti";

                dos.writeUTF(a);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
