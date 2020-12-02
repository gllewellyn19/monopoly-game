package ooga.model.exceptions;

/**
 * This exception is thrown if there is a problem with a card image. There are no assumptions or
 * dependencies. An example of how to use this class would be if the user enters a card image path
 * in the csv that is invalid
 *
 * @author Delaney Demark
 */
public class CardImageNotFoundException extends RuntimeException{

    public CardImageNotFoundException(String message) {
        super(message);
    }

    public CardImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
