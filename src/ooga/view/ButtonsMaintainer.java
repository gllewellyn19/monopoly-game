package ooga.view;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import javafx.scene.Node;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ModelDiceRollable;
import ooga.view.buttons.AccessOtherButtons;
import ooga.view.buttons.BoardInteractiveFeature;
import ooga.view.buttons.NumberPlayersChangable;
import ooga.view.gameView.DisplayInitializer;

/***
 * Highest level is abstract. Maintains the buttons for the game
 *
 * @author Grace Llewellyn
 */
public abstract class ButtonsMaintainer implements NumberPlayersChangable,
    AccessOtherButtons {

  public static final String DEFAULT_STARTING_VAL_FOLDER = "defaultStartingValues";

  /**
   * @return an initial input panel that takes user input for game set up
   */
  public abstract Node makeInitialInputPanel();

  /**
   * Creates a new input panel for capturing user input.
   * @return said input panel
   */
  public abstract Node makeInputPanel();

  /**
   * Finds and returns a certain button
   * @param buttonName ID of button in the codebase
   * @return the button's interactive feature
   */
  public abstract BoardInteractiveFeature getButton(String buttonName);

  /**
   * Set the number of players to a given value
   * @param newNumPlayers is the new number of players to set
   */
  public abstract void setNumberPlayers(int newNumPlayers);

  /**
   * Set the language of the game to a certain language
   * @param newLanguage is the language that the game will be updated to
   */
  public abstract void updateLanguage(String newLanguage);

  /**
   * Digests player information from input panels.
   * @return a map of player information indexed via player name
   */
  public abstract Map<String, List<String>> extractPlayerInformation();

  /**
   * Makes sure no two players have selected the same game piece
   * @return a check on the uniqueness of all player pieces
   */
  public abstract boolean checkAllPiecesUnique();

  /**
   * Makes sure no two player names are exactly the same
   * @return a check on the uniqueness of each player name in the
   * input panel
   */
  public abstract boolean checkAllNamesEnteredAreUnique();

  /**
   * Monitors the launch game, which begins gameplay
   */
  public abstract void checkEnableLaunchGameButton();

  /**
   * Set game resources to a new Properties object
   * @param gameValueResources is the new gameValueResources stored
   *                           by ButtonsMaintainer
   */
  public abstract void setGameValueResources(Properties gameValueResources);

  /**
   * Launches an initial dice roll
   */
  public abstract void startingRollsMode();

  /**
   * Launches a normal gameplay dice roll
   */
  public abstract void playingRollsMode();

  /**
   * Begins a new roll
   */
  public abstract void startNewRoll();

  /**
   * Initializes buttons used during gameplay
   * @param modelDiceRollable handles dice rolling
   * @param displayInitializer helps initialize button visualizations
   * @param infoForGamePlay gives information to new buttons
   */
  public abstract void initializeButtonsForGame(ModelDiceRollable modelDiceRollable,
      DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay);

  /**
   * @return number of players currently playing
   */
  public abstract int getNumberOfPlayers();

  /**
   * Makes all buttons on the screen non-interactive,
   * such that no user can press them
   */
  public abstract void disableAllButtons();

}
