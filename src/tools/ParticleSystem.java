package tools;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Particle> findCollisions(Particle encounter) {
        List<Particle> tmp = particles.stream().filter(p -> p.hasCollision(encounter)).collect(Collectors.toList());
        return tmp;
    }

    @Override
    public void handle(long now) {
        particles.forEach(tools.Particle::move);
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

    static public ParticleSystem getInstance() {
        if (particleSystem == null) new ParticleSystem(1024, 1024);
        return particleSystem;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMaxY() {
        return this.maxY;
    }
}
