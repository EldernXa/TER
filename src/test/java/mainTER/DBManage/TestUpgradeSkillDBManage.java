package mainTER.DBManage;

import mainTER.exception.UpgradeSkillDataGetException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestUpgradeSkillDBManage {

    private final UpgradeSkillDBManager upgradeSkillDBManager = new UpgradeSkillDBManager("testDB");
    private final String nameCharacter = "Paladin";
    private final int numSkill = 1;
    private final String nameUpgrade = "cooldown";
    private final float newValue = 5;
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
                    price, description);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingNewValue(){
        try{
            insertDataForUpgradeSkill();
            assertEquals(newValue, upgradeSkillDBManager.getNewValue(nameCharacter, numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingNewValueFromFakeDataThrowException(){
        try{
            insertDataForUpgradeSkill();
            assertThrows(UpgradeSkillDataGetException.class, ()->
                    upgradeSkillDBManager.getNewValue("aazze", numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingIsAlreadyDone(){
        try{
            insertDataForUpgradeSkill();
            assertFalse(upgradeSkillDBManager.getIsAlreadyDone(nameCharacter, numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingIsAlreadyDoneFromFakeDataThrowException(){
        try{
            insertDataForUpgradeSkill();
            assertThrows(UpgradeSkillDataGetException.class, ()->
                    upgradeSkillDBManager.getIsAlreadyDone("rraz", numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingIsAlreadyDoneBeforeAndAfterSetting(){
        try{
            insertDataForUpgradeSkill();
            assertFalse(upgradeSkillDBManager.getIsAlreadyDone(nameCharacter, numSkill, nameUpgrade));
            upgradeSkillDBManager.setUpgradeDone(nameCharacter, numSkill, nameUpgrade);
            assertTrue(upgradeSkillDBManager.getIsAlreadyDone(nameCharacter, numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingPrice(){
        try{
            insertDataForUpgradeSkill();
            assertEquals(price, upgradeSkillDBManager.getPrice(nameCharacter, numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingPriceFromFakeDataThrowException(){
        try{
            insertDataForUpgradeSkill();
            assertThrows(UpgradeSkillDataGetException.class, ()->
                    upgradeSkillDBManager.getPrice("rref", numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingDescription(){
        try{
            insertDataForUpgradeSkill();
            assertEquals(description, upgradeSkillDBManager.getDescription(nameCharacter, numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingDescriptionFromFakeDataThrowException(){
        try{
            insertDataForUpgradeSkill();
            assertThrows(UpgradeSkillDataGetException.class, ()->
                    upgradeSkillDBManager.getDescription("rokge", numSkill, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingListUpgradeSkillOfACharacter(){
        try{
            insertDataForUpgradeSkill();
            upgradeSkillDBManager.insertIntoTableUpgradeSkill(nameCharacter, 2, nameUpgrade, newValue,
                    price, description);
            Map<Integer, String> listUpgradeSkill = upgradeSkillDBManager.getListUpgradeSkillOfACharacter(nameCharacter);
            assertEquals(2, listUpgradeSkill.size());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingListUpgradeSkillOfACharacterFromFakeDataThrowException(){
        try{
            insertDataForUpgradeSkill();
            upgradeSkillDBManager.insertIntoTableUpgradeSkill(nameCharacter, 2, nameUpgrade, newValue,
                    price, description);
            assertThrows(UpgradeSkillDataGetException.class, ()->
                    upgradeSkillDBManager.getListUpgradeSkillOfACharacter("rrzed"));

        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingListUpgradeOfASkill(){
        try{
            insertDataForUpgradeSkill();
            upgradeSkillDBManager.insertIntoTableUpgradeSkill(nameCharacter, 1, "time", newValue,
                    price, description);
            List<String> listString = upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(nameCharacter, 1);
            assertEquals(2, listString.size());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingListUpgradeOfASkillFromFakeDataThrowException(){
        try{
            insertDataForUpgradeSkill();
            upgradeSkillDBManager.insertIntoTableUpgradeSkill(nameCharacter, 1, "time", newValue,
                    price, description);
            assertThrows(UpgradeSkillDataGetException.class, ()->
                    upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter("azred", 1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingInsideOfAListUpgradeOfASkill(){
        try{
            insertDataForUpgradeSkill();
            upgradeSkillDBManager.insertIntoTableUpgradeSkill(nameCharacter, 1, "time", newValue,
                    price, description);
            List<String> listString = upgradeSkillDBManager.getListUpgradeOfASkillOfACharacter(nameCharacter, 1);
            assertTrue(listString.contains(nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }


    private void insertDataForUpgradeSkill(){
        try{
            upgradeSkillDBManager.createTableUpgradeSkill();
            upgradeSkillDBManager.insertIntoTableUpgradeSkill(nameCharacter, numSkill, nameUpgrade, newValue,
                    price, description);
        }catch(Exception exception){
            fail();
        }
    }

}
