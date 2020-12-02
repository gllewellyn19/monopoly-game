package ooga.view;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import ooga.model.DataReader;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dataReaders.InfoForGameViewClassInstantiations;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.gamePlay.PropertyUpgradeable;
import ooga.view.dice.ViewDiceRollable;
import ooga.view.gameView.CreateCSSControls;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.ExtractPlayerInformationable;
import ooga.view.gameView.GetInformationForPrompter;
import ooga.view.gameView.LanguageControls;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.SetGameValueResourceable;
import ooga.view.gameView.StopGameAble;
import ooga.view.gameView.TurnableView;
import ooga.view.players.DisplayPlayer;

/***
 * Highest level is abstract. Maintains everything related to the view of the game
 *
 * @author Grace Llewellyn
 */
public abstract class GameView implements LanguageControls, CreateCSSControls,
    ExtractPlayerInformationable, TurnableView, SetGameValueResourceable, ViewDiceRollable,
    GetInformationForPrompter, StopGameAble {

  /**
   * initializes everything in the board view (money, board, cards, etc)
   * stops the game if initializing the board view fails
   */
  public abstract void initializeBoardView(
      InfoForGameViewClassInstantiations infoForGameViewClassInstantiations,
      Map<String, Integer> playerNamesAndIds,
      Map<String, String> playerNamesAndGamePieceFiles, DataReader dataReader);

  /**
   * Create a scene from the given style sheet
   *
   * @param cssFile - string with name of wanted css file
   * @return - a javafx scene
   */
  public abstract Optional<Scene> makeASceneWithCSS(String cssFile);

  /**
   * @param cardID is the ID of the card to be drawn
   * Adds the drawn card to the side panel of the board screen
   */
  public abstract void addDrawnCardToScreen(int cardID);

  /**
   * @param width  - width of new scene
   * @param height -  height of new scene
   * @return - starting scene with the default css that appears in the center of the screen
   */
  public abstract Optional<Scene> makeAnInitialScene(int width, int height);

  /**
   * Changes language of board and text objects in the scene
   * @param language is new language to use
   */
  public abstract void updateLanguage(String language);

  /**
   * @return the current language used and shown by the game
   */
  public abstract String getLanguage();

  /**
   *
   * @return a new scene with the styling of the starting screen
   */
  public abstract Optional<Scene> makeASceneWithInitialCSS();

  /**
   *
   * @param cssFile is the new stylesheet for the game to use
   * @return a new scene that uses the given CSS stylesheet
   */
  public abstract Optional<Scene> changeCSS(String cssFile);

  /**
   * Calls the buttons maintainer to extract the player information in a map
   */
  public abstract Map<String, List<String>> extractPlayerInformation();

  /**
   *
   * @param diceRoll is the value of the dice that
   * @param playerId rolled. Displays the value of the roll and
   *                 the related player name at the top of the board
   */
  public abstract void rollDice(List<Integer> diceRoll, int playerId);

  /**
   * Shows the user a message at the top of the screen
   * Specifies which player the message relates tox
   */
  public abstract void showUserTopTextMessage(String message, int playerId);

  /**
   * @return the ButtonsMaintainer used by the ClassicGameView class
   */
  public abstract ButtonsMaintainer getButtons();

  /**
   * Changes properties object used by the class
   * @param gameValueResources is the new Properties file to use
   */
  public abstract void setGameValueResources(Properties gameValueResources);

  /**
   * @return the player id associated with the given name and returns empty optional if not found
   */
  public abstract Optional<Integer> getPlayerIdFromName(String playerName);

  /**
   * Starts the rolls procedure for the game
   * @param firstPlayerRolling is the first player to roll
   */
  public abstract void startingRollsMode(int firstPlayerRolling);

  /**
   * Initiates normal play mode
   */
  public abstract void playingRollsMode();

  /**
   * called everytime new roll starting- tells which user should roll and enables the right buttons
   * @param newId is the which user should roll
   */
  public abstract void updateCurrentUser(int newId);

  /**
   * Intializes the gameView.
   * @param modelDiceRollable interface to roll the dice in model
   * @param propertyUpgradeable interface to upgrade properties
   * @param infoForGamePlay
   * Are all informational and relational classes utilized by ClassicGameView.
   * This changes for a given game.
   */
  public abstract void initializeGame(ModelDiceRollable modelDiceRollable,
      PropertyUpgradeable propertyUpgradeable, InfoForGamePlay infoForGamePlay);

  /**
   *
   * @return the language resources used in the game
   */
  public abstract ResourceBundle getLanguageResources();

  /**
   * @return a map of all players where the key is the integer unique ID and the
   * associated value is the DisplayPlayer, or player visualization
   */
  public abstract Map<Integer, DisplayPlayer> getPlayers();

  /**
   * @return board visualization (DisplayBoard) used by the game
   */
  public abstract DisplayBoard getDisplayBoard();

  /**
   * @return card visualization (DisplayCards) used by the game
   */
  public abstract DisplayCards getDisplayCards();

  /**
   * @return Promptable prompt controller used by the game
   */
  public abstract Promptable getPromptable();

  /**
   * @return ErrorPrintable error handler used by the game
   */
  public abstract ErrorPrintable getErrorPrintable();

  /**
   * Stops the game. Players can no longer input and interact with the game
   */
  public abstract void stopGame();

  /**
   * gets the money visualization used by the class
   * @return money visualization, DisplayMoney
   */
  public abstract DisplayMoney getMoney();

}
