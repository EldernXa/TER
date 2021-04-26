package mainTER.DBManage;

import mainTER.exception.SkillAlreadyExistException;
import mainTER.exception.SkillDataGetException;
import mainTER.exception.SkillCtrlAlreadyUsedException;
import mainTER.exception.SkillDataDoesntCorrectException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDBManager {

    private final DBManager dbManager;
    private static final String NAME_ATTRIBUTE_FOR_NUM_SKILL = "numSkill";

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

    public String getNameSkill(String nameCharacter, int numSkill) throws SkillDataGetException {
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try {
            while (resultSet.next()) {
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getString("nameSkill");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    public String getCtrlKey(String nameCharacter, int numSkill) throws SkillDataGetException{
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getString("ctrlKey");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    public boolean getAnimateMvt(String nameCharacter, int numSkill) throws SkillDataGetException{
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getBoolean("animateMvt");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    public boolean getAnimateAction(String nameCharacter, int numSkill) throws SkillDataGetException{
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getBoolean("animateAction");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    public boolean getIsMode(String nameCharacter, int numSkill) throws SkillDataGetException{
        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try{
            while(resultSet.next()){
                if(resultSet.getInt(NAME_ATTRIBUTE_FOR_NUM_SKILL)==numSkill){
                    return resultSet.getBoolean("isMode");
                }
            }
        }catch(SQLException sqlException){
            throw new SkillDataGetException(nameCharacter, numSkill);
        }
        throw new SkillDataGetException(nameCharacter, numSkill);
    }

    public void removeTableSkill(){
        try {
            dbManager.dropTable("Skill");
        }catch(Exception ignored){

        }
    }

    public void insertIntoTableSkill(String nameSkill, String ctrlKey, String nameCharacter, boolean animateMvt, boolean animateAction, boolean isMode) throws SkillAlreadyExistException, SkillCtrlAlreadyUsedException, SkillDataDoesntCorrectException {

        ResultSet resultSet = selectCharacterIntoTableSkill(nameCharacter);
        try {
            while (resultSet.next()) {
                if(resultSet.getString("nameSkill").compareTo(nameSkill)==0){
                    throw new SkillAlreadyExistException(nameCharacter, nameSkill);
                }
                if(resultSet.getString("ctrlKey").compareTo(ctrlKey)==0){
                    throw new SkillCtrlAlreadyUsedException(nameCharacter, ctrlKey);
                }
            }
        }catch(SQLException ignored){

        }

        int numSkill = getNumberSkillOfACharacter(nameCharacter)+1;

        if(nameSkill.compareTo("") == 0 || ctrlKey.compareTo("")==0 || nameCharacter.compareTo("")==0){
            throw new SkillDataDoesntCorrectException();
        }
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
        ResultSet resultSet;
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
