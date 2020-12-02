package ooga.model.exceptions;

/**
 * This exception is thrown if the user tries to do a card action that is not supported by the game.
 * There are no assumptions or
 * dependencies. An example of how to use this class would be if the user pulls a card that does
 * not have a supported action
 *
 * @author Delaney Demark
 */
public class UnsupportedCardActionException extends RuntimeException{
    public UnsupportedCardActionException(String message) {
        super(message);
    }

    public UnsupportedCardActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
