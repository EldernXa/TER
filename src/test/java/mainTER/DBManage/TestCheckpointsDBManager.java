package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCheckpointsDBManager {

    private final CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager("testDB");
    private final double valueX = 1.0;
    private final double valueY = 2.0;
    private final String valueNameCharacter = "Paladin";
    private final String valueNameMap = "Forest";

    @BeforeEach
    public void beforeTest(){
        checkpointsDBManager.dropCascade();
        checkpointsDBManager.createTableCheckPoints();
    }

    @AfterEach
    public void afterTest(){
        checkpointsDBManager.removeTableCheckPoints();
    }

    @Test
    public void testInsertDataIntoTableCheckpoints(){
        try{
            checkpointsDBManager.insertIntoTableControls(valueX, valueY, valueNameCharacter, valueNameMap);
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

    private void insertData(){
        checkpointsDBManager.insertIntoTableControls(valueX, valueY, valueNameCharacter, valueNameMap);
    }



}
