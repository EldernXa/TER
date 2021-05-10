package mainTER.DBManage;

import mainTER.exception.ControlsDataAlreadyExistsException;
import mainTER.exception.PersonDataGetException;

import java.sql.ResultSet;
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
        dbManager.createTableOrInsert("CREATE TABLE Profile (" +
                "name VARCHAR(30)," +
                "time VARCHAR(30)" +
                ");");
    }

    public void removeTableProfile(){
        dbManager.dropTable("Profile");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

    private ResultSet selectIntoTableProfile(String name){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT *" +
                    " FROM Profile WHERE name = '" + SecureManage.getEncrypted(name) + "'");
            rs.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données des profiles ");
        }
        return rs;
    }
    public void insertIntoTableProfile(String name, int time) {
        // TODO verify insert data
        // TODO verify data doesn't exist already



        String reqValues = "INSERT INTO Profile VALUES ("
                +
                "'" + SecureManage.getEncrypted(name) + "'" + ",'" + SecureManage.getEncrypted(String.valueOf(time))
                + "')";
        dbManager.createTableOrInsert(reqValues);
    }

    public int getTime(String name){
        ResultSet rs = selectIntoTableProfile(name);

        try {
            return Integer.parseInt(SecureManage.getDecrypted(rs.getString("time")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void setTime(String name,int time) {
        String request = "UPDATE Profile " +
                "SET " +
                "time = '" + SecureManage.getEncrypted(String.valueOf(time)) + "' " +
                "WHERE name = '" + SecureManage.getEncrypted(name)+"';";
        dbManager.updateTable(request);
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
