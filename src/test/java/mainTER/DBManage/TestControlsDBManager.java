package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestControlsDBManager {

    private final ControlsDBManager controlsDBManager = new ControlsDBManager("test.db");
    private final String nameCharacter = "Paladin";
    private final String right = "D";
    private final String left = "Q";
    private final String jump = " " ;
    private final String switchUp = "a";
    private final String switchDown = "e";
    private final String skill1 = "w";
    private final String skill2 = "x";
    private final String skill3 = "c";



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
            controlsDBManager.insertIntoTableControls(nameCharacter,right,left,jump,switchUp,switchDown,skill1,skill2,skill3);
        }catch(Exception exception){
            fail();
        }

    }

    @Test
    void testGettingRightFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(right, controlsDBManager.getRight(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    void testGettingLeftFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(left, controlsDBManager.getLeft(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    void testGettingJumpFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(jump, controlsDBManager.getJump(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    void testGettingSwitchUpFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(switchUp, controlsDBManager.getSwitchUp(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    void testGettingSwitchDownFromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(switchDown, controlsDBManager.getSwitchDown(nameCharacter));
        }catch(Exception exception){
            exception.printStackTrace();
            fail();
        }
    }
    @Test
    void testGettingSkill1FromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(skill1, controlsDBManager.getSkill1(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    void testGettingSkill2FromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(skill2, controlsDBManager.getSkill2(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    void testGettingSkill3FromTableControls(){
        try {
            insertValuesIntoControls();
            assertEquals(skill3, controlsDBManager.getSkill3(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }
    @Test
    void testGetListName(){
        insertValuesIntoControls();
        controlsDBManager.insertIntoTableControls("Serpent","d","q"," ","a","e","w","x","c");
        assertEquals(2, controlsDBManager.getListNameFromDatabase().size());
    }

    private void insertValuesIntoControls(){
        try{
            controlsDBManager.createTableControls();
            controlsDBManager.insertIntoTableControls(nameCharacter,right,left,jump,switchUp,switchDown,skill1,skill2,skill3);
        }catch(Exception sqlException){
            fail();
        }
    }
}
