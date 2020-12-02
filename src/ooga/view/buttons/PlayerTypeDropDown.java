package ooga.view.buttons;

import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import ooga.view.gameView.ErrorPrintable;

/**
 * Crates drop down menu for player type
 *
 * @author Grace Llewellyn
 */
public class PlayerTypeDropDown extends BoardChoiceBox {

  private final AccessOtherButtons accessOtherButtons;

  public PlayerTypeDropDown(ResourceBundle resources, String id, AccessOtherButtons
      accessOtherButtons, ErrorPrintable errorPrintable, Properties gameValueResources,
      Optional<Properties> defaultProperties) {
    super(resources, new ChoiceBox<>(), id, errorPrintable, gameValueResources, defaultProperties);
    this.accessOtherButtons = accessOtherButtons;
    initializeButton();
  }

  /**
   * initializes button
   */
  @Override
  protected void initializeButton(){
    super.initializeButton();
    super.initializeButtonWithResourceString("potentialPlayers");
    super.getCurrButton().setOnAction(event -> accessOtherButtons.checkEnableLaunchGameButton());
  }

  /**
   * Needs to remain in english because uses referencing
   */
  @Override
  public void updateLanguageOnFeature() {
    // would create mapping to english ideally if time
  }
}
