package mainTER.DBManage;

import mainTER.exception.ControlsDataGetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControlsDBManager {
    private final DBManager dbManager;



    public ControlsDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public ControlsDBManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void createTableControls(){
        dbManager.createTableOrInsert("CREATE TABLE Controls (" +
                "name VARCHAR(30) PRIMARY KEY,"+
                "rightControl VARCHAR(30)," +
                "leftControl VARCHAR(30)," +
                "jump VARCHAR(30)," +
                "switchUp VARCHAR(30)," +
                "switchDown VARCHAR(30)," +
                "skill1 VARCHAR(30)," +
                "skill2 VARCHAR(30),"+
                "skill3 VARCHAR(30)"+
                ");");
    }
    public void removeTableControls(){
        dbManager.dropTable("Controls");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }


    public void insertIntoTableControls(String name, String... characters) {
        // TODO verify insert data
        // TODO verify data doesn't exist already
        // TODO use PrepareStatement to insert



        String reqValues = "INSERT INTO Controls VALUES (" +
                "'" + name+"'";
                for(String character : characters){
                    reqValues += ",'" + character +"'";
                }
                 reqValues += ")";
        dbManager.createTableOrInsert(reqValues);
    }


    public List<String> getListNameFromDatabase(){
        ArrayList<String> listName = new ArrayList<>();
        ResultSet rs;
        try{
            rs = dbManager.selectIntoTable("SELECT * FROM Controls;");
            while(rs.next()){
                listName.add((String)rs.getObject("name"));
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listName;
    }

    private ResultSet selectIntoTableControls(String nameCharacter){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT *" +
                    " FROM Controls WHERE name = '" + nameCharacter + "'");
            rs.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données du controlsnages " + nameCharacter);
        }
        return rs;
    }


    public String getLeft(String nameCharacter) throws ControlsDataGetException {
        ResultSet rs = selectIntoTableControls(nameCharacter);
        try {
            return (String)rs.getObject("leftControl");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException(nameCharacter);
        }
    }

    public String getRight(String nameCharacter) throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls(nameCharacter);
        try{
            return (String) rs.getObject("rightControl");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException(nameCharacter);
        }
    }

    public String getJump(String nameCharacter) throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls(nameCharacter);
        try{
            return (String) rs.getObject("jump");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException(nameCharacter);
        }
    }

    public String getSwitchUp(String nameCharacter) throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls(nameCharacter);
        try{
            return (String)rs.getObject("switchUp");
        }catch(SQLException sqlException)
        {
            throw new ControlsDataGetException(nameCharacter);
        }
    }
    public String getSwitchDown(String nameCharacter) throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls(nameCharacter);
        try{
            return (String) rs.getObject("switchDown");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException(nameCharacter);
        }
    }
    public String getSkill1(String nameCharacter) throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls(nameCharacter);
        try{
            return (String) rs.getObject("skill1");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException(nameCharacter);
        }
    }
    public String getSkill2(String nameCharacter) throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls(nameCharacter);
        try{
            return (String) rs.getObject("skill2");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException(nameCharacter);
        }
    }
    public String getSkill3(String nameCharacter) throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls(nameCharacter);
        try{
            return (String) rs.getObject("skill3");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException(nameCharacter);
        }
    }

   
}
