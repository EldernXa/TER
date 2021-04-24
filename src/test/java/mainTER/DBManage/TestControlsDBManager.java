package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestControlsDBManager {

    private final ControlsDBManager controlsDBManager = new ControlsDBManager("test.db");
    private final String right = "D";
    private final String left = "Q";
    private final String jump = " " ;
    private final String switchUp = "a";
    private final String switchDown = "e";



    @BeforeEach
    public void init(){
        controlsDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        controlsDBManager.removeTableControls();
    }

    @Test
    void testCreateTablePerson(){
        try {
            controlsDBManager.createTableControls();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testInsertDataIntoTablePerson(){
        try {
            controlsDBManager.createTableControls();
            controlsDBManager.insertIntoTableControls(right,left,jump,switchUp,switchDown);
        }catch(Exception exception){
            fail();
        }

    }

    @Test
    void testGettingRightFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(right, controlsDBManager.getRight());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testSetRightFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setRight("s");
            assertEquals("s",controlsDBManager.getRight());
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void testGettingLeftFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(left, controlsDBManager.getLeft());
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    void testSetLeftFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setLeft("f");
            assertEquals("f",controlsDBManager.getLeft());
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void testGettingJumpFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(jump, controlsDBManager.getJump());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testSetJumpFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setJump("z");
            assertEquals("z",controlsDBManager.getJump());
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void testGettingSwitchUpFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(switchUp, controlsDBManager.getSwitchUp());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testSetSwitchUpFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSwitchUp("h");
            assertEquals("h",controlsDBManager.getSwitchUp());
        }catch (Exception e){
            fail();
        }
    }
    @Test
    void testGettingSwitchDownFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(switchDown, controlsDBManager.getSwitchDown());
        }catch(Exception exception){
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    void testSetSwitchDownFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSwitchDown("t");
            assertEquals("t",controlsDBManager.getSwitchDown());
        }catch (Exception e){
            fail();
        }
    }


    private void insertValuesIntoControls(){
        try{
            controlsDBManager.createTableControls();
            controlsDBManager.insertIntoTableControls(right,left,jump,switchUp,switchDown);

            System.out.println(controlsDBManager.toArray());
        }catch(Exception sqlException){
            sqlException.printStackTrace();
            fail();
        }
    }
}
