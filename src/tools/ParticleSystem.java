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
    private ArrayList<Particle> particles = new ArrayList<>();

    public ParticleSystem() {
    }

    @Override
    public void handle(long now) {
        //System.out.println(particles.size());
        for (Particle p: particles) {
            p.update();
        }
    }

    public void addParticle(@NotNull Particle p) {
        this.particles.add(p);
    }

    public static Vec2d getGravity() {
        return gravity;
    }

    public void remove(@NotNull Particle particle) {
        this.particles.remove(particle);
    }
}
