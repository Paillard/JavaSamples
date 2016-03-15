package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalTime;
import java.util.logging.Logger;

/**
 *
 * Quit by pressing the escape (Esc, Ech, echap, ...) touch.
 *
 * @author Treiber Julien
 *
 */
public class Timer extends Application {

    private LocalTime startTime = LocalTime.now();
    private Label timeLabel = new Label("");
    private boolean running = true;
    private Button startButton = new Button("Start");
    private Button stopButton = new Button("Stop");
    private Button resetButton = new Button("Reset");

    private void addExitOnEsc(Stage primaryStage) {
        // close windows on escape
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                running = false;
                Platform.exit();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // The main scene into which we draw our stuff
        Group root = new Group();
        Scene scene = new Scene(root, 1024, 728);
        primaryStage.setScene(scene);
        addExitOnEsc(primaryStage);

        configureInterface(root);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.R)) {
                resetButton.fire();
                event.consume();
            } else if (event.getCode().equals(KeyCode.S) && !startButton.isDisabled()) {
                startButton.fire();
                event.consume();
            } else if (event.getCode().equals(KeyCode.S)) {
                stopButton.fire();
            }
        });

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void configureInterface(Group root) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(configureTimerLabel(), configureButtons());
        root.getChildren().addAll(vBox);
    }

    private HBox configureTimerLabel() {
        timeLabel.setFont(new Font(200d));
        HBox hBox = new HBox(timeLabel);
        return hBox;
    }

    private HBox configureButtons() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(startButton, stopButton, resetButton);
        resetButton.setOnAction(e -> resetTimer());
        stopButton.setOnAction(e -> stopTimer());
        startButton.setOnAction(e -> startTimer());
        return hBox;
    }

    private void startTimer() {
        running = true;
        startButton.setDisable(true);
        stopButton.setDisable(false);
        new Thread(() -> {
            while (running) {
                Platform.runLater(this::updateTime);
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

    private void resetTimer() {
        startTime = LocalTime.now();
        updateTime();
    }

    private void stopTimer() {
        running = false;
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }

    public Duration getElapsedTime() {
        return Duration.between(startTime, LocalTime.now());
    }

    private void updateTime() {
        timeLabel.setText(getElapsedTime().toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
