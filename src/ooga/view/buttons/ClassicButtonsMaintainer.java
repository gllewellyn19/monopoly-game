package ooga.view.buttons;

/**
 * This purpose of this class is to create the buttons and create the displays that show the buttons
 * This class also needs to extract information from these buttons and check that this information
 * is valid. I think that this class is well designed for numerous reasons. It creates a map of all
 * of the buttons of type BoardInteractiveFeature so that they can be kept in the same map. This
 * follows dependency inversion principle because the highest level of abstraction does not care
 * about the details of how
 * the buttons are implemented. It also gives some of its responsibility to the ButtonDisplayCreator
 * class to better follow single responsibility principle. It gives responsibility to player input
 * lines to deal with the information the players input about themselves (name, player type and
 * player piece) which follows single responsibility principle. This class follows open closed
 * principle by not assuming that any specific buttons are going to be in the button mapping. This
 * class follows Listov substitution principle because it can be replaced with its abstract class
 * (ButtonsMaintainer) and not break the code. This code also handles some complex features like
 * the starting rolls which I describe in detail in my analysis. While this code does have public
 * setters, it does checks on these values before setting them. Also these methods need to be public
 * since they are implemented by interfaces even though the buttons that use them are in this package
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Scene;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ModelDiceRollable;
import ooga.Main;
import ooga.controller.Controller;
import ooga.model.exceptions.PropertiesFileException;
import ooga.view.ButtonsMaintainer;
import ooga.view.gameView.ClassicGameView;
import ooga.view.gameView.DisplayInitializer;

/**
 * Classic monopoly implementation for the buttons maintainer
 *
 * @author Grace Llewellyn
 */
public class ClassicButtonsMaintainer extends ButtonsMaintainer {

  private final Map<String, BoardInteractiveFeature> buttons;
  private PlayerInputLines playerInputLines;
  private Properties gameValueResources;
  private boolean startingRolls = false;
  private final Optional<Properties> defaultProperties;
  private final InterfacesForButtons interfacesForButtons;
  private final ButtonDisplayCreator buttonDisplayCreator;

  private int numPlayers = 0;

  public ClassicButtonsMaintainer(InterfacesForButtons interfacesForButtons, Optional<Properties> defaultProperties) {
    interfacesForButtons.uploadAccessToOtherButtons(this);
    interfacesForButtons.uploadNumberPlayersChangable(this);
    this.interfacesForButtons = interfacesForButtons;
    this.defaultProperties = defaultProperties;
    playerInputLines = new PlayerInputLines(interfacesForButtons.getErrorPrintable());
    buttonDisplayCreator = new ButtonDisplayCreator(defaultProperties);
    buttons = buttonDisplayCreator.createAllButtons(getPropertiesFromLanguage(
        interfacesForButtons.getLanguageControls().getLanguage()), interfacesForButtons,
        gameValueResources);
  }

  /**
   * Make the initial input panel which lets the user select the language and set the buttons in
   * the middle of the screen
   * @return the input panel
   */
  public Node makeInitialInputPanel() {
    return buttonDisplayCreator.makeInitialInputPanel(buttons, interfacesForButtons,
        getPropertiesFromLanguage(interfacesForButtons.getLanguageControls().getLanguage()),
        playerInputLines, gameValueResources);
  }

  private ResourceBundle getPropertiesFromLanguage(String language) {
    return ResourceBundle.getBundle(Controller.DEFAULT_RESOURCES_PACKAGE +
        ClassicGameView.LANGUAGE_FOLDER + language.toLowerCase());
  }

  /**
   * Creates the buttons for the top navigation panel and adds them and disables the correct ones
   * for a start of game setting
   * Returns the node for the input panel using the starting rolls and buttons for the panel
   */
  public Node makeInputPanel() {
    return buttonDisplayCreator.makeInputPanel(startingRolls, buttons);
  }

  /**
   * Returns the requested button based on the string type of the button- aligns with what the
   * button is called in the resource properties file
   * @param buttonName name of button to be returned
   * @return the requested button
   */
  public BoardInteractiveFeature getButton(String buttonName) {
    BoardInteractiveFeature buttonToReturn = buttons.get(buttonName);
    if (buttonToReturn == null) {
      interfacesForButtons.getErrorPrintable().printErrorMessageAlert("buttonNotFound", buttonName);
    }
    return buttonToReturn;
  }

  /**
   * Public setter but purpose is to make player selection dynamic and only one button is given
   * access to this. Checks to make sure the number of players is not negative or 0
   * @param newNumPlayers number of players to be set to
   */
  public void setNumberPlayers(int newNumPlayers) {
    if (newNumPlayers > 0) {
      numPlayers = newNumPlayers;
      Optional<Scene> scene = interfacesForButtons.getCreateCSSControls().makeAnInitialScene(
          Main.DEFAULT_SIZE.width, Main.DEFAULT_SIZE.height);
      scene.ifPresent(interfacesForButtons.getSceneControls()::setScene);
    }
  }

  /**
   * Updates the language of all of the interactive features of the problem
   * @param newLanguage - string representing new language for button
   */
  public void updateLanguage(String newLanguage) {
    ResourceBundle newLanguageResources = getPropertiesFromLanguage(newLanguage);
    for (Map.Entry<String,BoardInteractiveFeature> button : buttons.entrySet()){
      button.getValue().updateLanguage(newLanguageResources);
    }
  }

  /**
   * Extracts the player information when the launch game button is clicked and sends it to model
   * to create the players
   * @return the player information from the splash screen
   */
  public Map<String, List<String>> extractPlayerInformation() {
    try {
      return playerInputLines.extractPlayerInformation();
    } catch (PropertiesFileException e) {
      interfacesForButtons.getErrorPrintable().printErrorMessageAlert(e.getMessage());
    }
    return new HashMap<>();
  }

  /**
   * Prints an exception if all the player pieces are not unique and returns false so that the user
   * cannot move on
   * @return if the player pieces are unique
   */
  public boolean checkAllPiecesUnique() {
    return playerInputLines.checkAllPiecesUnique();
  }

  /**
   * Returns true if all the names are entered are unique and false otherwise. Does not require the
   * user to have entered all names
   * @return if the names of players are unique
   */
  public boolean checkAllNamesEnteredAreUnique() {
    return playerInputLines.checkAllNamesEnteredAreUnique();
  }

  /**
   * Determines if should enable the launch game button
   */
  public void checkEnableLaunchGameButton(){
    if (buttons.containsKey("LaunchGameButton")) {
      buttons.get("LaunchGameButton").getCurrInteractiveFeature().setDisable(
          playerInputLines.checkEnableLaunchGameButton());
    }
  }

  /**
   * Sets the new game value resources and recreates all the buttons so that it is applied
   */
  public void setGameValueResources(Properties gameValueResources) {
    this.gameValueResources = gameValueResources;
    Optional<Scene> scene = interfacesForButtons.getCreateCSSControls().
        makeAnInitialScene(Main.DEFAULT_SIZE.width, Main.DEFAULT_SIZE.height);
    scene.ifPresent(interfacesForButtons.getSceneControls()::setScene);
  }

  /**
   * Starting rolls means that the players see the button that says starting rolls and does that
   * action
   */
  public void startingRollsMode() {
    startingRolls = true;
    Optional<Scene> scene = interfacesForButtons.getCreateCSSControls().makeASceneWithInitialCSS();
    scene.ifPresent(interfacesForButtons.getSceneControls()::setScene);
  }

  /**
   * Normal play mode of the game where the user can roll the dice
   */
  public void playingRollsMode() {
    startingRolls = false;
    Optional<Scene> scene = interfacesForButtons.getCreateCSSControls().makeASceneWithInitialCSS();
    scene.ifPresent(interfacesForButtons.getSceneControls()::setScene);
  }

  /**
   * Enables the buttons so that the user can roll and you cannot end the turn yet
   */
  public void startNewRoll() {
    if (startingRolls) {
      buttons.get("StartingDiceRollButton").getCurrInteractiveFeature().setDisable(false);
    }
    else {
      buttons.get("RollDiceButton").getCurrInteractiveFeature().setDisable(false);
    }
    buttons.get("EndTurnButton").getCurrInteractiveFeature().setDisable(true);
  }

  /**
   * Uploads the modelDiceRollable because is initialized after gamePlay has been initialized
   */
  public void initializeButtonsForGame(ModelDiceRollable modelDiceRollable,
      DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay) {
    interfacesForButtons.uploadModelDiceRollable(modelDiceRollable);
    buttonDisplayCreator.uploadModelDiceRollable(buttons, interfacesForButtons,
        getPropertiesFromLanguage(interfacesForButtons.getLanguageControls().getLanguage()));
    buttonDisplayCreator.uploadDisplayInitializer(buttons, interfacesForButtons,
        getPropertiesFromLanguage(interfacesForButtons.getLanguageControls().getLanguage()),
        displayInitializer, infoForGamePlay);
  }

  /**
   * @return the number of players in the game
   */
  public int getNumberOfPlayers() {
    return numPlayers;
  }

  /**
   * disables all of the buttons
   */
  @Override
  public void disableAllButtons() {
    for (Map.Entry<String, BoardInteractiveFeature> button: buttons.entrySet()) {
      button.getValue().getCurrInteractiveFeature().setDisable(true);
    }
  }
}