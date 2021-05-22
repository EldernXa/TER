package mainTER.DBManage;

import java.util.ArrayList;

public class NbPointsDBManager {
    private final DBManager dbManager;

    /**
     * Constructor for the application.
     */
    public NbPointsDBManager(){
        this.dbManager = new DBManager();
    }

    /**
     * Constructor for the test purpose.
     * @param name name of the databases.
     */
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

    /**
     * create the table to save the number of points.
     */
    public void createTableNbPoints(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add("points");              listSize.add(40);
        dbManager.createTable("NbPoints", listName, 0, listSize);
    }

    /**
     * insert values in nbPoints table.
     * @param nbPoints the number of points of the player.
     */
    public void insertIntoTableNbPoints(int nbPoints){

        ArrayList<Object> listObject = new ArrayList<>();

        listObject.add(nbPoints);
        dbManager.insertIntoTable("NbPoints", listObject);

    }

    /**
     * get the number of points the player have.
     * @return the number of points the player have.
     */
    public int getNbPoints(){
        try {
            return Integer.parseInt(dbManager.getData("NbPoints", null, null,"points"));
        }catch(Exception ignored){
        }
        return -1;
    }

    /**
     * seet the number of points the player have.
     * @param nbPoints the new number of points the player must have.
     */
    public void setNbPoints(int nbPoints){

        dbManager.updateTable("NbPoints", null, null, "points", nbPoints);

    }
}
