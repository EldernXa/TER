package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPointsUpgrade {

    private final PointsUpgradeDBManager pointsUpgradeDBManager = new PointsUpgradeDBManager("testDB");
    private final String mapName = "Frest";
    private final double x = 5;
    private final double y = 20;


    @BeforeEach
    public void init(){
        pointsUpgradeDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        pointsUpgradeDBManager.removeTablePointsUpgrade();
    }

    @Test
    public void testCreateTablePerson(){
        try {
            pointsUpgradeDBManager.createTablePointsUpgrade();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertDataIntoTablePerson(){
        try {
            pointsUpgradeDBManager.createTablePointsUpgrade();
            pointsUpgradeDBManager.insertIntoTablePointsUpgrade(x,y,mapName);
        }catch(Exception exception){
            fail();
        }

    }


    @Test
    public void testGettingIsTaken(){
        try {
           insertValuesIntoPointsUpgrade();
           assertFalse(pointsUpgradeDBManager.isTaken(x,y,mapName));
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testSetIsTaken(){
        try {
            insertValuesIntoPointsUpgrade();
            assertFalse(pointsUpgradeDBManager.isTaken(x,y,mapName));
            pointsUpgradeDBManager.setIsTaken(x,y,mapName,true);
            assertTrue(pointsUpgradeDBManager.isTaken(x,y,mapName));
        }catch (Exception e){
            fail();
        }
    }

    private void insertValuesIntoPointsUpgrade(){
        try{
            pointsUpgradeDBManager.createTablePointsUpgrade();
            pointsUpgradeDBManager.insertIntoTablePointsUpgrade(x,y,mapName);
        }catch(Exception sqlException){
            fail();
        }
    }


}
