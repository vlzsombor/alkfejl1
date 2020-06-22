package hu.adatbazisokAlkFejl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {
    public static Stage parentWindow;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            parentWindow = stage;

            Parent root = FXMLLoader.load(getClass().getResource(hu.adatbazisokAlkFejl.util.Utils.MAIN_WINDOW_RELATIVE_PATH));

            //Parent root = FXMLLoader.load(getClass().getResource(hu.adatbazisokAlkFejl.util.Utils.TEST_WINDOW));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}