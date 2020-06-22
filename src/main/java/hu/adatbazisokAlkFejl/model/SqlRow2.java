package hu.adatbazisokAlkFejl.model;

import hu.adatbazisokAlkFejl.util.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlRow2 {

    private static String selected_table_name;
    private static List<String> columnsName = new ArrayList<>();


    private static List<String> queryColumnsName = new ArrayList<>();


    private static StringProperty primaryKeyColumnName = new SimpleStringProperty();

    private List<String> values = new ArrayList<>();
    private List<StringProperty> valuesProperty = new ArrayList<>();

    public static String getSelected_table_name() {
        return selected_table_name;
    }

    public static void setSelected_table_name(String selected_table_name) {
        SqlRow2.selected_table_name = selected_table_name;
    }

    public static List<String> getQueryColumnsName() {
        return queryColumnsName;
    }

    public static void setQueryColumnsName(List<String> queryColumnsName) {
        SqlRow2.queryColumnsName = queryColumnsName;
    }

    public static List<String> getColumnsName() {
        return columnsName;
    }

    public static void setColumnsName(List<String> columnsName) {
        SqlRow2.columnsName = columnsName;
    }

    public static String getPrimaryKeyColumnName() {
        return primaryKeyColumnName.get();
    }

    public static void setPrimaryKeyColumnName(String primaryKeyColumnName) {
        SqlRow2.primaryKeyColumnName.set(primaryKeyColumnName);
    }

    public static StringProperty primaryKeyColumnNameProperty() {
        return primaryKeyColumnName;
    }

    public static void retrieveSetColumnsAndPrimaryKeyName(Statement st, String tableName) throws SQLException {
        primaryKeyColumnName.set(retrievePrimaryKeyName(st, tableName));

        if (primaryKeyColumnName.getValue() == null) {
            columnsName = null;
        } else {
            columnsName = retrieveColumnsName(st, tableName);
        }

    }

    private static List<String> retrieveColumnsName(Statement st, String tableName) throws SQLException {
        if (primaryKeyColumnName.getValue() == null) {
            return null;
        }

        ResultSet rs = st.executeQuery(Utils.SQL.GET_RECORDS_SQLIT(tableName));

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<String> columnsName = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            String name = rsmd.getColumnName(i);
            columnsName.add(name);
        }
        rs.close();
        return columnsName;
    }

    private static String retrievePrimaryKeyName(Statement st, String tableName) throws SQLException {
        // primaryKeyIndexList = new ArrayList<>();
        ResultSet rs = st.executeQuery("PRAGMA table_info('" + tableName + "');");
        String retrievePrimaryKeyName = "";
        int howMany = 0;
        while (rs.next()) {
            if (rs.getInt("pk") > 0) {
                retrievePrimaryKeyName = rs.getString("name");
                howMany++;
            }
        }
        rs.close();
        if (howMany > 1) {
            Utils.showDialog(Alert.AlertType.ERROR, "Két primary kulcs nem támogatott", "Két primary kulcsos tábla nem támogatott. Válasszon másik táblát!");
            return null;
        }
        return retrievePrimaryKeyName;
    }

    @Override
    public String toString() {
        return "SqlRow2{" +
                "values=" + values +
                ", valuesProperty=" + valuesProperty +
                '}';
    }

    public List<StringProperty> getValuesProperty() {
        return valuesProperty;
    }

    public void setValuesProperty(List<StringProperty> valuesProperty) {
        this.valuesProperty = valuesProperty;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }


}

