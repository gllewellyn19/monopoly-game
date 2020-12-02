package ooga.model.exceptions;

/**
 * This exception is thrown if there is a problem setting up the cards. There are no assumptions or
 * dependencies. An example of how to use this class would be if the user enters a card in the csv
 * with an invalid type
 *
 *  @author Delaney Demark
 */
public class CardDataNotFound extends RuntimeException{
    public CardDataNotFound(String message) {
        super(message);
    }

    public CardDataNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
