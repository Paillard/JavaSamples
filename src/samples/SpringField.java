package samples;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

/**
 * This is three particles bounds by springs between them.
 * Use the mouse to move the particles. Drop to see springs
 * making fun tricks.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 */
public class SpringField extends Application {
    private MouseGestures mg = new MouseGestures();

    private List<Particle> particles = new ArrayList<>();
    private List<Spring> springs = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    private Particle addParticle(Group parent, Paint p, double x, double y, double mass) {
        Particle particle = new Particle(p, x, y, mass);
        mg.makeDraggable(particle);
        particles.add(particle);
        parent.getChildren().add(particle);
        return particle;
    }

    private void addSpring(Group parent, Particle p1, Particle p2) {
        Spring spring = new Spring(parent, p1, p2, 100., 0.5);
        springs.add(spring);
    }

    private void addExitOnEsc(Stage primaryStage) {
        // close windows on escape
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) Platform.exit();
        });
    }

    @Override
    public void start(Stage primaryStage) {

        addExitOnEsc(primaryStage);
        Group root = new Group();

        // create particles
        Particle pRed = addParticle(root, Color.RED, 300, 100, 10);
        Particle pBlue = addParticle(root, Color.BLUE, 600, 200, 1);
        Particle pGreen = addParticle(root, Color.GREEN, 300, 300, 1);


        // add springs
        addSpring(root, pRed, pBlue);
        addSpring(root, pGreen, pBlue);
        addSpring(root, pGreen, pRed);

        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();

        // animate
        startAnimation();
    }

    private void startAnimation() {

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                // move particles
                for (Particle p : particles) {
                    if (!p.selected) {
                        p.move();
                    }
                }

                // apply springs
                for (Spring s : springs) {
                    s.update();
                }

                // move particles to new location
                for (Particle p : particles) {
                    p.updateLocation();
                }

            }
        };
        timer.start();

    }

    /**
     * The spring constraint and calculation. Updates particle
     */
    public class Spring {

        Particle p1;
        Particle p2;

        double length; // length it tries to obtain
        double strength; //  how quickly it tries to reach that length

        Spring(Group parent, Particle p1, Particle p2, double length, double strength) {
            this.p1 = p1;
            this.p2 = p2;
            this.length = length;
            this.strength = strength;

            Line lineRedBlue = new Line(100, 100, 500, 500);
            lineRedBlue.setStroke(Color.BLACK);
            lineRedBlue.setStrokeWidth(5);
            lineRedBlue.startXProperty().bind(p1.centerXProperty());
            lineRedBlue.startYProperty().bind(p1.centerYProperty());
            lineRedBlue.endXProperty().bind(p2.centerXProperty());
            lineRedBlue.endYProperty().bind(p2.centerYProperty());
            parent.getChildren().add(lineRedBlue);
        }

        void update() {
            double stop = 1.0;
            double dx = p1.getCenterX() - p2.getCenterX();
            double dy = p1.getCenterY() - p2.getCenterY();

            double dist = Math.hypot(dx, dy);
            double theta = Math.atan2(dy, dx);
            double force = (length - dist) * strength;
            if (force > 0) { force *= 4; stop = 0.9; }

            // System.out.println( dist + ", " + Math.toDegrees( theta) + ", " + force);
            double speed = 0.001;
            Point2D p1v = new Point2D(force*Math.cos(theta)* speed /p1.mass, force*Math.sin(theta)* speed /p1.mass);
            Point2D p2v = new Point2D(-force*Math.cos(theta)* speed /p2.mass, -force*Math.sin(theta)* speed /p2.mass);
            p1.vector = p1.vector.add(p1v).multiply(stop);
            p2.vector = p2.vector.add(p2v).multiply(stop);
        }
    }

    /**
     * The particle itself
     */
    public class Particle extends Circle {

        double x;
        double y;

        Point2D vector = new Point2D(0, 0);

        double mass = 1;

        boolean selected = false;

        Particle(Paint color, double x, double y, double mass) {

            super(x, y, 50);

            this.x = x;
            this.y = y;
            this.mass = mass;

            setFill(color);
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);

        }

        void move() {

            x += vector.getX();
            y += vector.getY();
            double damping = 0.995;
            vector = vector.multiply(damping);

        }

        void updateLocation() {
            setCenterX( x);
            setCenterY( y);
        }
    }

    /**
     * Allow movement of objects via mouse.
     */
    public class MouseGestures {

        double orgSceneX, orgSceneY;
        double orgTranslateX, orgTranslateY;

        void makeDraggable(Node node) {
            node.setOnMousePressed(circleOnMousePressedEventHandler);
            node.setOnMouseDragged(circleOnMouseDraggedEventHandler);
            node.setOnMouseReleased(circleOnMouseReleasedEventHandler);
        }

        EventHandler<MouseEvent> circleOnMousePressedEventHandler = (MouseEvent t) -> {

            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            Particle p = ((Particle) (t.getSource()));
            p.selected = true;

            orgTranslateX = p.getCenterX();
            orgTranslateY = p.getCenterY();
        };

        EventHandler<MouseEvent> circleOnMouseReleasedEventHandler = t -> {

            Particle p = ((Particle) (t.getSource()));
            p.selected = false;

        };

        EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = (MouseEvent t) -> {

            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            Particle p = ((Particle) (t.getSource()));

            p.x = newTranslateX;
            p.y = newTranslateY;
        };

    }
}