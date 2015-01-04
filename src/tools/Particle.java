package tools;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

/**
 * Created by paill on 15/12/14.
 */
public class Particle {
    private Float mass;
    private Integer lifetime;
    public static String IMG_PATH_DEFAULT = "resources/sprite.png";
    private Vec2d velocity;
    private Image baseImg;
    private Vec2d baseImgSize;
    private ImageView sprite;
    private long oldNow = 0;
    private double theta;

    public Particle(@NotNull Vec2d coords, @NotNull Group root, @NotNull Vec2d velocity,
                    @NotNull String pathToSprite, @NotNull Integer lifetime, @NotNull Float mass) {

        assert mass > 0 : "One does simply weight something";
        assert mass < 1 : "Only gods weigth that much";

        this.mass = mass;
        this.lifetime = lifetime;

        this.velocity = velocity;

        // Load base image
        this.baseImg = new Image(pathToSprite, false);
        this.baseImgSize = new Vec2d(this.baseImg.getWidth(), this.baseImg.getHeight());

        // Scale sprite
        this.sprite = new ImageView(new Image(pathToSprite, this.baseImgSize.x * this.mass, this.baseImgSize.y * this.mass, true, true));

        // is there any gravity?
        // settle position of the sprite
        this.sprite.setX(coords.x - (this.baseImgSize.x * this.mass) / 2);
        this.sprite.setY(coords.y - (this.baseImgSize.y * this.mass) / 2);
        // add sprite to the scene to be drawn!
        root.getChildren().add(this.sprite);
        // monitoring the particule by adding it to the current particule system
        ParticleSystem.addParticle(this);
        // if the particule die, it will fade away?
        if (this.lifetime > 0) {

            // animate transition that fade color of the image
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(this.lifetime), this.sprite);
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

    static Random rand = new Random(System.nanoTime());
    static Float angle = rand.nextFloat();
    static Double vx = ((rand.nextInt(10) < 5) ? (Math.cos(angle)) : (-Math.cos(angle)));
    static Double vy = ((rand.nextInt(10) < 5) ? (Math.sin(angle)) : (-Math.sin(angle)));

    public Particle(Vec2d coords, Group root) {
        this(coords, root, new Vec2d(vx, vy), Particle.IMG_PATH_DEFAULT, rand.nextInt(3000) + 6000, new Float(0.7));
    }

    private void setCo(double x, double y) {
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    /**
     @Deprecated
     */
    public void update() {
        assert this.velocity != null : "failed initialised";
        assert this.sprite != null : "failed initialised";

        rand = new Random(System.nanoTime());
        angle = rand.nextFloat();
        vx = ((rand.nextInt(10) < 5) ? (Math.cos(angle)) : (-Math.cos(angle)));
        vy = ((rand.nextInt(10) < 5) ? (Math.sin(angle)) : (-Math.sin(angle)));

        //this.velocity.set(this.velocity.x + ParticleSystem.getGravity().x * 0.1, this.velocity.y + ParticleSystem.getGravity().y * 0.1);
        this.sprite.setX(this.sprite.getX() + (this.velocity.x));
        this.sprite.setY(this.sprite.getY() + (this.velocity.y));
    }

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
    }
}
