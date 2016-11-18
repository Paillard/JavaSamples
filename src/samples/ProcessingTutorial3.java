package samples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * This example shows how colors
 * melt one with another from Shape intersections.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 */
public class ProcessingTutorial3 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        // width of the frame
        Integer width = 400;
        assert width > 0 : "minimal width not provided, have \"" + width + "\"";
        // height of the frame
        Integer height = 400;
        assert height > 0: "minimal height not provided, have \"" + height + "\"";

        Scene scene = new Scene(root, width, height, Color.BLACK);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        /*
        ** We draw three circles with the RGB components as initials colors
        ** and we tell to sum these colors when Shapes intersects.
         */
        // Choose the radius of our circles
        Integer radius = 120;
        // Center the drawing
        Integer circlesX = width/2;
        Integer circlesY = height/2;
        // Always make sure that shapes intersects
        Integer centerOffset = radius/2;
        Circle red = new Circle(circlesX, circlesY - centerOffset, radius, Color.RED);
        red.setBlendMode(BlendMode.ADD);
        Circle green = new Circle(circlesX - centerOffset, circlesY + centerOffset, radius, Color.YELLOWGREEN);
        green.setBlendMode(BlendMode.ADD);
        Circle blue = new Circle(circlesX + centerOffset, circlesY + centerOffset, radius, Color.BLUE);
        blue.setBlendMode(BlendMode.ADD);

        // ---- Add all configured Shapes to the Group!
        root.getChildren().addAll(red, green, blue);
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
