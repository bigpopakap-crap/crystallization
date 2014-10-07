
package entities;

import exceptions.ParticleIndexOutOfBoundsException;

/**
 * A collection of the Particle class
 * @see entities.Particle
 * @author keaswar
 */
public class ParticleCollection {
    private Environment environment;
        public Environment getEnvironment() {
            return environment;
        }
    private Particle[] collection;
        public Particle getParticle(int index) throws ParticleIndexOutOfBoundsException {
            try {
                return collection[index];
            } catch (IndexOutOfBoundsException ex) {
                throw new ParticleIndexOutOfBoundsException(index);
            }
        }
    private int num;
        public int getNumParticles() {
            return num;
        }

    public ParticleCollection(Environment newEnvironment) {
        environment = newEnvironment;
        num = 0;
        collection = new Particle[500];
    }

    public void addParticle(Particle newParticle) {
        if (isFull()) {
            increaseSize();
        }
        collection[num] = newParticle;
        num++;
    }

    private int getCapacity() {
        return collection.length;
    }
    private boolean isFull() {
        return getNumParticles() == getCapacity();
    }
    private void increaseSize() {
        Particle[] temp = new Particle[2*getCapacity()];
        for (int i=0; i<getNumParticles(); i++) {
            try {
                temp[i] = getParticle(i);
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
        collection = temp;
    }

}
