package ooga.view.buttons;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates board choice drop down and implemented by all drop downs in the game
 *
 * @author Grace Llewellyn
 */
public abstract class BoardChoiceBox extends BoardInteractiveFeature{

  private final ChoiceBox<String> currButton;
  private final ErrorPrintable errorPrintable;
  private final Properties gameValueResources;
  private final Optional<Properties> defaultProperties;
  private boolean setUpSuccessful = true;

  public BoardChoiceBox(ResourceBundle resources, ChoiceBox<String> b, String id, ErrorPrintable
      errorPrintable, Properties gameValueResources, Optional<Properties> defaultProperties){
    super(resources, id);
    currButton = b;
    this.errorPrintable = errorPrintable;
    this.gameValueResources = gameValueResources;
    this.defaultProperties = defaultProperties;
  }

  /**
   * Initialize button functionality
   */
  protected void initializeButton(){
    currButton.setId(super.getId());
  }

  public Node getCurrInteractiveFeature() {
    return currButton;
  }

  /**
   * @return ChoiceBox<String> button
   */
  public ChoiceBox<String> getCurrButton() { return currButton; }

  /**
   * @return value string of the button you want
   */
  public String getValue() {
    return currButton.getValue();
  }

  /**
   * uses error printable interface to print the error message
   * @param key for error
   */
  protected void printError(String key) {
    errorPrintable.printErrorMessageAlert(key);
  }

  /**
   * uses error printable interface with a key and a message to print error messgae
   * @param key for error
   * @param additionalMessage additional error message
   */
  protected void printError(String key, String additionalMessage) {
    errorPrintable.printErrorMessageAlert(key, additionalMessage);
  }

  /**
   * Initializes the current button with choices from the default starting value file (i.e. player
   * types and player pieces uses this). Uses the given default list if not available
   */
  protected void initializeButtonWithResourceString(String propertyKey) {
    String[] potentialItems;
    try {
      potentialItems = gameValueResources.getProperty(propertyKey).split(",");
    } catch (NullPointerException | MissingResourceException e) {
      potentialItems = useDefaultResources(propertyKey);
    }
    currButton.getItems().clear();
    currButton.getItems().addAll(potentialItems);
  }

  /**
   * Returns an array of the potential items from the default resources and returns an empty list
   * if it could not be found after printing an error
   */
  private String[] useDefaultResources(String propertyKey) {
    String[] potentialItems;
    if (defaultProperties.isPresent() && defaultProperties.get().getProperty(propertyKey) != null) {
      potentialItems = defaultProperties.get().getProperty(propertyKey).split(",");
    }
    else {
      potentialItems = new String[0];
      errorPrintable.printErrorMessageAlert("missingDefaultProperties", propertyKey);
      setUpSuccessful = false;
    }
    return potentialItems;
  }

  /**
   * @return if the required property key was not found
   */
  public boolean propertyKeyNotFound() {
    return !setUpSuccessful;
  }


}
