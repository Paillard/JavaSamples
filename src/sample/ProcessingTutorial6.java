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
 * This example shows how you can draw
 * littles squares of different colors.
 * I call that a carpet of pixels.
 *
 * @author Treiber Julien
 */
public class ProcessingTutorial6 extends Application {

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

        Integer nbOfCells = 20; // the number of cells we want to prompt
        Float maxHeight = (float) (height / nbOfCells); // height of each cell
        Float maxWidth = (float) (width / nbOfCells); // width of each cell
        Color startColor = Color.BLACK; // start color factorized
        Color color = startColor; // the color that will change while increasing
        Integer iteCount = 0; // will allow to re-init color on a arbitrary value
        for (int y = 0; y < (nbOfCells); y++) {
            for (int x = 0; x < (nbOfCells); x++) {
                // add a new rectangle to the group of dots!
                Rectangle dot;
                dot = new Rectangle(maxWidth * x, maxHeight * y, maxWidth, maxHeight);
                dot.setFill(color);
                color = (iteCount == 10/* try to change it to 9 with initials settings ;) */) ? (startColor) : (color.brighter());
                iteCount = (color.equals(startColor)) ? (0) : (iteCount + 1);
                dots.getChildren().add(dot);
            }
        }
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
