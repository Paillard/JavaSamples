package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This example shows how to  fade colors.
 *
 * @author Treiber Julien
 */
public class ProcessingTutorial5 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        // width of the frame
        Integer width = 800;
        assert width > 0 : "minimal width not provided, have \"" + width + "\"";
        // height of the frame
        Integer height = 600;
        assert height > 0: "minimal height not provided, have \"" + height + "\"";

        Scene scene = new Scene(root, width, height, Color.BLACK);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        Integer rectHeight = 100;
        Integer offset = 50;
        Rectangle rectangle = new Rectangle(width, rectHeight, Color.RED);
        Rectangle rectangle1 = new Rectangle(0, rectHeight + offset, width, rectHeight);
        rectangle1.setFill(Color.web("red", 0.75));
        Rectangle rectangle2 = new Rectangle(0, 2*(rectHeight + offset), width, rectHeight);
        rectangle2.setFill(Color.web("red", 0.50));
        Rectangle rectangle3 = new Rectangle(0, 3*(rectHeight + offset), width, rectHeight);
        rectangle3.setFill(Color.web("red", 0.25));

        Rectangle halfLeft = new Rectangle(0, 0, width/2, height);
        halfLeft.setFill(Color.BLUE);

        // ---- Add all configured Shapes to the Group!
        root.getChildren().addAll(halfLeft, rectangle, rectangle1, rectangle2, rectangle3);
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
