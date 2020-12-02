package ooga.view.gameView;

/**
 * This interface allows a class to print errors as alerts. This class has no assumptions or dependencies.
 * An example of how to use this class is give it to the select properties folder button
 * so that it can print an error if there is one
 *
 * @author Grace Llewellyn
 */
public interface ErrorPrintable {

  /**
   * Prints an error message alert with the key
   *
   * @param key key to reference in the exceptions bundle
   */
  void printErrorMessageAlert(String key);
  /**
   * Prints an error message alert putting the additional message with the key in between a space
   *
   * @param key key to reference in the exceptions bundle
   * @param additionalMessage additional message to add to the key
   */
  void printErrorMessageAlert(String key, String additionalMessage);

  /**
   * Prints an error message alert putting the additional message in the key with String.format()
   * @param key key to reference in the exceptions bundle
   * @param additionalMessage additional message to add to the key
   */
  void printErrorMessageAlertWithStringFormat(String key, String additionalMessage);

}
