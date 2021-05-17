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

    private final DBManager dbManager;

    public UpgradeSkillDBManager(){
        this.dbManager = new DBManager();
    }

    public UpgradeSkillDBManager(String name){
        this.dbManager = new DBManager(name, "test");
    }

    public void createTableUpgradeSkill(){
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listSize = new ArrayList<>();
        listName.add(ATTRIBUTE_STRING_NAME_CHARACTER);              listSize.add(40);
        listName.add(ATTRIBUTE_STRING_NUM_SKILL);                   listSize.add(40);
        listName.add(ATTRIBUTE_STRING_NAME_UPGRADE);                listSize.add(50);
        listName.add(ATTRIBUTE_STRING_NUM_UPGRADE);                 listSize.add(40);
        listName.add(ATTRIBUTE_STRING_NEW_VALUE);                   listSize.add(40);
        listName.add(ATTRIBUTE_STRING_IS_ALREADY_LEARNED);                           listSize.add(40);
        listName.add(ATTRIBUTE_STRING_PRICE);                                      listSize.add(40);
        listName.add("description");                                listSize.add(500);
        dbManager.createTable("UpgradeSkill", listName, 4, listSize);
    }

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
        dbManager.insertIntoTable("UpgradeSkill", listObject);
    }

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

    public List<String> getListUpgradeOfASkillOfACharacter(String nameCharacter, int numSkill) throws UpgradeSkillDataGetException{
        ArrayList<String> listNameLine = new ArrayList<>();
        ArrayList<Object> listRealValue = new ArrayList<>();
        listNameLine.add(ATTRIBUTE_STRING_NAME_CHARACTER);                  listRealValue.add(nameCharacter);
        listNameLine.add(ATTRIBUTE_STRING_NUM_SKILL);                       listRealValue.add(numSkill);
        List<String> listUpgradeOfASkill;
        try{
            listUpgradeOfASkill = dbManager.getList("UpgradeSkill", listNameLine, listRealValue, ATTRIBUTE_STRING_NAME_UPGRADE);
            if(listUpgradeOfASkill.isEmpty())
                throw new UpgradeSkillDataGetException();
            return listUpgradeOfASkill;
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

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

    public Integer getLastNumOfAUpgrade(String nameCharacter, int numSkill, String nameUpgrade){
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
        if(num==0)
            return -1;
        return num-1;
    }

    public Integer getNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        try{
            return Integer.parseInt(dbManager.getData("UpgradeSkill", listName, listRequest, ATTRIBUTE_STRING_NUM_UPGRADE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    public float getNewValue(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        try{
            return Float.parseFloat(dbManager.getData("UpgradeSkill", listName, listRequest, ATTRIBUTE_STRING_NEW_VALUE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    public float getNewValueWithNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade, int numUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        listName.add(ATTRIBUTE_STRING_NUM_UPGRADE);
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        listRequest.add(numUpgrade);
        try{
            return Float.parseFloat(dbManager.getData("UpgradeSkill", listName, listRequest, ATTRIBUTE_STRING_NEW_VALUE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    public void setUpgradeDone(String nameCharacter, int numSkill, String nameUpgrade){
        String request = "UPDATE UpgradeSkill SET isAlreadyLearned = '" + SecureManage.getEncrypted("true")+"'" +
                " WHERE nameCharacter = '" + SecureManage.getEncrypted(nameCharacter) + "' AND numSkill = '" + SecureManage.getEncrypted(String.valueOf(numSkill))
                + "' AND nameUpgrade = '" + SecureManage.getEncrypted(nameUpgrade) + "';";
        dbManager.updateTable(request);
    }

    public boolean getIsAlreadyDone(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        try{
            return dbManager.getData("UpgradeSkill", listName, listRequest, ATTRIBUTE_STRING_IS_ALREADY_LEARNED).compareTo("true")==0;
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    public boolean getIsAlreadyDoneWithNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade, int numUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        listName.add(ATTRIBUTE_STRING_NUM_UPGRADE);
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        listRequest.add(numUpgrade);
        try{
            return dbManager.getData("UpgradeSkill", listName, listRequest, ATTRIBUTE_STRING_IS_ALREADY_LEARNED).compareTo("true")==0;
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    public int getPrice(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        try{
            return Integer.parseInt(dbManager.getData("UpgradeSkill", listName, listRequest, ATTRIBUTE_STRING_PRICE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    public int getPriceWithNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade, int numUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        listName.add(ATTRIBUTE_STRING_NUM_UPGRADE);
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        listRequest.add(numUpgrade);
        try{
            return Integer.parseInt(dbManager.getData("UpgradeSkill", listName, listRequest, ATTRIBUTE_STRING_PRICE));
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    public String getDescription(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        try{
            return dbManager.getData("UpgradeSkill", listName, listRequest, "description");
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    public String getDescriptionWithNumUpgrade(String nameCharacter, int numSkill, String nameUpgrade, int numUpgrade) throws UpgradeSkillDataGetException{
        List<String> listName = listNameForGetting();
        listName.add(ATTRIBUTE_STRING_NUM_UPGRADE);
        ArrayList<Object> listRequest = new ArrayList<>();
        listRequest.add(nameCharacter);
        listRequest.add(numSkill);
        listRequest.add(nameUpgrade);
        listRequest.add(numUpgrade);
        try{
            return dbManager.getData("UpgradeSkill", listName, listRequest, "description");
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
    }

    private List<String> listNameForGetting(){
        ArrayList<String> listName = new ArrayList<>();
        listName.add(ATTRIBUTE_STRING_NAME_CHARACTER);
        listName.add(ATTRIBUTE_STRING_NUM_SKILL);
        listName.add(ATTRIBUTE_STRING_NAME_UPGRADE);
        return listName;
    }

    private ResultSet selectUpgradeSkillForACharacter(String nameCharacter){
        ResultSet resultSet;
        resultSet = dbManager.selectIntoTable("SELECT * FROM UpgradeSkill WHERE nameCharacter = '" +
                SecureManage.getEncrypted(nameCharacter) + "'");
        return resultSet;
    }

    public void removeTableUpgradeSkill(){
        dbManager.dropTable("UpgradeSkill");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

}
