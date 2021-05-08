package mainTER.Tools;

import mainTER.DBManage.*;
import mainTER.exception.*;

public class DataInsert {

    private static final int NB_CHARACTER = 4;
    private static final int NB_SKILL = 5;

    private DataInsert(){

    }

    public static void insertPerson(){
        PersonDBManager personDBManager = new PersonDBManager();
//        if(personDBManager.getListNameFromDatabase().size()!=NB_CHARACTER) {
            personDBManager.removeTablePerson();
            personDBManager.createTablePerson();
            try {
                personDBManager.insertIntoTablePerson("Paladin", 5, 20, 5, 5, true);
                personDBManager.insertIntoTablePerson("Demon", 10, 5, 8, 2, true);
                personDBManager.insertIntoTablePerson("Serpent", 8, 10, 0, 3, false);
                personDBManager.insertIntoTablePerson("HommeDragon", 10, 5, 8, 2, true);
            } catch (PersonDataAlreadyExistException personDataAlreadyExistException) {
                System.out.println("Problème dans l'insertion des données des Personnages.");
            } catch (PersonDataNotCorrectException personDataDoesntCorrectException) {
                System.out.println("Les données inséres ne sont pas correcte.");
            }
//        }
    }

    public static void insertControls(){
        ControlsDBManager controlsDBManager = new ControlsDBManager();
        controlsDBManager.removeTableControls();
        controlsDBManager.createTableControls();
        try {
            controlsDBManager.insertIntoTableControls("d","q"," ","a","e","f");
        }catch (ControlsDataAlreadyExistsException controlsDataAlreadyExists){
            System.out.println("Probleme dans l'insertien de controles");
        }
    }

    public static void insertSkill(){
        SkillDBManager skillDBManager = new SkillDBManager();
//        if(skillDBManager.getListSkillName().size() != NB_SKILL) {
            skillDBManager.removeTableSkill();
            skillDBManager.createTableSkill();

            try {
                skillDBManager.insertIntoTableSkill("SHIELD", "1", "Paladin", true, false, true,"Use the shield" +
                        "to lift other characters");
                skillDBManager.insertIntoTableSkill("ATTACK", "2", "Paladin", false, true, false,"Use the sword to " +
                        "break obstacles");
                skillDBManager.insertIntoTableSkill("BARRIER", "3", "Paladin", true, false, true,"Create a barrier" +
                        "which absorb all damages for a short \ntime");
                skillDBManager.insertIntoTableSkill("FLY", "1", "Demon", true, false, true,"Increase the speed for a " +
                        "short time");
                skillDBManager.insertIntoTableSkill("MOULT", "1", "Serpent", false, false, false,"Create a moult of" +
                        "himself. The moult is usable as a \nplatform for one time");
                skillDBManager.insertIntoTableSkill("WALL_JUMP","1","HommeDragon",false,false,false,"Will grab walls" +
                        "while jumping");
            } catch (SkillAlreadyExistException | SkillCtrlAlreadyUsedException | SkillDataNotCorrectException |
                    SkillCtrlAlreadyUsedByMovementControlException | SkillCharacterNotExistException exception) {
                System.out.println(exception.getMessage());
            }
//        }
    }

    public static void insertMap(){
        MapDBManager mapDBManager = new MapDBManager();
        mapDBManager.removeTableMap();
        mapDBManager.createTableMap();
        try {
            mapDBManager.insertIntoTableMap("Forest", "Demon", 1325, 600);
            mapDBManager.insertIntoTableMap("Castle", "Demon", 10, 2250);
            mapDBManager.insertIntoTableMap("City", "Demon", 10, 4000);
        }catch(MapCharacterNotExistException | MapAlreadyExistException exception){
            exception.printStackTrace();
        }
    }

    public static void insetCheckpoints() {
        CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager();

        checkpointsDBManager.removeTableCheckPoints();
        checkpointsDBManager.createTableCheckPoints();

        try {
            checkpointsDBManager.insertIntoTableCheckpoints(0, 0, "Paladin", "Forest");
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

}
