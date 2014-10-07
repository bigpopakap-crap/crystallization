
package entities;

import exceptions.ParticleIndexOutOfBoundsException;

/**
 * A model of an environment of solute and solvent particles that uses ParticleCollections
 * to calculate and update the positions of particles through time
 * @author keaswar
 */
public class Environment {
    private double[] dimension;
        public double getWidth() {
            return dimension[0];
        }
        public double getHeight() {
            return dimension[1];
        }
    private ParticleCollection solutes;
    private ParticleCollection solvents;
        public ParticleCollection getSoluteCollection() {
            return solutes;
        }
        public ParticleCollection getSolventCollection() {
            return solvents;
        }
        public int getNumParticles() {
            return solutes.getNumParticles() + solvents.getNumParticles();
        }
        public Particle getParticle(int index) throws ParticleIndexOutOfBoundsException {
            if (index < solutes.getNumParticles()) {
                return solutes.getParticle(index);
            } else {
                return solvents.getParticle(index - solutes.getNumParticles());
            }
        }

    public Environment(double xWidth, double yHeight) {
        dimension = new double[2];
            dimension[0] = xWidth;
            dimension[1] = yHeight;
        solutes = new ParticleCollection(this);
        solvents = new ParticleCollection(this);
    }
    public Environment(double xWidth, double yHeight,
                       Particle modelSolute, int numSolutes,
                       Particle modelSolvent, int numSolvents) {
        this(xWidth, yHeight);
        for (int i=0; i<numSolutes; i++) {
            getSoluteCollection().addParticle(new Particle(modelSolute, this, false));
        }
        for (int i=0; i<numSolvents; i++) {
            getSolventCollection().addParticle(new Particle(modelSolvent, this, false));
        }
    }

    public void updateLinkedLists() {
        for (int i=0; i<getNumParticles(); i++) {
            try {
                getParticle(i).updateLinkedList();
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
    }

    /**
     * @return an array of length [numParticles]x[3] which contains the following
     * information about each corresponding particle:
     * [0]: xCoordinate,
     * [1]: yCoordinate, and
     * [2]: radius
     */
    public double[][] getCoordinates() {
        double[][] out = new double[solutes.getNumParticles() + solvents.getNumParticles()][3];
        for (int i=0; i<solutes.getNumParticles(); i++) {
            try {
                out[i][0] = solutes.getParticle(i).getPos()[0];
                out[i][1] = solutes.getParticle(i).getPos()[1];
                out[i][2] = solutes.getParticle(i).getRadius();
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
        for (int i=0; i<solvents.getNumParticles(); i++) {
            try {
                out[solutes.getNumParticles() + i][0] = solvents.getParticle(i).getPos()[0];
                out[solutes.getNumParticles() + i][1] = solvents.getParticle(i).getPos()[1];
                out[solutes.getNumParticles() + i][2] = solvents.getParticle(i).getRadius();
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
        return out;
    }

    /**
     * Randomized the velocities of the all particles uniformly
     * from 0 to a given maximum magnitude
     * @param maxMag the maxiumum magnitude
     */
    public void shake(double maxMag) {
        double newX;
        double newY;
        for (int i=0; i<getNumParticles(); i++) {
            newX = Math.random()*2*maxMag - maxMag;
            newY = Math.random()*2*Math.sqrt(Math.pow(maxMag, 2) - Math.pow(newX, 2)) - Math.sqrt(Math.pow(maxMag, 2) - Math.pow(newX, 2));
            try {
                getParticle(i).setVelocity(newX, newY);
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
    }
    public void shakeSolutes(double maxMag) {
        double newX;
        double newY;
        for (int i=0; i<getSoluteCollection().getNumParticles(); i++) {
            newX = Math.random()*2*maxMag - maxMag;
            newY = Math.random()*2*Math.sqrt(Math.pow(maxMag, 2) - Math.pow(newX, 2)) - Math.sqrt(Math.pow(maxMag, 2) - Math.pow(newX, 2));
            try {
                getSoluteCollection().getParticle(i).setVelocity(newX, newY);
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
    }
    public void shakeSolvents(double maxMag) {
        double newX;
        double newY;
        for (int i=0; i<getSolventCollection().getNumParticles(); i++) {
            newX = Math.random()*2*maxMag - maxMag;
            newY = Math.random()*2*Math.sqrt(Math.pow(maxMag, 2) - Math.pow(newX, 2)) - Math.sqrt(Math.pow(maxMag, 2) - Math.pow(newX, 2));
            try {
                getSolventCollection().getParticle(i).setVelocity(newX, newY);
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
    }
    /**
     * Randomizes the positions of all particles uniformly over the area,
     * but keeps each of their current velocities the same as before
     */
    public void scatter() {
        double newX;
        double newY;
        for (int i=0; i<getNumParticles(); i++) {
            newX = Math.random() * getWidth();
            newY = Math.random() * getHeight();
            try {
                getParticle(i).setPos(newX, newY);
                if (getParticle(i).isTouchingSomething()) {
                    i--;
                }
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
    }

    public void heat(double newKelvin) {
        throw new UnsupportedOperationException();
    }

    public void update(double dt) {
        for (int i=0; i<getNumParticles(); i++) {
            try {
                getParticle(i).update(dt);
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
    }

}
