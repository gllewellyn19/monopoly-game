package ooga.view.gameView;

import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.controller.Controller;
import ooga.controller.SceneControls;
import ooga.controller.SetUpable;
import ooga.model.DataReader;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dataReaders.InfoForGameViewClassInstantiations;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.exceptions.ModelException;
import ooga.model.gamePlay.PropertyUpgradeable;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.*;
import ooga.view.buttons.ButtonsMaintainerFactory;
import ooga.view.buttons.InterfacesForButtons;
import ooga.view.players.DisplayPlayer;

/***
 * Maintains all of the ooga.view for the game including the board and the graphics of the Cards
 *
 * @author Cameron Jarnot, Grace Llewellyn, Anna Diemel, Lina Leyhausen, Delaney Demark
 */
public class ClassicGameView extends GameView {

  public static final String STARTING_STYLESHEET = "Default.css";
  public static final String DEFAULT_STARTING_LANGUAGE = "English";
  public static final String STYLESHEETS_FOLDER = "stylesheets/";
  public static final String CSS_FILE_EXTENSION = ".css";
  public static final String LANGUAGE_FOLDER = "languages/";
  public static final String EXCEPTIONS_FOLDER = "exceptions/";
  public static final String EXCEPTIONS_FILE = "Exceptions";
  public static final String DEFAULT_MESSAGE_MISSING_EXCEPTIONS = "Cannot find resource bundle for "
      + "exceptions";
  public static final int STARTING_LOCATION = 0;
  public static final int STARTING_FONT_SIZE = 30;
  public static final int STARTING_FUNDS = 1500;

  private String language = DEFAULT_STARTING_LANGUAGE;
  private final ButtonsMaintainer buttonsMaintainer;
  private final Text topText;
  private Properties gameValueResources;
  private final DataReader dataReader;
  private ResourceBundle languageResources = ResourceBundle.getBundle(
      Controller.DEFAULT_RESOURCES_PACKAGE + LANGUAGE_FOLDER + this.language);
  private ModelDiceRollable modelDiceRollable;
  private int startingFunds;
  private String boardData;
  private final Prompter prompter;
  private final DisplayInitializer displayInitializer;
  private final ErrorPrinting errorPrinting;
  private boolean setUpSuccessful = true;

  public ClassicGameView(SceneControls sceneControls, SetUpable setUpable, DataReader
      dataReader, TurnableModel turnableModel,
      Optional<Properties> defaultProperties) {
    errorPrinting = new ErrorPrinting(this);
    buttonsMaintainer = new ButtonsMaintainerFactory().createButtonsMaintainer(new InterfacesForButtons(
        this, this, errorPrinting, sceneControls, setUpable,
        dataReader, this, turnableModel), defaultProperties, errorPrinting);
    this.dataReader = dataReader;
    startingFunds = STARTING_FUNDS;
    boardData = "data/ClassicMonopoly/boardspaceimages.csv";
    displayInitializer = new DisplayInitializer(errorPrinting);
    topText = new Text();
    topText.setFont(new Font(STARTING_FONT_SIZE));
    prompter = new Prompter(this);
  }

  /**
   * initializes everything in the board view (money, board, cards, etc)
   * stops the game if initializing the board view fails
   */
  public void initializeBoardView(InfoForGameViewClassInstantiations infoForGameViewClassInstantiations,
      Map<String, Integer> playerNamesAndIds,
      Map<String, String> playerNamesAndGamePieceFiles, DataReader dataReader) {
    try {
      displayInitializer.initializeBoardView(infoForGameViewClassInstantiations,
          playerNamesAndIds, playerNamesAndGamePieceFiles, dataReader,
          boardData, startingFunds, errorPrinting, dataReader.getInformationForDice());
    } catch (ModelException e) {
      errorPrinting.printErrorMessageAlert(e.getMessage());
      stopGame();
    }
  }

  /**
   * Create a scene from the given style sheet
   *
   * @param cssFile - string with name of wanted css file
   * @return - a javafx scene
   */
  public Optional<Scene> makeASceneWithCSS(String cssFile) {
    return displayInitializer.makeASceneWithCSS(cssFile, topText, buttonsMaintainer,
        errorPrinting, boardData, dataReader);
  }

  /**
   * @param cardID is the ID of the card to be drawn
   * Adds the drawn card to the side panel of the board screen
   */
  public void addDrawnCardToScreen(int cardID) {
    Rectangle drawnCard = displayInitializer.getDisplayCards().showDrawnCard(cardID);
    if (drawnCard != null) {
      displayInitializer.getDisplayMoney().getRightPane().getChildren().add(drawnCard);
    }
  }

  /**
   * @param width  - width of new scene
   * @param height -  height of new scene
   * @return - starting scene with the default css that appears in the center of the screen
   */
  public Optional<Scene> makeAnInitialScene(int width, int height) {
    return displayInitializer.makeAnInitialScene(width, height, errorPrinting, topText,
        gameValueResources, languageResources, buttonsMaintainer);
  }

  /**
   * Changes language of board and text objects in the scene
   * @param language is new language to use
   */
  @Override
  public void updateLanguage(String language) {
    this.language = language;
    try {
      languageResources = ResourceBundle.getBundle(Controller.DEFAULT_RESOURCES_PACKAGE +
              LANGUAGE_FOLDER + this.language);
      if (gameValueResources != null) { topText.setText(languageResources.getString("startingMessage") + " " +
          gameValueResources.getProperty("gameName"));
      } else {
        topText.setText(
            languageResources.getString("startingMessage") + " " + languageResources.getString(
                "startingGame"));
      }
      buttonsMaintainer.updateLanguage(language);
    } catch (MissingResourceException e) {
      errorPrinting.printErrorMessageAlert(e.getMessage());
    }
  }

  /**
   * @return the current language used and shown by the game
   */
  @Override
  public String getLanguage() {
    return language;
  }

  /**
   *
   * @return a new scene with the styling of the starting screen
   */
  @Override
  public Optional<Scene> makeASceneWithInitialCSS() {
    return makeASceneWithCSS(STARTING_STYLESHEET);
  }

  /**
   *
   * @param cssFile is the new stylesheet for the game to use
   * @return a new scene that uses the given CSS stylesheet
   */
  @Override
  public Optional<Scene> changeCSS(String cssFile) {
    return makeASceneWithCSS(cssFile + CSS_FILE_EXTENSION);
  }

  /**
   * Calls the buttons maintainer to extract the player information in a map
   */
  public Map<String, List<String>> extractPlayerInformation() {
    return buttonsMaintainer.extractPlayerInformation();
  }

  /**
   *
   * @param diceRoll is the value of the dice that
   * @param playerId rolled. Displays the value of the roll and
   *                 the related player name at the top of the board
   */
  @Override
  public void rollDice(List<Integer> diceRoll, int playerId) {
    topText.setText(displayInitializer.getPlayers().get(playerId).getPlayerName() + ": " +
        languageResources.getString("rollingDice") + " " + modelDiceRollable.sumDiceRoll(diceRoll));
    displayInitializer.getDisplayDice().rollDice(diceRoll);
  }

  /**
   * Moves a player piece on the board.
   * @param newLocation is location player piece moves to
   * @param playerId is the unique ID of the player to be moved
   */
  @Override
  public void movePlayer(int newLocation, int playerId) {
    DisplayPlayer playerToken = displayInitializer.getPlayers().get(playerId);
    int location = playerToken.getLocation();
    Pane currPane = displayInitializer.getDisplayBoard().getNthPane(location);
    currPane.getChildren().remove(playerToken.getGamePiece());
    displayInitializer.addPlayerToNthPane(playerToken, newLocation);
  }

  /**
   * Updates the displayed money value for all players.
   * @param playerBalances is the new balances of every player in
   *                       order of ID
   */
  @Override
  public void updateMoney(List<Integer> playerBalances) {
    for (int i = 0; i < playerBalances.size(); i++) {
      displayInitializer.getDisplayMoney().updateBalance(i, playerBalances.get(i));
    }
  }

  /**
   * Shows the user a message at the top of the screen
   * Specifies which player the message relates tox
   */
  @Override
  public void showUserTopTextMessage(String message, int playerId) {
    topText.setText(displayInitializer.getPlayers().get(playerId).getPlayerName() + ": " +
        languageResources.getString(message));
  }

  /**
   * @return the ButtonsMaintainer used by the ClassicGameView class
   */
  public ButtonsMaintainer getButtons() {
    return buttonsMaintainer;
  }

  /**
   * Changes properties object used by the class
   * @param gameValueResources is the new Properties file to use
   */
  @Override
  public void setGameValueResources(Properties gameValueResources) {
    this.gameValueResources = gameValueResources;
    buttonsMaintainer.setGameValueResources(gameValueResources);
    startingFunds = Integer.parseInt(gameValueResources.getProperty("startingFunds"));
    boardData = "data" + gameValueResources.getProperty("dataDirectory") + gameValueResources
        .get("boardSpacesPath");
    try {
      topText.setText(this.languageResources.getString("startingMessage") + " " +
          gameValueResources.getProperty("gameName"));
    } catch (MissingResourceException e) {
      errorPrinting.printErrorMessageAlert(e.getMessage());
    }
  }

  /**
   * @return the player id associated with the given name and returns empty optional if not found
   */
  @Override
  public Optional<Integer> getPlayerIdFromName(String playerName) {
    for (Map.Entry<Integer, DisplayPlayer> player : displayInitializer.getPlayers().entrySet()) {
      if (playerName.equals(player.getValue().getPlayerName())) {
        return Optional.of(player.getKey());
      }
    }
    errorPrinting.printErrorMessageAlert("invalidPlayerName");
    return Optional.empty();
  }

  /**
   * Starts the rolls procedure for the game
   * @param firstPlayerRolling is the first player to roll
   */
  @Override
  public void startingRollsMode(int firstPlayerRolling) {
    buttonsMaintainer.startingRollsMode();
    updateCurrentUser(firstPlayerRolling);
  }

  /**
   * Initiates normal play mode
   */
  @Override
  public void playingRollsMode() {
    buttonsMaintainer.playingRollsMode();
    buttonsMaintainer.startNewRoll();
  }

  /**
   * called everytime new roll starting- tells which user should roll and enables the right buttons
   * @param newId is the which user should roll
   */
  @Override
  public void updateCurrentUser(int newId) {
    showUserTopTextMessage("turnToRoll", newId);
    buttonsMaintainer.startNewRoll();
  }

  /**
   * Intializes the gameView.
   * @param modelDiceRollable Interface to roll the dice
   * @param propertyUpgradeable Interface to upgrade properties
   * @param infoForGamePlay
   * Are all informational and relational classes utilized by ClassicGameView.
   * This changes for a given game.
   */
  public void initializeGame(ModelDiceRollable modelDiceRollable,
      PropertyUpgradeable propertyUpgradeable, InfoForGamePlay infoForGamePlay) {
    this.modelDiceRollable = modelDiceRollable;
    buttonsMaintainer.initializeButtonsForGame(this.modelDiceRollable, displayInitializer, infoForGamePlay);
    PropertyUpgrade propertyUpgrade = new PropertyUpgrade(propertyUpgradeable);
  }

  /**
   *
   * @return the language resources used in the game
   */
  public ResourceBundle getLanguageResources() {
    return languageResources;
  }

  /**
   * @return a map of all players where the key is the integer unique ID and the
   * associated value is the DisplayPlayer, or player visualization
   */
  public Map<Integer, DisplayPlayer> getPlayers() {
    return displayInitializer.getPlayers();
  }

  /**
   * @return board visualization (DisplayBoard) used by the game
   */
  public DisplayBoard getDisplayBoard() {
    return displayInitializer.getDisplayBoard();
  }

  /**
   * @return card visualization (DisplayCards) used by the game
   */
  @Override
  public DisplayCards getDisplayCards() {
    return displayInitializer.getDisplayCards();
  }

  /**
   * @return Promptable prompt controller used by the game
   */
  public Promptable getPromptable() {
    return prompter;
  }

  /**
   * @return ErrorPrintable error handler used by the game
   */
  @Override
  public ErrorPrintable getErrorPrintable() {
    return errorPrinting;
  }

  /**
   * Stops the game. Players can no longer input and interact with the game
   */
  @Override
  public void stopGame() {
    setUpSuccessful = false;
    buttonsMaintainer.disableAllButtons();
  }

  /**
   * gets the money visualization used by the class
   * @return money visualization, DisplayMoney
   */
  @Override
  public DisplayMoney getMoney() {
    return displayInitializer.getDisplayMoney();
  }

  /**
   * @return setUpSuccessful, which describes the validity of the current gameView
   */
  public boolean getSetUpSuccessful() {
    return setUpSuccessful;
  }
}
