package mainTER.DBManage;

import mainTER.Tools.Coordinate;
import mainTER.exception.MapDataGetException;
import mainTER.exception.MapCharacterNotExistException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapDBManager {

    private final DBManager dbManager;

    private final boolean isForTest;

    private final String nameDatabases;

    public MapDBManager(){
        this.dbManager = new DBManager();
        isForTest = false;
        nameDatabases = "";
    }

    public MapDBManager(String name){
        this.dbManager = new DBManager(name, "test");
        isForTest = true;
        nameDatabases = name;
    }

    public void createTableMap(){
        dbManager.createTableOrInsert("CREATE TABLE Map (" +
                "mapName VARCHAR(50) PRIMARY KEY," +
                "nameFirstCharacter VARCHAR(50)," +
                "coordinateX VARCHAR(50)," +
                "coordinateY VARCHAR(50)" +
                ");");
    }

    public void insertIntoTableMap(String mapName, String nameFirstCharacter, double coordinateX, double coordinateY)
            throws MapCharacterNotExistException{
        PersonDBManager personDBManager;
        if(isForTest){
            personDBManager = new PersonDBManager(nameDatabases);
        }else{
            personDBManager = new PersonDBManager();
        }

        if(!personDBManager.isCharacterExist(nameFirstCharacter)){
            throw new MapCharacterNotExistException(mapName, nameFirstCharacter);
        }

        String reqValues = "INSERT INTO Map VALUES (" +
                "'"+SecureManage.getEncrypted(mapName)+"','" +
                SecureManage.getEncrypted(nameFirstCharacter)+"','" +
                SecureManage.getEncrypted(String.valueOf(coordinateX))+"','"+SecureManage.getEncrypted(String.valueOf(coordinateY))+"')";
        dbManager.createTableOrInsert(reqValues);
    }

    public String getFirstCharacter(String mapName) throws MapDataGetException {
        ResultSet resultSet = selectMapIntoTableMap(mapName);
        try {
            return SecureManage.getDecrypted(resultSet.getString("nameFirstCharacter"));
        }catch(SQLException sqlException){
            throw new MapDataGetException(mapName);
        }
    }

    public Coordinate getInitialCoordinate(String mapName) throws MapDataGetException{
        ResultSet resultSet = selectMapIntoTableMap(mapName);
        try {
            return new Coordinate(Double.parseDouble(SecureManage.getDecrypted(resultSet.getString("coordinateX"))),
                    Double.parseDouble(SecureManage.getDecrypted(resultSet.getString("coordinateY"))));
        }catch(SQLException sqlException){
            throw new MapDataGetException(mapName);
        }
    }

    private ResultSet selectMapIntoTableMap(String mapName){
        ResultSet resultSet;
        resultSet = dbManager.selectIntoTable("SELECT * FROM Map WHERE mapName = '" +
                SecureManage.getEncrypted(mapName) + "'");
        try{
            resultSet.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération des données.");
        }
        return resultSet;
    }

    public void removeTableMap(){
        try{
            dbManager.dropTable("Map");
        }catch(Exception ignored){

        }
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

}
