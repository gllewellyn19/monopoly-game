package ooga.model.exceptions;

/**
 * This exception is thrown if there is a necessary file is missing. There are no assumptions or
 * dependencies. An example of how to use this class would be if the user does not have a properties
 * file in their game folder
 *
 * @author Grace Llewellyn
 */
public class NecessaryFileNotFound extends RuntimeException{

  private String fileName;

  public NecessaryFileNotFound(String fileName){
    super("missingRequiredFile");
    this.fileName = fileName;
  }

  public String getFileName() {
    return fileName;
  }

}
