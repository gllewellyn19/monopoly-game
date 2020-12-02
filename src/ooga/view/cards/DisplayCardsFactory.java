package ooga.view.cards;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ooga.view.DisplayBoard;
import ooga.view.DisplayCards;
import ooga.view.board.ClassicDisplayBoard;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates a DisplayCards object with information from the user. Uses ClassicDisplayCards as default
 * if information not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class DisplayCardsFactory {

  public static final String DEFAULT_DISPLAY_CARDS_CLASS = "ClassicDisplayCards";
  public static final String FILE_PATH_DISPLAY_CARDS = "ooga.view.cards.";

  /**
   * @param displayCardsType the type of DisplayCards, such as Classic
   * @param errorPrintable error if the type of cards is not found
   * @return DisplayCards based on the type of cards needed for the game, ie classic
   */
  public DisplayCards createDisplayCards(Optional<String> displayCardsType,
      ErrorPrintable errorPrintable) {
    try {
      String displayCardsTypeOrDefault = displayCardsType.isEmpty() ?
          DEFAULT_DISPLAY_CARDS_CLASS: displayCardsType.get();
      Class displayCardsClass = Class.forName(FILE_PATH_DISPLAY_CARDS + displayCardsTypeOrDefault);
      return (DisplayCards) displayCardsClass.getDeclaredConstructor().
          newInstance();
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "displayCardsType");
      return new ClassicDisplayCards();
    }
  }

}
