package mainTER.DBManage;

import mainTER.CharacterGameplay.Character;
import mainTER.exception.PersonDataAlreadyExistException;
import mainTER.exception.PersonDataNotCorrectException;
import mainTER.exception.PersonDataGetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDBManager {
    private final DBManager dbManager;


    /**
     * Create or use (if already exist) file database for the application.
     */
    public PersonDBManager(){
        this.dbManager = new DBManager();
    }

    /**
     * Create or use (if already exist) file database for the test here.
     * @param nameFileDB name of the database.
     */
    public PersonDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB, "test");
    }

    /**
     * Create a table Person.
     */
     public void createTablePerson(){
         ArrayList<String> listName = new ArrayList<>();
         ArrayList<Integer> listSize = new ArrayList<>();
         listName.add("name");                  listSize.add(30);
         listName.add("speed");                 listSize.add(30);
         listName.add("weight");                listSize.add(30);
         listName.add("jumpStrength");          listSize.add(30);
         listName.add("fallingSpeed");          listSize.add(30);
         listName.add("canJump");               listSize.add(30);
         dbManager.createTable("Person", listName, 1, listSize);
         /*dbManager.createTableOrInsert("CREATE TABLE Person (" +
                 "name VARCHAR(30) PRIMARY KEY," +
                 "speed VARCHAR(30)," +
                 "weight VARCHAR(30)," +
                 "jumpStrength VARCHAR(30)," +
                 "fallingSpeed VARCHAR(30)," +
                 "canJump VARCHAR(30)" +
                 ");");*/
     }


    /**
     * Remove the table Person.
     */
    public void removeTablePerson(){
        dbManager.dropTable("Person");
    }

    /**
     * remove all table.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

    /**
     * Insert values into table Person.
     * @param name name of the character.
     * @param speed speed of the character.
     * @param weight weight of the character.
     * @param jumpStrength jumpStrength of the character.
     * @param fallingSpeed the fallingSpeed of the character.
     * @param canJump if the character can jump or not.
     * @throws PersonDataAlreadyExistException if the character is already in the databases.
     * @throws PersonDataNotCorrectException if the data inserted isn't correct.
     */
    public void insertIntoTablePerson(String name, double speed, double weight, double jumpStrength, double fallingSpeed, boolean canJump)
            throws PersonDataAlreadyExistException, PersonDataNotCorrectException {
        ResultSet resultSet = selectIntoTablePerson(name);
        try {
            resultSet.getObject("weight");
            throw new PersonDataAlreadyExistException(name);
        } catch (SQLException ignored) {

        }
        if(name.compareTo("")!=0 && speed>0 && weight>0 && jumpStrength>=0 &&fallingSpeed>=0) {
            ArrayList<Object> listObject = new ArrayList<>();
            listObject.add(name);
            listObject.add(speed);
            listObject.add(weight);
            listObject.add(jumpStrength);
            listObject.add(fallingSpeed);
            listObject.add(canJump?"true":"false");
            dbManager.insertIntoTable("Person", listObject);
        }else{
            throw new PersonDataNotCorrectException(name);
        }
    }

    /**
     * Get if a character exist in the databases.
     * @param nameCharacter the name of the character where we want to know it exists or not.
     * @return true if the character exist, false otherwise.
     */
    public boolean isCharacterExist(String nameCharacter){
        List<String> listCharacter = getListNameFromDatabase();
        return listCharacter.contains(nameCharacter);
    }

    public Character getCharacter(String nameCharacter) throws PersonDataGetException{
        if(isCharacterExist(nameCharacter))
            return new Character(nameCharacter);
        else
            throw new PersonDataGetException(nameCharacter);
    }

    /**
     *
     * @return list of the name present in the table person.
     */
    public List<String> getListNameFromDatabase(){
        try{
            return dbManager.getList("Person", null, null, "name");
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return null;
    }

    /**
     *
     * @param nameCharacter name of the character we want to get.
     * @return the data of the character with nameCharacter as name.
     */
    private ResultSet selectIntoTablePerson(String nameCharacter){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT *" +
                    " FROM Person WHERE name = '" + dbManager.getEncryptedFromObject(nameCharacter) + "'");
            rs.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données du personnages " + nameCharacter);
        }
        return rs;
    }

    /**
     *
     * @param nameCharacter the name of the Character
     * @return the speed of the character
     * @throws PersonDataGetException if the data speed of the character doesn't exist or if the character doesn't exist.
     */
    public double getSpeed(String nameCharacter) throws PersonDataGetException{
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Object> listRequest = new ArrayList<>();
        listName.add("name");      listRequest.add(nameCharacter);
        try {
            return Double.parseDouble(dbManager.getData("Person", listName, listRequest, "speed"));
        }catch(Exception exception){
            throw new PersonDataGetException(nameCharacter);
        }
    }

    /**
     *
     * @param nameCharacter the name of the Character.
     * @return if the character can jump or not
     * @throws PersonDataGetException if the data canJump of the character doesn't exist or if the character doesn't exist.
     */
    public boolean getCanJump(String nameCharacter) throws PersonDataGetException{
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Object> listRealValue = new ArrayList<>();
        listName.add("name");           listRealValue.add(nameCharacter);
        try{
            return dbManager.getData("Person", listName, listRealValue, "canJump").compareTo("true") == 0;
        }catch(Exception exception){
            throw new PersonDataGetException(nameCharacter);
        }
    }

    /**
     *
     * @param nameCharacter the name of the character.
     * @return the JumpStrength of the character.
     * @throws PersonDataGetException if the data jumpStrength of the character doesn't exist or if the character doesn't exist.
     */
    public double getJumpStrength(String nameCharacter) throws PersonDataGetException{
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Object> listRealValue = new ArrayList<>();
        listName.add("name");               listRealValue.add(nameCharacter);
        try{
            return Double.parseDouble(dbManager.getData("Person", listName, listRealValue, "jumpStrength"));
        }catch(SQLException sqlException){
            throw new PersonDataGetException(nameCharacter);
        }
    }

    /**
     *
     * @param nameCharacter the name of the character.
     * @return the weight of the character.
     * @throws PersonDataGetException if the data weight of the character doesn't exist or if the character doesn't exist.
     */
    public double getWeight(String nameCharacter) throws PersonDataGetException{
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Object> listRealValue = new ArrayList<>();
        listName.add("name");           listRealValue.add(nameCharacter);
        try{
            return Double.parseDouble(dbManager.getData("Person", listName, listRealValue, "weight"));
        }catch(SQLException sqlException)
        {
            throw new PersonDataGetException(nameCharacter);
        }
    }

    /**
     *
     * @param nameCharacter the name of the character.
     * @return the fallingSpeed of the character.
     * @throws PersonDataGetException if the data fallingSpeed of the character doesn't exist or if the character doesn't exist.
     */
    public double getFallingSpeed(String nameCharacter) throws PersonDataGetException{
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Object> listRealValue = new ArrayList<>();
        listName.add("name");           listRealValue.add(nameCharacter);
        try{
            return Double.parseDouble(dbManager.getData("Person", listName, listRealValue, "fallingSpeed"));
        }catch(SQLException sqlException){
            throw new PersonDataGetException(nameCharacter);
        }
    }

    public List<String> toArray(String nameCharacter) throws  SQLException {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = selectIntoTablePerson(nameCharacter);

        int columnCount = rs.getMetaData().getColumnCount();

        for (int i = 0; i <columnCount ; i++)
        {
            result.add(dbManager.getDecryptedFromString(rs.getString(i + 1)) );
        }
        return result;
    }

}
