package hu.adatbazisokAlkFejl.dao;

import hu.adatbazisokAlkFejl.model.SqlRow2;

import java.io.File;
import java.util.List;

public interface Record {

    public List<String> getTables();

    public List<SqlRow2> getRecords(String tableName);

    public boolean editColumn(String columnName,String newColumnName);

    public void setDbFilePath(File dbFilePath);
    public String exportCode(String tableName);
    public List<SqlRow2> executeQuery(String query);
}