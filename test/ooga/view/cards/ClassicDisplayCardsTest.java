package ooga.view.cards;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import ooga.DukeApplicationTest;
import ooga.Main;
import ooga.model.DataReader;
import ooga.model.GamePlay;
import ooga.model.dataReaders.DataReaderClassic;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.exceptions.NecessaryFileNotFound;
import ooga.view.DisplayCards;
import ooga.view.GameView;
import ooga.view.buttons.PlayerInputLines;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

class ClassicDisplayCardsTest extends DukeApplicationTest {

    public static final String FILE_PATH_CLASSIC_MONOPOLY =
            new File("").getAbsolutePath()+"/data/ClassicMonopoly";

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
    void showDrawnCard() {
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
        javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
        javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
                playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
        javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
                gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
        javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
                gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
        javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
     //   sleep(3, TimeUnit.SECONDS);

        DisplayCards cards = gameView.getDisplayCards();
        javafxRun(() -> cards.showDrawnCard(0));
    }

    @Test
    void getDeckImages() {
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
        javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
        javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
                playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
        javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
                gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
        javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
                gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
        javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
     //   sleep(3, TimeUnit.SECONDS);

        DisplayCards cards = gameView.getDisplayCards();
        javafxRun(() -> cards.getDeckImages(dataReader));
    }

    @Test
    void getChanceDeckImage() {
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
        javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
        javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
                playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
        javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
                gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
        javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
                gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
        javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
    //    sleep(3, TimeUnit.SECONDS);

        DisplayCards cards = gameView.getDisplayCards();
        javafxRun(() -> cards.getChanceDeckImage(dataReader));
    }

    @Test
    void getCommunityChestDeckImage() {
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
        javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
        javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
                playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
        javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
                gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
        javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
                gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
        javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
     //   sleep(3, TimeUnit.SECONDS);

        DisplayCards cards = gameView.getDisplayCards();
        javafxRun(() -> cards.getCommunityChestDeckImage(dataReader));
    }

    @Test
    void initializeCards() {
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
        javafxRun(()->dataReader.setFolderPath(FILE_PATH_CLASSIC_MONOPOLY));
        javafxRun(()->gameView.initializeBoardView(dataReader.getInfoForGameViewClassInstantiations(),
                playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
        javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
                gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
        javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
                gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
        javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
      //  sleep(3, TimeUnit.SECONDS);

        DisplayCards cards = gameView.getDisplayCards();
        javafxRun(() -> cards.initializeCards(dataReader));

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