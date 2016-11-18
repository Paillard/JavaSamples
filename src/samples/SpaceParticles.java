package samples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This show the distance from a point to the mouse.
 * It is used to understand how to draw tangent lines
 * to a circle passing through a point.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 */
public class SpaceParticles extends Application {

    private double cx = 0, cy = 0, px = 100, py = 100, radius = 100;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        Scene scene = new Scene(root, 1024, 728, Color.BLACK);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        Text cords = new Text("default");
        cords.setStroke(Color.RED);
        cords.setX(0);
        cords.setY(15);

        // ParticleSystem.getInstance().start();
        Circle circle = new Circle(cx, cy, radius);
        Line line1 = new Line(px, py, px, py);
        Line line2 = new Line(px, py, px, py);

        circle.setStroke(Color.WHITE);
        line1.setStroke(Color.WHITE);
        line2.setStroke(Color.WHITE);

        scene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());

            double dx, dy, dd, a, b, t, xT1, xT2, yT1, yT2;

            cx = event.getX();
            cy = event.getY();
            dx = cx - px;
            dy = cy - py;
            dd = Math.sqrt(dx * dx + dy * dy);
            a = Math.asin(radius / dd);
            b = Math.atan2(dy, dx);

            t = b - a;
            xT1 = radius * Math.sin(t);
            yT1 = radius * -Math.cos(t);

            t = b + a;
            xT2 = radius * -Math.sin(t);
            yT2 = radius * Math.cos(t);

            xT1 += cx;
            yT1 += cy;
            xT2 += cx;
            yT2 += cy;

            cords.setText("x: " + event.getX() + "\ny: " + event.getY()+ "\n");
            line1.setEndX(xT1);
            line1.setEndY(yT1);
            line2.setEndX(xT2);
            line2.setEndY(yT2);
        });
        root.getChildren().add(cords);

        root.getChildren().add(circle);
        root.getChildren().add(line1);
        root.getChildren().add(line2);
        primaryStage.show();
    }

    static public void  main(String[] args) {
        launch(args);
    }
}
