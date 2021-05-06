package mainTER.DBManage;

import mainTER.Tools.Coordinate;
import mainTER.exception.MapCharacterNotExistException;
import mainTER.exception.MapDataGetException;
import mainTER.exception.PersonDataAlreadyExistException;
import mainTER.exception.PersonDataNotCorrectException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMapDBManager {

    private final MapDBManager mapDBManager = new MapDBManager("testDB");
    private final PersonDBManager personDBManager = new PersonDBManager("testDB");
    private final String mapName = "Forest";
    private final String nameFirstCharacter = "Paladin";
    private final double coordinateX = 5.0;
    private final double coordinateY = 5.0;

    @BeforeEach
    public void beforeTest(){
        mapDBManager.dropCascade();
        personDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        mapDBManager.removeTableMap();
        personDBManager.removeTablePerson();
    }

    @Test
    public void testCreatingTable(){
        try{
            personDBManager.createTablePerson();
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
            personDBManager.createTablePerson();
            personDBManager.insertIntoTablePerson("Paladin", 5, 5, 5, 5, true);
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
    public void testGettingFirstNameCharacterWithFalseNameMapThrowException(){
        try{
            insertDataForMap();
            assertThrows(MapDataGetException.class, ()->mapDBManager.getFirstCharacter("Castle"));
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

    @Test
    public void testGettingInitialCoordinateWithFalseNameMapThrowException(){
        try{
            insertDataForMap();
            assertThrows(MapDataGetException.class, ()->mapDBManager.getInitialCoordinate("Castle"));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertingMapWithFirstCharacterWhoDoesntExistThrowException(){
        try{
            insertDataForMap();
            assertThrows(MapCharacterNotExistException.class, ()->mapDBManager.insertIntoTableMap("Castle", "Sazer", 5, 5));
        }catch(Exception exception){
            fail();
        }
    }

    private void insertDataForMap() throws MapCharacterNotExistException, PersonDataAlreadyExistException, PersonDataNotCorrectException {
        mapDBManager.createTableMap();
        personDBManager.createTablePerson();
        personDBManager.insertIntoTablePerson("Paladin", 5, 5, 5, 5, true);
        mapDBManager.insertIntoTableMap(mapName, nameFirstCharacter, coordinateX, coordinateY);
    }

}