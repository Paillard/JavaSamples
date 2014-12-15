package sample;

import com.sun.javafx.geom.Vec2d;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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

        Image image = new Image("resources/sprite.png");
        ImageView imageView = new ImageView(image);

        Scene scene = new Scene(root, 1024, 728, Color.BLACK);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        root.getChildren().addAll(imageView);
        primaryStage.show();

        Random r = new Random(System.nanoTime());
        Float angle = r.nextFloat();
        Vec2d velocity = new Vec2d(Math.cos(angle), Math.sin(angle));
        Vec2d gravity = new Vec2d(0., 0.1);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), imageView);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.);
        fadeTransition.play();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                velocity.set(velocity.x + gravity.x, velocity.y + gravity.y);
                imageView.setX(imageView.getX() + (velocity.x));
                imageView.setY(imageView.getY() + (velocity.y));
            }
        };
        timer.start();
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
