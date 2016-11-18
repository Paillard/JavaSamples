package samples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Box;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;

/**
 * This is a simple shape test.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 */
public class ShapeTests extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        // width of the frame
        Integer width = 600;
        assert width > 0 : "minimal width not provided, have \"" + width + "\"";
        // height of the frame
        Integer height = 600;
        assert height > 0: "minimal height not provided, have \"" + height + "\"";

        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        QuadCurve quadCurve = new QuadCurve(150.0, 150.0, 150.0, 250.0, 450.0, 475.0);

        Box b = new Box(15.0, 54.0, 30);

        root.getChildren().add(quadCurve);
        root.getChildren().add(b);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Run this function to start the application!
     * @param args Any arguments you think are necessary to
     *             run the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
