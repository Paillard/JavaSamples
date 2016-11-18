package tools;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.List;

/**
 * A simple particle is a spherical Object
 * moving into it's system until hurting something.
 *
 * @author Treiber Julien
 */
public class Particle extends Body {

    public static final String IMG_PATH_DEFAULT = "resources/sprite.png";

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

    public @Nullable Particle getClosest(@NotNull List<Particle> particules) {
        Particle closest = null;
        double distance = Double.MAX_VALUE;

        for (Particle p: particules) {
            if (!p.equals(this)) {
                double tmp = this.getDistanceFrom(p);
                if (distance > tmp) {
                    distance = tmp;
                    closest = p;
                }
            }
        }
        return closest;
    }

    @Override
    public void move() {

        this.setX(this.getX() + this.inertia.x);
        this.setY(this.getY() + this.inertia.y);

        double pcx = this.getCenterX();
        double pcy = this.getCenterY();
        ParticleSystem ps = ParticleSystem.getInstance();

        // particle can not leave the screen
        if ((pcx < 0) || (pcx > ps.getMaxX())) this.setInertia(-inertia.x, inertia.y);
        else if ((pcy < 0) || (pcy > ps.getMaxY())) this.setInertia(inertia.x, -inertia.y);
        //else { // particle collision one in another
        // TODO

           // tests sur un mouvement helicoidoale autour de la souris des particules
          /* if (ParticleSystem.getMouseCoordinates() != null) {
                double xOffset = Math.abs((ParticleSystem.getMouseCoordinates().x - this.getCenterX()));
                double yOffset = Math.abs((ParticleSystem.getMouseCoordinates().y - this.getCenterY()));


                if (ParticleSystem.getMouseCoordinates().distance(this.getCenterX(), this.getCenterY()) <= 200) {
                    //System.out.println((this.getCenterX() - ParticleSystem.getMouseCoordinates().x) / ParticleSystem.getMouseCoordinates().distance(this.getCenterX(), this.getCenterY()));
                    double dist = Math.abs(ParticleSystem.getMouseCoordinates().distance(this.getCenterX(), this.getCenterY()));
                    double theta = Math.asin((this.getCenterX() - ParticleSystem.getMouseCoordinates().x) / dist);
                    //double theta += ((now - oldNow) / 10E8) % (2 * Math.PI);
                    this.setX(ParticleSystem.getMouseCoordinates().x + (Math.sin(theta) * dist));
                    this.setY(ParticleSystem.getMouseCoordinates().y + (Math.cos(theta) * dist));
                } else {
                    if (this.getCenterX() >= ParticleSystem.getMouseCoordinates().x && this.getCenterY() >= ParticleSystem.getMouseCoordinates().y) {
                        this.inertia.set(this.getCenterX() - (xOffset * this.inertia.x), this.getCenterY() - (yOffset * this.inertia.y));
                    } else if (this.getCenterX() >= ParticleSystem.getMouseCoordinates().x && this.getCenterY() < ParticleSystem.getMouseCoordinates().y) {
                        this.inertia.set(this.getCenterX() - (xOffset * this.inertia.x), this.getCenterY() + (yOffset * this.inertia.y));
                    } else if (this.getCenterX() < ParticleSystem.getMouseCoordinates().x && this.getCenterY() >= ParticleSystem.getMouseCoordinates().y) {
                        this.inertia.set(this.getCenterX() + (xOffset * this.inertia.x), this.getCenterY() - (yOffset * this.inertia.y));
                    } else {
                        this.inertia.set(this.getCenterX() + (xOffset * this.inertia.x), this.getCenterY() + (yOffset * this.inertia.y));
                    }
                }
            }*/
           /* Particle closest = this.getClosest(ps.findCollisions(this));

            if (closest != null) {
                Vec2d cInertia = closest.getInertia();
                Vec2d oldInertia = new Vec2d(this.inertia);
                Vec2d oldCInertia = new Vec2d(cInertia);

                this.inertia.set((oldCInertia.x), ( oldCInertia.y));
                closest.setInertia(oldInertia.x, oldInertia.y);*/

/*
                double norm = Math.sqrt(Math.pow(oldInertia.x, 2) + Math.pow(oldInertia.y, 2));
                double newNorm = Math.sqrt(Math.pow(this.inertia.x, 2) + Math.pow(this.inertia.y, 2));
                if (inertia.y != 0) {
                    double k = norm / newNorm;
                    double m = Math.sqrt(Math.pow(k, 2) * Math.pow(newNorm, 2) * Math.pow(this.inertia.y, 2) / (Math.pow(this.inertia.x, 2) + Math.pow(this.inertia.y, 2)));
                    double n = m * this.inertia.x / this.inertia.y;

                    this.inertia.set(n,m);
                }

                norm = Math.sqrt(Math.pow(oldCInertia.x, 2) + Math.pow(oldCInertia.y, 2));
                newNorm = Math.sqrt(Math.pow(cInertia.x, 2) + Math.pow(cInertia.y, 2));
                if (this.inertia.y != 0) {
                    double k = norm / newNorm;
                    double m = Math.sqrt(Math.pow(k, 2) * Math.pow(newNorm, 2) * Math.pow(cInertia.y, 2) / (Math.pow(cInertia.x, 2) + Math.pow(cInertia.y, 2)));
                    double n = m * cInertia.x / cInertia.y;

                    cInertia.set(n, m);
                }*/
           // }
        //}
    }

    @Override
    public boolean hasCollision(Body b) {
            double pWidth = b.getWidth();
            double eWidth = this.getWidth();
            double distance = this.getDistanceFrom(b);
            // TODO : improve : only valid for circle like shape
            return (distance <= (pWidth / 2 + eWidth / 2));
    }

    // elliptic movement approach
   /* public void update(long now) {
        if (ParticleSystem.getMouseCoordinates() != null) {
            double xOffset = Math.abs((ParticleSystem.getMouseCoordinates().x - this.getCenterX()));
            double yOffset = Math.abs((ParticleSystem.getMouseCoordinates().y - this.getCenterY()));

            this.inertia.x = ((xOffset != 0) ? ((1 / xOffset)) : (this.inertia.x));
            this.inertia.y = ((yOffset != 0) ? ((1 / yOffset)) : (this.inertia.y));

            if (ParticleSystem.getMouseCoordinates().distance(this.getCenterX(), this.getCenterY()) <= 200) {
                //System.out.println((this.getCenterX() - ParticleSystem.getMouseCoordinates().x) / ParticleSystem.getMouseCoordinates().distance(this.getCenterX(), this.getCenterY()));
                double dist = Math.abs(ParticleSystem.getMouseCoordinates().distance(this.getCenterX(), this.getCenterY()));
                this.theta = Math.asin((this.getCenterX() - ParticleSystem.getMouseCoordinates().x) / dist);
                this.oldNow = (this.oldNow == 0) ? (now) : (this.oldNow);
                this.theta += ((now - oldNow) / 10E8) % (2 * Math.PI);
                this.oldNow = now;
                this.sprite.setX(ParticleSystem.getMouseCoordinates().x + (Math.sin(theta) * dist));
                this.sprite.setY(ParticleSystem.getMouseCoordinates().y + (Math.cos(theta) * dist));
            } else {
                if (this.getCenterX() >= ParticleSystem.getMouseCoordinates().x && this.getCenterY() >= ParticleSystem.getMouseCoordinates().y) {
                    this.inertia.set(this.getCenterX() - (xOffset * this.inertia.x), this.getCenterY() - (yOffset * this.inertia.y));
                } else if (this.getCenterX() >= ParticleSystem.getMouseCoordinates().x && this.getCenterY() < ParticleSystem.getMouseCoordinates().y) {
                    this.inertia.set(this.getCenterX() - (xOffset * this.inertia.x), this.getCenterY() + (yOffset * this.inertia.y));
                } else if (this.getCenterX() < ParticleSystem.getMouseCoordinates().x && this.getCenterY() >= ParticleSystem.getMouseCoordinates().y) {
                    this.inertia.set(this.getCenterX() + (xOffset * this.inertia.x), this.getCenterY() - (yOffset * this.inertia.y));
                } else {
                    this.inertia.set(this.getCenterX() + (xOffset * this.inertia.x), this.getCenterY() + (yOffset * this.inertia.y));
                }
            }
        }
    }*/
}
