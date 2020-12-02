package ooga.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javafx.scene.Node;
import javafx.stage.Stage;
import ooga.DukeApplicationTest;
import ooga.Main;
import ooga.model.dataReaders.DataReaderClassic;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.exceptions.NecessaryFileNotFound;
import ooga.view.GameView;
import ooga.view.buttons.PlayerInputLines;
import ooga.view.gameView.ClassicGameView;
import ooga.view.gameView.ErrorPrinting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

class GamePlayTest extends DukeApplicationTest {

  private Main main;
  private GamePlay gamePlay;
  private GameView gameView;
  private DataReader dataReader;

  @Start
  public void start (Stage stage) {
    main = new Main();
    main.start(stage);
    gamePlay = main.getController().getGamePlay();
    gameView = main.getController().getGameView();
    dataReader = new DataReaderClassic();
    //main.getController().setUpGame();

  }

  @Test
  void buyProperty() {
    Map<Integer, String> playerIdsAndTypes = new HashMap<>();
    playerIdsAndTypes.put(0, "InteractivePlayer");
    playerIdsAndTypes.put(1, "InteractivePlayer");
    Properties properties = readInPropertiesClassicMonopoly();
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    playerNamesAndIds.put("Grace", 0);
    playerNamesAndIds.put("Cam", 1);
    Map<String, String> playerNamesAndGamePieceFilePaths = new HashMap<>();
    playerNamesAndGamePieceFilePaths.put("Grace", PlayerInputLines.COLOR_IDENTIFIER+"Orange");
    playerNamesAndGamePieceFilePaths.put("Cam", PlayerInputLines.COLOR_IDENTIFIER+"Red");

    javafxRun(()->dataReader.setGameProperties(properties));
    javafxRun(()->dataReader.setFolderPath(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY));
    javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
        playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
    javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY),
            gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
    javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
            gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
    javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
    //sleep(3, TimeUnit.SECONDS);

  }

  //bug fix test
  @Test
  void diceTypeIncorrect() {
    Map<Integer, String> playerIdsAndTypes = new HashMap<>();
    playerIdsAndTypes.put(0, "InteractivePlayer");
    playerIdsAndTypes.put(1, "InteractivePlayer");
    Properties properties =  readInPropertiesTestingMonopoly();
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    playerNamesAndIds.put("Grace", 0);
    playerNamesAndIds.put("Cam", 1);
    Map<String, String> playerNamesAndGamePieceFilePaths = new HashMap<>();
    playerNamesAndGamePieceFilePaths.put("Grace", PlayerInputLines.COLOR_IDENTIFIER+"Orange");
    playerNamesAndGamePieceFilePaths.put("Cam", PlayerInputLines.COLOR_IDENTIFIER+"Red");

    javafxRun(()->dataReader.setGameProperties(properties));
    javafxRun(()->dataReader.setFolderPath(DataReaderTest.FILE_PATH_TESTING_MONOPOLY));
    javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
        playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
    javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, DataReaderTest.FILE_PATH_TESTING_MONOPOLY),
        gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
    javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
        gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
    javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
    //sleep(3, TimeUnit.SECONDS);
    Assertions.assertFalse(getDialogMessage().contains(ClassicGameView.DEFAULT_MESSAGE_MISSING_EXCEPTIONS));
  }


  @Test
  void getCurrentPlayer() {
  }

  @Test
  void moveToNextPlayer() {
  }

  @Test
  void getCurrentPlayerId() {
  }

  @Test
  void determineGameOver() {
  }

  @Test
  void getWinningPlayerID() {
  }

  @Test
  void getBoardSpaceLocationAndSendPlayerThere() {
  }

  @Test
  void sendPlayerToJail() {
  }

  @Test
  void movePlayerUsingDiceRoll() {
  }

  @Test
  void movePlayerToSpace() {
  }

  @Test
  void doActionOnCurrentLocation() {
  }

  @Test
  void getNumPlayers() {
  }

  @Test
  void startPlayingGame() {
  }

  @Test
  void startGame() {
  }

  @Test
  void determiningStartingPlayerId() {
  }

  @Test
  void getBoard() {
  }

  @Test
  void moveCurrentPlayer() {
  }

  @Test
  void getModelDiceRollable() {
  }

  @Test
  void initializeGame() {
  }

  private Properties readInPropertiesTestingMonopoly() {
    Properties gameValues = new Properties();
    try {
      FileInputStream fis = new FileInputStream(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY +
          DataReader.NAME_PROPERTIES_FILE + DataReader.PROPERTIES_FILE_EXTENSION);
      gameValues.load(fis);
      fis.close();
    } catch (IOException e) {
      throw new NecessaryFileNotFound("propertiesFile");
    }
    return gameValues;
  }

  private Properties readInPropertiesClassicMonopoly() {
    Properties gameValues = new Properties();
    try {
      FileInputStream fis = new FileInputStream(DataReaderTest.FILE_PATH_TESTING_MONOPOLY +
          DataReader.NAME_PROPERTIES_FILE + DataReader.PROPERTIES_FILE_EXTENSION);
      gameValues.load(fis);
      fis.close();
    } catch (IOException e) {
      throw new NecessaryFileNotFound("propertiesFile");
    }
    return gameValues;
  }
}