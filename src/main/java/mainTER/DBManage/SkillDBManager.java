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

    public String getNameSkill(String nameCharacter, int numSkill){
        // TODO add new exception
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try {
            while (resultSet.next()) {
                if(resultSet.getInt("numSkill")==numSkill){
                    return resultSet.getString("nameSkill");
                }
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return "";
    }

    public String getCtrlKey(String nameCharacter, int numSkill){
        // TODO add new exception
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt("numSkill")==numSkill){
                    return resultSet.getString("ctrlKey");
                }
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return "";
    }

    public boolean getAnimateMvt(String nameCharacter, int numSkill){
        // TODO add new exception
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt("numSkill")==numSkill){
                    return resultSet.getBoolean("animateMvt");
                }
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return false;
    }

    public boolean getAnimateAction(String nameCharacter, int numSkill){
        // TODO add new exception
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt("numSkill")==numSkill){
                    return resultSet.getBoolean("animateAction");
                }
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return false;
    }

    public boolean getIsMode(String nameCharacter, int numSkill){
        // TODO add new exception
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt("numSkill")==numSkill){
                    return resultSet.getBoolean("isMode");
                }
            }
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données.");
        }
        return false;
    }

    public void removeTableSkill(){
        dbManager.dropTable("Skill");
    }

    public void insertIntoTableSkill(String nameSkill, String ctrlKey, String nameCharacter, boolean animateMvt, boolean animateAction, boolean isMode){
        // TODO verify the skill doesn't exist.
        // TODO verify the data we want to insert.
        // TODO verify ctrl is not already used by the same character.

        int numSkill = getNumberSkillOfACharacter(nameCharacter)+1;

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
        resultSet = dbManager.selectIntoTable("SELECT * FROM Skill WHERE nameCharacter = '" + nameCharacter + "'");
        return resultSet;
    }

    private String convertBoolToString(boolean valToConvert){
        return valToConvert?"true":"false";
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }
}
