
package entities;

import exceptions.ParticleIndexOutOfBoundsException;
import main.Methods;

/**
 * A class to model real-world particles
 * @author keaswar
 */
public class Particle {
    private Environment environment;
        public Environment getEnvironment() {
            return environment;
        }
    private double[] pos;
        public double[] getPos() {
            return pos;
        }
        public void setPos(double newX, double newY) {
            pos[0] = newX;
            pos[1] = newY;
        }
    private double[] vel;
        public double[] getVelocity() {
            return vel;
        }
        public void setVelocity(double newX, double newY) {
            vel[0] = newX;
            vel[1] = newY;
        }
    private double mass;
        public double getMass() {
            return mass;
        }
        public void setMass(double newMass) {
            mass = newMass;
        }
    private double radius;
        public double getRadius() {
            return radius;
        }
        public void setRadius(double newRadius) {
            radius = newRadius;
        }
    private double mu;
        public double getMu() {
            return mu;
        }
        public void setMu(double newMu) {
            mu = newMu;
        }
    private double elastic;
        public double getElasticity() {
            return elastic;
        }
        public void setElasticity(double newElasticity) {
            elastic = newElasticity;
        }
    private double charge;
        public double getCharge() {
            return charge;
        }
        public void setCharge(double newCharge) {
            charge = newCharge;
        }
    private double[] magDipole;
        public double[] getMagneticDipole() {
            return magDipole;
        }
        public void setMagneticDipole(double newDipoleX, double newDipoleY) {
            magDipole[0] = newDipoleX;
            magDipole[1] = newDipoleY;
        }
    private double contactTolerance;
        public double getContactTolerance() {
            return contactTolerance;
        }
        public void setContactTolerance(double newTolerance) {
            contactTolerance = newTolerance;
        }
    private ParticleCollection linkedList;
        public void updateLinkedList() {
            linkedList = new ParticleCollection(getEnvironment());
            for (int i=0; i<getEnvironment().getNumParticles(); i++) {
                try {
                    if (getDistanceTo(getEnvironment().getParticle(i)) <= getContactTolerance()) {
                        linkedList.addParticle(getEnvironment().getParticle(i));
                    }
                } catch (ParticleIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                    System.out.println("...this is hardcoded to not happen");
                    System.exit(0);
                }
            }
        }

    public Particle(Environment newEnvironment) {
        environment = newEnvironment;
        pos = new double[2];
        setPos(0, 0);
        vel = new double[2];
        setVelocity(0, 0);
        setMass(0);
        setRadius(0);
        setMu(0);
        setElasticity(1);
        setCharge(0);
        magDipole = new double[2];
        setMagneticDipole(0, 0);
        setContactTolerance(getRadius() * 2);
    }
    public Particle(Particle toCopy, Environment newEnvironment, boolean copyPosAndVelocity) {
        pos = new double[2];
        vel = new double[2];
        if (copyPosAndVelocity) {
            setPos(toCopy.getPos()[0], toCopy.getPos()[1]);
            setVelocity(toCopy.getVelocity()[0], toCopy.getVelocity()[1]);
        } else {
            setPos(0, 0);
            setVelocity(0, 0);
        }
        environment = newEnvironment;
        setMass(toCopy.getMass());
        setRadius(toCopy.getRadius());
        setMu(toCopy.getMu());
        setElasticity(toCopy.getElasticity());
        setCharge(toCopy.getCharge());
        magDipole = new double[2];
        setMagneticDipole(toCopy.getMagneticDipole()[0], toCopy.getMagneticDipole()[1]);
        setContactTolerance(toCopy.getContactTolerance());
    }
    public boolean equals(Particle otherParticle) {
        return getClass().equals(otherParticle.getClass())
            && Methods.isAlmostEqual(getMass(), otherParticle.getMass())
            && Methods.isAlmostEqual(getRadius(), otherParticle.getRadius())
            && Methods.isAlmostEqual(getMu(), otherParticle.getMu())
            && Methods.isAlmostEqual(getElasticity(), otherParticle.getElasticity())
            && Methods.isAlmostEqual(getCharge(), otherParticle.getCharge())
            && Methods.isAlmostEqual(getMagneticDipole()[0], otherParticle.getMagneticDipole()[0])
            && Methods.isAlmostEqual(getMagneticDipole()[1], otherParticle.getMagneticDipole()[1]);
    }

    public double getDistanceTo(Particle otherParticle) {
        return Math.sqrt(Math.pow(otherParticle.getPos()[0] - getPos()[0], 2)
                       + Math.pow(otherParticle.getPos()[1] - getPos()[1], 2));
    }
    private boolean isHeadingAwayFrom(Particle otherParticle) {
        return Methods.vectorDot(Methods.vectorSubtract(getVelocity(), otherParticle.getVelocity()),
               Methods.vectorSubtract(otherParticle.getPos(), getPos())) <= 0;
    }
    public boolean isTouching(Particle otherParticle) {
        return getDistanceTo(otherParticle) <= getRadius() + otherParticle.getRadius();
    }
    public boolean isTouchingSomething() {
        for (int i=0; i<getEnvironment().getNumParticles(); i++) {
            try {
                if (isTouching(getEnvironment().getParticle(i)) && this != getEnvironment().getParticle(i)) {
                    return true;
                }
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
        return isOnSide();
    }
    public boolean isOnSide() {
        return isOnLeftSide() || isOnRightSide() || isOnTopSide() || isOnBottomSide();
    }
    private boolean isOnLeftSide() {
        return getPos()[0] - getRadius() <= 0;
    }
    private boolean isOnRightSide() {
        return getPos()[0] + getRadius() >= getEnvironment().getWidth();
    }
    private boolean isOnTopSide() {
        return getPos()[1] - getRadius() <= 0;
    }
    private boolean isOnBottomSide() {
        return getPos()[1] + getRadius() >= getEnvironment().getHeight();
    }

    public void update(double dt) {
        updateLinkedList();
        updateVelocity(dt);
        updatePos(dt);
    }
    private void updatePos(double dt) {
        setPos(getPos()[0] + getVelocity()[0]*dt, getPos()[1] + getVelocity()[1]*dt);
    }
    private void updateVelocity(double dt) {
        double[] Fnet = getNetForce();
        setVelocity(getVelocity()[0] + Fnet[0]*dt/getMass(),
                    getVelocity()[1] + Fnet[1]*dt/getMass());
    }
    public double[] getNetForce() {
        //return getForceFromEnvironment();
        return Methods.vectorAdd(getForceFromEnvironment(), getForceFromAllParticles());
    }
    private double[] getForceFromEnvironment() {
        double[] force = new double[2];
        if (isOnTopSide() && getVelocity()[1] < 0) {
            force[1] = Math.abs(getVelocity()[1] * getMass() * Math.pow(getPos()[1], 2) / .05);
        }
        if (isOnBottomSide() && getVelocity()[1] > 0) {
            force[1] = -1 * Math.abs(getVelocity()[1] * getMass() * Math.pow(getPos()[1] - getEnvironment().getHeight(), 2) / .05);
        }
        if (isOnLeftSide() && getVelocity()[0] < 0) {
            force[0] = Math.abs(getVelocity()[0] * getMass() * Math.pow(getPos()[0], 2) / .05);
        }
        if (isOnRightSide() && getVelocity()[0] > 0) {
            force[0] = -1 * Math.abs(getVelocity()[0] * getMass() * Math.pow(getPos()[0] - getEnvironment().getWidth(), 2) / .05);
        }
        return force;
    }
    private double[] getForceFromAllParticles() {
        double[] force = new double[2];
        for (int i=0; i<linkedList.getNumParticles(); i++) {
            try {
                force = Methods.vectorAdd(force, getContactForceFrom(linkedList.getParticle(i)));
            } catch (ParticleIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
//        for (int i=0; i<getEnvironment().getNumParticles(); i++) {
//            try {
//                force = Methods.vectorAdd(force, getElectricForceFrom(getEnvironment().getParticle(i)), getMagneticForceFrom(getEnvironment().getParticle(i)));
//            } catch (ParticleIndexOutOfBoundsException ex) {
//                ex.printStackTrace();
//                System.out.println("...this is hardcoded to not happen");
//                System.exit(0);
//            }
//        }
        return force;
    }
    private double[] getElectricForceFrom(Particle otherParticle) {
        return new double[2];
        //double mag = 0;
        //double mag = Methods.ONE_BY_FOUR_PI_EPSILON_0 * getCharge() * otherParticle.getCharge() / Math.pow(getDistanceTo(otherParticle), 2);
        //return Methods.vectorMultiple(-1 * mag, Methods.vectorUnit(Methods.vectorSubtract(otherParticle.getPos(), getPos())));
    }
    private double[] getMagneticForceFrom(Particle otherParticle) {
        return new double[2];
    }
    private double[] getContactForceFrom(Particle otherParticle) {
        //double[] force = new double[2];
        //if (isTouching(otherParticle) && !isHeadingAwayFrom(otherParticle)) {
            
        //}
        return new double[2];
    }
}
