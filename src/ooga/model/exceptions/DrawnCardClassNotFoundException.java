package ooga.model.exceptions;

/**
 * This exception is thrown if there is a problem with a drawn card image. There are no assumptions or
 * dependencies. An example of how to use this class would be if the game tries to pull a card
 * that is not valid. This error should not really be called because no invalid cards should be
 * in the deck
 *
 * @author Delaney Demark
 */
public class DrawnCardClassNotFoundException extends RuntimeException{
    public DrawnCardClassNotFoundException(String message) {
        super(message);
    }

    public DrawnCardClassNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
