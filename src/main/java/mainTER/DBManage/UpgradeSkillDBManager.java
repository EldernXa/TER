package mainTER.DBManage;

import mainTER.exception.UpgradeSkillDataGetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeSkillDBManager {

    private static final String ATTRIBUTE_STRING_NUM_SKILL = "numSkill";

    private static final String ATTRIBUTE_STRING_NAME_UPGRADE = "nameUpgrade";

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
        listName.add("nameCharacter");              listSize.add(40);
        listName.add("numSkill");                   listSize.add(40);
        listName.add("nameUpgrade");                listSize.add(50);
        listName.add("newValue");                   listSize.add(40);
        listName.add("isAlreadyLearned");           listSize.add(40);
        listName.add("price");                      listSize.add(40);
        listName.add("description");                listSize.add(500);
        dbManager.createTable("UpgradeSkill", listName, 3, listSize);
        /*dbManager.createTableOrInsert("CREATE TABLE UpgradeSkill (" +
                "nameCharacter VARCHAR(40)," +
                "numSkill VARCHAR(40)," +
                "nameUpgrade VARCHAR(50)," +
                "newValue VARCHAR(40)," +
                "isAlreadyLearned VARCHAR(40)," +
                "price VARCHAR(40)," +
                "description VARCHAR(500)," +
                "CONSTRAINT PK_UpgradeSkill PRIMARY KEY (nameCharacter, numSkill, nameUpgrade)" +
                ");");*/
    }

    public void insertIntoTableUpgradeSkill(String nameCharacter, int numSkill, String nameUpgrade, float newValue,
                                            int price, String description){
        ArrayList<Object> listObject = new ArrayList<>();
        listObject.add(nameCharacter);
        listObject.add(numSkill);
        listObject.add(nameUpgrade);
        listObject.add(newValue);
        listObject.add(false);
        listObject.add(price);
        listObject.add(description);
        dbManager.insertIntoTable("UpgradeSkill", listObject);
        /*String reqValues = "INSERT INTO UpgradeSkill VALUES (" +
                "'" + SecureManage.getEncrypted(nameCharacter) +"','" +
                SecureManage.getEncrypted(String.valueOf(numSkill)) + "','" +
                SecureManage.getEncrypted(nameUpgrade) + "','" +
                SecureManage.getEncrypted(String.valueOf(newValue)) + "','" +
                SecureManage.getEncrypted("false") + "','" +
                SecureManage.getEncrypted(String.valueOf(price)) + "','" +
                SecureManage.getEncrypted(description) +
                "')";
        dbManager.createTableOrInsert(reqValues);*/
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
        ArrayList<String> listUpgradeOfASkill = new ArrayList<>();
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString(ATTRIBUTE_STRING_NUM_SKILL).compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0){
                    listUpgradeOfASkill.add(SecureManage.getDecrypted(resultSet.getString(ATTRIBUTE_STRING_NAME_UPGRADE)));
                }
            }
            if(listUpgradeOfASkill.isEmpty())
                throw new UpgradeSkillDataGetException();
            return listUpgradeOfASkill;
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
            return Float.parseFloat(dbManager.getData("UpgradeSkill", listName, listRequest, "newValue"));
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
            return dbManager.getData("UpgradeSkill", listName, listRequest, "isAlreadyLearned").compareTo("true")==0;
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
            return Integer.parseInt(dbManager.getData("UpgradeSkill", listName, listRequest, "price"));
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

    private List<String> listNameForGetting(){
        ArrayList<String> listName = new ArrayList<>();
        listName.add("nameCharacter");
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
