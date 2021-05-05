package mainTER.Tools;

import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.PersonDBManager;
import mainTER.DBManage.SkillDBManager;
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
            controlsDBManager.insertIntoTableControls("d","q"," ","a","e");
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
                skillDBManager.insertIntoTableSkill("SHIELD", "1", "Paladin", true, false, true,"porte");
                skillDBManager.insertIntoTableSkill("ATTACK", "2", "Paladin", false, true, false,"attaque");
                skillDBManager.insertIntoTableSkill("BARRIER", "3", "Paladin", true, false, true,"protege");
                skillDBManager.insertIntoTableSkill("FLY", "1", "Demon", true, false, true,"sprinter");
                skillDBManager.insertIntoTableSkill("MOULT", "1", "Serpent", false, false, false,"muer");
                skillDBManager.insertIntoTableSkill("WALL_JUMP","1","HommeDragon",false,false,false,"grimper");
            } catch (SkillAlreadyExistException | SkillCtrlAlreadyUsedException | SkillDataNotCorrectException |
                    SkillCtrlAlreadyUsedByMovementControlException | SkillCharacterNotExistException exception) {
                System.out.println(exception.getMessage());
            }
//        }
    }

}
