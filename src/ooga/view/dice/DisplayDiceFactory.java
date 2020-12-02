package ooga.view.dice;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ooga.view.DisplayDice;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates a DisplayDice object with information from the user. Uses ClassicDisplayDice as default
 * if information not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class DisplayDiceFactory {

  public static final String DEFAULT_DISPLAY_DICE_CLASS = "ClassicDisplayDice";
  public static final String FILE_PATH_DISPLAY_DICE = "ooga.view.dice.";

  /**
   * Creates display dice that visualize the game dice
   * @param displayDiceType is optional dice type to be created
   * @return the created DisplayDice for gameView
   */
  public DisplayDice createDisplayDice(Optional<String> displayDiceType,
      ErrorPrintable errorPrintable) {
    try {
      String displayDiceTypeOrDefault = displayDiceType.isEmpty() ?
          DEFAULT_DISPLAY_DICE_CLASS: displayDiceType.get();
      Class displayDiceClass = Class.forName(FILE_PATH_DISPLAY_DICE + displayDiceTypeOrDefault);
      return (DisplayDice) displayDiceClass.getDeclaredConstructor().
          newInstance();
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "displayDiceType");
      return new ClassicDisplayDice();
    }
  }

}
