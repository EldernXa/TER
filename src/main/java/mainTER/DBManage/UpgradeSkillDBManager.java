package mainTER.DBManage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpgradeSkillDBManager {

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

    public float getNewValue(String nameCharacter, int numSkill, String nameUpgrade){
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString("numSkill").compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0 &&
                    resultSet.getString("nameUpgrade").compareTo(SecureManage.getEncrypted(String.valueOf(nameUpgrade)))==0){
                    return Float.parseFloat(SecureManage.getDecrypted(resultSet.getString("newValue")));
                }
            }
        }catch(SQLException ignored){

        }
        return -1;
    }

    public void setUpgradeDone(String nameCharacter, int numSkill, String nameUpgrade){
        String request = "UPDATE UpgradeSkill SET isAlreadyDone = '" + SecureManage.getEncrypted("true")+"'" +
                " WHERE nameCharacter = '" + SecureManage.getEncrypted(nameCharacter) + "' AND numSkill = '" + SecureManage.getEncrypted(String.valueOf(numSkill))
                + "' AND nameUpgrade = '" + SecureManage.getEncrypted(nameUpgrade) + "';";
        dbManager.updateTable(request);
    }

    public boolean getIsAlreadyDone(String nameCharacter, int numSkill, String nameUpgrade){
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString("numSkill").compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0 &&
                    resultSet.getString("nameUpgrade").compareTo(SecureManage.getEncrypted(String.valueOf(nameUpgrade)))==0){
                    return SecureManage.getDecrypted(resultSet.getString("isAlreadyDone")).compareTo("true")==0;
                }
            }
        }catch(SQLException ignored){

        }
        return false;
    }

    public int getPrice(String nameCharacter, int numSkill, String nameUpgrade){
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString("numSkill").compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0 &&
                    resultSet.getString("nameUpgrade").compareTo(SecureManage.getEncrypted(String.valueOf(nameUpgrade)))==0){
                    return Integer.parseInt(SecureManage.getDecrypted(resultSet.getString("price")));
                }
            }
        }catch(SQLException ignored){

        }
        return -1;
    }

    public String getDescription(String nameCharacter, int numSkill, String nameUpgrade){
        ResultSet resultSet = selectUpgradeSkillForACharacter(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString("numSkill").compareTo(SecureManage.getEncrypted(String.valueOf(numSkill)))==0 &&
                    resultSet.getString("nameUpgrade").compareTo(SecureManage.getEncrypted(nameUpgrade))==0){
                    return SecureManage.getDecrypted(resultSet.getString("description"));
                }
            }
        }catch(SQLException ignored){

        }
        return "";
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
