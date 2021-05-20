package mainTER.DBManage;

import mainTER.exception.ControlsDataNotCorrectException;
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
    private final String action = "f";



    @BeforeEach
    public void init(){
        controlsDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        controlsDBManager.removeTableControls();
    }

    @Test
    public void testCreateTablePerson(){
        try {
            controlsDBManager.createTableControls();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testVerifyTableNotExist(){
        try{
            assertFalse(controlsDBManager.verifyTableControlsExist());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testVerifyTableExist(){
        try{
            insertValuesIntoControls();
            assertTrue(controlsDBManager.verifyTableControlsExist());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertDataIntoTablePerson(){
        try {
            controlsDBManager.createTableControls();
            controlsDBManager.insertIntoTableControls(right,left,jump,switchUp,switchDown,action);
        }catch(Exception exception){
            fail();
        }

    }

    @Test
    public void testInsertIncorrectDataThrowException(){
        try{
            controlsDBManager.createTableControls();
            assertThrows(ControlsDataNotCorrectException.class, () -> controlsDBManager.insertIntoTableControls("", left, jump, switchUp, switchDown, action));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingRightFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(right, controlsDBManager.getRight());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testSetRightFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setRight("s");
            assertEquals("s",controlsDBManager.getRight());
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testGettingLeftFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(left, controlsDBManager.getLeft());
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    public void testSetLeftFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setLeft("f");
            assertEquals("f",controlsDBManager.getLeft());
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testGettingJumpFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(jump, controlsDBManager.getJump());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testSetJumpFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setJump("z");
            assertEquals("z",controlsDBManager.getJump());
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testGettingSwitchUpFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(switchUp, controlsDBManager.getSwitchUp());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testSetSwitchUpFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSwitchUp("h");
            assertEquals("h",controlsDBManager.getSwitchUp());
        }catch (Exception e){
            fail();
        }
    }
    @Test
    public void testGettingSwitchDownFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(switchDown, controlsDBManager.getSwitchDown());
        }catch(Exception exception){
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSetSwitchDownFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSwitchDown("t");
            assertEquals("t",controlsDBManager.getSwitchDown());
        }catch (Exception e){
            fail();
        }
    }
    @Test
    public void testGettingActionFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(action, controlsDBManager.getAction());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testSetActionFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setRight("f");
            assertEquals("f",controlsDBManager.getAction());
        }catch (Exception e){
            fail();
        }
    }


    private void insertValuesIntoControls(){
        try{
            controlsDBManager.createTableControls();
            controlsDBManager.insertIntoTableControls(right,left,jump,switchUp,switchDown,action);

            //System.out.println(controlsDBManager.toArray());
        }catch(Exception sqlException){
            sqlException.printStackTrace();
            fail();
        }
    }
}
