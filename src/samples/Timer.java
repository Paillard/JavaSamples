package samples;

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

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * This is a simple Chronometer.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 */
public class Timer extends Application {

    private LocalDateTime startTime = LocalDateTime.now();
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
                event.consume();
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
        timeLabel.setFont(new Font(100d));
        return new HBox(timeLabel);
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
        startTime = LocalDateTime.now();
        updateTime();
    }

    private void stopTimer() {
        running = false;
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }

    private void updateTime() {
        LocalDateTime now = LocalDateTime.now();
        timeLabel.setText(String.format("%d-%d-%d\t%d:%d:%d.%d",
                ChronoUnit.YEARS.between(startTime, now),
                ChronoUnit.MONTHS.between(startTime, now),
                ChronoUnit.DAYS.between(startTime, now),
                ChronoUnit.HOURS.between(startTime, now),
                ChronoUnit.MINUTES.between(startTime, now),
                ChronoUnit.SECONDS.between(startTime, now),
                ChronoUnit.NANOS.between(startTime, now)
        ));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
