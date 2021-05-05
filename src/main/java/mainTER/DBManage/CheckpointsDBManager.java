package mainTER.DBManage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckpointsDBManager {

    private final DBManager dbManager;



    public CheckpointsDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public CheckpointsDBManager(){
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


    public void insertIntoTableControls(double x, double y, String characterName, String mapName) {
        // TODO verify insert data
        // TODO verify data doesn't exist already
        // TODO verify if Character exist.
        // TODO verify if the map exist.




        String reqValues = "INSERT INTO Checkpoints VALUES (" +
                "'" + SecureManage.getEncrypted(String.valueOf(x)) + "'" + ",'" + SecureManage.getEncrypted(String.valueOf(y))
                + "','" + SecureManage.getEncrypted(characterName) + "','" + SecureManage.getEncrypted(mapName)
                + "');";


        dbManager.createTableOrInsert(reqValues);
    }
    public void setX(double x) {
        String request = "UPDATE Checkpoints " +
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
        String request = "UPDATE Checkpoints " +
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
    public void setCharacterName(String characterName) {
        String request = "UPDATE Checkpoints " +
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
        String request = "UPDATE Checkpoints " +
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
