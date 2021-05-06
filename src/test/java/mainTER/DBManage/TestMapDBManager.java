package mainTER.DBManage;

import mainTER.Tools.Coordinate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMapDBManager {

    private final MapDBManager mapDBManager = new MapDBManager("testDB");
    private final String mapName = "Forest";
    private final String nameFirstCharacter = "Paladin";
    private final double coordinateX = 5.0;
    private final double coordinateY = 5.0;

    @BeforeEach
    public void beforeTest(){
        mapDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        mapDBManager.removeTableMap();
    }

    @Test
    public void testCreatingTable(){
        try{
            mapDBManager.createTableMap();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertingDataIntoMap(){
        try{
            mapDBManager.createTableMap();
            mapDBManager.insertIntoTableMap(mapName, nameFirstCharacter, coordinateX, coordinateY);
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingFirstNameCharacter(){
        try{
            insertDataForMap();
            assertEquals(nameFirstCharacter, mapDBManager.getFirstCharacter(mapName));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingInitialCoordinate(){
        try{
            insertDataForMap();
            Coordinate c = mapDBManager.getInitialCoordinate(mapName);
            assertEquals(coordinateX, c.getX());
            assertEquals(coordinateY, c.getY());
        }catch(Exception exception){
            exception.printStackTrace();
            fail();
        }
    }

    private void insertDataForMap(){
        mapDBManager.createTableMap();
        mapDBManager.insertIntoTableMap(mapName, nameFirstCharacter, coordinateX, coordinateY);
    }

}
