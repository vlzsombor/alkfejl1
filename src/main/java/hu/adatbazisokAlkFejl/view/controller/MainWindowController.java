package hu.adatbazisokAlkFejl.view.controller;

import hu.adatbazisokAlkFejl.App;
import hu.adatbazisokAlkFejl.controller.RecordController;
import hu.adatbazisokAlkFejl.model.SqlRow2;
import hu.adatbazisokAlkFejl.util.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public static File file;

    @FXML
    private AnchorPane content;
    @FXML
    private TableView<String> table;
    @FXML
    private TableColumn<String, String> tablesCol;
    @FXML
    private TableView<SqlRow2> ujTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableInitialize();
    }

    private void tableInitialize(){
        tablesCol.setCellFactory(tc -> {
            TableCell<String, String> cell = new TableCell<String, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    openRecords(cell.getText());
                    setTable();
                }
            });
            return cell ;
        });

    }

    @FXML
    public void refreshTable() throws SQLException {
        if(file != null){
            List<String> list = RecordController.getInstance().getTables();
            ObservableList<String> details = FXCollections.observableArrayList(list);
            tablesCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
            table.setItems(details);
            ujTable.getColumns().clear();
            ujTable.refresh();
        }else{
            Utils.showDialog(Utils.FajlolvasasHiba.ALERT_TYPE, Utils.FajlolvasasHiba.TITLE, Utils.FajlolvasasHiba.HEADER_TEXT);
        }
    }


    public void setTable(){
        List<TableColumn<SqlRow2,String>> colList = new ArrayList<>();
        var sqlRowList = SqlRow2.getColumnsName();

        if(sqlRowList== null || sqlRowList.size() == 0){
            ujTable.getColumns().clear();
            return;
        }
        int i =0;

        for (var str:SqlRow2.getColumnsName()) {

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
        ujTable.getColumns().setAll(colList);
    }

    private void openRecords(String tableName){
        List<SqlRow2> list = RecordController.getInstance().getRecord(tableName);
        ArrayList<String> strList = new ArrayList<>();
        for (var a : list) {
            strList.addAll(a.getValues());
        }
        ujTable.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    public void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(Utils.FILE_CHOOSER_EXTENSION_TEXT[0], Utils.FILE_CHOOSER_EXTENSION_TEXT[1]);
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(App.parentWindow);
        RecordController.getInstance().setFile(file);
    }

    @FXML
    public void editColumnHeader(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(Utils.EDIT_WINDOWS_PATH));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            ujTable.refresh();
            ujTable.getColumns().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void export(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(Utils.EXPORT_WINDOW));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void developer(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(Utils.DEVELOPER_WINDOW));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
