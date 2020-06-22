package hu.adatbazisokAlkFejl.view.controller;

import com.jfoenix.controls.JFXTextArea;
import hu.adatbazisokAlkFejl.controller.RecordController;
import hu.adatbazisokAlkFejl.model.SqlRow2;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ExportController implements Initializable {
    @FXML
    private JFXTextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String text = RecordController.getInstance().exportCode(SqlRow2.getSelected_table_name());

        textArea.setText(text);
    }
}
