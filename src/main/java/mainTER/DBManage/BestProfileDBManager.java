package mainTER.DBManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BestProfileDBManager {

    private final DBManager dbManager;

    public BestProfileDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public BestProfileDBManager(){this.dbManager = new DBManager(); }

    public void createTableBestProfile(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("name");               listSize.add(30);
        listName.add("time");               listSize.add(30);
        listName.add("mapName");            listSize.add(30);
        dbManager.createTable("BestProfile", listName, 0, listSize);
    }

    private ResultSet selectIntoTableBestProfile( String mapName){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT *" +
                    " FROM BestProfile WHERE "+
                    "mapName= '" + SecureManage.getEncrypted(mapName)+ "' ;");
            rs.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données des profiles ");
        }
        return rs;
    }
    public void insertIntoTableBestProfile(String name, int time,String mapName) {
        // TODO verify insert data
        // TODO verify data doesn't exist already

        ArrayList<Object> listInsert = new ArrayList<>();
        listInsert.add(name);
        listInsert.add(time);
        listInsert.add(mapName);
        dbManager.insertIntoTable("BestProfile", listInsert);

        /*String reqValues = "INSERT INTO BestProfile VALUES ("
                +
                "'" + SecureManage.getEncrypted(name) + "'" + ",'" + SecureManage.getEncrypted(String.valueOf(time))
                + "','" + SecureManage.getEncrypted(mapName)
                + "')";
        dbManager.createTableOrInsert(reqValues);*/
    }

    public int getTime(String mapName){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add("mapName");                listRealValueOfLine.add(mapName);
        try {
            return Integer.parseInt(dbManager.getData("BestProfile", listNameLine, listRealValueOfLine, "time"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }

    }
    public String getName(String mapName){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add("mapName");                listRealValueOfLine.add(mapName);
        try {

            return dbManager.getData("BestProfile", listNameLine, listRealValueOfLine, "name");
        }catch(SQLException sqlException){
                sqlException.printStackTrace();
        }
        return "";
    }

    public void setName(String name,String mapName){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add("mapName");                listRealValueOfLine.add(mapName);
        dbManager.updateTable("BestProfile", listNameLine, listRealValueOfLine, "name", name);
    }

    public void setTime(int time,String mapName) {
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add("mapName");                listRealValueOfLine.add(mapName);
        dbManager.updateTable("BestProfile", listNameLine, listRealValueOfLine, "time", time);
    }

    public void removeTableProfile(){
        dbManager.dropTable("BestProfile");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }
}
