package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPersonDBManager {

    private final PersonDBManager personDBManager = new PersonDBManager("testDB");
    private final String nameCharacter = "Paladin";
    private final double speed = 5;
    private final double weight = 20;
    private final double jumpStrength = 5;
    private final double fallingSpeed = 3;
    private final boolean canJump = true;

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
            personDBManager.insertIntoTablePerson(nameCharacter, speed, weight, jumpStrength, fallingSpeed, canJump);
        }catch(Exception exception){
            fail();
        }

    }

    @Test
    void testGettingSpeedFromTablePerson(){
        try {
            insertValuesIntoPerson();
            assertEquals(speed, personDBManager.getSpeed(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingWeightFromTablePerson(){
        try{
            insertValuesIntoPerson();
            assertEquals(weight, personDBManager.getWeight(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingJumpStrengthFromTablePerson(){
        try{
            insertValuesIntoPerson();
            assertEquals(jumpStrength, personDBManager.getJumpStrength(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingFallingSpeed(){
        try{
            insertValuesIntoPerson();
            assertEquals(fallingSpeed, personDBManager.getFallingSpeed(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingCanJump(){
        try{
            insertValuesIntoPerson();
            assertEquals(canJump, personDBManager.getCanJump(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }


    @Test
    void testGetListName(){
        insertValuesIntoPerson();
        try {
            personDBManager.insertIntoTablePerson("Serpent", 5, 6, 4, 3, false);
            assertEquals(2, personDBManager.getListNameFromDatabase().size());
        }catch(Exception exception){
            fail();
        }
    }

    private void insertValuesIntoPerson(){
        try{
            personDBManager.createTablePerson();
            personDBManager.insertIntoTablePerson(nameCharacter, speed, weight, jumpStrength, fallingSpeed, canJump);
        }catch(Exception sqlException){
            fail();
        }
    }

}
