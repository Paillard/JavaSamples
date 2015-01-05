package sample;

import com.sun.javafx.geom.Vec2d;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tools.Particle;
import tools.ParticleSystem;

import java.util.Random;

/**
 * This example shows how you can draw
 * an Image into the scene.
 *
 * @author Treiber Julien
 */
public class ParticlesFun extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        double maxX = 1024, maxY = 728;
        Scene scene = new Scene(root, maxX, maxY, Color.BLACK);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        Random random = new Random(System.nanoTime());
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED,
                event -> new Particle(0.1, new Vec2d(event.getX(), event.getY()),
                        new Vec2d(((random.nextInt(10) < 5) ? (-random.nextFloat()) : (random.nextFloat())), ((random.nextInt(10) < 5) ? (-random.nextFloat()) : (random.nextFloat()))),
                        0., 1., Particle.IMG_PATH_DEFAULT, root));

        primaryStage.show();
        ParticleSystem.getInstance(maxX, maxY).start();
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
