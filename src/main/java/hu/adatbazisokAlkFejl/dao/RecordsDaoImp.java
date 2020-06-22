package hu.adatbazisokAlkFejl.dao;

import hu.adatbazisokAlkFejl.model.SqlRow2;
import hu.adatbazisokAlkFejl.util.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.io.File;
import java.sql.*;
import java.util.*;

public class RecordsDaoImp implements Record {

    private static File dbFilePath;

    public RecordsDaoImp(File file){
        dbFilePath = file;
    }

    public RecordsDaoImp(){  }

    public static File getDbFilePath() {
        return dbFilePath;
    }

    public void setDbFilePath(File dbFilePath) {
        RecordsDaoImp.dbFilePath = dbFilePath;
    }

    public List<String> getTables(){
        List<String> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Utils.SQL.SQLITE_CONNECTION_PRE + dbFilePath.getPath()); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(Utils.SQL.SELECT_TABLES);
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<SqlRow2> getRecords(String tableName) {
        List<SqlRow2> result = new ArrayList<>();
        SqlRow2.setSelected_table_name(tableName);

        try (Connection conn = DriverManager.getConnection(Utils.SQL.SQLITE_CONNECTION_PRE + dbFilePath.getPath());
            Statement st = conn.createStatement()) {

            SqlRow2.retrieveSetColumnsAndPrimaryKeyName(st,tableName);
            ResultSet rs = st.executeQuery(Utils.SQL.GET_RECORDS_SQLIT(tableName));
            while (rs.next()) {
                SqlRow2 sqlRow2 = new SqlRow2();

                for (int i = 0;i<rs.getMetaData().getColumnCount();i++){
                    String field = rs.getString(i+1);
                    sqlRow2.getValues().add(field);
                    StringProperty str = new SimpleStringProperty(field);
                    sqlRow2.getValuesProperty().add(str);
                }
                result.add(sqlRow2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean editColumn(String columnName,String newColumnName) {
        String query = "ALTER TABLE " + SqlRow2.getSelected_table_name()+" RENAME "+columnName +" TO "+ newColumnName + ";";
        try (Connection conn = DriverManager.getConnection(Utils.SQL.SQLITE_CONNECTION_PRE + dbFilePath.getPath());
             Statement st = conn.createStatement()) {

            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String exportCode(String tableName){
        if(dbFilePath == null){
            return null;
        }

        try (Connection conn = DriverManager.getConnection(Utils.SQL.SQLITE_CONNECTION_PRE + dbFilePath.getPath());
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(Utils.SQL.EXPORT_SQL(tableName));
            while (rs.next()){
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public List<SqlRow2> executeQuery(String query) {

        if(dbFilePath == null){
            return null;
        }
        List<SqlRow2> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Utils.SQL.SQLITE_CONNECTION_PRE + dbFilePath.getPath());
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<String> columnsName = new ArrayList<>();


            for (int i = 1; i <= columnCount; i++ ) {
                String name = rsmd.getColumnName(i);
                columnsName.add(name);
            }
            SqlRow2.getQueryColumnsName().clear();
            SqlRow2.getQueryColumnsName().addAll(columnsName);



            while (rs.next()) {
                SqlRow2 sqlRow2 = new SqlRow2();
                for (int i = 0;i<rs.getMetaData().getColumnCount();i++){
                    String field = rs.getString(i+1);
                    sqlRow2.getValues().add(field);
                    StringProperty str = new SimpleStringProperty(field);
                    sqlRow2.getValuesProperty().add(str);
                }
                result.add(sqlRow2);
            }
        } catch (SQLException e) {
            if(e.getErrorCode() == 101){
                Utils.showDialog(Alert.AlertType.INFORMATION,"Nincs eredmény","Nincs megjeleníthető eredmény a querynek");
                return null;
            }

            Utils.showDialog(Alert.AlertType.ERROR,"hiba",e.toString());
            return null;
            //e.printStackTrace();
        }
        return result;
    }

}

