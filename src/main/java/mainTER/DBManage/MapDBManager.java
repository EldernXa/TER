package mainTER.DBManage;

import mainTER.Tools.Coordinate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapDBManager {

    private final DBManager dbManager;

    public MapDBManager(){
        this.dbManager = new DBManager();
    }

    public MapDBManager(String name){
        this.dbManager = new DBManager(name, "test");
    }

    public void createTableMap(){
        dbManager.createTableOrInsert("CREATE TABLE Map (" +
                "mapName VARCHAR(50) PRIMARY KEY," +
                "nameFirstCharacter VARCHAR(50)," +
                "coordinateX VARCHAR(50)," +
                "coordinateY VARCHAR(50)" +
                ");");
    }

    public void insertIntoTableMap(String mapName, String nameFirstCharacter, double coordinateX, double coordinateY){
        String reqValues = "INSERT INTO Map VALUES (" +
                "'"+SecureManage.getEncrypted(mapName)+"','" +
                SecureManage.getEncrypted(nameFirstCharacter)+"','" +
                SecureManage.getEncrypted(String.valueOf(coordinateX))+"','"+SecureManage.getEncrypted(String.valueOf(coordinateY))+"')";
        dbManager.createTableOrInsert(reqValues);
    }

    public String getFirstCharacter(String mapName) throws SQLException {
        ResultSet resultSet = selectMapIntoTableMap(mapName);
        return SecureManage.getDecrypted(resultSet.getString("nameFirstCharacter"));
    }

    public Coordinate getInitialCoordinate(String mapName) throws SQLException{
        ResultSet resultSet = selectMapIntoTableMap(mapName);
        return new Coordinate(Double.parseDouble(SecureManage.getDecrypted(resultSet.getString("coordinateX"))),
                Double.parseDouble(SecureManage.getDecrypted(resultSet.getString("coordinateY"))));
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
