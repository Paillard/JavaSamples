package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by paill on 15/12/14.
 */
public class CanvasBackground extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(400,  300);

        holder.getChildren().add(canvas);
        root.getChildren().add(holder);

        holder.setStyle("-fx-background-color: red");
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}