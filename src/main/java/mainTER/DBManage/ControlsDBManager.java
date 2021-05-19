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

    private static final String ATTRIBUTE_STRING_RIGHT_CONTROL = "rightControl";

    private static final String ATTRIBUTE_STRING_LEFT_CONTROL = "leftControl";

    private static final String ATTRIBUTE_STRING_JUMP = "jump";

    private static final String ATTRIBUTE_STRING_SWITCH_UP = "switchUp";

    private static final String ATTRIBUTE_STRING_SWITCH_DOWN = "switchDown";

    private static final String ATTRIBUTE_STRING_ACTION = "action";

    public ControlsDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public ControlsDBManager(){this.dbManager = new DBManager(); }

    public void createTableControls(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add(ATTRIBUTE_STRING_RIGHT_CONTROL);                   listSize.add(30);
        listName.add(ATTRIBUTE_STRING_LEFT_CONTROL);                    listSize.add(30);
        listName.add(ATTRIBUTE_STRING_JUMP);                            listSize.add(30);
        listName.add(ATTRIBUTE_STRING_SWITCH_UP);                       listSize.add(30);
        listName.add(ATTRIBUTE_STRING_SWITCH_DOWN);                     listSize.add(30);
        listName.add(ATTRIBUTE_STRING_ACTION);                          listSize.add(30);
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

        try {
            dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_JUMP);
            throw new ControlsDataAlreadyExistsException(ATTRIBUTE_STRING_JUMP);
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

    public String getLeft() throws ControlsDataGetException {
        try {
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_LEFT_CONTROL);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    public void setLeft(String left) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_LEFT_CONTROL, left);
    }

    public String getRight() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_RIGHT_CONTROL);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setRight(String right) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_RIGHT_CONTROL, right);
    }

    public String getJump() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_JUMP);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setJump(String jump) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_JUMP, jump);
    }

    public String getSwitchUp() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_SWITCH_UP);
        }catch(SQLException sqlException)
        {
            throw new ControlsDataGetException();
        }
    }

    public void setSwitchUp(String switchUp) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_SWITCH_UP, switchUp);
    }

    public String getSwitchDown() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_SWITCH_DOWN);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }
    public void setSwitchDown(String switchDown) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_SWITCH_DOWN, switchDown);
    }

    public String getAction() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_ACTION);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    public void setAction(String action) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_ACTION, action);
    }

    public List<String> toArray() throws  SQLException {
        return dbManager.toArray(TABLE_NAME);
    }


   
}
