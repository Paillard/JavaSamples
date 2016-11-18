package samples;

import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/**
 * This example shows how you can animate
 * an infinite transition of the fill color of a Shape
 * from one Color to another.
 *
 * Seems like if there is a lot of transitions,
 * system slow down.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 */
public class InfiniteAnimatedPixelCarpet extends Application {

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

        Group dots = new Group();
        root.getChildren().add(dots);

        Random random = new Random(System.nanoTime());
        Integer nbOfCells = 100;
        Float maxHeight = (float) (height / nbOfCells);
        Float maxWidth = (float) (width / nbOfCells);
        for (int y = 0; y < (nbOfCells); y++) {
            for (int x = 0; x < (nbOfCells); x++) {
                Rectangle dot;
                dot = new Rectangle(maxWidth * x, maxHeight * y, maxWidth, maxHeight);
                FillTransition ft = new FillTransition(Duration.millis(random.nextInt(1000) + 500), dot, Color.RED, Color.GREEN);
                ft.setOnFinished((ActionEvent e) -> {
                    ft.setCycleCount(random.nextInt(10) + 1);
                    ft.play();
                });
                ft.setCycleCount(random.nextInt(10) + 1);
                ft.setAutoReverse(true);
                ft.play();
                dots.getChildren().add(dot);
            }
        }
        root.setEffect(new BoxBlur());

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
