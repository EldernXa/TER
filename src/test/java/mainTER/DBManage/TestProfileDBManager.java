package mainTER.DBManage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProfileDBManager {
    private final ProfileDBManager profileDBManager = new ProfileDBManager("testDB");
    private final String name = "Saken";
    private final int time = 6;


    @BeforeEach
    public void init(){
        profileDBManager.dropCascade();
    }

    @AfterEach
    public void afterTest(){
        profileDBManager.removeTableProfile();
    }

    @Test
    public void testCreateTablePerson(){
        try {
            profileDBManager.createTableProfile();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertDataIntoTablePerson(){
        try {
            profileDBManager.createTableProfile();
            profileDBManager.insertIntoTableProfile(name, time);
        }catch(Exception exception){
            fail();
        }
    }


    @Test
    public void testGettingTime(){
        try{
            insertValuesIntoProfile();
            assertEquals(time,profileDBManager.getTime(name));
        }catch (Exception e){
            fail();
        }
    }
    @Test
    public void testSettingTime(){
        try {

            insertValuesIntoProfile();
            profileDBManager.setTime(name,4);
            assertEquals(4,profileDBManager.getTime(name));
        }catch (Exception e){
            fail();
        }
    }


    private void insertValuesIntoProfile(){
        try{
            profileDBManager.createTableProfile();
            profileDBManager.insertIntoTableProfile(name,time);
        }catch(Exception sqlException){
            fail();
        }
    }
}
