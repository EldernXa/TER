package mainTER.DBManage;

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

    public void removeTableUpgradeSkill(){
        dbManager.dropTable("UpgradeSkill");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

}
