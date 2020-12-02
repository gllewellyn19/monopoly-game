package ooga.view.buttons;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import ooga.model.gamePlay.TurnableModel;

/**
 * Creates the button the player uses to end their turn
 *
 * @author Grace Llewellyn
 */
public class EndTurnButton extends BoardButton {

  private final TurnableModel turnableModel;

  public EndTurnButton(ResourceBundle resources, TurnableModel turnableModel) {
    super(resources, new Button(), "EndTurnButton");
    this.turnableModel = turnableModel;
    initializeButton();
  }

  /**
   * initializes the end turn button
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    super.getCurrButton().setOnAction(event ->  turnableModel.moveToNextPlayer());
  }

}
