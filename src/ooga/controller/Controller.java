package ooga.controller;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import ooga.model.Board;
import ooga.model.DataReader;
import ooga.model.GamePlay;
import ooga.model.dataReaders.DataReaderFactory;
import ooga.model.gamePlay.GamePlayFactory;
import ooga.view.ButtonsMaintainer;
import ooga.view.GameView;
import ooga.view.buttons.PlayerInputLines;
import ooga.view.gameView.GameViewFactory;

/***
 * Connects the front end and the back and calls the ooga.model.DataReader to read in data.
 * Instantiates the game as well. This class makes no assumptions since it creates GamePlay and
 * GameView through a factory and reads in the classes from the default properties file (uses
 * defaults if not there). GamePlay and GameView depend on this method to correctly create and
 * instantiate them. You can use this class by instantiating in the main class so that it can create
 * the GameView and GamePlay.
 *
 * @author Grace Llewellyn and Anna Diemel
 */
public class Controller implements SetUpable {

  public static final String RESOURCES = "resources/";
  public static final String DEFAULT_RESOURCES_PACKAGE = RESOURCES.replace("/", ".");
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  public static final Dimension DEFAULT_SIZE = new Dimension(1100, 800);
  public static final Dimension DEFAULT_SIDE_SIZE = new Dimension(150,800);

  private final GamePlay gamePlay;
  private final GameView gameView;
  private final DataReader dataReader;
  public Board board;

  public Controller(SceneControls sceneControls) {
    Optional<Properties> defaultProperties = extractDefaultPropertiesFolder();
    gamePlay = new GamePlayFactory().createGamePlay(defaultProperties);
    DataReaderFactory dataReaderFactory = new DataReaderFactory();
    dataReader = dataReaderFactory.createDataReader(defaultProperties);
    gameView = new GameViewFactory().createGameView(defaultProperties, sceneControls, this,
        dataReader, gamePlay);
    dataReader.addInterfaces(gameView, gameView.getErrorPrintable(), gameView);
  }

  /*
   *
   */

  /**
   * Extracts the default properties folder that is in. If it isn't present then the optionals
   * throughout the code are used
   *
   * @return Returns the default properties file if it is present
   */
  public Optional<Properties> extractDefaultPropertiesFolder() {
    Properties gameValues = new Properties();
    try {
      String currentPath = new File("").getAbsolutePath();
      FileInputStream fis = new FileInputStream(currentPath +"/src" + Controller.DEFAULT_RESOURCE_FOLDER +
          ButtonsMaintainer.DEFAULT_STARTING_VAL_FOLDER + "/" +
          ButtonsMaintainer.DEFAULT_STARTING_VAL_FOLDER + DataReader.PROPERTIES_FILE_EXTENSION);
      gameValues.load(fis);
      fis.close();
    } catch (IOException e) {
      return Optional.empty();
    }
    return Optional.of(gameValues);
  }

  public GamePlay getGamePlay() { return gamePlay; }

  public GameView getGameView() {
    return gameView;
  }

  public DataReader getDataReader() { return dataReader; }

  /**
   * Sets up the game by getting the information from DataReader about the players
   */
  @Override
  public void setUpGame() {
    Map<String, List<String>> playerInformation = gameView.extractPlayerInformation();
    Map<String, String> playerNamesAndGamePieceFiles = dataReader.createPlayerNamesAndGamePieceFiles(playerInformation);
    Map<String, String> playerNamesAndTypes = createPlayerNamesAndTypes(playerInformation);
    Map<String, Integer> playerNamesAndIds = createPlayerNamesAndIds(playerNamesAndTypes);
    Map<Integer, String> playersIdsAndTypes = createPlayerIdsAndTypes(playerNamesAndTypes,
        playerNamesAndIds);
    instantiateGame(playerNamesAndIds, playersIdsAndTypes, playerNamesAndGamePieceFiles);
  }

  /*
   * Creates a mapping of player ids to the type of player (interactive, computer player, etc.)
   */
  private Map<Integer, String> createPlayerIdsAndTypes(Map<String, String> playerNamesAndTypes,
      Map<String, Integer> playerNamesAndIds) {
    Map<Integer, String> playerIdsAndType = new HashMap<>();
    for (Map.Entry<String, Integer> player: playerNamesAndIds.entrySet()) {
      playerIdsAndType.put(player.getValue(), playerNamesAndTypes.get(player.getKey()));
    }
    return playerIdsAndType;
  }

  /**
   * Instantiates the game by calling the initialize methods for GamePlay and GameView with the
   * correct arguments
   *
   * @param playerNamesAndIds Mapping of the player names to their unique IDs
   * @param playersIdsAndTypes Mapping of the unique player IDs to the player types (interactive,
   *                           computer player, etc.)
   * @param playerNamesAndGamePieceFilePaths Mapping og the player names to the file paths of their
   *                                         game pieces
   */
  public void instantiateGame(Map<String, Integer> playerNamesAndIds, Map<Integer, String>
      playersIdsAndTypes, Map<String, String> playerNamesAndGamePieceFilePaths) {

    gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
        playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader);
    board = gamePlay.getBoard();

    gamePlay.initializeGame(playersIdsAndTypes, dataReader.getInformationForGamePlay(),
        gameView, gameView.getPromptable(), gameView.getErrorPrintable());
    gameView.initializeGame(gamePlay.getModelDiceRollable(),gamePlay.getPropertyUpgradeable(),
        dataReader.getInformationForGamePlay());
    playGame();
  }

  /**
   * Should only be used in main for the cheat key. Sets up a game with default values that are
   * gotten from the default properties file
   */
  public void setUpDefaultGame() {
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    Map<String, String> playerNamesAndTypes = new HashMap<>();
    Map<String, String> playerNamesAndGamePieceFilePaths = new HashMap<>();

    List<String[]> playerInfo = dataReader.getDefaultPlayerInformation();
    for (String[] player : playerInfo) {
      playerNamesAndIds.put(player[0], Integer.parseInt(player[1]));
      playerNamesAndTypes.put(player[0], player[2]);
      playerNamesAndGamePieceFilePaths.put(player[0], player[3]);
    }

    Map<Integer, String> playersIdsAndTypes = createPlayerIdsAndTypes(playerNamesAndTypes,
        playerNamesAndIds);
    instantiateGame(playerNamesAndIds, playersIdsAndTypes, playerNamesAndGamePieceFilePaths);
  }

  /**
   * Starts a game by telling the game play to start a game
   */
  public void playGame() {
    gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable());
  }

  /*
   * Creates the player names and ids mapping from the player names and types
   */
  private Map<String, String> createPlayerNamesAndTypes(Map<String, List<String>> playerInformation) {
    Map<String, String> playerNamesAndTypes = new HashMap<>();
    for (Map.Entry<String,List<String>> entry : playerInformation.entrySet()) {
      playerNamesAndTypes.put(entry.getKey(), entry.getValue().get(PlayerInputLines.INDEX_PLAYER_TYPE));
    }
    return playerNamesAndTypes;
  }

  /*
   * Creates the player names and ids mapping from the player names and types
   */
  private Map<String, Integer> createPlayerNamesAndIds(Map<String, String> playerNamesAndTypes) {
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    int playerID = 0;
    for (Map.Entry<String,String> entry : playerNamesAndTypes.entrySet()) {
      playerNamesAndIds.put(entry.getKey(), playerID);
      playerID++;
    }
    return playerNamesAndIds;
  }

  /**
   * Ends the current turn and moves to the next player
   */
  public void endTurnAndMoveToNextPlayer() {
    gamePlay.moveToNextPlayer();
  }
}
