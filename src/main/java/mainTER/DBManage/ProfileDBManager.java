package mainTER.DBManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileDBManager {

    private final DBManager dbManager;

    public ProfileDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public ProfileDBManager(){this.dbManager = new DBManager(); }

    public void createTableProfile(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("name");                   listSize.add(30);
        listName.add("time");                   listSize.add(30);
        listName.add("mapName");                listSize.add(30);
        dbManager.createTable("Profile", listName, 0, listSize);
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

        ArrayList<Object> listInsert = new ArrayList<>();
        listInsert.add(name);
        listInsert.add(time);
        listInsert.add(mapName);
        dbManager.insertIntoTable("Profile", listInsert);
    }

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

    public void setTime(String name,int time,String mapName) {
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add("name");                   listRealValueOfLine.add(name);
        listNameLine.add("mapName");                listRealValueOfLine.add(mapName);
        dbManager.updateTable("Profile", listNameLine, listRealValueOfLine, "time", time);
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

    public List<String> getListProfileFromDatabase(String mapName){
        ArrayList<String> listProfile = new ArrayList<>();
        ResultSet rs;
        try{
            rs = dbManager.selectIntoTable("SELECT * FROM Profile WHERE mapName = '"+SecureManage.getEncrypted(mapName)+"';");
            while(rs.next()){
                listProfile.add(SecureManage.getDecrypted(rs.getString("name")));
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listProfile;
    }

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
