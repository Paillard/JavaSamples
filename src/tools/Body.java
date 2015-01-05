package tools;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by paill on 04/01/15.
 */
public class Body {
    protected long birthDay;   // the date the body was created.
    protected double lifetime;          // the maximal time this body can live. in s
    protected double mass;              // the mass of the body.
    protected Vec2d inertia;            // the force that apply to the body.
    protected ImageView sprite;         // the image use to represent the body in a Scene.

    public Body(double mass,
                @NotNull Vec2d spaceCoordinates,
                @NotNull Vec2d inertia,
                double lifetime,
                double ratio,
                @NotNull String pathToSprite,
                @NotNull Group root) {
        this.mass = mass;
        this.inertia = inertia;
        this.lifetime = lifetime;
        this.birthDay = System.nanoTime();

        Image image = new Image(pathToSprite, false);
        // scale the sprite using the ratio
        this.sprite = new ImageView(new Image(pathToSprite, image.getWidth() * ratio, image.getHeight() * ratio, true, true, false));
        this.sprite.setX(spaceCoordinates.x - (image.getWidth() * ratio) / 2);
        this.sprite.setY(spaceCoordinates.y - (image.getHeight() * ratio)/2);
        root.getChildren().add(this.sprite);
    }

    public double getWidth() {
        assert this.sprite != null : "Using sprite here. Should be initialized";
        return this.sprite.getImage().getWidth();
    }

    public double getHeight() {
        assert this.sprite != null : "Using sprite here. Should be initialized";
        return this.sprite.getImage().getHeight();
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
}
