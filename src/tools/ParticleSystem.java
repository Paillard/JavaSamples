package tools;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paill on 15/12/14.
 */
public class ParticleSystem extends AnimationTimer {

    private static final Vec2d gravity = new Vec2d(0., 0.1);
    private static ArrayList<Particle> particles = new ArrayList<>();
    private static ParticleSystem particleSystem;
    private static Vec2d mouseCoordinates = new Vec2d(0.,0.);
    private double maxX;
    private double maxY;

    public static Vec2d getMouseCoordinates() {
        return mouseCoordinates;
    }

    public static void setMouseCoordinates(Vec2d mouseCoordinates) {
        ParticleSystem.mouseCoordinates = mouseCoordinates;
    }

    private ParticleSystem(double maxX, double maxY) {
        particleSystem = this;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public List<Particle> findCollusions(Particle encounter) {
        List<Particle> tmp = new ArrayList<>();
        for (Particle p: particles) {
            if (!(p.getCenterX() == encounter.getCenterX()) && !(p.getCenterY() == p.getCenterX())) {
                double pWidth = p.getWidth();
                double eWidth = encounter.getWidth();
                double distance = p.getDistanceFrom(encounter);
                // TODO : improve : only valid for circle like shape
                if (distance <= pWidth / 2 + eWidth / 2) tmp.add(p);
            }
        }
        return tmp;
    }

    @Override
    public void handle(long now) {
        for (Particle p: particles) {
            p.move();

            double pcx = p.getCenterX();
            double pcy = p.getCenterY();
            Vec2d inertia = p.getInertia();

            if ((pcx < 0) || (pcx > maxX)) p.setInertia(-inertia.x, inertia.y);
            else if ((pcy < 0) || (pcy > maxY)) p.setInertia(inertia.x, -inertia.y);
            else {
                for (Particle coll : this.findCollusions(p)) {
                    Vec2d cInertia = coll.getInertia();
                    Vec2d oldInertia = new Vec2d(inertia);

                    inertia.set(cInertia.x, cInertia.y);
                    cInertia.set(oldInertia.x, oldInertia.y);

                   /* double norm = Math.sqrt(Math.pow(oldInertia.x, 2) + Math.pow(oldInertia.y, 2));
                    double newNorm = Math.sqrt(Math.pow(inertia.x, 2) + Math.pow(inertia.y, 2));
                    if (inertia.y != 0) {
                        double k = norm / newNorm;
                        double m = Math.sqrt(Math.pow(k, 2) * Math.pow(newNorm, 2) * Math.pow(inertia.y, 2) / (Math.pow(inertia.x, 2) + Math.pow(inertia.y, 2)));
                        double n = m * inertia.x / inertia.y;

                        inertia.set(n,m);
                    }

                    norm = Math.sqrt(Math.pow(oldCInertia.x, 2) + Math.pow(oldCInertia.y, 2));
                    newNorm = Math.sqrt(Math.pow(cInertia.x, 2) + Math.pow(cInertia.y, 2));
                    if (inertia.y != 0) {
                        double k = norm / newNorm;
                        double m = Math.sqrt(Math.pow(k, 2) * Math.pow(newNorm, 2) * Math.pow(cInertia.y, 2) / (Math.pow(cInertia.x, 2) + Math.pow(cInertia.y, 2)));
                        double n = m * cInertia.x / cInertia.y;

                        cInertia.set(n, m);
                    }*/
                }
            }
        }
    }

    static public void addParticle(@NotNull Particle p) {
        particles.add(p);
    }

    static public Vec2d getGravity() {
        return gravity;
    }

    static public void remove(@NotNull Particle particle) {
        particles.remove(particle);
    }

    static public ParticleSystem getInstance(double maxX, double maxY) {
        if (particleSystem == null) new ParticleSystem(maxX, maxY);
        return particleSystem;
    }
}
