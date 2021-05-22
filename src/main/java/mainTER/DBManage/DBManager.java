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
    public Connection getco(){
        this.getConnection();
        return connection;
    }

    /**
     * Create a table generally.
     * @param nameTable the name of the table.
     * @param listName the list of the name of attribute the table must contains (will all be varchar type).
     * @param numPrimaryKey the number of primary key (the primary key must be first in listName.
     * @param listSize the list of size the different attribute will have.
     */
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
        createTableOrInsert(requestCreateTable.toString());
    }

    /**
     * Inserting values generally.
     * @param nameTable the name of the table.
     * @param listInsert a list who contains the values we must insert.
     */
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

    /**
     * get a list of values depending of different param.
     * @param nameTable the name of the table.
     * @param listNameLine a list containing all name of attributes who will be in the where clause.
     * @param listRealValueOfLine a list containing all values who must be equals to the attribute in the previous list.
     * @param nameRequest the attribute we want to get in the result list.
     * @return a list of values depending of different param.
     * @throws SQLException if we cannot get values from the databases.
     */
    public List<String> getList(String nameTable, List<String> listNameLine, List<Object> listRealValueOfLine, String nameRequest) throws SQLException{
        ArrayList<String> listRequested = new ArrayList<>();
        ResultSet resultSet = selectIntoTable(nameTable, listNameLine, listRealValueOfLine);
        while(resultSet.next()){
            if(!listRequested.contains(SecureManage.getDecrypted(resultSet.getString(nameRequest))))
                listRequested.add(SecureManage.getDecrypted(resultSet.getString(nameRequest)));
        }
        return listRequested;
    }

    /**
     * get only one data from the databases thanks to different param.
     * @param nameTable the name of the table.
     * @param listNameLine a list containing all name of attributes who will be in the where clause.
     * @param listRealValueOfLine a list containing all values who must be equals to the attribute in the previous list.
     * @param nameRequest the attribute we want to get for the result.
     * @return only one data from the databases thanks to different param.
     * @throws SQLException if we cannot get values from the databases.
     */
    public String getData(String nameTable, List<String> listNameLine, List<Object> listRealValueOfLine, String nameRequest) throws SQLException {
        ResultSet resultSet = selectIntoTable(nameTable, listNameLine, listRealValueOfLine);
        resultSet.next();
        return SecureManage.getDecrypted(resultSet.getString(nameRequest));
    }

    /**
     * get only one data from the databases thanks to different param.
     * @param nameTable the name of the table.
     * @param nameAtt the name of the attribute who will be in the where clause.
     * @param realValueOfAtt the values who must be equals to the attribute in the previous param.
     * @param nameRequest the attribute we want to get for the result.
     * @param useless a boolean who must be at true.
     * @return only one data from the databases.
     * @throws SQLException if we cannot get values from the databases.
     */
    public String getData(String nameTable, String nameAtt, Object realValueOfAtt, String nameRequest, boolean useless) throws SQLException{
        if(useless) {
            ResultSet resultSet = selectIntoTable(nameTable, nameAtt, realValueOfAtt);
            resultSet.next();
            return SecureManage.getDecrypted(resultSet.getString(nameRequest));
        }
        return null;
    }

    /**
     * Give a result for a select.
     * @param nameTable the name of the table.
     * @param listName a list containing all name of attributes who will be in the where clause.
     * @param listRequest a list containing all values who must be equals to the attribute in the previous list.
     * @return a result for a select.
     */
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
        resultSet = selectIntoTable(stringBuilder.toString());
        return resultSet;
    }

    /**
     * Give a result for a select.
     * @param nameTable the name of the table.
     * @param name the name of the one attribute who will be in the where clause.
     * @param request the values who must be equals to the previous attribute.
     * @return a result for a select.
     */
    private ResultSet selectIntoTable(String nameTable, String name, Object request){
        ResultSet resultSet;
        String stringBuilder = "SELECT * FROM " + nameTable +
                " WHERE " +
                name + " = '" + SecureManage.getEncrypted(String.valueOf(request)) + "'" +
                ";";
        resultSet = selectIntoTable(stringBuilder);
        return resultSet;
    }

    /**
     * update table generally.
     * @param tableName the name of the table.
     * @param listNameLine a list containing all name of attributes who will be in the where clause.
     * @param listRealValueOfLine a list containing all values who must be equals to the attribute in the previous list.
     * @param nameValToModify the name of the attribute we want to modify.
     * @param newVal the new value.
     */
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

    /**
     * Get a boolean from a resultSet.
     * @param resultSet resultSet who permit us to verify the value.
     * @param nameAttribute the name of the attribute we want to check.
     * @param valueAttribute the comparison value.
     * @return true if the value of nameAttribute is equals to valueAttribute, false otherwise.
     * @throws SQLException if we cannot get values from databases.
     */
    public boolean getFromResultSet(ResultSet resultSet, String nameAttribute, Object valueAttribute) throws SQLException{
        return resultSet.getString(nameAttribute).compareTo(SecureManage.getEncrypted(String.valueOf(valueAttribute)))==0;
    }

    /**
     * Get a string who is encrypted from a object.
     * @param valueObject the value we want to encrypt.
     * @return a string who is the values encrypted from a object.
     */
    public String getEncryptedFromObject(Object valueObject){
        return SecureManage.getEncrypted(String.valueOf(valueObject));
    }

    /**
     * Get a string who is the values decrypted from a string.
     * @param valueString the value we want to decrypt.
     * @return a string who is the values decrypted from a string.
     */
    public String getDecryptedFromString(String valueString){
        return SecureManage.getDecrypted(valueString);
    }

    /**
     * Update the table (not generally, we must insert the request by hand).
     * @param strCreateTable the request for update table.
     */
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

    /**
     * Give an array from one table (must have only one line inside).
     * @param nameTable  the name of the table.
     * @return an array from one table.
     * @throws SQLException if we cannot get values from the table (or the databases).
     */
    public List<String> toArray(String nameTable) throws SQLException{
        ArrayList<String> returnArray = new ArrayList<>();
        ResultSet resultSet = selectIntoTable("SELECT * FROM " + nameTable);
        resultSet.next();
        int columnCount = resultSet.getMetaData().getColumnCount();
        for(int i=0; i<columnCount; i++){
            returnArray.add(SecureManage.getDecrypted(resultSet.getString(i+1)));
        }
        return returnArray;
    }

}
