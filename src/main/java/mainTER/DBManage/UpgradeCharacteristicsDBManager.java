package mainTER.DBManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpgradeCharacteristicsDBManager {

    private final DBManager dbManager;

    /**
     * Constructor for the application.
     */
    public UpgradeCharacteristicsDBManager(){
        this.dbManager = new DBManager();
    }

    /**
     * Constructor for the test purpose.
     * @param nameDatabase the name of the databases.
     */
    public UpgradeCharacteristicsDBManager(String nameDatabase){
        this.dbManager = new DBManager(nameDatabase, "test");
    }

    /**
     * Create table for UpgradeCharacteristics.
     */
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

    /**
     * insert values into the table UpgradeCharacteristics.
     * @param nameCharacter the name of the character.
     * @param nameUpgrade the name of the upgrade.
     * @param newValue the new values.
     * @param price the price for the upgrade.
     * @param description the description for the upgrade.
     */
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

    /**
     * Get list upgrade for a character.
     * @param nameCharacter a name of a character.
     * @return a list of upgrade for a character.
     */
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

    /**
     * Get description for a character and the name of the upgrade.
     * @param nameCharacter the name of a character.
     * @param nameUpgrade the name of an upgrade.
     * @return the description for an upgrade.
     */
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

    /**
     * Get the Price for an upgrade.
     * @param nameCharacter the name of a character.
     * @param nameUpgrade the name of an upgrade.
     * @return the price for an upgrade.
     */
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

    /**
     * set an upgrade is done.
     * @param nameCharacter the name of a character.
     * @param nameUpgrade the name of an upgrade.
     */
    public void setUpgradeDone(String nameCharacter, String nameUpgrade){
        String request = "UPDATE UpgradeCharacteristics SET isAlreadyLearned = '" + SecureManage.getEncrypted("true")+"'" +
                " WHERE nameCharacter = '" + SecureManage.getEncrypted(nameCharacter) + "'" +
                " AND nameUpgrade = '" + SecureManage.getEncrypted(nameUpgrade) + "';";
        dbManager.updateTable(request);
    }

    /**
     * get if the upgrade is already learned.
     * @param nameCharacter the name of the character.
     * @param nameUpgrade the name of the upgrade.
     * @return true if the upgrade is already learned, false otherwise.
     */
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

    /**
     * Get the new value of the upgrade.
     * @param nameCharacter the name of the character.
     * @param nameUpgrade the name of the upgrade.
     * @return the new value of the upgrade.
     */
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

    /**
     * do a select request for the table UpgradeCharacteristics.
     * @param nameCharacter the name of a character.
     * @return a select request for the table UpgradeCharacteristics.
     */
    private ResultSet selectIntoTable(String nameCharacter){
        ResultSet resultSet;
        resultSet = dbManager.selectIntoTable("SELECT * From UpgradeCharacteristics WHERE nameCharacter = '"+
                SecureManage.getEncrypted(nameCharacter) + "'");
        return resultSet;
    }

    /**
     * Delete the table UpgradeCharacteristics.
     */
    public void deleteTableCharacteristics(){
        dbManager.dropTable("UpgradeCharacteristics");
    }

    /**
     * Drop cascade.
     */
    public void dropCascade(){
        dbManager.dropCascade();
    }

}
