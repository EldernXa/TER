package mainTER.DBManage;

import org.junit.jupiter.api.*;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class TestDBManager {

    private final DBManager dbManager = new DBManager("testDB", "test");

    @BeforeEach
    public void init(){
        dbManager.dropCascade();
    }

    @Test
    void testCreateTable(){
        try {
            dbManager.createTableOrInsert("CREATE TABLE tutorials_tbl (" +
                    "id INT NOT NULL, title VARCHAR(50) NOT NULL," +
                    "author VARCHAR(20) NOT NULL, submission_date DATE, " +
                    "PRIMARY KEY (id));");
            dbManager.dropTable("tutorials_tbl");
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    void testInsertIntoTable(){
        try {
            dbManager.createTableOrInsert("CREATE TABLE tutorials_tbl (" +
                    "id INT NOT NULL, title VARCHAR(50) NOT NULL," +
                    "author VARCHAR(20) NOT NULL, submission_date DATE, " +
                    "PRIMARY KEY (id));");
            dbManager.createTableOrInsert("INSERT INTO tutorials_tbl" +
                    " VALUES (200,'Learn PHP', 'John Poul', NOW())");
            ResultSet rs = dbManager.selectIntoTable("SELECT id, title, author FROM tutorials_tbl");
            rs.next();
            assertFalse(rs.next());
            dbManager.dropTable("tutorials_tbl");
            assertTrue(true);
        }catch(Exception exception){
            dbManager.dropTable("tutorials_tbl");
            exception.printStackTrace();
            fail();
        }
    }

}
