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
    void testSetRightFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setRight("Paladin","s");
            assertEquals("s",controlsDBManager.getRight("Paladin"));
        }catch (Exception e){
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
    void testSetLeftFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setLeft("Paladin","f");
            assertEquals("f",controlsDBManager.getLeft("Paladin"));
        }catch (Exception e){
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
    void testSetJumpFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setJump("Paladin","z");
            assertEquals("z",controlsDBManager.getJump("Paladin"));
        }catch (Exception e){
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
    void testSetSwitchUpFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSwitchUp("Paladin","h");
            assertEquals("h",controlsDBManager.getSwitchUp("Paladin"));
        }catch (Exception e){
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
    void testSetSwitchDownFromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSwitchDown("Paladin","t");
            assertEquals("t",controlsDBManager.getSwitchDown("Paladin"));
        }catch (Exception e){
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
    void testSetSkill1FromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSkill1("Paladin","j");
            assertEquals("j",controlsDBManager.getSkill1("Paladin"));
        }catch (Exception e){
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
    void testSetSkill2FromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSkill2("Paladin","p");
            assertEquals("p",controlsDBManager.getSkill2("Paladin"));
        }catch (Exception e){
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
    void testSetSkill3FromTableControls(){
        try {
            insertValuesIntoControls();
            controlsDBManager.setSkill3("Paladin","n");
            assertEquals("n",controlsDBManager.getSkill3("Paladin"));
        }catch (Exception e){
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

            System.out.println(controlsDBManager.toArray(nameCharacter));
        }catch(Exception sqlException){
            sqlException.printStackTrace();
            fail();
        }
    }
}
