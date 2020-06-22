package hu.adatbazisokAlkFejl.view.controller;

import hu.adatbazisokAlkFejl.controller.RecordController;
import hu.adatbazisokAlkFejl.model.SqlRow2;
import hu.adatbazisokAlkFejl.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditColumn implements Initializable {

    @FXML
    private ListView<String> sqlColumns;

    @FXML
    private TextField screen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        editColumnName();

    }
    private void editColumnName(){
        ObservableList list = FXCollections.observableArrayList();
        list.clear();

        List<String> columnNameList = SqlRow2.getColumnsName();

        list.addAll(columnNameList);
        sqlColumns.getItems().addAll(list);
        screen.setOnAction(event -> {
                    if(!screen.getText().matches(Utils.SQL.SQL_COLUMN_NAME_REGEX_PATTER)){
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText(Utils.SQL.COLUMN_NAMING_ERROR);
                        errorAlert.showAndWait();
                    }else{
                        RecordController.getInstance().editColumn(sqlColumns.getSelectionModel().getSelectedItem(),screen.getText());
                        Stage stage = (Stage) sqlColumns.getScene().getWindow();
                        stage.close();
                    }
        });
    }

    @FXML
    public void displaySelected(javafx.scene.input.MouseEvent event) {
        String selected = sqlColumns.getSelectionModel().getSelectedItem();
        if(selected == null || selected.isEmpty()){
            screen.setText("nothing is selected");
        }else{
            screen.setText(selected);
        }
        screen.setEditable(true);
    }
}
