package mainTER.DBManage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BestProfileDBManager {

    private final DBManager dbManager;

    public BestProfileDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public BestProfileDBManager(){this.dbManager = new DBManager(); }

    public void createTableBestProfile(){
        dbManager.createTableOrInsert("CREATE TABLE BestProfile (" +
                "name VARCHAR(30)," +
                "time VARCHAR(30)," +
                "mapName VARCHAR(30)" +
                ");");
    }
    public void removeTableProfile(){
        dbManager.dropTable("BestProfile");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }
    private ResultSet selectIntoTableBestProfile(String name, String mapName){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT *" +
                    " FROM BestProfile WHERE name = '" + SecureManage.getEncrypted(name) + "' AND "+
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



        String reqValues = "INSERT INTO BestProfile VALUES ("
                +
                "'" + SecureManage.getEncrypted(name) + "'" + ",'" + SecureManage.getEncrypted(String.valueOf(time))
                + "','" + SecureManage.getEncrypted(mapName)
                + "')";
        dbManager.createTableOrInsert(reqValues);
    }

    public int getTime(String name,String mapName){
        ResultSet rs = selectIntoTableBestProfile(name,mapName);

        try {
            return Integer.parseInt(SecureManage.getDecrypted(rs.getString("time")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void setTime(String name,int time,String mapName) {
        String request = "UPDATE BestProfile " +
                "SET " +
                "time = '" + SecureManage.getEncrypted(String.valueOf(time)) + "' " +
                "WHERE name = '" + SecureManage.getEncrypted(name)+"' AND " +
                "mapName= '" + SecureManage.getEncrypted(mapName)+ "' ;";
        dbManager.updateTable(request);
    }
}
