package mainTER.DBManage;

import mainTER.exception.CheckpointsCharacterDoesntExistException;
import mainTER.exception.CheckpointsDataAlreadyExistException;
import mainTER.exception.CheckpointsDataNotCorrectException;
import mainTER.exception.CheckpointsMapDoesntExistException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckpointsDBManager {

    /**
     * For the connection with the database.
     */
    private final DBManager dbManager;

    /**
     * True if the database is used for the test.
     */
    private final boolean isForTest;

    /**
     * Name of the databases if it is for the test, empty otherwise.
     */
    private final String nameDatabases;

    /**
     * variable for update the databases.
     */
    private static final String STRING_UPDATE_CHECKPOINTS = "UPDATE Checkpoints ";

    private static final String TABLE_NAME = "Checkpoints";

    /**
     * Constructor of the checkpoint databases for the test.
     * @param nameFileDB name of the databases for the test.
     */
    public CheckpointsDBManager(String nameFileDB){
        isForTest = true;
        nameDatabases = nameFileDB;
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    /**
     * Constructor of the checkpoint databases for the application.
     */
    public CheckpointsDBManager(){
        isForTest = false;
        nameDatabases = "";
        this.dbManager = new DBManager();
    }

    /**
     * Create the table for the checkpoint in the databases.
     */
    public void createTableCheckPoints(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("x");                  listSize.add(30);
        listName.add("y");                  listSize.add(30);
        listName.add("characterName");      listSize.add(30);
        listName.add("mapName");            listSize.add(30);
        dbManager.createTable(TABLE_NAME, listName, 0, listSize);
    }

     /**
     * Get the data of the table checkpoint.
     * @return the data of the table checkpoint.
     */
    private ResultSet selectIntoTableCheckpoints(){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT * FROM Checkpoints");
            rs.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données des controls ");
        }
        return rs;
    }

    /**
     * Delete the table checkpoint from the databases.
     */
    public void removeTableCheckPoints(){
        dbManager.dropTable(TABLE_NAME);
    }

    /**
     * Drop cascade.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

    public boolean verifyTableCheckpointsExist(){
        ResultSet resultSet = dbManager.selectIntoTable("SELECT * FROM Checkpoints");
        try{
            if(resultSet.next()){
                return true;
            }
        }catch(Exception ignored){
        }

        return false;
    }

    /**
     * verify if a character exist.
     * @param characterName name of the character we want to verify existence.
     * @throws CheckpointsCharacterDoesntExistException if the character doesn't exist in the databases.
     */
    private void verifyCharacterExist(String characterName) throws CheckpointsCharacterDoesntExistException{
        PersonDBManager personDBManager;
        if(isForTest){
            personDBManager = new PersonDBManager(nameDatabases);
        }else{
            personDBManager = new PersonDBManager();
        }
        try {
            if (!personDBManager.isCharacterExist(characterName)) {
                throw new CheckpointsCharacterDoesntExistException(characterName);
            }
        }catch(Exception exception){
            throw new CheckpointsCharacterDoesntExistException(characterName);
        }
    }

    /**
     * verify if the map exist.
     * @param mapName name of the map we want to verify existence.
     * @throws CheckpointsMapDoesntExistException if the map doesn't exist in the databases.
     */
    private void verifyMapExist(String mapName) throws CheckpointsMapDoesntExistException{
        MapDBManager mapDBManager;
        if(isForTest){
            mapDBManager = new MapDBManager(nameDatabases);
        }else{
            mapDBManager = new MapDBManager();
        }

        if(!mapDBManager.verifyMapExist(mapName)){
            throw new CheckpointsMapDoesntExistException(mapName);
        }
    }

    /**
     * Insert data into the table checkpoint in the database.
     * @param x the coordinate x.
     * @param y the coordinate y.
     * @param characterName the name of the current character.
     * @param mapName the name of the current map.
     * @throws CheckpointsDataNotCorrectException if the data we get aren't correct.
     * @throws CheckpointsDataAlreadyExistException if the data we want to insert already exist.
     * @throws CheckpointsCharacterDoesntExistException if the character doesn't exist in the databases.
     * @throws CheckpointsMapDoesntExistException if the map doesn't exist in the databases.
     */
    public void insertIntoTableCheckpoints(double x, double y, String characterName, String mapName)
        throws CheckpointsDataNotCorrectException, CheckpointsDataAlreadyExistException, CheckpointsCharacterDoesntExistException,
            CheckpointsMapDoesntExistException{

        if(characterName.compareTo("")==0 || mapName.compareTo("")==0){
            throw new CheckpointsDataNotCorrectException();
        }

        var resultSet = selectIntoTableCheckpoints();
        try{
            resultSet.getString("x");
            throw new CheckpointsDataAlreadyExistException();
        }catch(SQLException ignored){

        }

        verifyCharacterExist(characterName);
        verifyMapExist(mapName);

        ArrayList<Object> listInsert = new ArrayList<>();
        listInsert.add(x);
        listInsert.add(y);
        listInsert.add(characterName);
        listInsert.add(mapName);
        dbManager.insertIntoTable(TABLE_NAME, listInsert);
    }

    /**
     * Change value of the coordinate x in the table checkpoint in the databases.
     * @param x the new value of the coordinate x.
     */
    public void setX(double x) {
        dbManager.updateTable(TABLE_NAME, null, null, "x", x);
    }

    /**
     * Get the value of the coordinate x in the table checkpoint in the databases.
     * @return the value of the coordinate x in the table checkpoint in the databases.
     */
    public double getX() {
        try{
            return Double.parseDouble(dbManager.getData(TABLE_NAME, null, null, "x"));
        }catch(SQLException sqlException){
            return 0;
        }
    }

    /**
     * Change value of the coordinate y in the table checkpoint in the databases.
     * @param y the new value of the coordinate y.
     */
    public void setY(double y) {
        dbManager.updateTable(TABLE_NAME, null, null, "y", y);
    }

    /**
     * Get the value of the coordinate y in the table checkpoint in the databases.
     * @return the value of the coordinate y in the table checkpoint in the databases.
     */
    public double getY()  {
        try{
            return Double.parseDouble(dbManager.getData(TABLE_NAME, null, null, "y"));
        }catch(SQLException sqlException){
            return 0;
        }
    }

    /**
     * Change value of the character name in the table checkpoint in the databases.
     * @param characterName the new value of the name of the character.
     * @throws CheckpointsCharacterDoesntExistException if the character given doesn't exist in the databases.
     */
    public void setCharacterName(String characterName) throws CheckpointsCharacterDoesntExistException {
        verifyCharacterExist(characterName);
        dbManager.updateTable(TABLE_NAME, null, null, "characterName", characterName);
    }

    /**
     * Get the value of the character name in the table checkpoint in the databases.
     * @return the value of the character name in the table checkpoint in the databases.
     */
    public String getCharacterName() {
        try{
            return dbManager.getData(TABLE_NAME, null, null, "characterName");
        }catch(SQLException sqlException){
            return "";
        }
    }

    /**
     * Change value of the map name in the table checkpoint in the databases.
     * @param mapName the new value of the map name.
     * @throws CheckpointsMapDoesntExistException if the map given doesn't exist in the databases.
     */
    public void setMapName(String mapName) throws CheckpointsMapDoesntExistException {
        verifyMapExist(mapName);
        dbManager.updateTable(TABLE_NAME, null, null, "mapName", mapName);
    }

    /**
     * Get the value of the map name in the table checkpoint in the databases.
     * @return the value of the map name in the table checkpoint in the databases.
     */
    public String getMapName()  {
        try{
            return dbManager.getData(TABLE_NAME, null, null, "mapName");
        }catch(SQLException sqlException){
            return "";
        }
    }
}
