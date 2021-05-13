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

    public BestProfileDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB,"test");
    }

    public BestProfileDBManager(){this.dbManager = new DBManager(); }

    public void createTableBestProfile(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("name");               listSize.add(30);
        listName.add("time");               listSize.add(30);
        listName.add(NAME_MAP_NAME);            listSize.add(30);
        dbManager.createTable(NAME_TABLE, listName, 0, listSize);
    }

    private boolean isBestProfileExist(String mapName) throws BestProfileDataGetException{
        List<String> listCharacter = getListNameFromDatabase();
        return listCharacter.contains(mapName);
    }

    public List<String> getListNameFromDatabase() throws BestProfileDataGetException{
        try{
            return dbManager.getList(NAME_TABLE, null, null, "mapName");
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        throw new BestProfileDataGetException();
    }

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

    public int getTime(String mapName){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_MAP_NAME);                listRealValueOfLine.add(mapName);
        try {
            return Integer.parseInt(dbManager.getData(NAME_TABLE, listNameLine, listRealValueOfLine, "time"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }

    }
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

    public void setName(String name,String mapName){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_MAP_NAME);                listRealValueOfLine.add(mapName);
        dbManager.updateTable(NAME_TABLE, listNameLine, listRealValueOfLine, "name", name);
    }

    public void setTime(int time,String mapName) {
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_MAP_NAME);                listRealValueOfLine.add(mapName);
        dbManager.updateTable(NAME_TABLE, listNameLine, listRealValueOfLine, "time", time);
    }

    public void removeTableProfile(){
        dbManager.dropTable(NAME_TABLE);
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }
}
