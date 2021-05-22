package mainTER.DBManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileDBManager {

    private final DBManager dbManager;

    /**
     * Constructor for test purpose.
     * @param nameFileDB the name of the databases for the test.
     */
    public ProfileDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    /**
     * Constructor for the application.
     */
    public ProfileDBManager(){this.dbManager = new DBManager(); }

    /**
     * Create table Profile.
     */
    public void createTableProfile(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("name");                   listSize.add(30);
        listName.add("time");                   listSize.add(30);
        listName.add("mapName");                listSize.add(30);
        dbManager.createTable("Profile", listName, 0, listSize);
    }

    /**
     * Remove the table Profile.
     */
    public void removeTableProfile(){
        dbManager.dropTable("Profile");
    }

    /**
     * Drop cascade.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

    /**
     * Insert values into table Profile.
     * @param name the name of the player.
     * @param time the best time the player finished the map.
     * @param mapName the name of the map.
     */
    public void insertIntoTableProfile(String name, int time,String mapName) {
        // TODO verify insert data
        // TODO verify data doesn't exist already

        ArrayList<Object> listInsert = new ArrayList<>();
        listInsert.add(name);
        listInsert.add(time);
        listInsert.add(mapName);
        dbManager.insertIntoTable("Profile", listInsert);
    }

    /**
     * get the time a player has in a map.
     * @param name the name of a player.
     * @param mapName the name of the map.
     * @return the time a player has in a map.
     */
    public int getTime(String name,String mapName){
        try {
            ArrayList<String> listNameLine = new ArrayList<>();
            ArrayList<Object> listRealValueOfLine = new ArrayList<>();
            listNameLine.add("name");                   listRealValueOfLine.add(name);
            listNameLine.add("mapName");                listRealValueOfLine.add(mapName);
            return Integer.parseInt(dbManager.getData("Profile", listNameLine, listRealValueOfLine, "time"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Set a new time for a player
     * @param name the name of a player.
     * @param time the new time of this player.
     * @param mapName the map who he has a new time.
     */
    public void setTime(String name,int time,String mapName) {
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add("name");                   listRealValueOfLine.add(name);
        listNameLine.add("mapName");                listRealValueOfLine.add(mapName);
        dbManager.updateTable("Profile", listNameLine, listRealValueOfLine, "time", time);
    }

    /**
     * get if a name exist in the Profile table.
     * @param name the name we want to verify if he exist or not.
     * @param mapName the name of the map.
     * @return true if a name exist in the table Profile, false otherwise.
     */
    public boolean nameExist(String name,String mapName) {
        try {
            ArrayList<String> listNameLine = new ArrayList<>();
            ArrayList<Object> listRealValueOfLine = new ArrayList<>();
            listNameLine.add("name");                   listRealValueOfLine.add(name);
            listNameLine.add("mapName");                listRealValueOfLine.add(mapName);
            dbManager.getData("Profile", listNameLine, listRealValueOfLine, "mapName");
            return true;
        }catch (Exception ignored){
        }
        return false;

    }

    /**
     * get a map who contains all pseudo and their time for a map.
     * @param mapName the name of the map.
     * @return a map who contains all pseudo and their time for a map.
     */
    public Map<String, Double> getRanking(String mapName){
        HashMap<String,Double> listProfile = new HashMap<>();
        ResultSet rs;
        try{
            rs = dbManager.selectIntoTable("SELECT name,time FROM Profile WHERE mapName = '"+SecureManage.getEncrypted(mapName)+"';");
            while(rs.next()){
                listProfile.put(SecureManage.getDecrypted(rs.getString("name")),Double.parseDouble(SecureManage.getDecrypted(rs.getString("time"))));
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listProfile;
    }
}
