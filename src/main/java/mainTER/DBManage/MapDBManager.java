package mainTER.DBManage;

import mainTER.Tools.Coordinate;
import mainTER.exception.MapAlreadyExistException;
import mainTER.exception.MapDataGetException;
import mainTER.exception.MapCharacterNotExistException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("mapName");                listSize.add(50);
        listName.add("nameFirstCharacter");     listSize.add(50);
        listName.add("coordinateX");            listSize.add(50);
        listName.add("coordinateY");            listSize.add(50);
        dbManager.createTable("Map", listName, 1, listSize);
    }

    public boolean verifyTableMapExist(){
        ResultSet resultSet = dbManager.selectIntoTable("SELECT * FROM Map");
        try{
            if(resultSet.next()){
                return true;
            }
        }catch(Exception ignored){

        }
        return false;
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

        try{
            dbManager.getData("Map", "mapName", mapName, "mapName", true);
            throw new MapAlreadyExistException(mapName);
        }catch(SQLException ignored){

        }

        PersonDBManager personDBManager;
        if(isForTest){
            personDBManager = new PersonDBManager(nameDatabases);
        }else{
            personDBManager = new PersonDBManager();
        }

        try {
            if (!personDBManager.isCharacterExist(nameFirstCharacter)) {
                throw new MapCharacterNotExistException(mapName, nameFirstCharacter);
            }
        }catch(Exception exception){
            throw new MapCharacterNotExistException(mapName, nameFirstCharacter);
        }

        ArrayList<Object> listInsert = new ArrayList<>();
        listInsert.add(mapName);
        listInsert.add(nameFirstCharacter);
        listInsert.add(coordinateX);
        listInsert.add(coordinateY);
        dbManager.insertIntoTable("Map", listInsert);
    }

    public boolean verifyMapExist(String mapName){
        try{
            getFirstCharacter(mapName);
            return true;
        }catch(Exception exception){
            return false;
        }
    }

    /**
     * Getting the name of the first character thanks to the name of the map.
     * @param mapName the name of the map.
     * @return the name of the first character thanks to the name of the map.
     * @throws MapDataGetException if the name given doesn't exist in the databases.
     */
    public String getFirstCharacter(String mapName) throws MapDataGetException {
        try {
            return dbManager.getData("Map", "mapName", mapName, "nameFirstCharacter", true);
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
        try {
            double x = Double.parseDouble(dbManager.getData("Map", "mapName", mapName, "coordinateX", true));
            double y = Double.parseDouble(dbManager.getData("Map", "mapName", mapName, "coordinateY", true));
            return new Coordinate(x, y);
        }catch(SQLException sqlException){
            throw new MapDataGetException(mapName);
        }
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
