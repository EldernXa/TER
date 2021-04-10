package mainTER.DBManage;

import java.sql.*;

public class DBManager {

    private Connection connection = null;
    private final String nameDB;

    public DBManager(){
        this("DatabasesProject");
    }

    public DBManager(String nameDB){
        this.nameDB = nameDB;
    }

    private void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:hsqldb:file:"+nameDB, "SA", "");
    }

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

    public void dropTable(String strDropTable){
        try{
            this.getConnection();
            Statement statement = connection.createStatement();
            String strSql = "DROP TABLE " + strDropTable.toUpperCase();
            statement.executeUpdate("TRUNCATE TABLE " + strDropTable.toUpperCase());
            statement.executeUpdate(strSql);
            connection.commit();
            System.out.println("Table " + strDropTable + " droppée.");
            connection.close();
            connection = null;
        }catch(SQLException sqlException){
            System.out.println("Problème dans le delete de la table.");
            sqlException.printStackTrace();
        }
    }

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
