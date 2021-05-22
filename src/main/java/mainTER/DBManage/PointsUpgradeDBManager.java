package mainTER.DBManage;

import mainTER.exception.PersonDataAlreadyExistException;
import mainTER.exception.PersonDataGetException;
import mainTER.exception.PersonDataNotCorrectException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PointsUpgradeDBManager {

    private final DBManager dbManager;

    /**
     * Constructor for the application.
     */
    public PointsUpgradeDBManager(){
        this.dbManager = new DBManager();
    }

    /**
     * Constructor for the test.
     * @param name name of the databases for the test.
     */
    public PointsUpgradeDBManager(String name){
        this.dbManager = new DBManager(name, "test");
    }
    public void removeTablePointsUpgrade(){
        dbManager.dropTable("PointsUpgrade");
    }

    /**
     * remove all table.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

    /**
     * Create table for pointsUpgrade.
     */
    public void createTablePointsUpgrade(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("x");              listSize.add(40);
        listName.add("y");              listSize.add(40);
        listName.add("mapName");        listSize.add(50);
        listName.add("isTaken");        listSize.add(40);
        dbManager.createTable("PointsUpgrade", listName, 0, listSize);
    }

    /**
     * insert values into PointsUpgrade table.
     * @param x coordinate x.
     * @param y coordinate y.
     * @param mapName the name of the map.
     */
    public void insertIntoTablePointsUpgrade(double x, double y, String mapName){

            ArrayList<Object> listObject = new ArrayList<>();
            listObject.add(x);
            listObject.add(y);
            listObject.add(mapName);
            listObject.add("false");
            dbManager.insertIntoTable("PointsUpgrade", listObject);

    }

    /**
     * get if the point is taken or not.
     * @param x the coordinate x.
     * @param y the coordinate y.
     * @param mapName the map of the name.
     * @return true if the point is taken, false otherwise.
     */
    public boolean isTaken(double x,double y, String mapName) {
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Object> listRequest = new ArrayList<>();
        listName.add("x");      listRequest.add(x);
        listName.add("y");      listRequest.add(y);
        listName.add("mapName"); listRequest.add(mapName);
        try {
            return dbManager.getData("PointsUpgrade", listName, listRequest, "isTaken").compareTo("true") == 0;
        }catch(Exception ignored){
        }
        return false;
    }

    /**
     * update table for if the point is taken.
     * @param x the coordinate x.
     * @param y the coordinate y.
     * @param mapName the name of the map.
     * @param newValue the new boolean value.
     */
    public void setIsTaken(double x, double y, String mapName, boolean newValue){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add("x");         listRealValueOfLine.add(x);
        listNameLine.add("y");          listRealValueOfLine.add(y);
        listNameLine.add("mapName");    listRealValueOfLine.add(mapName);
        dbManager.updateTable("PointsUpgrade", listNameLine, listRealValueOfLine, "isTaken", newValue);
    }

    /**
     * get the number of points the player has taken.
     * @return the number of points the player has taken.
     */
    public int getPoints(){
        int i = 0;
        ResultSet rs;
        try{
            rs = dbManager.selectIntoTable("SELECT * FROM PointsUpgrade WHERE isTaken = '"+SecureManage.getEncrypted("true")+"' ;");
            while(rs.next()){
                i++;
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        System.out.println(i);

        return i;
    }


}
