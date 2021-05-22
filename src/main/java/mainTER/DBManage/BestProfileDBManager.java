package mainTER.DBManage;

import mainTER.exception.BestProfileDataExistAlreadyException;
import mainTER.exception.BestProfileDataGetException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BestProfileDBManager {

    private final DBManager dbManager;

    private static final String NAME_TABLE = "BestProfile";

    private static final String NAME_MAP_NAME = "mapName";

    /**
     * Constructor for the test purpose.
     * @param nameFileDB the name of the databases for the test.
     */
    public BestProfileDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    /**
     * Constructor for the application.
     */
    public BestProfileDBManager(){this.dbManager = new DBManager(); }

    /**
     * Create table BestProfile who save the best profile for each map.
     */
    public void createTableBestProfile(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("name");               listSize.add(30);
        listName.add("time");               listSize.add(30);
        listName.add(NAME_MAP_NAME);            listSize.add(30);
        dbManager.createTable(NAME_TABLE, listName, 0, listSize);
    }

    /**
     * verify if a best profile exist for a map.
     * @param mapName the name of the map.
     * @return true if a best profile exist for the map, false otherwise.
     * @throws BestProfileDataGetException when there's problem with the table BestProfile.
     */
    private boolean isBestProfileExist(String mapName) throws BestProfileDataGetException{
        List<String> listCharacter = getListNameFromDatabase();
        return listCharacter.contains(mapName);
    }

    /**
     * get a list containing all map from the table BestProfile.
     * @return a list containing all map from the table BestProfile.
     * @throws BestProfileDataGetException when there's a problem with the table BestProfile.
     */
    public List<String> getListNameFromDatabase() throws BestProfileDataGetException{
        try{
            return dbManager.getList(NAME_TABLE, null, null, "mapName");
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        throw new BestProfileDataGetException();
    }

    /**
     * insert value into table BestProfile.
     * @param name the name of the player.
     * @param time the time he use to finish a map.
     * @param mapName the name of the map he finished.
     * @throws BestProfileDataExistAlreadyException when there's a problem with the table BestProfile.
     */
    public void insertIntoTableBestProfile(String name, int time,String mapName) throws BestProfileDataExistAlreadyException {
        // TODO verify insert data

        try {
            if (isBestProfileExist(mapName)) {
                throw new BestProfileDataExistAlreadyException(mapName);
            }
        }catch(BestProfileDataGetException ignored){

        }

        ArrayList<Object> listInsert = new ArrayList<>();
        listInsert.add(name);
        listInsert.add(time);
        listInsert.add(mapName);
        dbManager.insertIntoTable(NAME_TABLE, listInsert);
    }

    /**
     * get best time for a map from the databases.
     * @param mapName the map we want to get the best time.
     * @return the best time for a map from the databases.
     */
    public int getTime(String mapName){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_MAP_NAME);                listRealValueOfLine.add(mapName);
        try {
            return Integer.parseInt(dbManager.getData(NAME_TABLE, listNameLine, listRealValueOfLine, "time"));
        } catch (SQLException throwables) {
            return -1;
        }

    }

    /**
     * get the name of the best player for a map from the databases.
     * @param mapName the map we want to get the name of the player who had the best time.
     * @return the name of the best player for a map from the databases.
     */
    public String getName(String mapName){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_MAP_NAME);                listRealValueOfLine.add(mapName);
        try {

            return dbManager.getData(NAME_TABLE, listNameLine, listRealValueOfLine, "name");
        }catch(SQLException sqlException){
                sqlException.printStackTrace();
        }
        return "";
    }

    /**
     * Change the best player for a map.
     * @param name the new best player.
     * @param mapName the map we want to change the best player.
     */
    public void setName(String name,String mapName){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_MAP_NAME);                listRealValueOfLine.add(mapName);
        dbManager.updateTable(NAME_TABLE, listNameLine, listRealValueOfLine, "name", name);
    }

    /**
     * Change the best time for a map.
     * @param time the new best time.
     * @param mapName the map we want to change the best time.
     */
    public void setTime(int time,String mapName) {
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_MAP_NAME);                listRealValueOfLine.add(mapName);
        dbManager.updateTable(NAME_TABLE, listNameLine, listRealValueOfLine, "time", time);
    }

    /**
     * Delete the table BestProfile.
     */
    public void removeTableProfile(){
        dbManager.dropTable(NAME_TABLE);
    }

    /**
     * Drop cascade.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }
}
