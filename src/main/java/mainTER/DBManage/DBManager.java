package mainTER.DBManage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public void createTable(String nameTable, List<String> listName, int numPrimaryKey, List<Integer> listSize){
        StringBuilder requestCreateTable = new StringBuilder("CREATE TABLE IF NOT EXISTS " + nameTable + " ( \n");
        for(int i = 0; i<listName.size();i++){
            requestCreateTable.append(listName.get(i)).append(" VARCHAR(").append(listSize.get(i)).append(")");
            if(numPrimaryKey == 1 && i == 0){
                requestCreateTable.append(" PRIMARY KEY ");
            }
            if(i<listName.size()-1 || numPrimaryKey>1)
                requestCreateTable.append(", ");
            requestCreateTable.append("\n");
        }
        if(numPrimaryKey > 1){
            requestCreateTable.append("CONSTRAINT PK_").append(nameTable).append(" PRIMARY KEY (");
            for(int i=0;i<numPrimaryKey;i++){
                requestCreateTable.append(listName.get(i));
                if(i<numPrimaryKey-1)
                    requestCreateTable.append(",");
            }
            requestCreateTable.append(")");
        }
        requestCreateTable.append(");");
        //System.out.println(requestCreateTable);
        createTableOrInsert(requestCreateTable.toString());
    }

    public void insertIntoTable(String nameTable, List<Object> listInsert){
        StringBuilder stringBuilderInsert = new StringBuilder("INSERT INTO ");
        stringBuilderInsert.append(nameTable).append(" VALUES (");
        for (int i = 0; i<listInsert.size(); i++){
            try{
                stringBuilderInsert.append("'").append(SecureManage.getEncrypted(String.valueOf(listInsert.get(i)))).append("'");
                if(i<listInsert.size()-1)
                    stringBuilderInsert.append(",");
            }catch(Exception exception){
                System.out.println("Problème.");
            }
        }
        stringBuilderInsert.append(")");
        createTableOrInsert(stringBuilderInsert.toString());
    }

    public List<String> getList(String nameTable, List<String> listNameLine, List<Object> listRealValueOfLine, String nameRequest) throws SQLException{
        ArrayList<String> listRequested = new ArrayList<>();
        ResultSet resultSet = selectIntoTable(nameTable, listNameLine, listRealValueOfLine);
        while(resultSet.next()){
            if(!listRequested.contains(SecureManage.getDecrypted(resultSet.getString(nameRequest))))
                listRequested.add(SecureManage.getDecrypted(resultSet.getString(nameRequest)));
        }
        return listRequested;
    }

    public String getData(String nameTable, List<String> listNameLine, List<Object> listRealValueOfLine, String nameRequest) throws SQLException {
        ResultSet resultSet = selectIntoTable(nameTable, listNameLine, listRealValueOfLine);
        resultSet.next();
        return SecureManage.getDecrypted(resultSet.getString(nameRequest));
    }

    private ResultSet selectIntoTable(String nameTable, List<String> listName, List<Object> listRequest){
        ResultSet resultSet;
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ");
        stringBuilder.append(nameTable);
        if(listName!=null) {
            stringBuilder.append(" WHERE ");
            for (int i = 0; i < listName.size(); i++) {
                stringBuilder.append(listName.get(i)).append(" = '").append(SecureManage.getEncrypted(String.valueOf(listRequest.get(i)))).append("'");
                if (i < listName.size() - 1) {
                    stringBuilder.append(" AND ");
                }
            }
        }
        stringBuilder.append(";");
        //System.out.println(stringBuilder);
        resultSet = selectIntoTable(stringBuilder.toString());
        return resultSet;
    }

    public void updateTable(String tableName, List<String> listNameLine, List<Object> listRealValueOfLine, String nameValToModify, Object newVal){
        StringBuilder stringBuilderToUpdate = new StringBuilder("Update ");
        stringBuilderToUpdate.append(tableName).append(" \nSET ");
        stringBuilderToUpdate.append(nameValToModify).append(" = '").append(SecureManage.getEncrypted(String.valueOf(newVal))).append("'");
        if(listNameLine != null && !listNameLine.isEmpty()){
            stringBuilderToUpdate.append(" WHERE ");
            for(int i=0; i<listNameLine.size();i++){
                stringBuilderToUpdate.append(listNameLine.get(i)).append(" = '").append(SecureManage.getEncrypted(String.valueOf(listRealValueOfLine.get(i)))).append("'");
                if(i<listNameLine.size()-1){
                    stringBuilderToUpdate.append(" AND ");
                }
            }
        }
        updateTable(stringBuilderToUpdate.toString());
    }

    public boolean getFromResultSet(ResultSet resultSet, String nameAttribute, Object valueAttribute) throws SQLException{
        return resultSet.getString(nameAttribute).compareTo(SecureManage.getEncrypted(String.valueOf(valueAttribute)))==0;
    }

    public String getEncryptedFromObject(Object valueObject){
        return SecureManage.getEncrypted(String.valueOf(valueObject));
    }

    public String getDecryptedFromString(String valueString){
        return SecureManage.getDecrypted(valueString);
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
        }catch(SQLException ignored){

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
        }catch(SQLException ignored){

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
