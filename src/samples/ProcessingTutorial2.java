package samples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

/**
 * This example is about coordinates of Shapes.
 *
 * It shows how to draw some existing Shapes like
 * Circle, Rectangle, Ellipse with javafx making a
 * little creature.
 *
 * The drawing is axed on the head start position, which is
 * the center of the Frame.
 *
 * Quit by pressing the escape key.
 *
 * @author Treiber Julien
 */
public class ProcessingTutorial2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        // width of the frame
        Integer width = 600;
        assert width > 0 : "minimal width not provided, have \"" + width + "\"";
        // height of the frame
        Integer height = 600;
        assert height > 0: "minimal height not provided, have \"" + height + "\"";

        Scene scene = new Scene(root, width, height, Color.GRAY);
        primaryStage.setScene(scene);
        // close windows on escape
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ESCAPE))
                primaryStage.close();
        });

        // ---- Creating the head as a simple Circle
        Integer headSize = 60;
        Circle head = new Circle(width/2, height/2, headSize);
        head.setFill(Color.WHITE);
        head.setStroke(Color.BLACK);

        // ---- Creating the body as a simple Rectangle
        Integer bodyWidth = 50;
        Integer bodyHeight = 100;
        Integer neckOffset = 10;
        Rectangle body = new Rectangle(head.getCenterX() - bodyWidth/2, head.getCenterY() + head.getRadius() - neckOffset, bodyWidth, bodyHeight);
        body.setFill(Color.WHITE);
        body.setStroke(Color.BLACK);

        // ---- Creating the eyes as simples Ellipse
        Integer eyesWidth = 16;
        Integer eyesHeight = 32;
        Ellipse leftEye = new Ellipse(head.getCenterX() - (2 * head.getRadius() / 3), head.getCenterY(), eyesWidth, eyesHeight);
        leftEye.setFill(Color.WHITE);
        leftEye.setStroke(Color.BLACK);

        Ellipse rightEye = new Ellipse(head.getCenterX() + (2 * head.getRadius() / 3), head.getCenterY(), eyesWidth, eyesHeight);
        rightEye.setFill(Color.WHITE);
        rightEye.setStroke(Color.BLACK);

        // ---- Creating the arms as simples Lines
        Integer shoulderOffset = 15;
        Integer fistOffset = 25;
        Line leftArm = new Line(body.getX(), body.getY() + shoulderOffset,
                                body.getX() - fistOffset, body.getY() + shoulderOffset + fistOffset);
        leftArm.setStroke(Color.BLACK);

        Line rightArm = new Line(body.getX() + body.getWidth(), body.getY() + shoulderOffset,
                                 body.getX() + body.getWidth() + fistOffset, body.getY() + shoulderOffset + fistOffset);
        rightArm.setStroke(Color.BLACK);

        // ---- Creating the feets as simples Lines
        Integer toesOffset = 10;
        Line leftFoot = new Line(body.getX(), body.getY() + body.getHeight(),
                                 body.getX() - 10, body.getY() + body.getHeight() + toesOffset);
        leftFoot.setStroke(Color.BLACK);

        Line rightFoot = new Line(body.getX() + body.getWidth(), body.getY() + body.getHeight(),
                                  body.getX() + body.getWidth() + 10, body.getY() + body.getHeight() + toesOffset);
        rightFoot.setStroke(Color.BLACK);

        // ---- Add all configured Shapes to the Group!
        root.getChildren().addAll(body, head, leftEye, rightEye, leftFoot, rightFoot, leftArm, rightArm);
        primaryStage.setResizable(false);
        primaryStage.show();
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
