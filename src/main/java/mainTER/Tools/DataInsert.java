package mainTER.Tools;

import mainTER.DBManage.PersonDBManager;
import mainTER.exception.PersonDataAlreadyExistException;

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
        }
    }

}
