package mainTER.DBManage;

import mainTER.exception.ControlsDataAlreadyExistsException;
import mainTER.exception.ControlsDataGetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControlsDBManager {
    private final DBManager dbManager;



    public ControlsDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public ControlsDBManager(){this.dbManager = new DBManager(); };

    public void createTableControls(){
        dbManager.createTableOrInsert("CREATE TABLE Controls (" +
                "rightControl VARCHAR(30)," +
                "leftControl VARCHAR(30)," +
                "jump VARCHAR(30)," +
                "switchUp VARCHAR(30)," +
                "switchDown VARCHAR(30)" +
                ");");
    }
    public void removeTableControls(){
        dbManager.dropTable("Controls");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }


    public void insertIntoTableControls(String... controls) throws ControlsDataAlreadyExistsException {
        // TODO verify insert data
        // TODO verify data doesn't exist already

        ResultSet resultSet = selectIntoTableControls();
        try {
            resultSet.getObject("jump");
            throw new ControlsDataAlreadyExistsException("jump");
        } catch (SQLException ignored) {

        }

        String reqValues = "INSERT INTO Controls VALUES (";

                for(String control : controls){
                    reqValues += "'" + control +"',";
                }
                reqValues = reqValues.substring(0,reqValues.length()-1);
                 reqValues += ")";
        dbManager.createTableOrInsert(reqValues);
    }



    private ResultSet selectIntoTableControls(){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT * FROM Controls");
            rs.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données des controls ");
        }
        return rs;
    }


    public String getLeft() throws ControlsDataGetException {
        ResultSet rs = selectIntoTableControls();
        try {

            return (String)rs.getObject("leftControl");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    public void setLeft(String left) {
        String request = "UPDATE Controls " +
                "SET " +
                "leftControl = '" + left + "';";
        dbManager.updateTable(request);
    }

    public String getRight() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return (String) rs.getObject("rightControl");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setRight(String right) {
        String request = "UPDATE Controls " +
                "SET " +
                "rightControl = '" + right + "';";
        dbManager.updateTable(request);
    }

    public String getJump() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return (String) rs.getObject("jump");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setJump(String jump) {
        String request = "UPDATE Controls " +
                "SET " +
                "jump = '" + jump + "';";
        dbManager.updateTable(request);
    }

    public String getSwitchUp() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return (String)rs.getObject("switchUp");
        }catch(SQLException sqlException)
        {
            throw new ControlsDataGetException();
        }
    }
    public void setSwitchUp(String switchUp) {
        String request = "UPDATE Controls " +
                "SET " +
                "switchUp = '" + switchUp + "';";
        dbManager.updateTable(request);
    }
    public String getSwitchDown() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return (String) rs.getObject("switchDown");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setSwitchDown(String switchDown) {
        String request = "UPDATE Controls " +
                "SET " +
                "switchDown = '" + switchDown + "';";
        dbManager.updateTable(request);
    }



    public ArrayList<String> toArray() throws  SQLException {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = selectIntoTableControls();

        int columnCount = rs.getMetaData().getColumnCount();

        for (int i = 0; i <columnCount ; i++)
        {
            result.add( rs.getString(i + 1) );
        }
        return result;
    }

    public void initializeControls(){
        setJump(" ");
        setLeft("q");
        setSwitchUp("a");
        setSwitchDown("e");
        setRight("d");
    }

   
}
