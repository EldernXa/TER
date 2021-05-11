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
        dbManager.createTableOrInsert("CREATE TABLE UpgradeSkill (" +
                "nameCharacter VARCHAR(40)," +
                "numSkill VARCHAR(40)," +
                "nameUpgrade VARCHAR(50)," +
                "newValue VARCHAR(40)," +
                "isAlreadyDone VARCHAR(40)," +
                "price VARCHAR(40)," +
                "description VARCHAR(500)," +
                "CONSTRAINT PK_UpgradeSkill PRIMARY KEY (nameCharacter, numSkill, nameUpgrade)" +
                ");");
    }

    public void insertIntoTableUpgradeSkill(String nameCharacter, int numSkill, String nameUpgrade, float newValue,
                                            boolean isAlreadyDone, int price, String description){
        String reqValues = "INSERT INTO UpgradeSkill VALUES (" +
                "'" + SecureManage.getEncrypted(nameCharacter) +"','" +
                SecureManage.getEncrypted(String.valueOf(numSkill)) + "','" +
                SecureManage.getEncrypted(nameUpgrade) + "','" +
                SecureManage.getEncrypted(String.valueOf(newValue)) + "','" +
                SecureManage.getEncrypted(isAlreadyDone?"true":"false") + "','" +
                SecureManage.getEncrypted(String.valueOf(price)) + "','" +
                SecureManage.getEncrypted(description) +
                "')";
        dbManager.createTableOrInsert(reqValues);
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
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString(ATTRIBUTE_STRING_NUM_SKILL).compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0 &&
                    resultSet.getString(ATTRIBUTE_STRING_NAME_UPGRADE).compareTo(SecureManage.getEncrypted(String.valueOf(nameUpgrade)))==0){
                    return Float.parseFloat(SecureManage.getDecrypted(resultSet.getString("newValue")));
                }
            }
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
        throw new UpgradeSkillDataGetException();
    }

    public void setUpgradeDone(String nameCharacter, int numSkill, String nameUpgrade){
        String request = "UPDATE UpgradeSkill SET isAlreadyDone = '" + SecureManage.getEncrypted("true")+"'" +
                " WHERE nameCharacter = '" + SecureManage.getEncrypted(nameCharacter) + "' AND numSkill = '" + SecureManage.getEncrypted(String.valueOf(numSkill))
                + "' AND nameUpgrade = '" + SecureManage.getEncrypted(nameUpgrade) + "';";
        dbManager.updateTable(request);
    }

    public boolean getIsAlreadyDone(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString(ATTRIBUTE_STRING_NUM_SKILL).compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0 &&
                    resultSet.getString(ATTRIBUTE_STRING_NAME_UPGRADE).compareTo(SecureManage.getEncrypted(String.valueOf(nameUpgrade)))==0){
                    return SecureManage.getDecrypted(resultSet.getString("isAlreadyDone")).compareTo("true")==0;
                }
            }
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
        throw new UpgradeSkillDataGetException();
    }

    public int getPrice(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString(ATTRIBUTE_STRING_NUM_SKILL).compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0 &&
                    resultSet.getString(ATTRIBUTE_STRING_NAME_UPGRADE).compareTo(SecureManage.getEncrypted(String.valueOf(nameUpgrade)))==0){
                    return Integer.parseInt(SecureManage.getDecrypted(resultSet.getString("price")));
                }
            }
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
        throw new UpgradeSkillDataGetException();
    }

    public String getDescription(String nameCharacter, int numSkill, String nameUpgrade) throws UpgradeSkillDataGetException{
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString(ATTRIBUTE_STRING_NUM_SKILL).compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0 &&
                    resultSet.getString(ATTRIBUTE_STRING_NAME_UPGRADE).compareTo(SecureManage.getEncrypted(nameUpgrade))==0){
                    return SecureManage.getDecrypted(resultSet.getString("description"));
                }
            }
        }catch(SQLException sqlException){
            throw new UpgradeSkillDataGetException();
        }
        throw new UpgradeSkillDataGetException();
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
