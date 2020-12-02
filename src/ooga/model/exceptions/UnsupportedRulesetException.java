package ooga.model.exceptions;


/**
 * This exception is thrown if there is a problem the ruleset the user gave. This has no assumptions
 * or dependencies. An
 * example of how to use this class would be if the user enters an invalid ruleset type
 *
 * @author Grace Llewellyn
 */
public class UnsupportedRulesetException extends RuntimeException{
    public UnsupportedRulesetException(String message) {
        super(message);
    }

    public UnsupportedRulesetException(String message, Throwable cause) {
        super(message, cause);
    }
}
