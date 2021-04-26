package mainTER.DBManage;

import mainTER.exception.SkillDataGetException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestSkillDBManager {

    private final SkillDBManager skillDBManager = new SkillDBManager("testDB");
    private final String nameSkill1 = "SHIELD";
    private final int numSkill1 = 1;
    private final String ctrlKey1 = "R";
    private final String nameCharacter = "Paladin";
    private final boolean animateMvt = true;
    private final boolean animateAction = false;
    private final boolean isMode = true;
    private final String nameSkill2 = "Test";
    private final String ctrlKey2 = "F";

    @BeforeEach
    public void init(){
        skillDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        skillDBManager.removeTableSkill();
    }

    @Test
    void testCreateTableSkill(){
        try{
            skillDBManager.createTableSkill();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testInsertIntoSkill(){
        try {
            skillDBManager.createTableSkill();
            skillDBManager.insertIntoTableSkill(nameSkill1, ctrlKey1, nameCharacter, animateMvt, animateAction, isMode);
            assertTrue(true);
        }catch(Exception exception)
        {
            fail();
        }
    }

    @Test
    void testGetListNameSkill(){
        insertValueIntoSkill();
        try{
            skillDBManager.insertIntoTableSkill(nameSkill2, ctrlKey2, nameCharacter, animateMvt, animateAction, isMode);
            assertEquals(2, skillDBManager.getListSkillName().size());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGetListNameCharacter(){
        insertValueIntoSkill();
        try{
            skillDBManager.insertIntoTableSkill(nameSkill2, ctrlKey2, nameCharacter, animateMvt, animateAction, isMode);
            assertEquals(1, skillDBManager.getListNameCharacterWithSkill().size());
            assertEquals(nameCharacter, skillDBManager.getListNameCharacterWithSkill().get(0));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGetNumberOfSkillOfACharacter(){
        insertValueIntoSkill();
        try{
            skillDBManager.insertIntoTableSkill(nameSkill2, ctrlKey2, nameCharacter, animateMvt, animateAction, isMode);
            assertEquals(2, skillDBManager.getNumberSkillOfACharacter(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingNameSkill(){
        insertValueIntoSkill();
        try{
            assertEquals(nameSkill1, skillDBManager.getNameSkill(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingFalseNameSkillThrowException(){
        insertValueIntoSkill();
        assertThrows(SkillDataGetException.class, ()-> skillDBManager.getNameSkill(nameCharacter, 5));
    }

    @Test
    void testGettingCtrlKey(){
        insertValueIntoSkill();
        try{
            assertEquals(ctrlKey1, skillDBManager.getCtrlKey(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingFalseCtrlKeyThrowException(){
        insertValueIntoSkill();
        assertThrows(SkillDataGetException.class, ()-> skillDBManager.getCtrlKey(nameCharacter, 5));
    }

    @Test
    void testGettingAnimateMvt(){
        insertValueIntoSkill();
        try{
            assertEquals(animateMvt, skillDBManager.getAnimateMvt(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingFalseAnimateMvtThrowException(){
        insertValueIntoSkill();
        assertThrows(SkillDataGetException.class, ()-> skillDBManager.getAnimateMvt(nameCharacter, 5));
    }

    @Test
    void testGettingAnimateAction(){
        insertValueIntoSkill();
        try{
            assertEquals(animateAction, skillDBManager.getAnimateAction(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingFalseAnimateActionThrowException(){
        insertValueIntoSkill();
        assertThrows(SkillDataGetException.class, ()->skillDBManager.getAnimateAction(nameCharacter, 5));
    }

    @Test
    void testGettingIsMode(){
        insertValueIntoSkill();
        try{
            assertEquals(isMode, skillDBManager.getIsMode(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGettingFalseIsModeThrowException(){
        insertValueIntoSkill();
        assertThrows(SkillDataGetException.class, ()->skillDBManager.getIsMode(nameCharacter, 5));
    }

    private void insertValueIntoSkill(){
        skillDBManager.createTableSkill();
        skillDBManager.insertIntoTableSkill(nameSkill1, ctrlKey1, nameCharacter, animateMvt, animateAction, isMode);

    }

}
