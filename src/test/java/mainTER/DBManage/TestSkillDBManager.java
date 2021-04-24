package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestSkillDBManager {

    private final SkillDBManager skillDBManager = new SkillDBManager("testDB");

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
            skillDBManager.insertIntoTableSkill("SHIELD", 1, "R", "Paladin", true, false, true);
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
            skillDBManager.insertIntoTableSkill("Test", 2, "R", "Paladin", true, false, true);
            assertEquals(2, skillDBManager.getListSkillName().size());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGetListNameCharacter(){
        insertValueIntoSkill();
        try{
            skillDBManager.insertIntoTableSkill("Test", 2, "R", "Paladin", true, false, true);
            assertEquals(1, skillDBManager.getListNameCharacterWithSkill().size());
            assertEquals("Paladin", skillDBManager.getListNameCharacterWithSkill().get(0));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testGetNumberOfSkillOfACharacter(){
        insertValueIntoSkill();
        try{
            skillDBManager.insertIntoTableSkill("Test", 2, "R", "Paladin", true, false, true);
            assertEquals(2, skillDBManager.getNumberSkillOfACharacter("Paladin"));
        }catch(Exception exception){
            fail();
        }
    }

    private void insertValueIntoSkill(){
        skillDBManager.createTableSkill();
        skillDBManager.insertIntoTableSkill("SHIELD", 1, "R", "Paladin", true, false, true);

    }

}
