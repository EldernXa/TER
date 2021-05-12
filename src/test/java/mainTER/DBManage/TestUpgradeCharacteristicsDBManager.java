package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUpgradeCharacteristicsDBManager {

    private final UpgradeCharacteristicsDBManager upgradeCharacteristicsDBManager = new UpgradeCharacteristicsDBManager("testDB");

    private final String nameCharacter = "Paladin";

    private final String nameUpgrade = "speed";

    private final float newValue = 50;

    private final int price = 5;

    private final String description = "aaz";

    @BeforeEach
    public void initVarForTest(){
        upgradeCharacteristicsDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        upgradeCharacteristicsDBManager.deleteTableCharacteristics();
    }

    @Test
    public void testCreateTable(){
        try{
            upgradeCharacteristicsDBManager.createTableUpgradeCharacteristics();
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertData(){
        try{
            upgradeCharacteristicsDBManager.createTableUpgradeCharacteristics();
            upgradeCharacteristicsDBManager.insertIntoTableUpgradeCharacteristics(nameCharacter, nameUpgrade, newValue, price, description);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingNewValue(){
        try{
            insertData();
            assertEquals(newValue, upgradeCharacteristicsDBManager.getNewValue(nameCharacter, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingIsAlreadyLearned(){
        try{
            insertData();
            assertFalse(upgradeCharacteristicsDBManager.getIsAlreadyLearned(nameCharacter, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingIsAlreadyLearnedBeforeAndAfterSetting(){
        try{
            insertData();
            assertFalse(upgradeCharacteristicsDBManager.getIsAlreadyLearned(nameCharacter, nameUpgrade));
            upgradeCharacteristicsDBManager.setUpgradeDone(nameCharacter, nameUpgrade);
            assertTrue(upgradeCharacteristicsDBManager.getIsAlreadyLearned(nameCharacter, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingPrice(){
        try{
            insertData();
            assertEquals(price, upgradeCharacteristicsDBManager.getPrice(nameCharacter, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingDescription(){
        try{
            insertData();
            assertEquals(description, upgradeCharacteristicsDBManager.getDescription(nameCharacter, nameUpgrade));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingListUpgrade(){
        try{
            insertData();
            upgradeCharacteristicsDBManager.insertIntoTableUpgradeCharacteristics(nameCharacter, "weight", newValue, price, description);
            List<String> listUpgradeOfACharacter = upgradeCharacteristicsDBManager.getListUpgrade(nameCharacter);
            assertEquals(2, listUpgradeOfACharacter.size());
        }catch(Exception exception){
            fail();
        }
    }

    private void insertData(){
        upgradeCharacteristicsDBManager.createTableUpgradeCharacteristics();
        upgradeCharacteristicsDBManager.insertIntoTableUpgradeCharacteristics(nameCharacter, nameUpgrade, newValue, price, description);
    }

}
