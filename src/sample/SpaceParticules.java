package sample;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Vec2d;
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
import tools.Particle;
import tools.ParticleSystem;

import java.util.Random;

/**
 * Created by paill on 15/12/14.
 */
public class SpaceParticules extends Application {

    double cx = 0, cy = 0, px = 100, py = 100, radius = 100;

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

        Text coords = new Text("default");
        coords.setStroke(Color.RED);
        coords.setX(0);
        coords.setY(15);


        // ParticleSystem.getInstance().start();
        Circle circle = new Circle(cx, cy, radius);
        Line line1 = new Line(px, py, px, py);
        Line line2 = new Line(px, py, px, py);

        circle.setStroke(Color.WHITE);
        line1.setStroke(Color.WHITE);
        line2.setStroke(Color.WHITE);


        // Random random = new Random();
//        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> new  Particle(new Vec2d(random.nextInt((int)scene.getWidth()), random.nextInt((int)scene.getHeight())), root, new Vec2d(random.nextDouble(), random.nextDouble()),
//                Particle.IMG_PATH_DEFAULT, -1, (float) 0.5));
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
//            ParticleSystem.setMouseCoordinates(new Vec2d(event.getX(), event.getY()));
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

            coords.setText("x: " + event.getX() + "\ny: " + event.getY()+ "\n");
            line1.setEndX(xT1);
            line1.setEndY(yT1);
            line2.setEndX(xT2);
            line2.setEndY(yT2);
        });
        root.getChildren().add(coords);


        root.getChildren().add(circle);
        root.getChildren().add(line1);
        root.getChildren().add(line2);
        primaryStage.show();
    }

    static public void  main(String[] args) {
        launch(args);
    }
}
