package ooga.view.buttons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.view.gameView.DisplayInitializer;

/**
 * Creates all of the buttons for the game
 *
 * @author Grace Llewellyn
 */
public abstract class ButtonCreationFactory {

  public static final String FILE_PATH_BUTTONS = "ooga.view.buttons.";

  private List<String> createdButtons;

  /**
   * Creates the buttons using the default properties file or uses the constant above if it cannot
   * create it
   */
  public void createButtons(
      Map<String, BoardInteractiveFeature> buttons, ResourceBundle languageResources,
      InterfacesForButtons interfacesForButtons, Properties gameValueResources, Optional<Properties> defaultProperties,
      List<String> defaultButtonNames, String propertyName) {
    List<String> buttonNames = new ArrayList<>(extractButtonNames(defaultProperties, defaultButtonNames,
        propertyName));
    for (int i=0; i<buttonNames.size(); i++) {
      BoardInteractiveFeature buttonToAdd = createButton(buttonNames.get(i), languageResources, interfacesForButtons,
          gameValueResources, defaultProperties);
      if (buttonToAdd != null) {
        buttons.put(buttonNames.get(i), buttonToAdd);
      }
      else {
        buttonNames.remove(i);
        i--;
      }
    }
    createdButtons = buttonNames;
  }

  /**
   * Extracts the button names from the default properties file or from the default list above
   */
  private List<String> extractButtonNames(Optional<Properties> defaultProperties, List<String>
      defaultButtonNames, String propertyName) {
    if(defaultProperties.isPresent() && defaultProperties.get().getProperty(propertyName) != null) {
      return Arrays.asList(defaultProperties.get().getProperty(propertyName).split(","));
    }
    else {
      return defaultButtonNames;
    }
  }

  /**
   * Creates a button and returns that button. Returns null if no button can be created
   */
  protected abstract BoardInteractiveFeature createButton(String buttonName, ResourceBundle languageResources,
      InterfacesForButtons interfacesForButtons, Properties gameValueResources, Optional<Properties> defaultProperties);

  /**
   * @return List of the buttons that were creating
   */
  public List<String> getCreatedButtons() {
    return createdButtons;
  }

  /**
   * Uploads the initializer of the display
   * @param buttons for the game
   * @param interfacesForButtons includes all interfaces
   * @param resourceBundle button resources
   * @param displayInitializer initializer for the display
   * @param infoForGamePlay information needed to start the game
   */
  public abstract void uploadDisplayInitializer(Map<String, BoardInteractiveFeature> buttons,
      InterfacesForButtons interfacesForButtons,
      ResourceBundle resourceBundle,
      DisplayInitializer displayInitializer,
      InfoForGamePlay infoForGamePlay);
}
