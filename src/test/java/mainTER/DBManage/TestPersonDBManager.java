package mainTER.DBManage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class TestPersonDBManager {

    private final PersonDBManager personDBManager = new PersonDBManager("testDB");

    @BeforeEach
    public void init(){
        personDBManager.dropCascade();
    }

    @Test
    void testCreateTablePerson(){
        try {
            personDBManager.createTablePerson();
            personDBManager.removeTablePerson();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testInsertDataIntoTablePerson(){
        try {
            personDBManager.createTablePerson();
            personDBManager.insertIntoTablePerson("Paladin", 5.0, 20.0, 5.0, true);
            personDBManager.removeTablePerson();
        }catch(Exception exception){
            fail();
        }

    }

    @Test
    void testGettingSpeedFromTablePerson(){
        try {
            personDBManager.createTablePerson();
            personDBManager.insertIntoTablePerson("Paladin", 5.0, 20.0, 5.0, true);
            personDBManager.toStringPerson("Paladin");
            assertEquals(5.0, personDBManager.getSpeed("Paladin"));
            personDBManager.removeTablePerson();
        }catch(Exception exception){
            exception.printStackTrace();
            fail();
        }
    }

    // TODO add more test


}
