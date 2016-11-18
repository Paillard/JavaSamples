package samples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

/**
 * This example presents how to handle a mouse event with JavaFX.
 *
 * It draws a white line on an orange-red background, starting from
 * the left corner of the frame.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 *
 */
public class ProcessingTutorial1 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        // frame width
        Integer width = 400;
        assert width > 0 : "minimal width not provided, have \"" + width + "\"";
        // frame height
        Integer height = 400;
        assert height > 0: "minimal height not provided, have \"" + height + "\"";

        // The main scene into which we draw our stuff
        Scene scene = new Scene(root, width, height, Color.ORANGERED);
        primaryStage.setScene(scene);

        // Here we add a listener on the mouse moved event.
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, (MouseEvent mouseEvent) -> { // lambda are greats
            // draw a new line from left corner to the mouse position
            Line line = new Line(0, 0, mouseEvent.getX(), mouseEvent.getY());
            line.setStrokeType(StrokeType.OUTSIDE);
            // line color and transparency
            line.setStroke(Color.web("white", 0.90));
            /* remove all children to "redraw" the line
            ** and give the impression that the line is tracking
            ** the mouse
            */
            root.getChildren().clear();
            root.getChildren().add(line);
        });

        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

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
