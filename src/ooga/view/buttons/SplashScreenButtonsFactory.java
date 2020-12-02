package ooga.view.buttons;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.view.gameView.DisplayInitializer;

/**
 * Creates splash screen buttons
 *
 * @author Grace Llewellyn
 */
public class SplashScreenButtonsFactory extends ButtonCreationFactory{

  public static final List<String> DEFAULT_BUTTON_NAMES = List.of("ChooseLanguageButton", "NumberOfUsersButton",
      "LaunchGameButton", "ChoosePropertiesFolderButton", "ChooseGameThemeButton");

  /**
   * creates splash screen buttons
   * @param buttons for splash screen
   * @param languageResources language options
   * @param interfacesForButtons all interface info for buttons
   * @param gameValueResources game values
   * @param defaultProperties default properties for input
   */
  public void createButtons (
      Map<String, BoardInteractiveFeature> buttons, ResourceBundle languageResources,
      InterfacesForButtons interfacesForButtons, Properties gameValueResources, Optional<Properties> defaultProperties) {
    super.createButtons(buttons, languageResources, interfacesForButtons, gameValueResources,
        defaultProperties, DEFAULT_BUTTON_NAMES, "splashScreenButtons");
  }
  /**
   * Creates a button and returns that button. Returns null if no button can be created
   */
  @Override
  protected BoardInteractiveFeature createButton(String buttonName, ResourceBundle languageResources,
      InterfacesForButtons interfacesForButtons, Properties gameValueResources, Optional<Properties> defaultProperties) {
    try {
      Class buttonClass = Class.forName(ButtonCreationFactory.FILE_PATH_BUTTONS + buttonName);
      if (buttonName.equals("ChooseLanguageButton")) {
        return new ChooseLanguageButton(languageResources,
            interfacesForButtons.getLanguageControls(), interfacesForButtons.getErrorPrintable(),
            gameValueResources, defaultProperties);
      } else if (buttonName.equals("NumberOfUsersButton")) {
        NumberOfUsersButton newButton = new NumberOfUsersButton(languageResources,
            interfacesForButtons.getNumberPlayersChangable(),
            interfacesForButtons.getAccessOtherButtons(),
            interfacesForButtons.getErrorPrintable(),
            interfacesForButtons.getNumberPlayersChangable().
                getNumberOfPlayers(), gameValueResources, defaultProperties);
        newButton.getCurrButton().setDisable(true);
        return newButton;
      } else if (buttonName.equals("LaunchGameButton")) {
        return new LaunchGameButton(languageResources,
            interfacesForButtons.getSceneControls(), interfacesForButtons.getCreateCSSControls(),
            interfacesForButtons.getErrorPrintable(), interfacesForButtons.getSetUpable(),
            interfacesForButtons.getAccessOtherButtons());
    } else if (buttonName.equals("ChoosePropertiesFolderButton")) {
        return new ChoosePropertiesFolderButton(languageResources,
            interfacesForButtons.getSceneControls(), interfacesForButtons.getAccessOtherButtons(),
            interfacesForButtons.getLoadingFolderControls());
      } else if (buttonName.equals("ChooseGameThemeButton")) {
        return new ChooseGameThemeButton(languageResources,
            interfacesForButtons.getAccessOtherButtons(), interfacesForButtons.getLoadingFolderControls(),
            interfacesForButtons.getErrorPrintable(), gameValueResources, defaultProperties);
      }
      //creates a generic button- adding a new button must follow these rules
      else {
        return (BoardInteractiveFeature) buttonClass.getDeclaredConstructor(ResourceBundle.class,
            InterfacesForButtons.class, Properties.class, Optional.class).
            newInstance(languageResources, interfacesForButtons, gameValueResources, defaultProperties);
      }
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      interfacesForButtons.getErrorPrintable().printErrorMessageAlert("invalidButtonName", buttonName);
    }
    return null;
  }

  /**
   * uploads the display initializer
   * @param buttons for the game
   * @param interfacesForButtons includes all interfaces
   * @param resourceBundle button resources
   * @param displayInitializer initializer for the display
   * @param infoForGamePlay information needed to start the game
   */
  @Override
  public void uploadDisplayInitializer(Map<String, BoardInteractiveFeature> buttons,
      InterfacesForButtons interfacesForButtons, ResourceBundle resourceBundle,
      DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay) { }

}
