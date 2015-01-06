package tools;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Created by paill on 15/12/14.
 */
public class Particle extends Body {

    public static String IMG_PATH_DEFAULT = "resources/sprite.png";

    public Particle(double mass, @NotNull Vec2d coordinates, @NotNull Vec2d inertia, double lifetime, double ratio, @NotNull String pathToSprite, @NotNull Group root) {
        super(mass, coordinates, inertia, lifetime, ratio, pathToSprite, root);
        // monitoring the particule by adding it to the current particule system
        ParticleSystem.addParticle(this);
        // if the particule die, it will fade away?
        if (this.lifetime > 0) {

            // animate transition that fade color of the image
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(this.lifetime / 1000), this.sprite);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.);
            // on finish remove the particule from the system.
            fadeTransition.setOnFinished(event -> {
                root.getChildren().remove(this.sprite);
                ParticleSystem.remove(this);
            });
            // start animation
            fadeTransition.play();
        }
    }

    public Particle(double mass, double lifetime, @NotNull Group root) {
        this(mass, new Vec2d(0., 0.), new Vec2d(0., 0.), lifetime, 1.0, Particle.IMG_PATH_DEFAULT, root);
    }

    @Override
    public void move() {
        this.setX(this.getX() + this.inertia.x);
        this.setY(this.getY() + this.inertia.y);
    }
/*
    public void update(long now) {
        if (ParticleSystem.getMouseCoordinates() != null) {
            double xOffset = Math.abs((ParticleSystem.getMouseCoordinates().x - this.sprite.getX()));
            double yOffset = Math.abs((ParticleSystem.getMouseCoordinates().y - this.sprite.getY()));

            this.velocity.x = ((xOffset != 0) ? ((1 / xOffset)) : (this.velocity.x));
            this.velocity.y = ((yOffset != 0) ? ((1 / yOffset)) : (this.velocity.y));

            if (ParticleSystem.getMouseCoordinates().distance(this.sprite.getX(), this.sprite.getY()) <= 200) {
                //System.out.println((this.sprite.getX() - ParticleSystem.getMouseCoordinates().x) / ParticleSystem.getMouseCoordinates().distance(this.sprite.getX(), this.sprite.getY()));
                double dist = Math.abs(ParticleSystem.getMouseCoordinates().distance(this.sprite.getX(), this.sprite.getY()));
                this.theta = Math.asin((this.sprite.getX() - ParticleSystem.getMouseCoordinates().x) / dist);
                this.oldNow = (this.oldNow == 0) ? (now) : (this.oldNow);
                this.theta += ((now - oldNow) / 10E8) % (2 * Math.PI);
                this.oldNow = now;
                this.sprite.setX(ParticleSystem.getMouseCoordinates().x + (Math.sin(theta) * dist));
                this.sprite.setY(ParticleSystem.getMouseCoordinates().y + (Math.cos(theta) * dist));
            } else {
                if (this.sprite.getX() >= ParticleSystem.getMouseCoordinates().x && this.sprite.getY() >= ParticleSystem.getMouseCoordinates().y) {
                    this.setCo(this.sprite.getX() - (xOffset * this.velocity.x), this.sprite.getY() - (yOffset * this.velocity.y));
                } else if (this.sprite.getX() >= ParticleSystem.getMouseCoordinates().x && this.sprite.getY() < ParticleSystem.getMouseCoordinates().y) {
                    this.setCo(this.sprite.getX() - (xOffset * this.velocity.x), this.sprite.getY() + (yOffset * this.velocity.y));
                } else if (this.sprite.getX() < ParticleSystem.getMouseCoordinates().x && this.sprite.getY() >= ParticleSystem.getMouseCoordinates().y) {
                    this.setCo(this.sprite.getX() + (xOffset * this.velocity.x), this.sprite.getY() - (yOffset * this.velocity.y));
                } else {
                    this.setCo(this.sprite.getX() + (xOffset * this.velocity.x), this.sprite.getY() + (yOffset * this.velocity.y));
                }
            }
        }
    }*/
}
