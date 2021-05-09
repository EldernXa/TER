package mainTER.DBManage;

import mainTER.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCheckpointsDBManager {

    private final CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager("testDB");
    private final PersonDBManager personDBManager = new PersonDBManager("testDB");
    private final double valueX = 1.0;
    private final double valueY = 2.0;
    private final String valueNameCharacter = "Paladin";
    private final String valueNameMap = "Forest";

    @BeforeEach
    public void beforeTest(){
        checkpointsDBManager.dropCascade();
        checkpointsDBManager.createTableCheckPoints();
        personDBManager.createTablePerson();
    }

    @AfterEach
    public void afterTest(){
        checkpointsDBManager.removeTableCheckPoints();
        personDBManager.removeTablePerson();
    }

    @Test
    public void testInsertDataIntoTableCheckpoints(){
        try{
            personDBManager.insertIntoTablePerson(valueNameCharacter, 5.0, 5.0, 5.0, 5.0, true);
            checkpointsDBManager.insertIntoTableCheckpoints(valueX, valueY, valueNameCharacter, valueNameMap);
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingX(){
        try{
            insertData();
            assertEquals(valueX, checkpointsDBManager.getX());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingXBeforeAndAfterSetting(){
        try{
            insertData();
            assertEquals(valueX, checkpointsDBManager.getX());
            checkpointsDBManager.setX(valueX+5);
            assertEquals(valueX+5, checkpointsDBManager.getX());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingY(){
        try{
            insertData();
            assertEquals(valueY, checkpointsDBManager.getY());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingYBeforeAndAfterSetting(){
        try{
            insertData();
            assertEquals(valueY, checkpointsDBManager.getY());
            checkpointsDBManager.setY(valueY+5);
            assertEquals(valueY+5, checkpointsDBManager.getY());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingNameCharacter(){
        try{
            insertData();
            assertEquals(valueNameCharacter, checkpointsDBManager.getCharacterName());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingNameCharacterBeforeAndAfterSetting(){
        try{
            insertData();
            assertEquals(valueNameCharacter, checkpointsDBManager.getCharacterName());
            personDBManager.insertIntoTablePerson("Serpent", 5.0, 5.0, 5.0, 5.0, true);
            checkpointsDBManager.setCharacterName("Serpent");
            assertEquals("Serpent", checkpointsDBManager.getCharacterName());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingNameMap(){
        try{
            insertData();
            assertEquals(valueNameMap, checkpointsDBManager.getMapName());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingNameMapBeforeAndAfterSetting(){
        try{
            insertData();
            assertEquals(valueNameMap, checkpointsDBManager.getMapName());
            checkpointsDBManager.setMapName("Volcan");
            assertEquals("Volcan", checkpointsDBManager.getMapName());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertingDataTwiceThrowException(){
        try{
            insertData();
            assertThrows(CheckpointsDataAlreadyExistException.class, ()->checkpointsDBManager.insertIntoTableCheckpoints(valueX, valueY, valueNameCharacter, valueNameMap));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertIncorrectDataThrowException(){
        assertThrows(CheckpointsDataNotCorrectException.class, ()->
                    checkpointsDBManager.insertIntoTableCheckpoints(0.0, 0.0, "", ""));
    }

    @Test
    public void testInsertCharacterWhoDoesntExistThrowException(){
        assertThrows(CheckpointsCharacterDoesntExistException.class,
                    ()->checkpointsDBManager.insertIntoTableCheckpoints(0, 0, "errze", "aa"));
    }

    @Test
    public void testSetCharacterWhoDoesntExistThrowException(){
        try{
            insertData();
            assertThrows(CheckpointsCharacterDoesntExistException.class,
                    ()->checkpointsDBManager.setCharacterName("aaaz"));
        }catch(Exception exception){
            fail();
        }
    }

    private void insertData() throws CheckpointsDataNotCorrectException, CheckpointsDataAlreadyExistException, CheckpointsCharacterDoesntExistException, PersonDataAlreadyExistException, PersonDataNotCorrectException {
        personDBManager.insertIntoTablePerson(valueNameCharacter, 5.0, 5.0, 5.0, 5.0, true);
        checkpointsDBManager.insertIntoTableCheckpoints(valueX, valueY, valueNameCharacter, valueNameMap);
    }



}
