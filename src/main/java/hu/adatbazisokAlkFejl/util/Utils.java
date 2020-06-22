package hu.adatbazisokAlkFejl.util;

import javafx.scene.control.Alert;

public class Utils {
    public final static String[] FILE_CHOOSER_EXTENSION_TEXT = {"SQLite files (*.db)", "*.db"};
    private final static String BASE_PATH = "/hu/adatbazisokAlkFejl/view/";
    public final static String MAIN_WINDOW_RELATIVE_PATH= BASE_PATH + "main_window.fxml";
    public final static String TEST_WINDOW= BASE_PATH + "testFelulet.fxml";
    public final static String EDIT_WINDOWS_PATH = BASE_PATH + "edit_column.fxml";
    public final static String EXPORT_WINDOW = BASE_PATH + "export_window.fxml";
    public final static String DEVELOPER_WINDOW = BASE_PATH + "developer_window.fxml";


    public final int test = 600;

    public static void showDialog(Alert.AlertType alertType, String title, String headerText){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.show();
    }

    public static class SQL{
        public final static String SQL_COLUMN_NAME_REGEX_PATTER = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        public final static String SQLITE_CONNECTION_PRE = "jdbc:sqlite:";
        public static final String SELECT_TABLES = "SELECT \n" +
                "    name\n" +
                "FROM \n" +
                "    sqlite_master \n" +
                "WHERE \n" +
                "    type ='table' AND \n" +
                "    name NOT LIKE 'sqlite_%';";
        public static final String COLUMN_NAMING_ERROR = "Nem megfelelő oszlop elnevezés, az oszlopnév csak alfanumerikus karakterekből állhat, és nem kezdődhet számokkal";

        public static String GET_RECORDS_SQLIT(String tableName){

            return "SELECT * FROM " + tableName + ";";
        }

        public static String EXPORT_SQL(String tableName){
            return "select sql from sqlite_master where name='" + tableName + "';";
        }
    }

    public static class FajlolvasasHiba {
        public static final Alert.AlertType ALERT_TYPE = Alert.AlertType.ERROR;
        public static final String TITLE = "Nem lett adatbázis kiválasztva";
        public static final String HEADER_TEXT = "Kérem a File->Open-re kattintva válasszon ki egy tetszőleges SQLite adatbázist";
    }

}
