package mainTER.DBManage;

import mainTER.Tools.Coordinate;
import mainTER.exception.MapAlreadyExistException;
import mainTER.exception.MapDataGetException;
import mainTER.exception.MapCharacterNotExistException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapDBManager {

    /**
     * variable to connect to the databases.
     */
    private final DBManager dbManager;

    /**
     * Variable who says if the instance is used for test or for the app.
     */
    private final boolean isForTest;

    /**
     * The name of the databases.
     */
    private final String nameDatabases;

    /**
     * Constructor for the App.
     */
    public MapDBManager(){
        this.dbManager = new DBManager();
        isForTest = false;
        nameDatabases = "";
    }

    /**
     * Constructor for the test.
     * @param name name of the databases for the test.
     */
    public MapDBManager(String name){
        this.dbManager = new DBManager(name, "test");
        isForTest = true;
        nameDatabases = name;
    }

    /**
     * Create the table Map in the databases.
     */
    public void createTableMap(){
        dbManager.createTableOrInsert("CREATE TABLE Map (" +
                "mapName VARCHAR(50) PRIMARY KEY," +
                "nameFirstCharacter VARCHAR(50)," +
                "coordinateX VARCHAR(50)," +
                "coordinateY VARCHAR(50)" +
                ");");
    }

    /**
     * Insert values into the table Map in the databases.
     * @param mapName the name of the map we want to insert.
     * @param nameFirstCharacter the name of the first character we want to insert.
     * @param coordinateX the x coordinate of the initial position in the map.
     * @param coordinateY the y coordinate of the initial position in the map.
     * @throws MapCharacterNotExistException if the first character doesn't exist in the databases.
     */
    public void insertIntoTableMap(String mapName, String nameFirstCharacter, double coordinateX, double coordinateY)
            throws MapCharacterNotExistException, MapAlreadyExistException {

        ResultSet resultSet = selectMapIntoTableMap(mapName);
        try{
            resultSet.getString("mapName");
            throw new MapAlreadyExistException(mapName);
        }catch(SQLException ignored){

        }

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

    /**
     * Getting the name of the first character thanks to the name of the map.
     * @param mapName the name of the map.
     * @return the name of the first character thanks to the name of the map.
     * @throws MapDataGetException if the name given doesn't exist in the databases.
     */
    public String getFirstCharacter(String mapName) throws MapDataGetException {
        ResultSet resultSet = selectMapIntoTableMap(mapName);
        try {
            return SecureManage.getDecrypted(resultSet.getString("nameFirstCharacter"));
        }catch(SQLException sqlException){
            throw new MapDataGetException(mapName);
        }
    }

    /**
     * Getting the initial coordinate for the map we ask.
     * @param mapName the name of the map.
     * @return the initial coordinate for the map we ask.
     * @throws MapDataGetException if the name given doesn't exist in the databases.
     */
    public Coordinate getInitialCoordinate(String mapName) throws MapDataGetException{
        ResultSet resultSet = selectMapIntoTableMap(mapName);
        try {
            return new Coordinate(Double.parseDouble(SecureManage.getDecrypted(resultSet.getString("coordinateX"))),
                    Double.parseDouble(SecureManage.getDecrypted(resultSet.getString("coordinateY"))));
        }catch(SQLException sqlException){
            throw new MapDataGetException(mapName);
        }
    }

    /**
     * Getting all data for a map.
     * @param mapName the name of a map.
     * @return all data for the map we ask.
     */
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

    /**
     * Remove the table Map.
     */
    public void removeTableMap(){
        try{
            dbManager.dropTable("Map");
        }catch(Exception ignored){

        }
    }

    /**
     * Drop cascade.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

}
