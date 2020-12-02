package ooga.model.exceptions;

/**
 * This exception is thrown if there is a general model problem. There are no assumptions or
 * dependencies. An example of how to use this class would be if the user enters an empty board
 *
 * @author Grace Llewellyn
 */
public class ModelException extends RuntimeException{

  public ModelException(String message){
    super(message);
  }

}
