package ooga.model.dice;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ooga.model.Dice;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates a dice object with information from the user. Uses ClassicDice as default if information
 * not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class DiceFactory {

  public static final String DEFAULT_DICE_CLASS = "ClassicDice";
  public static final String FILE_PATH_DICE = "ooga.model.dice.";

  /**
   * Creates an instance of Dice
   * @param diceType is optional dice type, will be default if empty
   * @param diceInfo is information for Dice instantiation that passes
   *                 through the Dice constructor
   * @param errorPrintable handles any errors in creating the Dice
   * @return new Dice instance
   */
  public Dice createDice(Optional<String> diceType, InfoForGamePlay diceInfo, ErrorPrintable errorPrintable) {
    try {
      String diceTypeOrDefault = diceType.isEmpty() ?
          DEFAULT_DICE_CLASS: diceType.get();
      Class diceClass = Class.forName(FILE_PATH_DICE + diceTypeOrDefault);
      return (Dice) diceClass.getDeclaredConstructor(InfoForGamePlay.class).
          newInstance(diceInfo);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "diceType");
      return new ClassicDice(diceInfo);
    }
  }

}
