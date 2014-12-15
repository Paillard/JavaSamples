package tools;

import com.sun.javafx.geom.Vec2d;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

/**
 * Created by paill on 15/12/14.
 */
public class Particle {
    private Vec2d velocity;
    private ImageView sprite;

    public Particle(double x, double y, Group root, ParticleSystem ps) {
        Random rand = new Random(System.nanoTime());
        Float angle = rand.nextFloat();
        Double vx = Math.cos(angle);
        Double vy = Math.sin(angle);
        vx = ((rand.nextInt(10) < 5) ? (vx) : (-vx));
        vy = ((rand.nextInt(10) < 5) ? (vy) : (-vy));
        //System.out.println(String.format("vx: %f\tvy: %f", vx, vy));
        this.velocity = new Vec2d(vx, vy);
        Double ratio = rand.nextDouble() * 100;
        this.sprite = new ImageView(new Image("resources/sprite.png", ratio, ratio, true, true));
        this.sprite.setX(x);
        this.sprite.setY(y);
        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(1);
        boxBlur.setHeight(1);
        boxBlur.setIterations(1);
        this.sprite.setEffect(boxBlur);

        root.getChildren().add(this.sprite);

        Integer lifeTime = rand.nextInt(7500) + 6000;
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(lifeTime), this.sprite);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.);
        fadeTransition.setOnFinished(event -> {
            root.getChildren().remove(this.sprite);
            ps.remove(this);
        });
        fadeTransition.play();
    }

    public void update() {
        this.velocity.set(this.velocity.x + ParticleSystem.getGravity().x * 0.1, this.velocity.y + ParticleSystem.getGravity().y * 0.1);
        this.sprite.setX(this.sprite.getX() + (this.velocity.x));
        this.sprite.setY(this.sprite.getY() + (this.velocity.y));
    }
}
