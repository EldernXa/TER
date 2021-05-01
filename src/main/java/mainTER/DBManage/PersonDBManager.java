package mainTER.DBManage;

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
        dbManager.createTableOrInsert("CREATE TABLE Person (" +
                "name VARCHAR(30) PRIMARY KEY," +
                "speed FLOAT(3)," +
                "weight FLOAT(3)," +
                "jumpStrength FLOAT(3)," +
                "fallingSpeed FLOAT(3)," +
                "canJump BOOLEAN" +
                ");");
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
            String reqValues = "INSERT INTO Person VALUES (" +
                    "'" + name + "'" + "," + speed + "," + weight + "," + jumpStrength + "," + fallingSpeed + "," + "'" + (canJump ? "true" : "false") + "'" +
                    ")";
            dbManager.createTableOrInsert(reqValues);
        }else{
            throw new PersonDataNotCorrectException(name);
        }
    }

    /**
     *
     * @return list of the name present in the table person.
     */
    public List<String> getListNameFromDatabase(){
        ArrayList<String> listName = new ArrayList<>();
        ResultSet rs;
        try{
            rs = dbManager.selectIntoTable("SELECT * FROM Person;");
            while(rs.next()){
                listName.add((String)rs.getObject("name"));
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listName;
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
                    " FROM Person WHERE name = '" + nameCharacter + "'");
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
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try {
            return (double)rs.getObject("speed");
        }catch(SQLException sqlException){
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
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try{
            return (boolean) rs.getObject("canJump");
        }catch(SQLException sqlException){
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
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try{
            return (double) rs.getObject("jumpStrength");
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
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try{
            return (double)rs.getObject("weight");
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
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try{
            return (double)rs.getObject("fallingSpeed");
        }catch(SQLException sqlException){
            throw new PersonDataGetException(nameCharacter);
        }
    }

}
