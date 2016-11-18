package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * This is a simple Hello Word Application,
 * using a controller and an FXML file for
 * interface description.
 */
public class Main extends Application {

    private void addExitOnEsc(Stage primaryStage) {
        // close windows on escape
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                Platform.exit();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXMLLoader can throw Exceptions.
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        addExitOnEsc(primaryStage);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
