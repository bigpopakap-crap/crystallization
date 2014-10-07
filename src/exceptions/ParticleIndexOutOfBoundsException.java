
package exceptions;

/**
 * thrown when a requested particle does not exist. Used to replace
 * IndexOutOfBoundsException.
 * @author keaswar
 */
public class ParticleIndexOutOfBoundsException extends CrystallizationException {
    private boolean isSet = false;
    private int attemptedIndex;

    public ParticleIndexOutOfBoundsException(int attemptedIndex) {
        isSet = true;
        this.attemptedIndex = attemptedIndex;
    }

    @Override
    public String getMessage() {
        if (isSet) {
            return "No particle in index " + attemptedIndex;
        } else {
            return "No particle in requested index";
        }
    }

}
