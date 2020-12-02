package ooga.view.buttons;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.view.gameView.DisplayInitializer;

/**
 * Creates game play buttons
 *
 * @author Grace Llewellyn
 */
public class GamePlayButtonsFactory extends ButtonCreationFactory{

  public static final List<String> DEFAULT_BUTTON_NAMES = List.of("ChangeStylesheetButton",
      "StartingDiceRollButton","RollDiceButton", "EndTurnButton", "MakeTradeButton", "BuyRealEstateButton");

  /**
   * creates game play buttons
   * @param buttons Mapping of buttons to create
   * @param languageResources ResourceBundle with information for languages
   * @param interfacesForButtons interfaces needed by any button
   * @param gameValueResources Resources about the game
   * @param defaultProperties Default properties for the game
   */
  public void createButtons (
      Map<String, BoardInteractiveFeature> buttons, ResourceBundle languageResources,
      InterfacesForButtons interfacesForButtons, Properties gameValueResources, Optional<Properties> defaultProperties) {
    super.createButtons(buttons, languageResources, interfacesForButtons, gameValueResources,
        defaultProperties, DEFAULT_BUTTON_NAMES, "gamePlayButtons");
  }

  /**
   * Creates a button and returns that button. Returns null if no button can be created
   */
  @Override
  protected BoardInteractiveFeature createButton(String buttonName, ResourceBundle languageResources,
      InterfacesForButtons interfacesForButtons, Properties gameValueResources, Optional<Properties> defaultProperties) {
    try {
      Class buttonClass = Class.forName(ButtonCreationFactory.FILE_PATH_BUTTONS + buttonName);
      if (buttonName.equals("ChangeStylesheetButton")) {
        return new ChangeStylesheetButton(languageResources,
            interfacesForButtons.getCreateCSSControls(), interfacesForButtons.getErrorPrintable(),
            interfacesForButtons.getSceneControls(), gameValueResources, defaultProperties);
      } else if (buttonName.equals("EndTurnButton")) {
        return new EndTurnButton(languageResources, interfacesForButtons.getTurnableModel());
      } else if (buttonName.equals("StartingDiceRollButton")) {
        return  new StartingDiceRollButton(languageResources,
            interfacesForButtons.getModelDiceRollable(), interfacesForButtons.getViewDiceRollable(),
            interfacesForButtons.getTurnableModel(), interfacesForButtons.getAccessOtherButtons());
      } else if (buttonName.equals("RollDiceButton")) {
        return new RollDiceButton(languageResources,
            interfacesForButtons.getModelDiceRollable(), interfacesForButtons.getViewDiceRollable(),
            interfacesForButtons.getAccessOtherButtons(), interfacesForButtons.getTurnableModel());
      } else if (buttonName.equals("MakeTradeButton")) {
        return new MakeTradeButton(languageResources, interfacesForButtons.getTurnableModel());
      } else if (buttonName.equals("BuyRealEstateButton")) {
        return new BuyRealEstateButton(languageResources, interfacesForButtons.getTurnableModel());
      }
      else if (buttonName.equals("BackButton")) {
        return new BackButton(languageResources, interfacesForButtons.getSceneControls());
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
   * creates the display initializer
   * @param buttons for the game
   * @param interfacesForButtons includes all interfaces
   * @param resourceBundle button resources
   * @param displayInitializer initializer for the display
   * @param infoForGamePlay information needed to start the game
   */
  @Override
  public void uploadDisplayInitializer(
      Map<String, BoardInteractiveFeature> buttons,
      InterfacesForButtons interfacesForButtons,
      ResourceBundle resourceBundle,
      DisplayInitializer displayInitializer,
      InfoForGamePlay infoForGamePlay) {
    if (buttons.containsKey("MakeTradeButton")) {
      buttons.put("MakeTradeButton", new MakeTradeButton(resourceBundle, interfacesForButtons.getTurnableModel(), displayInitializer, infoForGamePlay));
    } if (buttons.containsKey("BuyRealEstateButton")) {
      buttons.put("BuyRealEstateButton", new BuyRealEstateButton(resourceBundle, interfacesForButtons.getTurnableModel(), displayInitializer, infoForGamePlay));
    }
  }

  /**
   * Remakes the 2 buttons affected by a new modelDiceRollable
   */
  protected void uploadModelDiceRollable(Map<String, BoardInteractiveFeature> buttons,
      InterfacesForButtons interfacesForButtons, ResourceBundle languageResources) {
    if (buttons.containsKey("StartingDiceRollButton")) {
      buttons.put("StartingDiceRollButton", new StartingDiceRollButton(languageResources,
          interfacesForButtons.getModelDiceRollable(), interfacesForButtons.getViewDiceRollable(),
          interfacesForButtons.getTurnableModel(), interfacesForButtons.getAccessOtherButtons()));
    }
    if (buttons.containsKey("RollDiceButton")) {
      buttons.put("RollDiceButton", new RollDiceButton(languageResources,
          interfacesForButtons.getModelDiceRollable(), interfacesForButtons.getViewDiceRollable(),
          interfacesForButtons.getAccessOtherButtons(), interfacesForButtons.getTurnableModel()));
    }
  }

  /**
   * Removes the starting roll button if the game is in play and vice versa because both buttons are
   * always created
   */
  public List<String> getCreatedButtons(boolean startingRolls) {
    List<String> allButtonsCreated = new ArrayList<>(super.getCreatedButtons());
    if (startingRolls) {
      allButtonsCreated.remove("RollDiceButton");
    }
    else {
      allButtonsCreated.remove("StartingDiceRollButton");
    }
    return allButtonsCreated;
  }

}
