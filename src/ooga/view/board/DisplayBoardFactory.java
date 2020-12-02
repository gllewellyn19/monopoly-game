package ooga.view.board;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ooga.view.DisplayBoard;
import ooga.view.gameView.ErrorPrintable;

/**
 * This class assists ClassicDisplayBoard by creating a default DisplayBoard with either the given
 * style of board or the default of a classic monopoly board
 *
 * @author Grace Llewellyn
 */
public class DisplayBoardFactory {

  public static final String DEFAULT_DISPLAY_BOARD_CLASS = "ClassicDisplayBoard";
  public static final String FILE_PATH_DISPLAY_BOARD = "ooga.view.board.";

  /**
   * Factory method for creating a DisplayBoard instance.
   * Current default is ClassicDisplayBoard, but is open for extension
   * @param displayBoardType is optional DisplayBoardType; defaults
   *                         to ClassicDisplayBoard if param is empty
   * @param errorPrintable handles any errors created in the constructor
   *                       call
   * @return a new DisplayBoard instance
   */
  public DisplayBoard createDisplayBoard(Optional<String> displayBoardType,
      ErrorPrintable errorPrintable) {
    try {
      String displayBoardTypeOrDefault = displayBoardType.isEmpty() ?
          DEFAULT_DISPLAY_BOARD_CLASS: displayBoardType.get();
      Class displayBoardClass = Class.forName(FILE_PATH_DISPLAY_BOARD + displayBoardTypeOrDefault);
      return (DisplayBoard) displayBoardClass.getDeclaredConstructor().
          newInstance();
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "displayBoardType");
      return new ClassicDisplayBoard();
    }
  }

}
