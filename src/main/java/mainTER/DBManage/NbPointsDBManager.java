package mainTER.DBManage;

import java.util.ArrayList;

public class NbPointsDBManager {
    private final DBManager dbManager;

    public NbPointsDBManager(){
        this.dbManager = new DBManager();
    }

    public NbPointsDBManager(String name){
        this.dbManager = new DBManager(name, "test");
    }
    public void removeTableNbPointsDBManager(){
        dbManager.dropTable("NbPoints");
    }

    /**
     * remove all table.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

    public void createTableNbPoints(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("points");              listSize.add(40);
        dbManager.createTable("NbPoints", listName, 0, listSize);
    }
    public void insertIntoTableNbPoints(int nbPoints){

        ArrayList<Object> listObject = new ArrayList<>();

        listObject.add(nbPoints);
        dbManager.insertIntoTable("NbPoints", listObject);

    }

    public int getNbPoints(){
        try {
            return Integer.parseInt(dbManager.getData("NbPoints", null, null,"points"));
        }catch(Exception ignored){
        }
        return -1;
    }

    public void setNbPoints(int nbPoints){

        dbManager.updateTable("NbPoints", null, null, "points", nbPoints);

    }
}
