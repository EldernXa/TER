package mainTER.DBManage;

public class SkillDBManager {

    private final DBManager dbManager;

    public SkillDBManager(){
        this.dbManager = new DBManager();
    }

    public SkillDBManager(String name){
        this.dbManager = new DBManager(name, "test");
    }

    public void createTableSkill(){
        dbManager.createTableOrInsert("CREATE TABLE Skill (" +
                "nameSkill VARCHAR(50) PRIMARY KEY," +
                "nameCharacter VARCHAR(30)," +
                "ctrlKey VARCHAR(10)," +
                "animateMvt BOOLEAN," +
                "animateAction BOOLEAN," +
                "isMode BOOLEAN" +
                ");");
    }

    public void removeTableSkill(){
        dbManager.dropTable("Skill");
    }

    public void insertIntoTableSkill(String nameSkill, String nameCharacter, String ctrlKey, boolean animateMvt, boolean animateAction, boolean isMode){
        String reqValues = "INSERT INTO Skill VALUES (" +
                "'"+ nameSkill + "','" +nameCharacter +"'," +
                "'" + ctrlKey + "'," + (animateMvt?"true":"false") +"," + (animateAction?"true":"false")
                + "," + (isMode?"true":"false") +
                ")";
        dbManager.createTableOrInsert(reqValues);
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }
}
