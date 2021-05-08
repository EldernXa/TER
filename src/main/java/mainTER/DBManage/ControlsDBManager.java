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

    public ControlsDBManager(){this.dbManager = new DBManager(); }

    public void createTableControls(){
        dbManager.createTableOrInsert("CREATE TABLE Controls (" +
                "rightControl VARCHAR(30)," +
                "leftControl VARCHAR(30)," +
                "jump VARCHAR(30)," +
                "switchUp VARCHAR(30)," +
                "switchDown VARCHAR(30)," +
                "action VARCHAR(30)"+
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

        StringBuilder reqValues = new StringBuilder("INSERT INTO Controls VALUES (");

                for(String control : controls){
                    reqValues.append("'").append(SecureManage.getEncrypted(control)).append("',");
                }
                reqValues = new StringBuilder(reqValues.substring(0, reqValues.length() - 1));
                 reqValues.append(")");
        dbManager.createTableOrInsert(reqValues.toString());
    }

    public boolean getIfCtrlAlreadyUsed(String ctrlKey) throws ControlsDataGetException{
        return getLeft().compareToIgnoreCase(ctrlKey) == 0 || getJump().compareToIgnoreCase(ctrlKey)==0 ||
                getRight().compareToIgnoreCase(ctrlKey) == 0 || getSwitchDown().compareToIgnoreCase(ctrlKey)==0 ||
                getSwitchUp().compareToIgnoreCase(ctrlKey)==0 || getAction().compareToIgnoreCase(ctrlKey)==0;
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

            return SecureManage.getDecrypted((String) rs.getObject("leftControl"));
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    public void setLeft(String left) {
        String request = "UPDATE Controls " +
                "SET " +
                "leftControl = '" + SecureManage.getEncrypted(left) + "';";
        dbManager.updateTable(request);
    }

    public String getRight() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return SecureManage.getDecrypted((String) rs.getObject("rightControl"));
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setRight(String right) {
        String request = "UPDATE Controls " +
                "SET " +
                "rightControl = '" + SecureManage.getEncrypted(right) + "';";
        dbManager.updateTable(request);
    }

    public String getJump() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return SecureManage.getDecrypted((String) rs.getObject("jump"));
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setJump(String jump) {
        String request = "UPDATE Controls " +
                "SET " +
                "jump = '" + SecureManage.getEncrypted(jump) + "';";
        dbManager.updateTable(request);
    }

    public String getSwitchUp() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return SecureManage.getDecrypted((String)rs.getObject("switchUp"));
        }catch(SQLException sqlException)
        {
            throw new ControlsDataGetException();
        }
    }
    public void setSwitchUp(String switchUp) {
        String request = "UPDATE Controls " +
                "SET " +
                "switchUp = '" + SecureManage.getEncrypted(switchUp) + "';";
        dbManager.updateTable(request);
    }
    public String getSwitchDown() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return SecureManage.getDecrypted((String) rs.getObject("switchDown"));
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setSwitchDown(String switchDown) {
        String request = "UPDATE Controls " +
                "SET " +
                "switchDown = '" + SecureManage.getEncrypted(switchDown) + "';";
        dbManager.updateTable(request);
    }

    public String getAction() throws ControlsDataGetException{
        ResultSet rs = selectIntoTableControls();
        try{
            return SecureManage.getDecrypted((String) rs.getObject("action"));
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setAction(String action) {
        String request = "UPDATE Controls " +
                "SET " +
                "action = '" + SecureManage.getEncrypted(action) + "';";
        dbManager.updateTable(request);
    }



    public ArrayList<String> toArray() throws  SQLException {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = selectIntoTableControls();

        int columnCount = rs.getMetaData().getColumnCount();

        for (int i = 0; i <columnCount ; i++)
        {
            result.add( SecureManage.getDecrypted(rs.getString(i + 1)) );
        }
        return result;
    }


   
}
