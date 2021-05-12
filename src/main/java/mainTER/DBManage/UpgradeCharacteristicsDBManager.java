package mainTER.DBManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpgradeCharacteristicsDBManager {

    private final DBManager dbManager;

    public UpgradeCharacteristicsDBManager(){
        this.dbManager = new DBManager();
    }

    public UpgradeCharacteristicsDBManager(String nameDatabase){
        this.dbManager = new DBManager(nameDatabase, "test");
    }

    public void createTableUpgradeCharacteristics(){
        dbManager.createTableOrInsert("CREATE TABLE UpgradeCharacteristics (" +
                "nameCharacter VARCHAR(40)," +
                "nameUpgrade VARCHAR(40)," +
                "newValue VARCHAR(40)," +
                "isAlreadyLearned VARCHAR(40)," +
                "price VARCHAR(40)," +
                "description VARCHAR(500)," +
                "CONSTRAINT PK_UpgradeCharacteristics PRIMARY KEY (nameCharacter, nameUpgrade)" +
                ");");
    }

    public void insertIntoTableUpgradeCharacteristics(String nameCharacter, String nameUpgrade, float newValue, int price, String description){
        String reqValues = "INSERT INTO UpgradeCharacteristics VALUES ('" +
                SecureManage.getEncrypted(nameCharacter) + "','" +
                SecureManage.getEncrypted(nameUpgrade) + "','" +
                SecureManage.getEncrypted(String.valueOf(newValue)) + "','" +
                SecureManage.getEncrypted("false") + "','" +
                SecureManage.getEncrypted(String.valueOf(price))+"','" +
                SecureManage.getEncrypted(description) +
                "');";
        dbManager.createTableOrInsert(reqValues);
    }

    public List<String> getListUpgrade(String nameCharacter){
        ArrayList<String> listUpgrade = new ArrayList<>();
        ResultSet resultSet = selectIntoTable(nameCharacter);
        try{
            while(resultSet.next()){
                listUpgrade.add(SecureManage.getDecrypted(resultSet.getString("nameUpgrade")));
            }
        }catch(SQLException sqlException){
            return listUpgrade;
        }
        return listUpgrade;
    }

    public String getDescription(String nameCharacter, String nameUpgrade){
        ResultSet resultSet = selectIntoTable(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString("nameUpgrade")
                        .compareTo(SecureManage.getEncrypted(nameUpgrade))==0){
                    return SecureManage.getDecrypted(resultSet.getString("description"));
                }
            }
        }catch(SQLException sqlException){
            return null;
        }
        return null;
    }

    public int getPrice(String nameCharacter, String nameUpgrade){
        ResultSet resultSet = selectIntoTable(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString("nameUpgrade")
                        .compareTo(SecureManage.getEncrypted(nameUpgrade))==0){
                    return Integer.parseInt(SecureManage.getDecrypted(resultSet.getString("price")));
                }
            }
        }catch(SQLException sqlException){
            return -1;
        }
        return -1;
    }

    public void setUpgradeDone(String nameCharacter, String nameUpgrade){
        String request = "UPDATE UpgradeCharacteristics SET isAlreadyLearned = '" + SecureManage.getEncrypted("true")+"'" +
                " WHERE nameCharacter = '" + SecureManage.getEncrypted(nameCharacter) + "'" +
                " AND nameUpgrade = '" + SecureManage.getEncrypted(nameUpgrade) + "';";
        dbManager.updateTable(request);
    }

    public boolean getIsAlreadyLearned(String nameCharacter, String nameUpgrade){
        ResultSet resultSet = selectIntoTable(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString("nameUpgrade")
                        .compareTo(SecureManage.getEncrypted(nameUpgrade))==0){
                    return SecureManage.getDecrypted(resultSet.getString("isAlreadyLearned")).compareTo("true")==0;
                }
            }
        }catch(SQLException sqlException){
            return false;
        }
        return false;
    }

    public float getNewValue(String nameCharacter, String nameUpgrade){
        ResultSet resultSet = selectIntoTable(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getString("nameUpgrade")
                        .compareTo(SecureManage.getEncrypted(nameUpgrade))==0){
                    return Float.parseFloat(SecureManage.getDecrypted(resultSet.getString("newValue")));
                }
            }
        }catch(SQLException sqlException){
            return -1;
        }
        return -1;
    }

    private ResultSet selectIntoTable(String nameCharacter){
        ResultSet resultSet;
        resultSet = dbManager.selectIntoTable("SELECT * From UpgradeCharacteristics WHERE nameCharacter = '"+
                SecureManage.getEncrypted(nameCharacter) + "'");
        return resultSet;
    }

    public void deleteTableCharacteristics(){
        dbManager.dropTable("UpgradeCharacteristics");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

}
