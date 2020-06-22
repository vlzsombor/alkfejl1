package hu.adatbazisokAlkFejl.view.controller;

import hu.adatbazisokAlkFejl.App;
import hu.adatbazisokAlkFejl.controller.RecordController;
import hu.adatbazisokAlkFejl.util.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TestWindowController implements Initializable {
    public static File file;

    @FXML
    private TableView<String> table;
    @FXML
    private TableColumn<String, String> tablesCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void refreshTable() throws SQLException {
        if(file != null){
            List<String> list = RecordController.getInstance().getTables();

            ObservableList<String> details = FXCollections.observableArrayList(list);

            tablesCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
            table.setItems(details);
        }else{
            Utils.showDialog(Utils.FajlolvasasHiba.ALERT_TYPE, Utils.FajlolvasasHiba.TITLE, Utils.FajlolvasasHiba.HEADER_TEXT);
        }

    }
    @FXML
    public void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(Utils.FILE_CHOOSER_EXTENSION_TEXT[0], Utils.FILE_CHOOSER_EXTENSION_TEXT[1]);
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(App.parentWindow);

    }
}
