package ooga.view.money;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ooga.view.DisplayMoney;
import ooga.view.gameView.ErrorPrintable;

/**
 * Factory for creating DisplayMoney instances
 * Allows for reflection of multiple DisplayMoney types and
 *  constructors.
 */
public class DisplayMoneyFactory {

  public static final String DEFAULT_DISPLAY_MONEY_CLASS = "ClassicDisplayMoney";
  public static final String FILE_PATH_DISPLAY_MONEY = "ooga.view.money.";

  /**
   * Creates a new instance of DisplayMoney
   * @param displayMoneyType is an optional description of the display money type
   *                         If empty, the default DisplayMoney type is used
   * @param errorPrintable handles any errors in creating the DisplayMoney instance
   * @return new DisplayMoney instance
   */
  public DisplayMoney createDisplayMoney(Optional<String> displayMoneyType,
      ErrorPrintable errorPrintable) {
    try {
      String displayMoneyTypeOrDefault = displayMoneyType.isEmpty() ?
          DEFAULT_DISPLAY_MONEY_CLASS: displayMoneyType.get();
      Class displayMoneyClass = Class.forName(FILE_PATH_DISPLAY_MONEY + displayMoneyTypeOrDefault);
      return (DisplayMoney) displayMoneyClass.getDeclaredConstructor().
          newInstance();
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "displayMoneyType");
      return new ClassicDisplayMoney();
    }
  }

}
