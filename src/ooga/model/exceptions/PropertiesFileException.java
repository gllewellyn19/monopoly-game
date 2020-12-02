package ooga.model.exceptions;

/**
 * This exception is thrown if there is a problem with a properties value. There are no assumptions or
 * dependencies. An example of how to use this class would be if the user enters a the boardPath
 * wrong
 *
 * @author Grace Llewellyn
 */
public class PropertiesFileException extends RuntimeException {

  private String propertyFileName;

  public PropertiesFileException(String propertyFileName){
    super("invalidProperties");
    this.propertyFileName = propertyFileName;
  }

  public String getPropertyFileName() {
    return propertyFileName;
  }

}
