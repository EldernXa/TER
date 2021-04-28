package mainTER.DBManage;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {

    private Connection connection = null;
    private final String nameDB;

    /**
     * Constructor who take the database name for the project.
     */
    public DBManager(){
        this("DatabasesProject", "main");
    }

    /**
     * Constructor who take one database name for test principally (the file with this name must exist).
     * @param nameDB name for the database.
     */
    public DBManager(String nameDB, String path){
        this.nameDB = "src/" + path + "/resources/" + nameDB;
    }

    /**
     * Connect to the databases (which is a file in this case).
     */
    private void getConnection() {
        System.setProperty("hsqldb.reconfig_logging", "false");
        Logger.getLogger("hsqldb.db").setLevel(Level.SEVERE);
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:file:" + nameDB, "SA", "");
        }catch(SQLException sqlException){
            System.out.println("Problème de connexion");
        }
    }

    /**
     * Function who create a table or insert values into a table.
     * @param strCreateTable request for the databases (must be create table or select).
     */
    public void createTableOrInsert(String strCreateTable){
        this.getConnection();
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(strCreateTable);
            connection.commit();
            connection.close();
            connection = null;
        }catch(SQLException exception){
            System.out.println("Problème dans l'insertion ou la création.");
            exception.printStackTrace();
        }
    }
    public void updateTable(String strCreateTable){
        this.getConnection();
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(strCreateTable);
            connection.commit();
            connection.close();
            connection = null;
        }catch(SQLException exception){
            System.out.println("Problème dans l'update");
            exception.printStackTrace();
        }
    }

    /**
     * Function who drop a table.
     * @param strDropTable name of the table we want to delete.
     */
    public void dropTable(String strDropTable){
        this.getConnection();
        try(Statement statement = connection.createStatement()){
            String strSql = String.format("DROP TABLE %s", strDropTable.toUpperCase());
            statement.executeUpdate(String.format("TRUNCATE TABLE %s", strDropTable.toUpperCase()));
            statement.executeUpdate(strSql);
            connection.commit();
            connection.close();
            connection = null;
        }catch(SQLException sqlException){
            System.out.println("Problème dans le delete de la table.");
            sqlException.printStackTrace();
        }
    }

    /**
     * Do a request for a select into the databases.
     * @param strSelect Request for a select in the table
     * @return a ResultSet who contains all result for the request.
     */
    public ResultSet selectIntoTable(String strSelect){
        this.getConnection();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet;
            resultSet = statement.executeQuery(strSelect);
            connection.close();
            connection=null;
            return resultSet;
        }catch(SQLException exception){
            System.out.println("Problème dans la sélection de données.");
        }
        return null;
    }

    /**
     * Drop all tables.
     */
    public void dropCascade(){
        this.getConnection();
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP SCHEMA PUBLIC CASCADE");
            connection.commit();
            connection.close();
            connection = null;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

}
