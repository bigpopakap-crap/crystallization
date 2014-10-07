
package main;

import entities.Environment;
import entities.Particle;
import exceptions.ParticleIndexOutOfBoundsException;
import main.gui.Window;

public class Main {

    public static void printVitalStatistics(Environment e) {
        for (int i=0; i<e.getSoluteCollection().getNumParticles(); i++) {
            try {
                System.out.println("particle " + i + "):\n\t POS<" + e.getSoluteCollection().getParticle(i).getPos()[0] + ", " + e.getSoluteCollection().getParticle(i).getPos()[1] + ">" +
                                                       "\n\t VEL<" + e.getSoluteCollection().getParticle(i).getVelocity()[0] + ", " + e.getSoluteCollection().getParticle(i).getVelocity()[1] + ">" +
                                                       "\n\t FRC<" + e.getSoluteCollection().getParticle(i).getNetForce()[0] + ", " + e.getSoluteCollection().getParticle(i).getNetForce()[1] + ">" +
                                                       "\n\t SIDE: " + e.getSoluteCollection().getParticle(i).isOnSide());
            } catch (ParticleIndexOutOfBoundsException ex) {
                System.out.println("error in: " + i);
            }
        }
        System.out.println();
    }
    public static void printParticle0Statistics(Environment e) {
        try {
            System.out.println("particle " + 0 + "):\n\t POS<" + e.getSoluteCollection().getParticle(0).getPos()[0] + ", " + e.getSoluteCollection().getParticle(0).getPos()[1] + ">" +
                                                   "\n\t VEL<" + e.getSoluteCollection().getParticle(0).getVelocity()[0] + ", " + e.getSoluteCollection().getParticle(0).getVelocity()[1] + ">" +
                                                   "\n\t FRC<" + e.getSoluteCollection().getParticle(0).getNetForce()[0] + ", " + e.getSoluteCollection().getParticle(0).getNetForce()[1] + ">" +
                                                   "\n\t SIDE: " + e.getSoluteCollection().getParticle(0).isOnSide() +
                                                   "\n\t TOUCHING SOMETHING: " + e.getSoluteCollection().getParticle(0).isTouchingSomething() +
                                                   "\n\t CHARGE: " + e.getSoluteCollection().getParticle(0).getCharge());
        } catch (ParticleIndexOutOfBoundsException ex) {
            System.out.println("error in: " + 0);
        }
    }

    /*
     * todo right away:
     *  - Environment.heat(double newKelvin)
     *      - needs: Particle.getAverageVelocity(double newKelvin)
     *      - will set all velocities to getAverageVelocity(newKelvin) with some random element
     *  - Physics of collisons
     *      - force from wall
     *      - contact force from other particles
     *      - other forces from other particles

    /*
     * this is only a 2D environment
     * forces from walls are calculated with the goal that v=<0,0> after .05s
     * forces from other particles are calculated as if the others are walls
     * does not include:
     *  - interparticle electric force is now just <0,0> always
     *  - interparticle magnetic force is now just <0,0> always
     *  - having magnetic fields in environment
     *  - elasticities are not taken into account (all particles are perfectly hard)
     *  - friction is not taken into account
     *  - spins of particles
     */

    public static void main(String[] args) {
        Particle soluteTemplate = new Particle(null);
            soluteTemplate.setCharge(0);
            soluteTemplate.setElasticity(0);
            soluteTemplate.setMagneticDipole(0, 0);
            soluteTemplate.setMass(1);
            soluteTemplate.setMu(0);
            soluteTemplate.setPos(0, 0);
            soluteTemplate.setRadius(.15);
            soluteTemplate.setVelocity(0, 0);
            soluteTemplate.setContactTolerance(soluteTemplate.getRadius() * 2);
        Particle solventTemplate = new Particle(null);
            solventTemplate.setCharge(0);
            solventTemplate.setElasticity(0);
            solventTemplate.setMagneticDipole(0, 0);
            solventTemplate.setMass(.1);
            solventTemplate.setMu(0);
            solventTemplate.setPos(0, 0);
            solventTemplate.setRadius(.01);
            solventTemplate.setVelocity(0, 0);
            solventTemplate.setContactTolerance(soluteTemplate.getRadius() * 2);

        Environment environment = new Environment(5, 5, soluteTemplate, 50, solventTemplate, 1000);
            environment.scatter();
            environment.shakeSolvents(1.5);
            environment.shakeSolutes(.5);

        Window window = new Window(environment, 675, 675);
            window.setDtLenght(.01);
     }

}
