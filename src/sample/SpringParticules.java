package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Random;
import java.util.logging.Logger;

public class SpringParticules extends Application {

    private boolean running = true;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();


        Circle particule = new Circle(5, Color.CHARTREUSE);
        particule.setCenterX(0);
        particule.setCenterY(0);
        particule.setEffect(new BoxBlur());

        Circle particule2 = new Circle(5, Color.AQUA);
        particule2.setCenterX(1023);
        particule2.setCenterY(0);
        particule2.setEffect(new BoxBlur());
        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(20);
                    double[] vect = new double[2];
                    vect[0] = 1.9;
                    vect[1] = 1.9;
                    double px = particule.getCenterX();
                    double py = particule.getCenterY();
                    if (px > 1024 || px < 0 || py > 768 || py < 0) {
                        Platform.runLater(() -> {
                            particule.setCenterX(0);
                            particule.setCenterY(0);
                        });
                    } else {
                        Platform.runLater(() -> {
                            particule.setCenterX(particule.getCenterX() + vect[0]);
                            particule.setCenterY(particule.getCenterY() + vect[1]);
                        });
                    }

                    double[] vect2 = new double[2];
                    vect2[0] = -1.9;
                    vect2[1] = 1.5;
                    px = particule2.getCenterX();
                    py = particule2.getCenterY();
                    if (px > 1024 || px < 0 || py > 768 || py < 0) {
                        Platform.runLater(() -> {
                            particule2.setCenterX(1023);
                            particule2.setCenterY(0);
                        });
                    } else {
                        Platform.runLater(() -> {
                            particule2.setCenterX(particule2.getCenterX() + vect2[0]);
                            particule2.setCenterY(particule2.getCenterY() + vect2[1]);
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
//        line.toBack();
        line.setVisible(false);

        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(31l);
                    final double p1x = particule.getCenterX();
                    final double p1y = particule.getCenterY();
                    final double p2x = particule2.getCenterX();
                    final double p2y = particule2.getCenterY();
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


        root.getChildren().addAll(particule, particule2, line);

        primaryStage.setScene(new Scene(root, 1024, 768, Color.BLACK));
        primaryStage.setTitle("Strings Particules");
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
