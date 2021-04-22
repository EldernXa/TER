package mainTER.DBManage;

import mainTER.exception.PersonDataGetException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDBManager {
    private final DBManager dbManager;

    public PersonDBManager(){
        this.dbManager = new DBManager();
    }

    public PersonDBManager(String nameFileDB){
        this.dbManager = new DBManager(nameFileDB);
    }

    public void createTablePerson(){
        dbManager.createTableOrInsert("CREATE TABLE Person (" +
                "name VARCHAR(30) PRIMARY KEY," +
                "speed FLOAT(3)," +
                "weight FLOAT(3)," +
                "jumpStrength FLOAT(3)," +
                "canJump BOOLEAN" +
                ");");
    }

    public void removeTablePerson(){
        dbManager.dropTable("Person");
    }

    public void dropCascade(){
        dbManager.dropCascade();
    }

    public void insertIntoTablePerson(String name, double speed, double weight, double jumpStrength, boolean canJump) {
        // TODO verify insert data
        // TODO verify data doesn't exist already
        // TODO use PrepareStatement to insert
        String reqValues = "INSERT INTO Person VALUES (" +
                "'" + name+"'" + "," + speed+","+weight+","+jumpStrength+","+"'"+(canJump?"true":"else")+"'"+
                ")";
        dbManager.createTableOrInsert(reqValues);
    }

    private ResultSet selectIntoTablePerson(String nameCharacter){
        ResultSet rs = null;
        try {
            rs = dbManager.selectIntoTable("SELECT name, speed, weight, jumpStrength, canJump" +
                    " FROM Person WHERE name = '" + nameCharacter + "'");
            rs.next();
        }catch(SQLException sqlException){
            System.out.println("Problème dans la récupération de données du personnages " + nameCharacter);
        }
        return rs;
    }

    public double getSpeed(String nameCharacter) throws PersonDataGetException{
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try {
            return (double)rs.getObject("speed");
        }catch(SQLException sqlException){
            throw new PersonDataGetException(nameCharacter);
        }
    }

    public boolean getCanJump(String nameCharacter) throws PersonDataGetException{
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try{
            return (boolean) rs.getObject("canJump");
        }catch(SQLException sqlException){
            throw new PersonDataGetException(nameCharacter);
        }
    }

    public double getJumpStrength(String nameCharacter) throws PersonDataGetException{
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try{
            return (double) rs.getObject("jumpStrength");
        }catch(SQLException sqlException){
            throw new PersonDataGetException(nameCharacter);
        }
    }

    public double getWeight(String nameCharacter) throws PersonDataGetException{
        ResultSet rs = selectIntoTablePerson(nameCharacter);
        try{
            return (double)rs.getObject("weight");
        }catch(SQLException sqlException)
        {
            throw new PersonDataGetException(nameCharacter);
        }
    }

    public void toStringPerson(String nameCharacter){
        ResultSet resultSet = selectIntoTablePerson(nameCharacter);
        try {
            System.out.println("Personnage : " + resultSet.getObject("name") +", "+
                    resultSet.getObject("speed")+ ", " + resultSet.getObject("weight")+
                    ", " + resultSet.getObject("jumpStrength")+", "+resultSet.getObject("canJump"));
        }catch(SQLException sqlException){
            System.out.println("Problème dans l'affichage des données de " + nameCharacter);
        }
    }

}