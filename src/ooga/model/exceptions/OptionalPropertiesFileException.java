package ooga.model.exceptions;

/**
 * This exception is thrown if there is a problem with an optional properties file. If there is this
 * error a default value with be used. There are no assumptions or
 * dependencies. An example of how to use this class would be if the user enters diceType incorrectly
 *
 * @author Grace Llewellyn
 */
public class OptionalPropertiesFileException extends RuntimeException {

  private String propertyFileName;

  public OptionalPropertiesFileException(String propertyFileName){
    super("optionalPropertiesInvalid");
    this.propertyFileName = propertyFileName;
  }

  public String getPropertyFileName() {
    return propertyFileName;
  }

}
