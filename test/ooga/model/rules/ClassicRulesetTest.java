package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
import ooga.model.banks.ClassicBank;
import ooga.model.boardSpaces.NonOwnableCardSpace;
import ooga.model.boardSpaces.NonOwnableChanceCardSpace;
import ooga.model.boardSpaces.OwnablePropertySpace;
import ooga.model.dataReaders.DataReaderClassic;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.exceptions.NecessaryFileNotFound;
import ooga.model.players.InteractivePlayer;
import ooga.model.players.Player;
import ooga.model.turns.TurnModel;
import ooga.view.GameView;
import ooga.view.buttons.PlayerInputLines;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

class ClassicRulesetTest extends DukeApplicationTest {
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
    void notAllMortgagedButOnePlayerLeft() {
        ClassicRuleset rules = new ClassicRuleset();
        Player a = new InteractivePlayer();
        Player b = new InteractivePlayer();
        a.setPlayerBalance(-1);
        b.setPlayerBalance(1);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1,a);
        players.put(2,b);
        a.addToPlayerProperties(new OwnablePropertySpace("Boardwalk",100,"blue", 200, 300, new ClassicBank(players), new HashMap<>()));
        assertFalse(!rules.isGameOver(players));
    }

    @Test
    void notGameOver(){
        ClassicRuleset rules = new ClassicRuleset();
        Player a = new InteractivePlayer();
        Player b = new InteractivePlayer();
        a.setPlayerBalance(500);
        b.setPlayerBalance(1);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1,a);
        players.put(2,b);
        assertFalse(rules.isGameOver(players));
    }

    @Test
    void getWinnerGameWon(){
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
        javafxRun(()->gameView.initializeBoardView(
                dataReader.getInfoForGameViewClassInstantiations(), playerNamesAndIds, playerNamesAndGamePieceFilePaths, dataReader));
        javafxRun(()->gamePlay.initializeGame(playerIdsAndTypes, new InfoForGamePlay(properties, FILE_PATH_CLASSIC_MONOPOLY),
                gameView, gameView.getPromptable(), gameView.getErrorPrintable()));
        javafxRun(()->gameView.initializeGame(gamePlay.getModelDiceRollable(),
                gamePlay.getPropertyUpgradeable(), dataReader.getInformationForGamePlay()));
        javafxRun(()->gamePlay.startGame(gameView, dataReader.getGameStartType(), gameView.getPromptable()));
       // sleep(3, TimeUnit.SECONDS);

        Map<Integer, Player> players = gamePlay.getPlayers();
        NonOwnableCardSpace space = new NonOwnableChanceCardSpace("Chance Card Space", new ClassicBank(players));
        TurnModel turn = new TurnModel(5);
        Player current = players.get(0);

        OwnablePropertySpace property = (OwnablePropertySpace) gamePlay.getBoard().getBoardSpaceAtLocation(1);
        current.addToPlayerProperties(property);

        ClassicRuleset rules = new ClassicRuleset();
        assertEquals(0,rules.determineWinner(players));
    }

    @Test
    void getWinnerGameNotWon(){
        ClassicRuleset rules = new ClassicRuleset();
        Player a = new InteractivePlayer();
        Player b = new InteractivePlayer();
        a.setPlayerBalance(100);
        b.setPlayerBalance(1);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1,a);
        players.put(2,b);
        assertEquals(-1,rules.determineWinner(players));
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