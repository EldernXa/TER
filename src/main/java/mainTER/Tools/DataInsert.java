package mainTER.Tools;

import mainTER.DBManage.ControlsDBManager;
import mainTER.DBManage.PersonDBManager;
import mainTER.DBManage.SkillDBManager;
import mainTER.exception.ControlsDataAlreadyExistsException;
import mainTER.exception.PersonDataAlreadyExistException;
import mainTER.exception.PersonDataDoesntCorrectException;
import mainTER.exception.SkillAlreadyExistException;

public class DataInsert {

    private DataInsert(){

    }

    public static void insertPerson(){
        PersonDBManager personDBManager = new PersonDBManager();
        personDBManager.removeTablePerson();
        personDBManager.createTablePerson();
        try {
            personDBManager.insertIntoTablePerson("Paladin", 5, 20, 5, 5, true);
            personDBManager.insertIntoTablePerson("Demon", 10, 5, 8, 2, true);
            personDBManager.insertIntoTablePerson("Serpent", 8, 10, 0, 3, false);
            personDBManager.insertIntoTablePerson("HommeDragon", 10, 5, 8, 2, true);
        }catch(PersonDataAlreadyExistException personDataAlreadyExistException){
            System.out.println("Problème dans l'insertion des données des Personnages.");
        }catch(PersonDataDoesntCorrectException personDataDoesntCorrectException){
            System.out.println("Les données inséres ne sont pas correcte.");
        }
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
        skillDBManager.removeTableSkill();
        skillDBManager.createTableSkill();

        try {
            skillDBManager.insertIntoTableSkill("SHIELD", "R", "Paladin", true, false, true);
        }catch(SkillAlreadyExistException skillAlreadyExistException){
            System.out.println(skillAlreadyExistException.getMessage());
        }
    }

}
