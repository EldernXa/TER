package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TestBestProfileDBManager {
    private final BestProfileDBManager bestProfileDBManager = new BestProfileDBManager("testDB");
    private final String name = "Saken";
    private final int time = 6;
    private final String mapName = "Faille";


    @BeforeEach
    public void init(){
        bestProfileDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        bestProfileDBManager.removeTableProfile();
    }

    @Test
    public void testCreateTablePerson(){
        try {
            bestProfileDBManager.createTableBestProfile();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertDataIntoTablePerson(){
        try {
            bestProfileDBManager.createTableBestProfile();
            bestProfileDBManager.insertIntoTableBestProfile(name, time,mapName);
        }catch(Exception exception){
            fail();
        }
    }


    @Test
    public void testGettingTime(){
        try{
            insertValuesIntoBestProfile();
            assertEquals(time, bestProfileDBManager.getTime(mapName));
        }catch (Exception e){
            fail();
        }
    }
    @Test
    public void testSettingTime(){
        try {

            insertValuesIntoBestProfile();
            bestProfileDBManager.setTime(4,mapName);
            assertEquals(4, bestProfileDBManager.getTime(mapName));
        }catch (Exception e){
            fail();
        }
    }
    @Test
    public void testGettingName(){
        try{
            insertValuesIntoBestProfile();
            assertEquals(name, bestProfileDBManager.getName(mapName));
        }catch (Exception e){
            fail();
        }
    }
    @Test
    public void testSettingName(){
        try {

            insertValuesIntoBestProfile();
            bestProfileDBManager.setName("adam",mapName);
            assertEquals("adam", bestProfileDBManager.getName(mapName));
        }catch (Exception e){
            fail();
        }
    }


    private void insertValuesIntoBestProfile(){
        try{
            bestProfileDBManager.createTableBestProfile();
            bestProfileDBManager.insertIntoTableBestProfile(name,time,mapName);
        }catch(Exception sqlException){
            fail();
        }
    }
}
