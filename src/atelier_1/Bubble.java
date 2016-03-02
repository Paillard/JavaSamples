package atelier_1;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;

import java.util.Random;

/**
 * Created by me on 3/2/16.
 */
public class Bubble extends Circle {
    private static double DEFAULT_RADIUS = 10d;
    private static Color DEFAULT_COLOR = Color.RED;
    private BubblesPopping app;
    private boolean popped = false;

    public Bubble(double centerx, double centery, BubblesPopping app) {
        super(centerx, centery, DEFAULT_RADIUS, DEFAULT_COLOR);
        this.app = app;
        addOnClicAction();
        addMotionEvent();
    }

    public void dePop() {
        this.popped = true;
    }

    private void addMotionEvent() {
        Thread t = new Thread(() -> {
            while (app.isRunning() && !popped) {
                try {
                    long time = 400000000 / app.getElapsedTime().toMillis();
                    System.out.println(time);
                    if (time < 100L) time = 100L;
                    Thread.sleep(time);
                } catch (InterruptedException ignored) {
                }
                Screen screen = Screen.getPrimary();
                Random r = new Random();
                Platform.runLater(() -> {
                    this.setLayoutX(r.nextDouble() * screen.getBounds().getWidth());
                    this.setLayoutY(r.nextDouble() * screen.getBounds().getHeight());
                });
            }
        });
        t.start();
    }

    private void addOnClicAction() {
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> app.depop(this));
    }
}
