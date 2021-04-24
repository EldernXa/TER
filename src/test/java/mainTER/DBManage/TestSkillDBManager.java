package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
            skillDBManager.insertIntoTableSkill("SHIELD", "Paladin", "R", true, false, true);
            assertTrue(true);
        }catch(Exception exception)
        {
            fail();
        }
    }

}
