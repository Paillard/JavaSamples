package tools;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by paill on 04/01/15.
 */
public abstract class Body {
    protected long birthDay;   // the date the body was created.
    protected double lifetime;          // the maximal time this body can live. in s
    protected double mass;              // the mass of the body.
    protected Vec2d inertia;            // the force that apply to the body.
    protected Vec2d externalForces;     // the force that apply to the body.
    protected Image img;
    protected ImageView sprite;         // the image use to represent the body in a Scene.

    public Body(double mass,
                @NotNull Vec2d coordinates,
                @NotNull Vec2d inertia,
                double lifetime,
                double ratio,
                @NotNull String pathToSprite,
                @NotNull Group root) {
        this.mass = mass;
        this.inertia = inertia;
        this.lifetime = lifetime;
        this.birthDay = System.nanoTime();
        this.externalForces = new Vec2d(0., 0.);

        Image image = new Image(pathToSprite, false);
        // scale the sprite using the ratio
        this.img = new Image(pathToSprite, image.getWidth() * ratio, image.getHeight() * ratio, true, true, false);
        this.sprite = new ImageView(this.img);
        this.setX(coordinates.x - (image.getWidth() * ratio) / 2);
        this.setY(coordinates.y - (image.getHeight() * ratio) / 2);
        root.getChildren().add(this.sprite);
    }

    public double getWidth() {
        assert this.img != null : "Using sprite here. Should be initialized";
        return this.img.getWidth();
    }

    public double getHeight() {
        assert this.img != null : "Using sprite here. Should be initialized";
        return this.img.getHeight();
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return this.mass;
    }

    public double getX() {
        assert this.sprite != null : "A Body needs coordinates";
        return this.sprite.getX();
    }

    public double getY() {
        assert this.sprite != null : "A Body needs coordinates";
        return this.sprite.getY();
    }

    public void setX(double x) {
        assert this.sprite != null : "A Body needs coordinates";
        this.sprite.setX(x);
    }

    public void setY(double y) {
        assert this.sprite != null : "A Body needs coordinates";
        this.sprite.setY(y);
    }

    public void setCoordinates(double x, double y) {
        assert this.sprite != null : "A Body needs coordinates";
        this.setX(x);
        this.setY(y);
    }

    public void setCoordinates(@NotNull Vec2d coordinates) {
        assert this.sprite != null : "A Body needs coordinates";
        this.setCoordinates(coordinates.x, coordinates.y);
    }

    public void setInertia(double x, double y) {
        assert this.inertia != null : "inertia should have been set for use";
        this.inertia.set(x, y);
    }

    public void setInertia(@NotNull Vec2d inertia) {
        assert this.inertia != null : "inertia should have been set for use";
        this.setInertia(inertia.x, inertia.y);
    }

    public Vec2d getCoordinates() {
        assert this.sprite != null : "A Body needs coordinates";
        return new Vec2d(this.getX(), this.getY());
    }

    public Vec2d getInertia() {
        assert this.inertia != null : "inertia should have been set for use";
        return this.inertia;
    }

    public double getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    public double getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }

    public double getDistanceFrom(Body body) {
        return (Math.sqrt(Math.pow(body.getCenterX() - this.getCenterX(), 2) + Math.pow(body.getCenterY() - this.getCenterY(), 2)));
    }

    public Vec2d getExternalForces() {
        return this.externalForces;
    }

    public void addExternalForce(Vec2d force) {
        this.externalForces.x += force.x;
        this.externalForces.y += force.y;
    }

    /**
     *
     * @param b
     * @return
     */
    public abstract boolean hasCollision(Body b);

    /**
     * The way you move your body!
     */
    public abstract void move();
}
