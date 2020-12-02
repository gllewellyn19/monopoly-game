package ooga.view.gameView;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ooga.controller.Controller;

public class ErrorPrinting implements ErrorPrintable {

  private final LanguageControls languageControls;

  public ErrorPrinting(LanguageControls languageControls) {
    this.languageControls = languageControls;
  }

  /**
   * Formulates an error to be printed.
   * @param key gets exception type
   * @param additionalMessage describes the exception instance
   */
  public void printErrorMessageAlertWithStringFormat(String key, String additionalMessage) {
    try {
      Alert errorAlert = new Alert(AlertType.ERROR);
      ResourceBundle exceptions = ResourceBundle.getBundle(Controller.DEFAULT_RESOURCES_PACKAGE +
          ClassicGameView.EXCEPTIONS_FOLDER + languageControls.getLanguage().toLowerCase() + ClassicGameView.EXCEPTIONS_FILE);
      errorAlert.setHeaderText(exceptions.getString("headerForExceptionsAlerts"));
      errorAlert.setContentText(String.format(exceptions.getString(key), additionalMessage));
      errorAlert.showAndWait();
    } catch (MissingResourceException e) {
      defaultAlertIfNoExceptionsBundle();
    }
  }

  /**
   * @param key - key contained in exception .properties file with exception message as value Prints
   *            the given error message as an alert. Given a key that corresponds to a resource
   *            bundle with the correct error message
   */
  public void printErrorMessageAlert(String key) {
    createAlert(key, "");
  }

  /**
   * @param key-              key contained in exception .properties file with exception message as
   *                          value
   * @param additionalMessage - additional message to be printed when exception is caught Creates
   *                          and display an alert with the given message
   */
  private void createAlert(String key, String additionalMessage) {
    try {
      Alert errorAlert = new Alert(AlertType.ERROR);
      ResourceBundle exceptions = ResourceBundle.getBundle(Controller.DEFAULT_RESOURCES_PACKAGE +
          ClassicGameView.EXCEPTIONS_FOLDER + languageControls.getLanguage().toLowerCase() + ClassicGameView.EXCEPTIONS_FILE);
      errorAlert.setHeaderText(exceptions.getString("headerForExceptionsAlerts"));
      errorAlert.setContentText(exceptions.getString(key) + additionalMessage);
      errorAlert.setResizable(true);
      errorAlert.showAndWait();
    } catch (MissingResourceException e) {
      defaultAlertIfNoExceptionsBundle();
    }
  }

  /**
   * @param key-              key contained in exception .properties file with exception message as
   *                          value
   * @param additionalMessage - additional message to be printed when exception is caught Prints the
   *                          given error message as an alert. Given a key that corresponds to a
   *                          resource bundle with the correct error message and prints the
   *                          additional message with a space between that and the original message
   *                          key
   */
  public void printErrorMessageAlert(String key, String additionalMessage) {
    createAlert(key, " " + additionalMessage);
  }

  /*
   * The default alert if cannot find the exceptions bundle because cannot get the exceptions to
   * print so must use default values
   */
  private void defaultAlertIfNoExceptionsBundle() {
    Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.setContentText(ClassicGameView.DEFAULT_MESSAGE_MISSING_EXCEPTIONS);
    errorAlert.showAndWait();
  }


}
