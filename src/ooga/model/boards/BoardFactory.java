package ooga.model.boards;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ooga.model.Bank;
import ooga.model.Board;
import ooga.model.Cards;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.exceptions.BoardSpaceException;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates a board object with information from the user. Uses BasicBoard as default if information
 * not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class BoardFactory {

  public static final String DEFAULT_BOARD_CLASS = "BasicBoard";
  public static final String FILE_PATH_BOARD = "ooga.model.boards.";

  public Board createBoard(Optional<String> boardType, InfoForGamePlay infoForGamePlay, Bank bank,
                           Cards cards,  ErrorPrintable errorPrintable, ModelDiceRollable modelDiceRollable,
                           int amountPassingGo) throws FileNotFoundException, BoardSpaceException {
    try {
      String boardTypeOrDefault = boardType.isEmpty() ?
          DEFAULT_BOARD_CLASS: boardType.get();
      Class boardClass = Class.forName(FILE_PATH_BOARD + boardTypeOrDefault);
      return (Board) boardClass.getDeclaredConstructor(InfoForGamePlay.class, Bank.class, Cards.class,
          ModelDiceRollable.class, Integer.class).
          newInstance(infoForGamePlay, bank, cards, modelDiceRollable, amountPassingGo);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "boardType");
      return new BasicBoard(infoForGamePlay, bank, cards, modelDiceRollable, amountPassingGo);
    }
  }

}
