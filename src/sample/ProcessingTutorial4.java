package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * This example shows how to  fade colors.
 *
 * @author Treiber Julien
 */
public class ProcessingTutorial4 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        // width of the frame
        Integer width = 1000;
        assert width > 0 : "minimal width not provided, have \"" + width + "\"";
        // height of the frame
        Integer height = 400;
        assert height > 0: "minimal height not provided, have \"" + height + "\"";

        Scene scene = new Scene(root, width, height, Color.WHITE);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        // Choose the radius of our circles
        Integer radius = 100;
        // Center the drawing
        Integer circlesX = width/2;
        Integer circlesY = height/2;
        Integer offset = 5;
        // Always make sure that shapes intersects
        Circle red = new Circle(circlesX - (2*radius + offset), circlesY, radius, Color.RED);

        Circle blackRed = new Circle(circlesX, circlesY, radius, Color.DARKRED);
        Circle lightRed = new Circle(circlesX + (2*radius + offset), circlesY, radius, Color.web("red", 0.5));

        // ---- Add all configured Shapes to the Group!
        root.getChildren().addAll(red, blackRed, lightRed);
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
