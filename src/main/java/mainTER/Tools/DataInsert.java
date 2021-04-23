package mainTER.Tools;

import mainTER.DBManage.PersonDBManager;

public class DataInsert {

    private DataInsert(){

    }

    public static void insertPerson(){
        PersonDBManager personDBManager = new PersonDBManager();
        personDBManager.removeTablePerson();
        personDBManager.createTablePerson();
        personDBManager.insertIntoTablePerson("Paladin", 5, 20, 5, 5, true);
        personDBManager.insertIntoTablePerson("Demon", 10, 5, 8, 2, true);
        personDBManager.insertIntoTablePerson("Serpent", 8, 10, 0, 3, false);
        personDBManager.insertIntoTablePerson("HommeDragon", 10, 5, 8, 2, true);
    }

}
