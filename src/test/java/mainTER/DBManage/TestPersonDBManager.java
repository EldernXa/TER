package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPersonDBManager {

    private final PersonDBManager personDBManager = new PersonDBManager("testDB");

    @BeforeEach
    public void init(){
        personDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        personDBManager.removeTablePerson();
    }

    @Test
    void testCreateTablePerson(){
        try {
            personDBManager.createTablePerson();
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
        }catch(Exception exception){
            fail();
        }

    }

    @Test
    void testGettingSpeedFromTablePerson(){
        try {
            insertValuesIntoPerson();
            assertEquals(5, personDBManager.getSpeed("Paladin"));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingWeightFromTablePerson(){
        try{
            insertValuesIntoPerson();
            assertEquals(2, personDBManager.getWeight("Paladin"));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingJumpStrengthFromTablePerson(){
        try{
            insertValuesIntoPerson();
            assertEquals(5, personDBManager.getJumpStrength("Paladin"));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingCanJump(){
        try{
            insertValuesIntoPerson();
            assertTrue(personDBManager.getCanJump("Paladin"));
        }catch(Exception exception){
            fail();
        }
    }

    private void insertValuesIntoPerson(){
        try{
            personDBManager.createTablePerson();
            personDBManager.insertIntoTablePerson("Paladin", 5.0, 2.0, 5.0, true);
        }catch(Exception sqlException){
            fail();
        }
    }

}
