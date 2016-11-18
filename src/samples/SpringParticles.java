package samples;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * This is an example of drawing moving object
 * Logging activities and draw new things on a
 * condition.
 *
 * Quit by pressing escape key.
 *
 * @author Treiber Julien
 */
public class SpringParticles extends Application {

    private boolean running = true;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        Circle particle = new Circle(5, Color.CHARTREUSE);
        particle.setCenterX(0);
        particle.setCenterY(0);
        particle.setEffect(new BoxBlur());

        Circle particle2 = new Circle(5, Color.AQUA);
        particle2.setCenterX(1023);
        particle2.setCenterY(0);
        particle2.setEffect(new BoxBlur());
        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(20);
                    double[] vect = new double[2];
                    vect[0] = 1.9;
                    vect[1] = 1.9;
                    double px = particle.getCenterX();
                    double py = particle.getCenterY();
                    if (px > 1024 || px < 0 || py > 768 || py < 0) {
                        Platform.runLater(() -> {
                            particle.setCenterX(0);
                            particle.setCenterY(0);
                        });
                    } else {
                        Platform.runLater(() -> {
                            particle.setCenterX(particle.getCenterX() + vect[0]);
                            particle.setCenterY(particle.getCenterY() + vect[1]);
                        });
                    }

                    double[] vect2 = new double[2];
                    vect2[0] = -1.9;
                    vect2[1] = 1.5;
                    px = particle2.getCenterX();
                    py = particle2.getCenterY();
                    if (px > 1024 || px < 0 || py > 768 || py < 0) {
                        Platform.runLater(() -> {
                            particle2.setCenterX(1023);
                            particle2.setCenterY(0);
                        });
                    } else {
                        Platform.runLater(() -> {
                            particle2.setCenterX(particle2.getCenterX() + vect2[0]);
                            particle2.setCenterY(particle2.getCenterY() + vect2[1]);
                        });
                    }
                } catch (Exception ignored) {
                    Logger.getGlobal().warning(ignored.getMessage());
                }
            }
        }).start();

        Line line = new Line();
        line.setFill(Color.WHITE);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(3);
        line.setEffect(new BoxBlur());
        line.setVisible(false);

        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(31L);
                    final double p1x = particle.getCenterX();
                    final double p1y = particle.getCenterY();
                    final double p2x = particle2.getCenterX();
                    final double p2y = particle2.getCenterY();
                    if (Math.sqrt(Math.pow((p1x - p2x), 2) + Math.pow((p1y - p2y), 2)) < 200) {
                        Platform.runLater(() -> {
                            line.setStartX(p1x);
                            line.setStartY(p1y);
                            line.setEndX(p2x);
                            line.setEndY(p2y);
                            line.setVisible(true);
                        });
                    } else Platform.runLater(() -> line.setVisible(false));
                    Logger.getGlobal().warning("dist: " + Math.sqrt(Math.pow((p1x - p2x), 2) + Math.pow((p1y - p2y), 2)));
                } catch (Exception ignored) {}
            }
        }).start();


        root.getChildren().addAll(particle, particle2, line);

        primaryStage.setScene(new Scene(root, 1024, 768, Color.BLACK));
        primaryStage.setTitle("Strings Particles");
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> cleanExit());
        primaryStage.setOnCloseRequest(event -> cleanExit());
        primaryStage.show();
    }

    private void cleanExit() {
        running = false;
        Platform.exit();
    }

    public static void main(String... args) {
        launch(args);
    }
}
