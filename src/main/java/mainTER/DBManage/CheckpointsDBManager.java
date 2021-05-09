package mainTER.DBManage;

import mainTER.exception.CheckpointsCharacterDoesntExistException;
import mainTER.exception.CheckpointsDataAlreadyExistException;
import mainTER.exception.CheckpointsDataNotCorrectException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckpointsDBManager {

    private final DBManager dbManager;

    private final boolean isForTest;

    private final String nameDatabases;

    private static final String STRING_UPDATE_CHECKPOINTS = "UPDATE Checkpoints ";



    public CheckpointsDBManager(String nameFileDB){
        isForTest = true;
        nameDatabases = nameFileDB;
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public CheckpointsDBManager(){
        isForTest = false;
        nameDatabases = "";
        this.dbManager = new DBManager();
    }

    public void createTableCheckPoints(){
        dbManager.createTableOrInsert("CREATE TABLE Checkpoints (" +
                "x VARCHAR(30)," +
                "y VARCHAR(30)," +
                "characterName VARCHAR(30)," +
                "mapName VARCHAR(30)" +
                ");");
    }

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

    public void removeTableCheckPoints(){
        dbManager.dropTable("Checkpoints");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

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

    public void insertIntoTableCheckpoints(double x, double y, String characterName, String mapName)
        throws CheckpointsDataNotCorrectException, CheckpointsDataAlreadyExistException, CheckpointsCharacterDoesntExistException {
        // TODO verify if the map exist.
        if(characterName.compareTo("")==0 || mapName.compareTo("")==0){
            throw new CheckpointsDataNotCorrectException();
        }

        ResultSet resultSet = selectIntoTableCheckpoints();
        try{
            resultSet.getString("x");
            throw new CheckpointsDataAlreadyExistException();
        }catch(SQLException ignored){

        }

        verifyCharacterExist(characterName);

        String reqValues = "INSERT INTO Checkpoints VALUES (" +
                "'" + SecureManage.getEncrypted(String.valueOf(x)) + "'" + ",'" + SecureManage.getEncrypted(String.valueOf(y))
                + "','" + SecureManage.getEncrypted(characterName) + "','" + SecureManage.getEncrypted(mapName)
                + "');";


        dbManager.createTableOrInsert(reqValues);
    }
    public void setX(double x) {
        String request = STRING_UPDATE_CHECKPOINTS +
                "SET " +
                "x = '" + SecureManage.getEncrypted(String.valueOf(x)) + "';";
        dbManager.updateTable(request);
    }
    public double getX() {
        ResultSet rs = selectIntoTableCheckpoints();
        try{
            return Double.parseDouble(SecureManage.getDecrypted((String) rs.getObject("x")));
        }catch(SQLException sqlException){
            return 0;
        }
    }
    public void setY(double y) {
        String request = STRING_UPDATE_CHECKPOINTS +
                "SET " +
                "y = '" + SecureManage.getEncrypted(String.valueOf(y)) + "';";
        dbManager.updateTable(request);
    }
    public double getY()  {
        ResultSet rs = selectIntoTableCheckpoints();
        try{
            return Double.parseDouble(SecureManage.getDecrypted((String) rs.getObject("y")));
        }catch(SQLException sqlException){
            return 0;
        }
    }
    public void setCharacterName(String characterName) throws CheckpointsCharacterDoesntExistException {
        verifyCharacterExist(characterName);
        String request = STRING_UPDATE_CHECKPOINTS +
                "SET " +
                "characterName = '" + SecureManage.getEncrypted(characterName) + "';";
        dbManager.updateTable(request);
    }
    public String getCharacterName() {
        ResultSet rs = selectIntoTableCheckpoints();
        try{
            return SecureManage.getDecrypted((String) rs.getObject("characterName"));
        }catch(SQLException sqlException){
            return "";
        }
    }

    public void setMapName(String mapName) {
        String request = STRING_UPDATE_CHECKPOINTS +
                "SET " +
                "mapName = '" + SecureManage.getEncrypted(mapName) + "';";
        dbManager.updateTable(request);
    }
    public String getMapName()  {
        ResultSet rs = selectIntoTableCheckpoints();
        try{
            return SecureManage.getDecrypted((String) rs.getObject("mapName"));
        }catch(SQLException sqlException){
            return "";
        }
    }
}
