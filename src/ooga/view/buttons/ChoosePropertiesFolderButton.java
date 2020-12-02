package ooga.view.buttons;

import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ooga.model.dataReaders.LoadingFolderControls;
import ooga.controller.SceneControls;

/**
 * Creates the button for uploading a properties folder
 *
 * @author Grace Llewellyn
 */
public class ChoosePropertiesFolderButton extends BoardButton {

  private final DirectoryChooser fileChooser = new DirectoryChooser();

  private final SceneControls sceneControls;
  private final AccessOtherButtons accessOtherButtons;
  private final LoadingFolderControls loadingFolderControls;

  public ChoosePropertiesFolderButton(ResourceBundle resources, SceneControls sceneControls,
      AccessOtherButtons accessOtherButtons, LoadingFolderControls loadingFolderControls) {
    super(resources, new Button(), "ChoosePropertiesFolderButton");
    this.sceneControls = sceneControls;
    this.accessOtherButtons = accessOtherButtons;
    this.loadingFolderControls = loadingFolderControls;
    initializeButton();
  }

  /**
   * Initializing the text and id of the button from the resources bundle and sets the action of the
   * button to be uploading a new properties file to the controller and enabling the play,
   * next generation and export buttons
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    Stage stage = sceneControls.getStage();
    super.getCurrButton().setOnAction(
        e -> readInPropertyFolder(stage));
  }

  /*
   * Read in the folder with all the properties for the game
   */
 private void readInPropertyFolder(Stage stage) {
    File selectedDirectory = fileChooser.showDialog(stage);
    if (selectedDirectory != null) {
      loadingFolderControls.uploadPropertiesFolder(selectedDirectory.getAbsolutePath());
      if (loadingFolderControls.getSetUpSuccessful()) {
        //gets this button to refresh and clear what was selected before
        accessOtherButtons.getButton("ChooseGameThemeButton").updateLanguageOnFeature();
        updateButtonEnables();
      }
    }
  }

  /*
   * Updates the player drop down so that it can be pressed
   */
  private void updateButtonEnables() {
   accessOtherButtons.getButton("NumberOfUsersButton").getCurrInteractiveFeature().setDisable(false);
  }
}
