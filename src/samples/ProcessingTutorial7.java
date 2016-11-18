package samples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This example shows how you can draw
 * an Image into the scene.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 */
public class ProcessingTutorial7 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        Image image = new Image("resources/catIsTooAfraid.jpg");
        ImageView imageView = new ImageView(image);
        //imageView.setBlendMode(BlendMode.EXCLUSION);

        Image image2 = new Image("resources/googleBoobs.png", image.getWidth(), image.getHeight(), false, true, false);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setBlendMode(BlendMode.OVERLAY);

        Scene scene = new Scene(root, image.getWidth(), image.getHeight(), Color.WHITE);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        root.getChildren().addAll(imageView, imageView2);
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
