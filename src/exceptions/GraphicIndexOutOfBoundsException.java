
package exceptions;

public class GraphicIndexOutOfBoundsException extends CrystallizationException {
    private boolean isSet = false;
    private int attemptedIndex;

    public GraphicIndexOutOfBoundsException(int newIndex) {
        isSet = true;
        attemptedIndex = newIndex;
    }

    @Override
    public String getMessage() {
        if (isSet) {
            return "No frame in index " + attemptedIndex;
        } else {
            return "No frame in requested index";
        }
    }

}
