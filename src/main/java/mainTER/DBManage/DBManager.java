package mainTER.DBManage;

import java.sql.*;

public class DBManager {

    private Connection connection = null;
    private final String nameDB;

    /**
     * Constructor who take the database name for the project.
     */
    public DBManager(){
        this("DatabasesProject");
    }

    /**
     * Constructor who take one database name for test principally (the file with this name must exist).
     * @param nameDB name for the database.
     */
    public DBManager(String nameDB){
        // TODO Create the file with the name nameDB.db if it doesn't exist anymore.
        this.nameDB = nameDB;
    }

    /**
     * Connect to the databases (which is a file in this case).
     * @throws SQLException when the file doesn't exist or if the user and/or the password is not good.
     */
    private void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:hsqldb:file:"+nameDB, "SA", "");
    }

    /**
     * Function who create a table or insert values into a table.
     * @param strCreateTable request for the databases (must be create table or select).
     */
    public void createTableOrInsert(String strCreateTable){
        try {
            this.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(strCreateTable);
            connection.commit();
            connection.close();
            connection = null;
        }catch(SQLException exception){
            System.out.println("Problème dans l'insertion ou la création.");
            exception.printStackTrace();
        }
    }

    /**
     * Function who drop a table.
     * @param strDropTable name of the table we want to delete.
     */
    public void dropTable(String strDropTable){
        try{
            this.getConnection();
            Statement statement = connection.createStatement();
            String strSql = "DROP TABLE " + strDropTable.toUpperCase();
            statement.executeUpdate("TRUNCATE TABLE " + strDropTable.toUpperCase());
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
        try{
            this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
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
        try {
            this.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP SCHEMA PUBLIC CASCADE");
            connection.commit();
            connection.close();
            connection = null;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

}
