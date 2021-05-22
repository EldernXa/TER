package mainTER.DBManage;

import mainTER.exception.UpgradeSkillDataGetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UpgradeSkillDBManager {

    private static final String ATTRIBUTE_STRING_NUM_SKILL = "numSkill";

    private static final String ATTRIBUTE_STRING_NAME_UPGRADE = "nameUpgrade";

    private static final String ATTRIBUTE_STRING_NAME_CHARACTER = "nameCharacter";

    private static final String ATTRIBUTE_STRING_NUM_UPGRADE = "numUpgrade";

    private static final String ATTRIBUTE_STRING_NEW_VALUE = "newValue";

    private static final String ATTRIBUTE_STRING_IS_ALREADY_LEARNED = "isAlreadyLearned";

    private static final String ATTRIBUTE_STRING_PRICE = "price";

    private static final String ATTRIBUTE_STRING_DESCRIPTION = "description";

    private static final String TABLE_NAME = "UpgradeSkill";

    private final DBManager dbManager;

    /**
     * Constructor for the application.
     */
    public UpgradeSkillDBManager(){
        this.dbManager = new DBManager();
    }

    /**
     * Constructor for the test purpose.
     * @param name the name of the databases.
     */
    public UpgradeSkillDBManager(String name){
        this.dbManager = new DBManager(name, "test");
    }

    /**
     * Creating the table UpgradeSkill.
     */
    public void createTableUpgradeSkill(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add(ATTRIBUTE_STRING_NAME_CHARACTER);              listSize.add(40);
        listName.add(ATTRIBUTE_STRING_NUM_SKILL);                   listSize.add(40);
        listName.add(ATTRIBUTE_STRING_NAME_UPGRADE);                listSize.add(50);
        listName.add(ATTRIBUTE_STRING_NUM_UPGRADE);                 listSize.add(40);
        listName.add(ATTRIBUTE_STRING_NEW_VALUE);                   listSize.add(40);
        listName.add(ATTRIBUTE_STRING_IS_ALREADY_LEARNED);          listSize.add(40);
        listName.add(ATTRIBUTE_STRING_PRICE);                       listSize.add(40);
        listName.add(ATTRIBUTE_STRING_DESCRIPTION);                 listSize.add(500);
        dbManager.createTable(TABLE_NAME, listName, 4, listSize);
    }

    /**
     * verify if table UpgradeSkill exist.
     * @return true if table UpgradeSkill exist, false otherwise.
     */
    public boolean verifyTableUpgradeSkillExist(){
        ResultSet resultSet = dbManager.selectIntoTable("SELECT * FROM UpgradeSkill");
        try{
            if(resultSet.next()){
                return true;
            }
        }catch(Exception ignored){

        }
        return false;
    }

    /**
     * insert values into the table UpgradeSkill
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @param nameUpgrade the name of the upgrade.
     * @param newValue the new value.
     * @param price the price of the upgrade.
     * @param description the description of the upgrade.
     */
    public void insertIntoTableUpgradeSkill(String nameCharacter, int numSkill, String nameUpgrade, float newValue,
                                            int price, String description){
        ArrayList<Object> listObject = new ArrayList<>();
        listObject.add(nameCharacter);
        listObject.add(numSkill);
        listObject.add(nameUpgrade);
        listObject.add(getLastNum(nameCharacter, numSkill, nameUpgrade)+1);
        listObject.add(newValue);
        listObject.add(false);
        listObject.add(price);
        listObject.add(description);
        dbManager.insertIntoTable(TABLE_NAME, listObject);
    }

    /**
     * Get list of upgrade.
     * @param nameCharacter the name of the character we want to get the upgrades.
     * @return a map who contains all the num skill and the name upgrade for a character.
     * @throws UpgradeSkillDataGetException if the character doesn't contains upgrade.
     */
    public Map<Integer, String> getListUpgradeSkillOfACharacter(String nameCharacter) throws UpgradeSkillDataGetException{
        HashMap<Integer, String> listUpgradeSkill = new HashMap<>();
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                listUpgradeSkill.put(Integer.parseInt(SecureManage.getDecrypted(resultSet.getString(ATTRIBUTE_STRING_NUM_SKILL))),
                        SecureManage.getDecrypted(resultSet.getString(ATTRIBUTE_STRING_NAME_UPGRADE)));
            }
            if(listUpgradeSkill.isEmpty())
                throw new UpgradeSkillDataGetException();
            return listUpgradeSkill;
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * get list of upgrade.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @return a list who contains a upgrade name for a character.
     * @throws UpgradeSkillDataGetException if the character doesn't contains any upgrade for this skill.
     */
    public List<String> getListUpgradeOfASkillOfACharacter(String nameCharacter, int numSkill) throws UpgradeSkillDataGetException{
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValue = new ArrayList<>();
        listNameLine.add(ATTRIBUTE_STRING_NAME_CHARACTER);                  listRealValue.add(nameCharacter);
        listNameLine.add(ATTRIBUTE_STRING_NUM_SKILL);                       listRealValue.add(numSkill);
        List<String> listUpgradeOfASkill;
        try{
            listUpgradeOfASkill = dbManager.getList(TABLE_NAME, listNameLine, listRealValue, ATTRIBUTE_STRING_NAME_UPGRADE);
            if(listUpgradeOfASkill.isEmpty())
                throw new UpgradeSkillDataGetException();
            return listUpgradeOfASkill;
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Get list Upgrade sorted.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @return a list who contains a upgrade name for a character.
     * @throws UpgradeSkillDataGetException if the character doesn't contains any upgrade for this skill.
     */
    public List<String> getListUpgrade(String nameCharacter, int numSkill) throws UpgradeSkillDataGetException {
        ArrayList<String> listReturn = new ArrayList<>();
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(dbManager.getFromResultSet(resultSet, ATTRIBUTE_STRING_NUM_SKILL, numSkill)){
                    listReturn.add(dbManager.getDecryptedFromString(resultSet.getString(ATTRIBUTE_STRING_NAME_UPGRADE)) + " " +
                            Integer.parseInt(dbManager.getDecryptedFromString(resultSet.getString(ATTRIBUTE_STRING_NUM_UPGRADE))));
                }
            }
            Collections.sort(listReturn);
            return listReturn;
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Get the last upgrade available for a name upgrade and a skill for the constructor principally.
     * @param nameCharacter the name of a character.
     * @param numSkill the num of a skill.
     * @param nameUpgrade the name of an upgrade.
     * @return the last upgrade available for a name upgrade and a skill.
     */
    private Integer getLastNum(String nameCharacter, int numSkill, String nameUpgrade){
        int num = 0;
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(dbManager.getFromResultSet(resultSet, ATTRIBUTE_STRING_NUM_SKILL, numSkill) &&
                        dbManager.getFromResultSet(resultSet, ATTRIBUTE_STRING_NAME_UPGRADE, nameUpgrade)){
                    int newNum = Integer.parseInt(dbManager.getDecryptedFromString(resultSet.getString(ATTRIBUTE_STRING_NUM_UPGRADE)));
                    if(num<newNum){
                        num = newNum;
                    }
                }
            }
        }catch(SQLException ignored){

        }
        return num;
    }

    /**
     * Get the last upgrade available for a name upgrade and a skill for the application purpose.
     * @param nameCharacter the name of a character.
     * @param numSkill the num of a skill.
     * @param nameUpgrade the name of an upgrade.
     * @return the last upgrade available for a name upgrade and a skill and -1 if there aren't upgrade skill.
     */
    public Integer getLastNumOfAUpgrade(String nameCharacter, int numSkill, String nameUpgrade){
        int num = getLastNum(nameCharacter, numSkill, nameUpgrade);
        if(num==0)
            return -1;
        return num-1;
    }

    /**
     * Get values from the table UpgradeSkill.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @param nameUpgrade the name of the upgrade.
     * @param stringRequested the name of the attribute.
     * @return a values from the table UpgradeSkill.
     * @throws SQLException if the upgrade requested doesn't exist.
     */
    private String getObject(String nameCharacter, int numSkill, String nameUpgrade, String stringRequested) throws SQLException {
        List<String> listName = listNameForGetting();
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        return dbManager.getData(TABLE_NAME, listName, listRequest, stringRequested);
    }

    /**
     * Get the num of an upgrade.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of a skill.
     * @param nameUpgrade the name of an upgrade.
     * @return the num of an upgrade.
     * @throws UpgradeSkillDataGetException if the upgrade requested doesn't exist.
     */
    public Integer getNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        try{
            return Integer.parseInt(getObject(nameCharacter, numSkill, nameUpgrade, ATTRIBUTE_STRING_NUM_UPGRADE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Get the new value of an upgrade.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of a skill.
     * @param nameUpgrade the name of an upgrade.
     * @return the num of an upgrade.
     * @throws UpgradeSkillDataGetException if the upgrade requested doesn't exist.
     */
    public float getNewValue(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        try{
            return Float.parseFloat(getObject(nameCharacter, numSkill, nameUpgrade, ATTRIBUTE_STRING_NEW_VALUE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * get value with num upgrade in addition.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @param nameUpgrade the name of the upgrade.
     * @param numUpgrade the num of the upgrade.
     * @param stringRequested the name of the attribute we want.
     * @return a value.
     * @throws SQLException if the upgrade requested doesn't exist.
     */
    public String getObjectWithNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade, int numUpgrade, String stringRequested) throws SQLException {
        List<String> listName = listNameForGetting();
        listName.add(ATTRIBUTE_STRING_NUM_UPGRADE);
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        listRequest.add(numUpgrade);
        return dbManager.getData(TABLE_NAME, listName, listRequest, stringRequested);
    }

    /**
     * get the new value of an upgrade with num upgrade in addition.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of a skill.
     * @param nameUpgrade the name of an upgrade.
     * @param numUpgrade the num of an upgrade.
     * @return the new value of an upgrade.
     * @throws UpgradeSkillDataGetException if the upgrade requested doesn't exist.
     */
    public float getNewValueWithNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade, int numUpgrade) throws UpgradeSkillDataGetException{
        try{
            return Float.parseFloat(getObjectWithNumUpgrade(nameCharacter, numSkill, nameUpgrade, numUpgrade, ATTRIBUTE_STRING_NEW_VALUE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Set that the upgrade is already learned.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @param nameUpgrade the name of the upgrade.
     */
    public void setUpgradeDone(String nameCharacter, int numSkill, String nameUpgrade){
        String request = "UPDATE UpgradeSkill SET isAlreadyLearned = '" + SecureManage.getEncrypted("true")+"'" +
                " WHERE nameCharacter = '" + SecureManage.getEncrypted(nameCharacter) + "' AND numSkill = '" + SecureManage.getEncrypted(String.valueOf(numSkill))
                + "' AND nameUpgrade = '" + SecureManage.getEncrypted(nameUpgrade) + "';";
        dbManager.updateTable(request);
    }

    /**
     * Get if the upgrade is already learned.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of a skill.
     * @param nameUpgrade the name of an upgrade.
     * @return true if the upgrade is already learned, false otherwise.
     * @throws UpgradeSkillDataGetException if the upgrade requested doesn't exist.
     */
    public boolean getIsAlreadyDone(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        try{
            return getObject(nameCharacter, numSkill, nameUpgrade, ATTRIBUTE_STRING_IS_ALREADY_LEARNED).compareTo("true") == 0;
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Get isAlreadyLearned with num upgrade in addition.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @param nameUpgrade the name of the upgrade.
     * @param numUpgrade the num of the upgrade.
     * @return true if the upgrade is already learned, false otherwise.
     * @throws UpgradeSkillDataGetException if the upgrade requested doesn't exist.
     */
    public boolean getIsAlreadyDoneWithNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade, int numUpgrade) throws UpgradeSkillDataGetException{
        try{
            return getObjectWithNumUpgrade(nameCharacter, numSkill, nameUpgrade, numUpgrade, ATTRIBUTE_STRING_IS_ALREADY_LEARNED).compareTo("true") != 0;
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Get price.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @param nameUpgrade the name of the upgrade.
     * @return the price of an upgrade.
     * @throws UpgradeSkillDataGetException if the upgrade requested doesn't exist.
     */
    public int getPrice(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        try{
            return Integer.parseInt(getObject(nameCharacter, numSkill, nameUpgrade, ATTRIBUTE_STRING_PRICE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Get price with num upgrade in addition.
     * @param nameCharacter the name of the character.
     * @param numSkill the num of the skill.
     * @param nameUpgrade the name of the upgrade.
     * @param numUpgrade the num of the upgrade.
     * @return the price of an upgrade.
     * @throws UpgradeSkillDataGetException if the upgrade requested doesn't exist.
     */
    public int getPriceWithNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade, int numUpgrade) throws UpgradeSkillDataGetException{
        try{
            return Integer.parseInt(getObjectWithNumUpgrade(nameCharacter, numSkill, nameUpgrade, numUpgrade, ATTRIBUTE_STRING_PRICE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Get description.
     * @param nameCharacter the name of a character.
     * @param numSkill the num of a skill.
     * @param nameUpgrade the name of an upgrade.
     * @return the description of an upgrade.
     * @throws UpgradeSkillDataGetException if the upgrade requested doesn't exist.
     */
    public String getDescription(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        try{
            return getObject(nameCharacter, numSkill, nameUpgrade, ATTRIBUTE_STRING_DESCRIPTION);
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    /**
     * Get a list containing the main attribute name for the upgrade.
     * @return a list containing the main attribute name for the upgrade.
     */
    private List<String> listNameForGetting(){
        ArrayList<String> listName = new ArrayList<>();
        listName.add(ATTRIBUTE_STRING_NAME_CHARACTER);
        listName.add(ATTRIBUTE_STRING_NUM_SKILL);
        listName.add(ATTRIBUTE_STRING_NAME_UPGRADE);
        return listName;
    }

    /**
     * do a request for a select.
     * @param nameCharacter the name of a character.
     * @return a resultSet containing the result of a select for the UpgradeSkill table.
     */
    private ResultSet selectUpgradeSkillForACharacter(String nameCharacter){
        ResultSet resultSet;
        resultSet = dbManager.selectIntoTable("SELECT * FROM UpgradeSkill WHERE nameCharacter = '" +
                SecureManage.getEncrypted(nameCharacter) + "'");
        return resultSet;
    }

    /**
     * Remove table UpgradeSkill.
     */
    public void removeTableUpgradeSkill(){
        dbManager.dropTable(TABLE_NAME);
    }

    /**
     * Drop cascade.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

}
