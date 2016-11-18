package bubbles;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

/**
 * This is a little game.
 * It is fullscreen sized. You have to press on red bubbles
 * to gain points.
 *
 * Quit by pressing ctrl + Q key combination.
 * // TODO fix remaining process when closing
 *
 * @author Treiber Julien
 */
public class BubblesPopping extends Application {

    // static variables for program configuration
    private static KeyCode APP_SHORT_CLOSE = KeyCode.Q; // On control down
    private static String APP_TITLE = "Bubbles Popping";
    private static long TIME_BETWEEN_POPS_IN_MS = 2000L;

    private boolean running = true;
    private Group root = new Group();
    private SimpleIntegerProperty score = new SimpleIntegerProperty(0);
    private LocalTime startTime = LocalTime.now();
    private Label timeLabel = new Label();

    public static void main(String... args) {
        launch(args);
    }

    // function to be call when exiting.
    // clear your mess here
    private void clearAndExit() {
        running = false;
        Platform.exit();
    }

    // add Listener to exit
    private void addExitOnKey(Stage primaryStage) {
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            // test key value
            if (event.isControlDown() && event.getCode().equals(APP_SHORT_CLOSE)) {
                event.consume();
                clearAndExit();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // configure event that pops bubbles
        addPoppingEvent();

        addShowScore();
        addShowTime();
        // configure primaryStage
        primaryStage.setScene(new Scene(root, 1024, 728));
        addExitOnKey(primaryStage);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setOnCloseRequest(event -> clearAndExit());
        // then show up the application
        primaryStage.show();
    }

    private void updateTime() {
        timeLabel.setText(getElapsedTime().toString());
    }

    private void addShowTime() {
        Screen screen = Screen.getPrimary();
        timeLabel.setLayoutX(screen.getBounds().getWidth() * 0.01);
        timeLabel.setLayoutY(screen.getBounds().getHeight() * 0.8);
        timeLabel.setFont(new Font(100d));
        root.getChildren().add(timeLabel);
        Thread t = new Thread(() -> {
            while (running) {
                Platform.runLater(this::updateTime);
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException ignored) {}
            }
        });
        t.start();
    }

    private void addShowScore() {
        Label scoreLabel = new Label();
        scoreLabel.textProperty().bindBidirectional(score, new NumberStringConverter());
        Screen screen = Screen.getPrimary();
        scoreLabel.setLayoutX(screen.getBounds().getWidth() * 0.01);
        scoreLabel.setLayoutY(screen.getBounds().getHeight() * 0.01);
        scoreLabel.setFont(new Font(100d));
        root.getChildren().add(scoreLabel);
    }

    private void pop() {
        Random r = new Random();
        Screen screen = Screen.getPrimary();
        root.getChildren().add(new Bubble(r.nextDouble() * screen.getBounds().getWidth(), r.nextDouble() * screen.getBounds().getHeight(), this));
    }

    //
    private void addPoppingEvent() {
        Thread t = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(TIME_BETWEEN_POPS_IN_MS);
                } catch (InterruptedException ignored) {
                }
                Platform.runLater(this::pop);
            }
        });
        t.start();
    }

    void depop(Bubble bubble) {
        bubble.dePop();
        root.getChildren().remove(bubble);
        score.set(1 + score.get());
    }

    boolean isRunning() {
        return running;
    }

    Duration getElapsedTime() {
        return Duration.between(startTime, LocalTime.now());
    }
}
