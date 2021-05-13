package mainTER.DBManage;

import mainTER.exception.*;

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
     * static variable uses to get the nameSkill of a skill.
     */
    private static final String NAME_ATTRIBUTE_FOR_NAME_SKILL = "nameSkill";

    /**
     * static variable uses to get the control key of a skill.
     */
    private static final String NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL = "ctrlKey";

    /**
     * static variable uses to get the name character of a skill.
     */
    private static final String NAME_ATTRIBUTE_FOR_NAME_CHARACTER_OF_A_SKILL = "nameCharacter";

    private static final String NAME_ATTRIBUTE_FOR_DESCRIPTION_OF_SKILL = "Description";

    private static final String NAME_ATTRIBUTE_FOR_TIME_COOLDOWN = "timeCooldown";

    private static final String NAME_ATTRIBUTE_FOR_TIME_SKILL = "timeSkill";

    private static final String NAME_TABLE = "Skill";

    /**
     * static variable uses to display an error when trying to get data who doesn't exist.
     */
    private static final String NAME_ERROR_GETTING_DATA = "Problème dans la récupération de données.";

    /**
     * true if we use the database for the test, else otherwise.
     */
    private final boolean isForTest;

    /**
     * the name of the database.
     */
    private final String nameDatabase;

    /**
     * Constructor to uses skill databases for the application.
     */
    public SkillDBManager(){
        this.dbManager = new DBManager();
        isForTest = false;
        nameDatabase = "";
    }

    /**
     * Constructor to uses skill databases for the tests.
     * @param name name of the databases we want for the tests.
     */
    public SkillDBManager(String name){
        this.dbManager = new DBManager(name, "test");
        isForTest = true;
        nameDatabase = name;
    }

    /**
     * Create the databases for the skills of a character.
     */
    public void createTableSkill(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add(NAME_ATTRIBUTE_FOR_NAME_CHARACTER_OF_A_SKILL);                  listSize.add(30);
        listName.add(NAME_ATTRIBUTE_FOR_NUM_SKILL);                                  listSize.add(30);
        listName.add(NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL);                          listSize.add(30);
        listName.add(NAME_ATTRIBUTE_FOR_NAME_SKILL);                                 listSize.add(50);
        listName.add("animateMvt");                                                  listSize.add(30);
        listName.add("animateAction");                                               listSize.add(30);
        listName.add("isMode");                                                      listSize.add(30);
        listName.add(NAME_ATTRIBUTE_FOR_TIME_COOLDOWN);                              listSize.add(30);
        listName.add(NAME_ATTRIBUTE_FOR_TIME_SKILL);                                 listSize.add(30);
        listName.add(NAME_ATTRIBUTE_FOR_DESCRIPTION_OF_SKILL);                       listSize.add(500);
        dbManager.createTable(NAME_TABLE, listName, 3, listSize);
    }

    /**
     * Get the name of a skill with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get the name.
     * @return the name of a skill thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public String getNameSkill(String nameCharacter, int numSkill) throws SkillDataGetException {
        return gettingData(nameCharacter, numSkill, NAME_ATTRIBUTE_FOR_NAME_SKILL);
    }
    public String getDescription(String nameCharacter,int numSkill) throws SkillDataGetException {
        return gettingData(nameCharacter,numSkill,NAME_ATTRIBUTE_FOR_DESCRIPTION_OF_SKILL);
    }

    private String returnFromDatabases(String nameCharacter, int numSkill, String valueToGet) throws SkillDataGetException{
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_ATTRIBUTE_FOR_NAME_CHARACTER_OF_A_SKILL);             listRealValueOfLine.add(nameCharacter);
        listNameLine.add(NAME_ATTRIBUTE_FOR_NUM_SKILL);                             listRealValueOfLine.add(numSkill);
        try{
            return dbManager.getData(NAME_TABLE, listNameLine, listRealValueOfLine, valueToGet);
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
    }

    /**
     * Private function who permit to getting a data thanks to the name of a character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get the name.
     * @param valueToGet the data we want to get.
     * @return the data we want thanks to the name of the character, the skill number and the name of the data we want.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    private String gettingData(String nameCharacter, int numSkill, String valueToGet) throws SkillDataGetException{
        return returnFromDatabases(nameCharacter, numSkill, valueToGet);
    }

    public float getTimeCooldown(String nameCharacter, int numSkill) throws SkillDataGetException{
        return Float.parseFloat(returnFromDatabases(nameCharacter, numSkill, NAME_ATTRIBUTE_FOR_TIME_COOLDOWN));

    }

    public float getTimeSkill(String nameCharacter, int numSkill) throws SkillDataGetException{
        return Float.parseFloat(returnFromDatabases(nameCharacter, numSkill, NAME_ATTRIBUTE_FOR_TIME_SKILL));
    }

    /**
     * Get the key of a skill with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get the key.
     * @return the key of a skill thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public String getCtrlKey(String nameCharacter, int numSkill) throws SkillDataGetException{
        return gettingData(nameCharacter, numSkill, NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL);
    }

    /**
     * Get if the skill needs a movement animation or not with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get if it need a movement animation or not.
     * @return if the skill needs a movement animation or not thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public boolean getAnimateMvt(String nameCharacter, int numSkill) throws SkillDataGetException{
        return returnFromDatabases(nameCharacter, numSkill, "animateMvt").compareTo("true")==0;
    }

    /**
     * Get if the skill needs a movement action or not with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get if it need a movement action or not.
     * @return if the skill needs a movement action or not thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public boolean getAnimateAction(String nameCharacter, int numSkill) throws SkillDataGetException{
        return returnFromDatabases(nameCharacter, numSkill, "animateAction").compareTo("true")==0;
    }

    /**
     * Get if the skill can be turn on and turn off with the name of the character and the skill number.
     * @param nameCharacter the name of the character.
     * @param numSkill the skill number we want to get if the skill can be turn on and turn off.
     * @return if the skill can be turn on and turn off thanks to the name of the character and the skill number.
     * @throws SkillDataGetException when the skill asked doesn't exist.
     */
    public boolean getIsMode(String nameCharacter, int numSkill) throws SkillDataGetException{
        return returnFromDatabases(nameCharacter, numSkill, "isMode").compareTo("true")==0;
    }

    /**
     * Delete the table Skill from the database.
     */
    public void removeTableSkill(){
        try {
            dbManager.dropTable(NAME_TABLE);
        }catch(Exception ignored){

        }
    }

    private void verifyCtrlNotUsedByControlMovement(String ctrlKey) throws SkillCtrlAlreadyUsedByMovementControlException{
        ControlsDBManager controlsDBManager;
        if(isForTest){
            controlsDBManager = new ControlsDBManager(nameDatabase);
        }else{
            controlsDBManager = new ControlsDBManager();
        }

        try{
            if(controlsDBManager.getIfCtrlAlreadyUsed(ctrlKey)){
                throw new SkillCtrlAlreadyUsedByMovementControlException(ctrlKey);
            }
        }catch(ControlsDataGetException ignored){

        }
    }

    private void verifyCharacterExist(String nameCharacter) throws SkillCharacterNotExistException {
        PersonDBManager personDBManager;
        if(isForTest){
            personDBManager = new PersonDBManager(nameDatabase);
        }else{
            personDBManager = new PersonDBManager();
        }
        try {
            if (!personDBManager.isCharacterExist(nameCharacter)) {
                throw new SkillCharacterNotExistException(nameCharacter);
            }
        }catch(Exception exception){
            throw new SkillCharacterNotExistException(nameCharacter);
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
     * @throws SkillDataNotCorrectException if we try to insert data that doesn't correct.
     */
    public void insertIntoTableSkill(String nameSkill, String ctrlKey, String nameCharacter, boolean animateMvt, boolean animateAction,
                                     boolean isMode, float timeCooldown, float timeSkill, String description)
            throws SkillAlreadyExistException, SkillCtrlAlreadyUsedException, SkillDataNotCorrectException,
            SkillCtrlAlreadyUsedByMovementControlException, SkillCharacterNotExistException {

        verifyCtrlNotUsedByControlMovement(ctrlKey);
        verifyCharacterExist(nameCharacter);
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try {
            while (resultSet.next()) {
                if(dbManager.getFromResultSet(resultSet, NAME_ATTRIBUTE_FOR_NAME_SKILL, nameSkill)){
                    throw new SkillAlreadyExistException(nameCharacter, nameSkill);
                }
                if(dbManager.getFromResultSet(resultSet, NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL, ctrlKey)){
                    throw new SkillCtrlAlreadyUsedException(nameCharacter, ctrlKey);
                }
            }
        }catch(SQLException ignored){

        }
        int numSkill;
        if(ctrlKey.compareTo("")==0){
            numSkill = getNumberSkillPassiveOfACharacter(nameCharacter)+1;
        }
        else{
            numSkill = getNumberSkillActiveOfACharacter(nameCharacter)+1;
        }

        if(nameSkill.compareTo("") == 0
                || nameCharacter.compareTo("")==0){
            throw new SkillDataNotCorrectException();
        }
        ArrayList<Object> listInsert = new ArrayList<>();
        listInsert.add(nameCharacter);
        listInsert.add(numSkill);
        listInsert.add(ctrlKey);
        listInsert.add(nameSkill.toUpperCase());
        listInsert.add(convertBoolToString(animateMvt));
        listInsert.add(convertBoolToString(animateAction));
        listInsert.add(convertBoolToString(isMode));
        listInsert.add(timeCooldown);
        listInsert.add(timeSkill);
        listInsert.add(description);

        dbManager.insertIntoTable(NAME_TABLE, listInsert);
    }

    /**
     * Get a list who contains all Control key for skill of a character.
     * @param nameCharacter the name of the character we want to get all the control key.
     * @return a list who contains all control key for skill of a character.
     */
    public List<String> getCtrlKeyOfACharacter(String nameCharacter){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_ATTRIBUTE_FOR_NAME_CHARACTER_OF_A_SKILL);              listRealValueOfLine.add(nameCharacter);

        try{
            return dbManager.getList(NAME_TABLE, listNameLine, listRealValueOfLine, NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL);
        }catch(SQLException sqlException){
            System.out.println("Problème avec la base de données.");
        }

        return new ArrayList<>();
    }

    /**
     * Modify a control key for a skill of a character.
     * @param nameCharacter the name of the character we want to modify the skill.
     * @param nameSkill the name of the skill we want to change.
     * @param newCtrlKey the new control key we want to put for the character and this skill.
     */
    public void modifyCtrlOfACharacter(String nameCharacter, String nameSkill, String newCtrlKey) throws SkillCtrlAlreadyUsedException,
            SkillCtrlAlreadyUsedByMovementControlException{

        verifyCtrlNotUsedByControlMovement(newCtrlKey);

        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(dbManager.getFromResultSet(resultSet, NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL, newCtrlKey)){
                    throw new SkillCtrlAlreadyUsedException(nameCharacter, newCtrlKey);
                }
            }
        }catch(SQLException ignored){

        }
        update(nameCharacter, nameSkill, NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL, newCtrlKey);

    }

    public void modifyTimeCooldown(String nameCharacter, String nameSkill, float newTimeCooldown){
        update(nameCharacter, nameSkill, NAME_ATTRIBUTE_FOR_TIME_COOLDOWN, newTimeCooldown);
    }

    public void modifyTimeSkill(String nameCharacter, String nameSkill, float newTimeSkill){
        update(nameCharacter, nameSkill, NAME_ATTRIBUTE_FOR_TIME_SKILL, newTimeSkill);
    }

    private void update(String nameCharacter, String nameSkill, String valToModify, Object newValue){
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValueOfLine = new ArrayList<>();
        listNameLine.add(NAME_ATTRIBUTE_FOR_NAME_CHARACTER_OF_A_SKILL);         listRealValueOfLine.add(nameCharacter);
        listNameLine.add(NAME_ATTRIBUTE_FOR_NAME_SKILL);                        listRealValueOfLine.add(nameSkill);
        dbManager.updateTable(NAME_TABLE, listNameLine, listRealValueOfLine, valToModify, newValue);
    }

    /**
     *
     * @return list of all the name of skill in the database.
     */
    public List<String> getListSkillName(){
        try{
            return dbManager.getList(NAME_TABLE, null, null, NAME_ATTRIBUTE_FOR_NAME_SKILL);
        }catch(SQLException sqlException){
            System.out.println(NAME_ERROR_GETTING_DATA);
        }
        return new ArrayList<>();
    }

    /**
     *
     * @return list of all the name character who have skill (there are inside the database).
     */
    public List<String> getListNameCharacterWithSkill(){
        try{
            return dbManager.getList(NAME_TABLE, null, null, NAME_ATTRIBUTE_FOR_NAME_CHARACTER_OF_A_SKILL);
        }catch(SQLException sqlException){
            System.out.println(NAME_ERROR_GETTING_DATA);
        }
        return new ArrayList<>();
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
            System.out.println(NAME_ERROR_GETTING_DATA);
        }
        return nb;
    }

    /**
     * Get number of passive skill of a character.
     * @param nameCharacter the name of the character we want to get the number of passive skill.
     * @return the number of passive skill of a character.
     */
    public int getNumberSkillPassiveOfACharacter(String nameCharacter){
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);

        int nb = 0;
        try{
            while(resultSet.next()){
                if(dbManager.getFromResultSet(resultSet, NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL, "")){
                    nb++;
                }
            }
        }catch(SQLException sqlException){
            System.out.println(NAME_ERROR_GETTING_DATA);
        }
        return nb;
    }

    /**
     * Get number of active skill of a character.
     * @param nameCharacter the name of the character we want to get the number of active skill.
     * @return the number of active skill of a character.
     */
    public int getNumberSkillActiveOfACharacter(String nameCharacter){
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);

        int nb = 0;
        try{
            while(resultSet.next()){
                if(!dbManager.getFromResultSet(resultSet, NAME_ATTRIBUTE_FOR_CTRL_KEY_OF_SKILL, "")){
                    nb++;
                }
            }
        }catch(SQLException sqlException){
            System.out.println(NAME_ERROR_GETTING_DATA);
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
        resultSet = dbManager.selectIntoTable("SELECT * FROM Skill WHERE nameCharacter = '" +
                dbManager.getEncryptedFromObject(nameCharacter) + "'");
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
