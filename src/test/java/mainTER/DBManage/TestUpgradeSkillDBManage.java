package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUpgradeSkillDBManage {

    private final UpgradeSkillDBManager upgradeSkillDBManager = new UpgradeSkillDBManager("testDB");
    private final String nameCharacter = "Paladin";
    private final int numSkill = 1;
    private final String nameUpgrade = "cooldown";
    private final float newValue = 5;
    private final boolean isAlreadyDone = false;
    private final int price = 50;
    private final String description = "aa";

    @BeforeEach
    public void initBeforeTest(){
        upgradeSkillDBManager.dropCascade();
    }

    @AfterEach
    public void destructAfterTest(){
        upgradeSkillDBManager.removeTableUpgradeSkill();
    }

    @Test
    public void testCreateTable(){
        try{
            upgradeSkillDBManager.createTableUpgradeSkill();
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertingData(){
        try{
            upgradeSkillDBManager.createTableUpgradeSkill();
            upgradeSkillDBManager.insertIntoTableUpgradeSkill(nameCharacter, numSkill, nameUpgrade, newValue,
                    isAlreadyDone, price, description);
        }catch(Exception exception){
            fail();
        }
    }

}
