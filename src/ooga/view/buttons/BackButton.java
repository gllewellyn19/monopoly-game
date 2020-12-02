package ooga.view.buttons;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import ooga.controller.SceneControls;

/**
 * Creates the back back button on the game screen
 *
 * @author Grace Llewellyn
 */
public class BackButton extends BoardButton{

  private final SceneControls sceneControls;

  public BackButton(ResourceBundle resources, SceneControls sceneControls) {
    super(resources, new Button(), "BackButton");
    this.sceneControls = sceneControls;
    initializeButton();
  }

  /**
   * Initializing the text and id of the button from the resources bundle and sets the action of the
   * button to be going back to the splash screen of the game
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    super.getCurrButton().setOnAction(
        e -> sceneControls.restart());
  }


}
