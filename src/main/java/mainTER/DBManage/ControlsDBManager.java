package mainTER.DBManage;

import mainTER.exception.ControlsDataAlreadyExistsException;
import mainTER.exception.ControlsDataGetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControlsDBManager {

    private final DBManager dbManager;

    private static final String TABLE_NAME = "Controls";

    public ControlsDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public ControlsDBManager(){this.dbManager = new DBManager(); }

    public void createTableControls(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("rightControl");                   listSize.add(30);
        listName.add("leftControl");                    listSize.add(30);
        listName.add("jump");                           listSize.add(30);
        listName.add("switchUp");                       listSize.add(30);
        listName.add("switchDown");                     listSize.add(30);
        listName.add("action");                         listSize.add(30);
        dbManager.createTable(TABLE_NAME, listName, 0, listSize);
    }
    public void removeTableControls(){
        dbManager.dropTable(TABLE_NAME);
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

    public boolean verifyTableControlsExist(){
        ResultSet resultSet = dbManager.selectIntoTable("SELECT * FROM Controls");
        try{
            if(resultSet.next()){
                return true;
            }
        }catch(Exception ignored){

        }
        return false;
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

        ArrayList<Object> listInsert = new ArrayList<>(Arrays.asList(controls));
        dbManager.insertIntoTable(TABLE_NAME, listInsert);
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
        try {
            return dbManager.getData(TABLE_NAME, null, null, "leftControl");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    public void setLeft(String left) {
        dbManager.updateTable(TABLE_NAME, null, null, "leftControl", left);
    }

    public String getRight() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, "rightControl");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setRight(String right) {
        dbManager.updateTable(TABLE_NAME, null, null, "rightControl", right);
    }

    public String getJump() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, "jump");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setJump(String jump) {
        dbManager.updateTable(TABLE_NAME, null, null, "jump", jump);
    }

    public String getSwitchUp() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, "switchUp");
        }catch(SQLException sqlException)
        {
            throw new ControlsDataGetException();
        }
    }

    public void setSwitchUp(String switchUp) {
        dbManager.updateTable(TABLE_NAME, null, null, "switchUp", switchUp);
    }

    public String getSwitchDown() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, "switchDown");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setSwitchDown(String switchDown) {
        dbManager.updateTable(TABLE_NAME, null, null, "switchDown", switchDown);
    }

    public String getAction() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, "action");
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    public void setAction(String action) {
        dbManager.updateTable(TABLE_NAME, null, null, "action", action);
    }

    public List<String> toArray() throws  SQLException {
        return dbManager.toArray(TABLE_NAME);
    }


   
}
