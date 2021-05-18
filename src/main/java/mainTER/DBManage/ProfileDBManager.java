package mainTER.DBManage;

import mainTER.exception.ControlsDataAlreadyExistsException;
import mainTER.exception.PersonDataGetException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileDBManager {

    private final DBManager dbManager;

    public ProfileDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public ProfileDBManager(){this.dbManager = new DBManager(); }

    public void createTableProfile(){
        dbManager.createTableOrInsert("CREATE TABLE IF NOT EXISTS Profile (" +
                "name VARCHAR(30)," +
                "time VARCHAR(30)," +
                "mapName VARCHAR(30)" +
                ");");
    }

    public void removeTableProfile(){
        dbManager.dropTable("Profile");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

    private ResultSet selectIntoTableProfile(String name,String mapName){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT *" +
                    " FROM Profile WHERE name = '" + SecureManage.getEncrypted(name) + "' AND "+
                                        "mapName= '" + SecureManage.getEncrypted(mapName)+ "' ;");
            rs.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données des profiles ");
        }
        return rs;
    }
    public void insertIntoTableProfile(String name, int time,String mapName) {
        // TODO verify insert data
        // TODO verify data doesn't exist already



        String reqValues = "INSERT INTO Profile VALUES ("
                +
                "'" + SecureManage.getEncrypted(name) + "'" + ",'" + SecureManage.getEncrypted(String.valueOf(time))
                + "','" + SecureManage.getEncrypted(mapName)
                + "')";
        dbManager.createTableOrInsert(reqValues);
    }

    public int getTime(String name,String mapName){
        ResultSet rs = selectIntoTableProfile(name,mapName);

        try {
            return Integer.parseInt(SecureManage.getDecrypted(rs.getString("time")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void setTime(String name,int time,String mapName) {
        String request = "UPDATE Profile " +
                "SET " +
                "time = '" + SecureManage.getEncrypted(String.valueOf(time)) + "' " +
                "WHERE name = '" + SecureManage.getEncrypted(name)+"' AND " +
                "mapName= '" + SecureManage.getEncrypted(mapName)+ "' ;";
        dbManager.updateTable(request);
    }

    public boolean nameExist(String name,String mapName) {
        try {
            String sql = "Select 1 from Profile where name = ? and mapName = ? ";

            PreparedStatement ps = dbManager.getco().prepareStatement(sql);
            ps.setString(1, SecureManage.getEncrypted(name));
            ps.setString(2,SecureManage.getEncrypted(mapName));
            ResultSet rs = ps.executeQuery();

            return rs.next();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;




    }

    public List<String> getListProfileFromDatabase(){
        ArrayList<String> listProfile = new ArrayList<>();
        ResultSet rs;
        try{
            rs = dbManager.selectIntoTable("SELECT * FROM Profile;");
            while(rs.next()){
                listProfile.add(SecureManage.getDecrypted(rs.getString("name")));
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listProfile;
    }
}
