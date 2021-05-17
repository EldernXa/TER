package mainTER.DBManage;

import mainTER.exception.PersonDataAlreadyExistException;
import mainTER.exception.PersonDataGetException;
import mainTER.exception.PersonDataNotCorrectException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PointsUpgradeDBManager {

    private final DBManager dbManager;

    public PointsUpgradeDBManager(){
        this.dbManager = new DBManager();
    }

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

    public void createTablePointsUpgrade(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("x");              listSize.add(40);
        listName.add("y");              listSize.add(40);
        listName.add("mapName");        listSize.add(50);
        listName.add("isTaken");        listSize.add(40);
        dbManager.createTable("PointsUpgrade", listName, 0, listSize);
    }

    public void insertIntoTablePointsUpgrade(double x, double y, String mapName){

            ArrayList<Object> listObject = new ArrayList<>();
            listObject.add(x);
            listObject.add(y);
            listObject.add(mapName);
            listObject.add("false");
            dbManager.insertIntoTable("PointsUpgrade", listObject);

    }


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

    public void setIsTaken(double x, double y, String mapName, boolean newValue){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add("x");         listRealValueOfLine.add(x);
        listNameLine.add("y");          listRealValueOfLine.add(y);
        listNameLine.add("mapName");    listRealValueOfLine.add(mapName);
        dbManager.updateTable("PointsUpgrade", listNameLine, listRealValueOfLine, "isTaken", newValue);
    }


}
