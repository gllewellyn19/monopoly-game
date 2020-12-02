package ooga.view.buttons;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.dice.ViewDiceRollable;

/**
 * Creates the starting dice roll button
 *
 * @author Grace Llewellyn
 */
public class StartingDiceRollButton extends BoardButton {

  private final ModelDiceRollable modelDiceRollable;
  private final ViewDiceRollable viewDiceRollable;
  private final TurnableModel turnableModel;
  private final AccessOtherButtons accessOtherButtons;

  public StartingDiceRollButton(ResourceBundle resources, ModelDiceRollable modelDiceRollable,
      ViewDiceRollable viewDiceRollable, TurnableModel turnableModel, AccessOtherButtons accessOtherButtons) {
    super(resources, new Button(), "StartingDiceRollButton");
    this.modelDiceRollable = modelDiceRollable;
    this.viewDiceRollable = viewDiceRollable;
    this.turnableModel = turnableModel;
    this.accessOtherButtons = accessOtherButtons;
    initializeButton();
  }

  /**
   * creates the dice roll button
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    super.getCurrButton().setOnAction(event -> onRoll());
  }

  /**
   * Calls the model to get a value to roll that then calls the view to roll with that value
   * Then calls the logic to determine if that player is now the starting player
   */
  private void onRoll() {
    List<Integer> diceRoll = modelDiceRollable.rollDice();
    viewDiceRollable.rollDice(diceRoll, turnableModel.getCurrentPlayerId());
    if (turnableModel.determiningStartingPlayerId(diceRoll)) {
      super.getCurrButton().setDisable(true);
      accessOtherButtons.getButton("EndTurnButton").getCurrInteractiveFeature().setDisable(false);
    }
  }

}
