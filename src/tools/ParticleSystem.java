package tools;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

/**
 * Created by paill on 15/12/14.
 */
public class ParticleSystem extends AnimationTimer {

    private static final Vec2d gravity = new Vec2d(0., 0.1);
    private static ArrayList<Particle> particles = new ArrayList<>();
    private static ParticleSystem particleSystem;
    private static Vec2d mouseCoordinates = new Vec2d(0.,0.);

    public static Vec2d getMouseCoordinates() {
        return mouseCoordinates;
    }

    public static void setMouseCoordinates(Vec2d mouseCoordinates) {
        ParticleSystem.mouseCoordinates = mouseCoordinates;
    }

    private ParticleSystem() {
        particleSystem = this;
    }

    @Override
    public void handle(long now) {
        for (Particle p: particles) p.update(now);
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

    static public ParticleSystem getInstance() {
        if (particleSystem == null) new ParticleSystem();
        return particleSystem;
    }
}
