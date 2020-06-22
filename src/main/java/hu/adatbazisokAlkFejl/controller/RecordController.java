package hu.adatbazisokAlkFejl.controller;

import hu.adatbazisokAlkFejl.dao.Record;
import hu.adatbazisokAlkFejl.dao.RecordsDaoImp;
import hu.adatbazisokAlkFejl.model.SqlRow2;

import java.io.File;
import java.util.List;

public class RecordController {

    private static RecordController single_instance = null;
    private Record dao;
    private File file;


    private RecordController() {
        dao = new RecordsDaoImp(file);
    }

    public static RecordController getInstance(){
        if(single_instance == null){
            single_instance = new RecordController();
        }
        return single_instance;
    }

    public void setFile(File file){
        single_instance.dao.setDbFilePath(file);
    }
    public List<SqlRow2> getRecord(String tableName) {
        return dao.getRecords(tableName);
    }
    public List<String> getTables() {
        return dao.getTables();
    }
    public boolean editColumn(String tableName,String newTableName){ return dao.editColumn(tableName,newTableName); }
    public String exportCode(String tableName){ return dao.exportCode(tableName);}
    public List<SqlRow2> executeQuery(String query){ return dao.executeQuery(query); }

}
