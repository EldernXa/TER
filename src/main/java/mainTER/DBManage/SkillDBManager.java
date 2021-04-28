package mainTER.DBManage;

import mainTER.exception.SkillAlreadyExistException;
import mainTER.exception.SkillDataGetException;
import mainTER.exception.SkillCtrlAlreadyUsedException;
import mainTER.exception.SkillDataDoesntCorrectException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDBManager {

    /**
     * connection with the databases.
     */
    private final DBManager dbManager;
    /**
     * Static variable uses to get the numSkill of a skill.
     */
    private static final String NAME_ATTRIBUTE_FOR_NUM_SKILL = "numSkill";

    /**
     * Constructor to uses skill databases for the application.
     */
    public SkillDBManager(){
        this.dbManager = new DBManager();
    }

    /**
     * Constructor to uses skill databases for the tests.
     * @param name name of the databases we want for the tests.
     */
    public SkillDBManager(String name){
        this.dbManager = new DBManager(name, "test");
    }

    /**
     * Create the databases for the skills of a character.
     */
    public void createTableSkill(){
        dbManager.createTableOrInsert("CREATE TABLE Skill (" +
                "nameSkill VARCHAR(50),"+
                "numSkill INTEGER," +"ctrlKey VARCHAR(30),"+
                "nameCharacter VARCHAR(30)," +
                "animateMvt BOOLEAN," +
                "animateAction BOOLEAN," +
                "isMode BOOLEAN," +
                "CONSTRAINT PK_Person PRIMARY KEY (nameCharacter,numSkill)" +
                ");");
    }

    /**
     * Get the name of a skill with the name of the character and the skill number.
     * @param nameCharacter the name of the character
     * @param numSkill the skill number we want to get the name.
     * @return the name of a skill thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public String getNameSkill(String nameCharacter, int numSkill) throws SkillDataGetException {
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try {
            while (resultSet.next()) {
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getString("nameSkill");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    /**
     * Get the key of a skill with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get the key.
     * @return the key of a skill thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public String getCtrlKey(String nameCharacter, int numSkill) throws SkillDataGetException{
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getString("ctrlKey");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    /**
     * Get if the skill needs a movement animation or not with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get if it need a movement animation or not.
     * @return if the skill needs a movement animation or not thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public boolean getAnimateMvt(String nameCharacter, int numSkill) throws SkillDataGetException{
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getBoolean("animateMvt");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    /**
     * Get if the skill needs a movement action or not with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get if it need a movement action or not.
     * @return if the skill needs a movement action or not thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public boolean getAnimateAction(String nameCharacter, int numSkill) throws SkillDataGetException{
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getBoolean("animateAction");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    /**
     * Get if the skill can be turn on and turn off with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get if the skill can be turn on and turn off.
     * @return if the skill can be turn on and turn off thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public boolean getIsMode(String nameCharacter, int numSkill) throws SkillDataGetException{
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getBoolean("isMode");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    /**
     * Delete the table Skill from the database.
     */
    public void removeTableSkill(){
        try {
            dbManager.dropTable("Skill");
        }catch(Exception ignored){

        }
    }

    /**
     * Insert a Skill in the database.
     * @param nameSkill the name of the skill.
     * @param ctrlKey the key we need to press in order to use the skill.
     * @param nameCharacter the name of the character.
     * @param animateMvt if the skill change the animation when we move.
     * @param animateAction if the skill use a unique animation.
     * @param isMode if the skill can be turn on and turn off.
     * @throws SkillAlreadyExistException if a skill with the same name exist already for the same character.
     * @throws SkillCtrlAlreadyUsedException if the key is already used for an another skill of the same character.
     * @throws SkillDataDoesntCorrectException if we try to insert data that doesn't correct.
     */
    public void insertIntoTableSkill(String nameSkill, String ctrlKey, String nameCharacter, boolean animateMvt, boolean animateAction, boolean isMode) throws SkillAlreadyExistException, SkillCtrlAlreadyUsedException, SkillDataDoesntCorrectException {

        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try {
            while (resultSet.next()) {
                if(resultSet.getString("nameSkill").compareTo(nameSkill)==0){
                    throw new SkillAlreadyExistException(nameCharacter, nameSkill);
                }
                if(resultSet.getString("ctrlKey").compareTo(ctrlKey)==0){
                    throw new SkillCtrlAlreadyUsedException(nameCharacter, ctrlKey);
                }
            }
        }catch(SQLException ignored){

        }

        // TODO count only for the same skill (passive or active).
        // TODO add exception for if character exist or not.
        int numSkill = getNumberSkillOfACharacter(nameCharacter)+1;

        if(nameSkill.compareTo("") == 0 || nameCharacter.compareTo("")==0){
            throw new SkillDataDoesntCorrectException();
        }
        String reqValues = "INSERT INTO Skill VALUES (" +
                "'"+ nameSkill + "'," + numSkill + ",'" + ctrlKey + "','" +nameCharacter +"',"
                 + convertBoolToString(animateMvt) +"," + convertBoolToString(animateAction)
                + "," + convertBoolToString(isMode) +
                ")";
        dbManager.createTableOrInsert(reqValues);
    }

    /**
     *
     * @return list of all the name of skill in the database.
     */
    public List<String> getListSkillName(){
        ArrayList<String> listSkillName = new ArrayList<>();
        ResultSet resultSet;
        try{
            resultSet = dbManager.selectIntoTable("SELECT * FROM Skill;");
            while(resultSet.next()){
                listSkillName.add((String)resultSet.getObject("nameSkill"));
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listSkillName;
    }

    /**
     *
     * @return list of all the name character who have skill (there are inside the database).
     */
    public List<String> getListNameCharacterWithSkill(){
        ArrayList<String> listName = new ArrayList<>();
        ResultSet resultSet;
        try{
            resultSet = dbManager.selectIntoTable("SELECT * FROM Skill;");
            while(resultSet.next()){
                if(!listName.contains(resultSet.getString("nameCharacter"))){
                    listName.add((String)resultSet.getObject("nameCharacter"));
                }
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listName;
    }

    /**
     *
     * @param nameCharacter the name of a character.
     * @return the number of skill a character does have.
     */
    public int getNumberSkillOfACharacter(String nameCharacter){
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);

        int nb = 0;
        try {
            while(resultSet.next()){
                nb++;
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données");
        }
        return nb;
    }

    /**
     *
     * @param nameCharacter the name of a character.
     * @return all data that have the character asked.
     */
    private ResultSet selectCharacterIntoTableSkill(String nameCharacter){
        ResultSet resultSet;
        resultSet = dbManager.selectIntoTable("SELECT * FROM Skill WHERE nameCharacter = '" + nameCharacter + "'");
        return resultSet;
    }

    /**
     *
     * @param valToConvert a boolean we want to convert into String.
     * @return a String that is the conversion of the boolean we ask.
     */
    private String convertBoolToString(boolean valToConvert){
        return valToConvert?"true":"false";
    }

    /**
     * Drop cascade of the database.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }
}
