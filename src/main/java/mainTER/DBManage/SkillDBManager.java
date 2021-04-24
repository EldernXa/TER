package mainTER.DBManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                "nameSkill VARCHAR(50),"+
                "numSkill INTEGER," +"ctrlKey VARCHAR(30),"+
                "nameCharacter VARCHAR(30)," +
                "animateMvt BOOLEAN," +
                "animateAction BOOLEAN," +
                "isMode BOOLEAN," +
                "CONSTRAINT PK_Person PRIMARY KEY (nameCharacter,numSkill)" +
                ");");
    }

    public void removeTableSkill(){
        dbManager.dropTable("Skill");
    }

    public void insertIntoTableSkill(String nameSkill,int numSkill, String ctrlKey, String nameCharacter, boolean animateMvt, boolean animateAction, boolean isMode){
        // TODO verify the skill doesn't exist.
        // TODO verify the data we want to insert.

        String reqValues = "INSERT INTO Skill VALUES (" +
                "'"+ nameSkill + "'," + numSkill + ",'" + ctrlKey + "','" +nameCharacter +"',"
                 + convertBoolToString(animateMvt) +"," + convertBoolToString(animateAction)
                + "," + convertBoolToString(isMode) +
                ")";
        dbManager.createTableOrInsert(reqValues);
    }

    public List<String> getListSkillName(){
        ArrayList<String> listSkillName = new ArrayList<>();
        ResultSet resultSet;
        try{
            resultSet = dbManager.selectIntoTable("SELECT * FROM Skill;");
            while(resultSet.next()){
                listSkillName.add((String)resultSet.getObject("nameSkill"));
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listSkillName;
    }

    public List<String> getListNameCharacterWithSkill(){
        ArrayList<String> listName = new ArrayList<>();
        ResultSet resultSet;
        try{
            resultSet = dbManager.selectIntoTable("SELECT * FROM Skill;");
            while(resultSet.next()){
                if(!listName.contains(resultSet.getString("nameCharacter"))){
                    listName.add((String)resultSet.getObject("nameCharacter"));
                }
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return listName;
    }

    public int getNumberSkillOfACharacter(String nameCharacter){
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);

        int nb = 0;
        try {
            nb++;
            while(resultSet.next()){
                nb++;
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données");
        }
        return nb;
    }

    private ResultSet selectCharacterIntoTableSkill(String nameCharacter){
        ResultSet resultSet = null;
        try{
            resultSet = dbManager.selectIntoTable("SELECT * FROM Skill WHERE nameCharacter = '" + nameCharacter + "'");
            resultSet.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données du personnages " + nameCharacter);
        }
        return resultSet;
    }

    private String convertBoolToString(boolean valToConvert){
        return valToConvert?"true":"false";
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }
}
