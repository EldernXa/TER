package mainTER.DBManage;

import mainTER.exception.ControlsDataAlreadyExistsException;
import mainTER.exception.ControlsDataGetException;
import mainTER.exception.ControlsDataNotCorrectException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControlsDBManager {

    /**
     * Variable for the connexion with the databases.
     */
    private final DBManager dbManager;

    /**
     * private constant for the name of the table.
     */
    private static final String TABLE_NAME = "Controls";

    /**
     * private constant for the attribute right control in the databases.
     */
    private static final String ATTRIBUTE_STRING_RIGHT_CONTROL = "rightControl";

    /**
     * private constant for the attribute left control in the databases.
     */
    private static final String ATTRIBUTE_STRING_LEFT_CONTROL = "leftControl";

    /**
     * private constant for the attribute jump in the databases.
     */
    private static final String ATTRIBUTE_STRING_JUMP = "jump";

    /**
     * private constant for the attribute switch up in the databases.
     */
    private static final String ATTRIBUTE_STRING_SWITCH_UP = "switchUp";

    /**
     * private constant for the attribute switch down in the databases.
     */
    private static final String ATTRIBUTE_STRING_SWITCH_DOWN = "switchDown";

    /**
     * private constant for the attribute action in the databases.
     */
    private static final String ATTRIBUTE_STRING_ACTION = "action";

    /**
     * Constructor to use for the test.
     * @param nameFileDB name of the databases for the test.
     */
    public ControlsDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    /**
     * Constructor for the application.
     */
    public ControlsDBManager(){this.dbManager = new DBManager(); }

    /**
     * Create the table Controls in the databases.
     */
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

    /**
     * Remove the table Controls from the databases.
     */
    public void removeTableControls(){
        dbManager.dropTable(TABLE_NAME);
    }

    /**
     * Drop cascade.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

    /**
     * function who determine if the table controls exist or not.
     * @return true if the table exist, false otherwise.
     */
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

    /**
     * Insert values into table Controls
     * @param controls some parameters to insert into the databases.
     * @throws ControlsDataAlreadyExistsException if a line already exist inside.
     * @throws ControlsDataNotCorrectException if one of the values aren't correct.
     */
    public void insertIntoTableControls(String... controls) throws ControlsDataAlreadyExistsException, ControlsDataNotCorrectException {

        try {
            dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_JUMP);
            throw new ControlsDataAlreadyExistsException(ATTRIBUTE_STRING_JUMP);
        } catch (SQLException ignored) {

        }

        ArrayList<Object> listInsert = new ArrayList<>();

        for(String str : controls){
            if(str.compareTo("")==0){
                throw new ControlsDataNotCorrectException();
            }else{
                listInsert.add(str);
            }
        }
        dbManager.insertIntoTable(TABLE_NAME, listInsert);
    }

    /**
     * verify if a control is already used by one of those that are already in use.
     * @param ctrlKey the control key who we want to verify if it is already in use.
     * @return true if the control key is already in use, false otherwise.
     * @throws ControlsDataGetException if the table is empty.
     */
    public boolean getIfCtrlAlreadyUsed(String ctrlKey) throws ControlsDataGetException{
        return getLeft().compareToIgnoreCase(ctrlKey) == 0 || getJump().compareToIgnoreCase(ctrlKey)==0 ||
                getRight().compareToIgnoreCase(ctrlKey) == 0 || getSwitchDown().compareToIgnoreCase(ctrlKey)==0 ||
                getSwitchUp().compareToIgnoreCase(ctrlKey)==0 || getAction().compareToIgnoreCase(ctrlKey)==0;
    }

    /**
     *
     * @return the value for the control of left attribute.
     * @throws ControlsDataGetException if the table is empty.
     */
    public String getLeft() throws ControlsDataGetException {
        try {
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_LEFT_CONTROL);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    /**
     * setting a new control for the left attribute.
     * @param left the new control for the left attribute.
     */
    public void setLeft(String left) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_LEFT_CONTROL, left);
    }

    /**
     *
     * @return the value for the control of right attribute.
     * @throws ControlsDataGetException if the table is empty.
     */
    public String getRight() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_RIGHT_CONTROL);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    /**
     * setting a new control for the right attribute.
     * @param right the new control for the right attribute.
     */
    public void setRight(String right) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_RIGHT_CONTROL, right);
    }

    /**
     *
     * @return the value for the control of jump attribute.
     * @throws ControlsDataGetException if the table is empty.
     */
    public String getJump() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_JUMP);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    /**
     * setting a new control for the jump attribute.
     * @param jump the new control for the jump attribute.
     */
    public void setJump(String jump) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_JUMP, jump);
    }

    /**
     *
     * @return the value for the control of switchUp attribute.
     * @throws ControlsDataGetException if the table is empty.
     */
    public String getSwitchUp() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_SWITCH_UP);
        }catch(SQLException sqlException)
        {
            throw new ControlsDataGetException();
        }
    }

    /**
     * setting a new control for the switchUp attribute.
     * @param switchUp the new control for the switchUp attribute.
     */
    public void setSwitchUp(String switchUp) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_SWITCH_UP, switchUp);
    }

    /**
     *
     * @return the value for the control of switchDown attribute.
     * @throws ControlsDataGetException if the table is empty.
     */
    public String getSwitchDown() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_SWITCH_DOWN);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    /**
     * setting a new control for the switchDown attribute.
     * @param switchDown the new control for the switchDown attribute.
     */
    public void setSwitchDown(String switchDown) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_SWITCH_DOWN, switchDown);
    }

    /**
     *
     * @return the value of the control of action attribute.
     * @throws ControlsDataGetException if the table is empty.
     */
    public String getAction() throws ControlsDataGetException{
        try{
            return dbManager.getData(TABLE_NAME, null, null, ATTRIBUTE_STRING_ACTION);
        }catch(SQLException sqlException){
            throw new ControlsDataGetException();
        }
    }

    /**
     * setting a new control for the action attribute.
     * @param action the new control for the action attribute.
     */
    public void setAction(String action) {
        dbManager.updateTable(TABLE_NAME, null, null, ATTRIBUTE_STRING_ACTION, action);
    }


    /**
     *
     * @return a array containing values from the one line inside the table.
     * @throws SQLException if there's a problem with the connexion with the dabatases.
     */
    public List<String> toArray() throws  SQLException {
        return dbManager.toArray(TABLE_NAME);
    }


   
}
