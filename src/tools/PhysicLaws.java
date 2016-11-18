package tools;

/**
 * Not used for the moment, but the purpose is to implements
 * physical laws to influence movements and bouncing of the
 * particles in the particle's system.
 *
 * @author Treiber Julien
 */
public class PhysicLaws {
    // Newton_first_law
    // uniform_motion : an object that is at rest stay at rest unless an external force acts upon it.
    //                  an object in motion will not change its velocity unless an external force acts upon it.
    // sum_of_forces = 0 is equivalent to dv/dt = cte

    // Newton_second_law
    // Net force applied
    // F = dp/dt = d(mv)/dt is equivalent to F = m * dv/dt as m is a constant (mass of the body).
    // dv/dt = body's acceleration.

    // Newton_third_law
    // Repulsion
    // One body (A) on another body (B) does not just collapse into the void. FA = -FB.

    // Newton_gravitation_law
    //
    // F = G * ((m1 * m2) / (r * r))
    // where
    // F: the forces between the masses
    // G: the gravitational constant (6.673 * (10 ^ -11) N.(m/kg)^2)
    // m1: the first mass
    // m2: the second mass
    // r: the distance between the centers of the masses.

    // Vector form
    // F12 = -G * ((m1 * m2) / (|r12|^2)) * ^r12
    //
    // where
    // F12: the force applied on obj2 due to obj1
    // G: the gravitational constant (6.673 * (10 ^ -11) N.(m/kg)^2)
    // m1: the mass of obj1
    // m2: the mass of obj2
    // |r12| = |r2 - r1| = distance btw obj1 and obj2
    // ^r12  = (r2 - r1) / (|r1 - r2|) = unit vector from obj1 to obj2
    // F12 = -F21

    //
    // ####### GRAVITATION FIELD ##################
    // gravitational_acceleration:
    //
    // g(r12) = -G * m1 / (|r12|^2) * ^r12
    // F(r12) = m2 * g(r12)
    // g(r12) = - delta V(r) // gravitational fields are conservative
    // V(r) = -G * m1 / r // force field outside a sphere is isotropic i.e. depends only on the distance r from
    //                      the center of the sphere.
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
}
