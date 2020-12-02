package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javafx.stage.Stage;
import ooga.DukeApplicationTest;
import ooga.Main;
import ooga.model.dataReaders.DataReaderClassic;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.exceptions.NecessaryFileNotFound;
import ooga.view.GameView;
import ooga.view.buttons.PlayerInputLines;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

public class DataReaderTest extends DukeApplicationTest {

  public static final String FILE_PATH_CLASSIC_MONOPOLY =
          new File("").getAbsolutePath()+"/data/ClassicMonopoly";
  public static final String FILE_PATH_TESTING_MONOPOLY =
      new File("").getAbsolutePath()+"/data/TestingMonopoly";

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

  }

  @Test
  void readFile() {
    Map<Integer, String> playerIdsAndTypes = new HashMap<>();
    playerIdsAndTypes.put(0, "ComputerPlayer");
    playerIdsAndTypes.put(1, "ComputerPlayer");
    Properties properties = readInPropertiesClassicMonopoly();
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    playerNamesAndIds.put("Grace", 0);
    playerNamesAndIds.put("Cam", 1);
    Map<String, String> playerNamesAndGamePieceFilePaths = new HashMap<>();
    playerNamesAndGamePieceFilePaths.put("Grace", PlayerInputLines.COLOR_IDENTIFIER+"Orange");
    playerNamesAndGamePieceFilePaths.put("Cam", PlayerInputLines.COLOR_IDENTIFIER+"Red");

    javafxRun(()->dataReader.setGameProperties(properties));
    javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
    javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
            playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
    javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
            gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
    javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
            gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
    javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
   // sleep(3, TimeUnit.SECONDS);

    try {
      dataReader.readFile(new FileInputStream(new File("./././data/ClassicMonopoly/chance.csv")));
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }
  }


  @Test
  void getCommunityCardMessages() {
    Map<Integer, String> playerIdsAndTypes = new HashMap<>();
    playerIdsAndTypes.put(0, "ComputerPlayer");
    playerIdsAndTypes.put(1, "ComputerPlayer");
    Properties properties = readInPropertiesClassicMonopoly();
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    playerNamesAndIds.put("Grace", 0);
    playerNamesAndIds.put("Cam", 1);
    Map<String, String> playerNamesAndGamePieceFilePaths = new HashMap<>();
    playerNamesAndGamePieceFilePaths.put("Grace", PlayerInputLines.COLOR_IDENTIFIER+"Orange");
    playerNamesAndGamePieceFilePaths.put("Cam", PlayerInputLines.COLOR_IDENTIFIER+"Red");

    javafxRun(()->dataReader.setGameProperties(properties));
    javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
    javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
            playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
    javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
            gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
    javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
            gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
    javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
 //   sleep(3, TimeUnit.SECONDS);

    try {
      dataReader.getCommunityCardMessages(new File("./././data/ClassicMonopoly/communitychest.csv"));
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }
  }

  @Test
  void getChanceCardMessages() {
    Map<Integer, String> playerIdsAndTypes = new HashMap<>();
    playerIdsAndTypes.put(0, "ComputerPlayer");
    playerIdsAndTypes.put(1, "ComputerPlayer");
    Properties properties = readInPropertiesClassicMonopoly();
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    playerNamesAndIds.put("Grace", 0);
    playerNamesAndIds.put("Cam", 1);
    Map<String, String> playerNamesAndGamePieceFilePaths = new HashMap<>();
    playerNamesAndGamePieceFilePaths.put("Grace", PlayerInputLines.COLOR_IDENTIFIER+"Orange");
    playerNamesAndGamePieceFilePaths.put("Cam", PlayerInputLines.COLOR_IDENTIFIER+"Red");

    javafxRun(()->dataReader.setGameProperties(properties));
    javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
    javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
            playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
    javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
            gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
    javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
            gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
    javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
  //  sleep(3, TimeUnit.SECONDS);

    try {
      dataReader.getChanceCardMessages(new File("./././data/ClassicMonopoly/chance.csv"));
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }
  }


  @Test
  void getPropertyNames() {
    Map<Integer, String> playerIdsAndTypes = new HashMap<>();
    playerIdsAndTypes.put(0, "ComputerPlayer");
    playerIdsAndTypes.put(1, "ComputerPlayer");
    Properties properties = readInPropertiesClassicMonopoly();
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    playerNamesAndIds.put("Grace", 0);
    playerNamesAndIds.put("Cam", 1);
    Map<String, String> playerNamesAndGamePieceFilePaths = new HashMap<>();
    playerNamesAndGamePieceFilePaths.put("Grace", PlayerInputLines.COLOR_IDENTIFIER+"Orange");
    playerNamesAndGamePieceFilePaths.put("Cam", PlayerInputLines.COLOR_IDENTIFIER+"Red");

    javafxRun(()->dataReader.setGameProperties(properties));
    javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
    javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
            playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
    javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
            gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
    javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
            gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
    javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
  //  sleep(3, TimeUnit.SECONDS);

    try {
      dataReader.getPropertyNames(new File("./././data/ClassicMonopoly/americanmonopolyboard.csv"));
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }
  }

  @Test
  void getInformationForGamePlay() {
    Map<Integer, String> playerIdsAndTypes = new HashMap<>();
    playerIdsAndTypes.put(0, "ComputerPlayer");
    playerIdsAndTypes.put(1, "ComputerPlayer");
    Properties properties = readInPropertiesClassicMonopoly();
    Map<String, Integer> playerNamesAndIds = new HashMap<>();
    playerNamesAndIds.put("Grace", 0);
    playerNamesAndIds.put("Cam", 1);
    Map<String, String> playerNamesAndGamePieceFilePaths = new HashMap<>();
    playerNamesAndGamePieceFilePaths.put("Grace", PlayerInputLines.COLOR_IDENTIFIER+"Orange");
    playerNamesAndGamePieceFilePaths.put("Cam", PlayerInputLines.COLOR_IDENTIFIER+"Red");

    javafxRun(()->dataReader.setGameProperties(properties));
    javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
    javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
            playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
    javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
            gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
    javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
            gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
    javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
  //  sleep(3, TimeUnit.SECONDS);

    dataReader.getInformationForGamePlay();

  }

  @Test
  void getDisplayMoneyImagePath() {
  }

  @Test
  void createPlayerNamesAndGamePieceFiles() {
  }

  @Test
  void uploadPropertiesFolder() {
  }

  @Test
  void extractInformationPropertiesFolder() {
  }

  @Test
  void uploadGameType() {
  }

  @Test
  void testReadFile() {
  }

  private Properties readInPropertiesClassicMonopoly() {
    Properties gameValues = new Properties();
    try {
      FileInputStream fis = new FileInputStream(FILE_PATH_CLASSIC_MONOPOLY +
              DataReader.NAME_PROPERTIES_FILE + DataReader.PROPERTIES_FILE_EXTENSION);
      gameValues.load(fis);
      fis.close();
    } catch (IOException e) {
      throw new NecessaryFileNotFound("propertiesFile");
    }
    return gameValues;
  }
}