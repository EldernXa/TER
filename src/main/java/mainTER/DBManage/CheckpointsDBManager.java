package mainTER.DBManage;

import mainTER.exception.CheckpointsCharacterDoesntExistException;
import mainTER.exception.CheckpointsDataAlreadyExistException;
import mainTER.exception.CheckpointsDataNotCorrectException;
import mainTER.exception.CheckpointsMapDoesntExistException;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        dbManager.createTableOrInsert("CREATE TABLE Checkpoints (" +
                "x VARCHAR(30)," +
                "y VARCHAR(30)," +
                "characterName VARCHAR(30)," +
                "mapName VARCHAR(30)" +
                ");");
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
        dbManager.dropTable("Checkpoints");
    }

    /**
     * Drop cascade.
     */
    public void dropCascade(){
        dbManager.dropCascade();
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
        if(!personDBManager.isCharacterExist(characterName)){
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

        String reqValues = "INSERT INTO Checkpoints VALUES (" +
                "'" + SecureManage.getEncrypted(String.valueOf(x)) + "'" + ",'" + SecureManage.getEncrypted(String.valueOf(y))
                + "','" + SecureManage.getEncrypted(characterName) + "','" + SecureManage.getEncrypted(mapName)
                + "');";


        dbManager.createTableOrInsert(reqValues);
    }

    /**
     * Change value of the coordinate x in the table checkpoint in the databases.
     * @param x the new value of the coordinate x.
     */
    public void setX(double x) {
        String request = STRING_UPDATE_CHECKPOINTS +
                "SET " +
                "x = '" + SecureManage.getEncrypted(String.valueOf(x)) + "';";
        dbManager.updateTable(request);
    }

    /**
     * Get the value of the coordinate x in the table checkpoint in the databases.
     * @return the value of the coordinate x in the table checkpoint in the databases.
     */
    public double getX() {
        ResultSet rs = selectIntoTableCheckpoints();
        try{
            return Double.parseDouble(SecureManage.getDecrypted((String) rs.getObject("x")));
        }catch(SQLException sqlException){
            return 0;
        }
    }

    /**
     * Change value of the coordinate y in the table checkpoint in the databases.
     * @param y the new value of the coordinate y.
     */
    public void setY(double y) {
        String request = STRING_UPDATE_CHECKPOINTS +
                "SET " +
                "y = '" + SecureManage.getEncrypted(String.valueOf(y)) + "';";
        dbManager.updateTable(request);
    }

    /**
     * Get the value of the coordinate y in the table checkpoint in the databases.
     * @return the value of the coordinate y in the table checkpoint in the databases.
     */
    public double getY()  {
        ResultSet rs = selectIntoTableCheckpoints();
        try{
            return Double.parseDouble(SecureManage.getDecrypted((String) rs.getObject("y")));
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
        String request = STRING_UPDATE_CHECKPOINTS +
                "SET " +
                "characterName = '" + SecureManage.getEncrypted(characterName) + "';";
        dbManager.updateTable(request);
    }

    /**
     * Get the value of the character name in the table checkpoint in the databases.
     * @return the value of the character name in the table checkpoint in the databases.
     */
    public String getCharacterName() {
        ResultSet rs = selectIntoTableCheckpoints();
        try{
            return SecureManage.getDecrypted((String) rs.getObject("characterName"));
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
        String request = STRING_UPDATE_CHECKPOINTS +
                "SET " +
                "mapName = '" + SecureManage.getEncrypted(mapName) + "';";
        dbManager.updateTable(request);
    }

    /**
     * Get the value of the map name in the table checkpoint in the databases.
     * @return the value of the map name in the table checkpoint in the databases.
     */
    public String getMapName()  {
        ResultSet rs = selectIntoTableCheckpoints();
        try{
            return SecureManage.getDecrypted((String) rs.getObject("mapName"));
        }catch(SQLException sqlException){
            return "";
        }
    }
}
