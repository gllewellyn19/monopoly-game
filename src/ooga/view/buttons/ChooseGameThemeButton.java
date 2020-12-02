package ooga.view.buttons;

import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import ooga.view.gameView.ErrorPrintable;
import ooga.model.dataReaders.LoadingFolderControls;

/**
 * Creates drop down choice box for choosing the game theme
 *
 * @author Grace Llewellyn
 */
public class ChooseGameThemeButton extends BoardChoiceBox {

  private final AccessOtherButtons accessOtherButtons;
  private final LoadingFolderControls loadingFolderControls;

  public ChooseGameThemeButton(
      ResourceBundle resources, AccessOtherButtons accessOtherButtons,
      LoadingFolderControls loadingFolderControls, ErrorPrintable errorPrintable, Properties
      gameValueResources, Optional<Properties> defaultProperties) {
    super(resources, new ChoiceBox<>(), "ChooseGameThemeButton", errorPrintable, gameValueResources,
        defaultProperties);
    this.accessOtherButtons = accessOtherButtons;
    this.loadingFolderControls = loadingFolderControls;
    initializeButton();
  }

  /**
   * Initialize button functionality
   */
  @Override
  protected void initializeButton(){
    super.initializeButton();
    super.initializeButtonWithResourceString("boardThemes");
    super.getCurrButton().setOnAction(event -> enableStartingButtonsAndUploadGameType());
  }

  /**
   * Uploads the game type to the data reader who then gives the information to the correct classes
   * and enables the user to now choose the number of starting users if the setup was successful
   */
  private void enableStartingButtonsAndUploadGameType() {
    loadingFolderControls.uploadGameType(super.getValue().replaceAll(" ", ""));
    if (loadingFolderControls.getSetUpSuccessful()) {
      accessOtherButtons.getButton("NumberOfUsersButton").getCurrInteractiveFeature().
          setDisable(false);
    }
  }

  /**
   * Updates the language
   */
  @Override
  public void updateLanguageOnFeature() {
    super.getCurrButton().setOnAction(null);
    initializeButton();
  }
}
