package samples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Use your mouse to draw!
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien.
 */
public class MouseDrawing extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        Scene scene = new Scene(root, 1024, 728, Color.BLACK);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        scene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> root.getChildren().add(new Circle(event.getX(), event.getY(), 10, Color.ANTIQUEWHITE)));

        primaryStage.show();
    }

    static public void  main(String[] args) {
        launch(args);
    }
}
