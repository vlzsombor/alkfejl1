package hu.adatbazisokAlkFejl.view.controller;

import com.jfoenix.controls.JFXTextArea;
import hu.adatbazisokAlkFejl.controller.RecordController;
import hu.adatbazisokAlkFejl.model.SqlRow2;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Developer implements Initializable{

    @FXML
    private JFXTextArea textArea;

    @FXML
    private TableView<SqlRow2> table;

    @FXML
    public void execute(){

        String query = textArea.getText();

        var list = RecordController.getInstance().executeQuery(query);

        if(list == null || list.isEmpty()){
            return;
        }

        List<TableColumn<SqlRow2,String>> colList = new ArrayList<>();
        var sqlRowList = SqlRow2.getColumnsName();
        int i =0;

        for (var str:SqlRow2.getQueryColumnsName()) {
            TableColumn<SqlRow2,String> column = new TableColumn(str);
            int finalI = i;
            column.setCellValueFactory(
                    new Callback<TableColumn.CellDataFeatures<SqlRow2, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SqlRow2, String> p) {

                            // p.getValue() returns the Person instance for a particular TableView row
                            return p.getValue().getValuesProperty().get(finalI);
                        }
                    }
            );
            colList.add(column);
            i++;
        }
        table.getColumns().setAll(colList);

        ArrayList<String> strList = new ArrayList<>();

        for (var a : list) {
            strList.addAll(a.getValues());
        }
        table.setItems(FXCollections.observableArrayList(list));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showError(SQLException e){
    }


}
