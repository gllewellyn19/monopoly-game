package ooga.view.buttons;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Properties;
import ooga.model.Dice;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ClassicDice;
import ooga.view.ButtonsMaintainer;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates a ButtonsMaintainer object with information from the user. Uses ClassicButtonsMaintainer
 * as default if information not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class ButtonsMaintainerFactory {

  public static final String DEFAULT_BUTTONS_MAINTAINER_CLASS = "ClassicButtonsMaintainer";
  public static final String FILE_PATH_BUTTONS = "ooga.view.buttons.";

  /**
   * Creates and returns the class that maintains the buttons
   * @param interfacesForButtons includes all interfaces
   * @param defaultProperties the default buttons maintainer properties
   * @param errorPrintable object to inform user of an error
   * @return
   */
  public ButtonsMaintainer createButtonsMaintainer(InterfacesForButtons interfacesForButtons,
      Optional<Properties> defaultProperties, ErrorPrintable errorPrintable) {
    try {
      String buttonsTypeOrDefault = defaultProperties.isPresent() && defaultProperties.get().
          getProperty("buttonsMaintainerType")!=null ?
          defaultProperties.get().getProperty("buttonsMaintainerType"): DEFAULT_BUTTONS_MAINTAINER_CLASS;
      Class buttonsClass = Class.forName(FILE_PATH_BUTTONS + buttonsTypeOrDefault);
      return (ButtonsMaintainer) buttonsClass.getDeclaredConstructor(InterfacesForButtons.class,
          Optional.class).newInstance(interfacesForButtons, defaultProperties);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "buttonsMaintainerType");
      return new ClassicButtonsMaintainer(interfacesForButtons, defaultProperties);
    }
  }

}
