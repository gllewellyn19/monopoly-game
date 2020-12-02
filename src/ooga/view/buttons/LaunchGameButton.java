package ooga.view.buttons;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import ooga.view.gameView.CreateCSSControls;
import ooga.view.gameView.ErrorPrintable;
import ooga.controller.SceneControls;
import ooga.controller.SetUpable;

/**
 * Creates button to launch game
 */
public class LaunchGameButton extends BoardButton {

  private final CreateCSSControls createCSSControls;
  private final ErrorPrintable errorPrintable;
  private final SceneControls sceneControls;
  private final SetUpable setUpable;
  private final AccessOtherButtons accessOtherButtons;

  public LaunchGameButton(ResourceBundle resources, SceneControls sceneControls,
      CreateCSSControls createCSSControls, ErrorPrintable errorPrintable, SetUpable setUpable,
      AccessOtherButtons accessOtherButtons) {
    super(resources, new Button(), "LaunchGameButton");
    this.createCSSControls = createCSSControls;
    this.errorPrintable = errorPrintable;
    this.sceneControls = sceneControls;
    this.setUpable = setUpable;
    this.accessOtherButtons = accessOtherButtons;
    initializeButton();
  }

  /**
   * initializes the button
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    super.getCurrButton().setOnAction(event -> onLaunchClick());
  }

  /**
   * Starts the simulation game after the language was chosen with the default CSS. Also calls
   * GamePlay in model to set up the game for the start
   */
  public void onLaunchClick() {
    if (accessOtherButtons.checkAllNamesEnteredAreUnique() && accessOtherButtons.checkAllPiecesUnique()) {
      //setUpable.setUpGame();
      Optional<Scene> scene = createCSSControls.makeASceneWithInitialCSS();
      scene.ifPresent(sceneControls::setScene);
      setUpable.setUpGame();
    }
  }


}
