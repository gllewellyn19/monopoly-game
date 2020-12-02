package ooga.view.buttons;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.dice.ViewDiceRollable;

/**
 * Creates button to roll dice
 *
 * @author Grace Llewellyn
 */
public class RollDiceButton extends BoardButton {

  private final ModelDiceRollable modelDiceRollable;
  private final ViewDiceRollable viewDiceRollable;
  private final AccessOtherButtons accessOtherButtons;
  private final TurnableModel turnableModel;

  public RollDiceButton(ResourceBundle resources, ModelDiceRollable modelDiceRollable,
      ViewDiceRollable viewDiceRollable, AccessOtherButtons
      accessOtherButtons, TurnableModel turnableModel) {
    super(resources, new Button(), "RollDiceButton");
    this.modelDiceRollable = modelDiceRollable;
    this.viewDiceRollable = viewDiceRollable;
    this.accessOtherButtons = accessOtherButtons;
    this.turnableModel = turnableModel;
    initializeButton();
  }

  /**
   * initializes button to roll dice
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    super.getCurrButton().setOnAction(event -> onRoll());
  }

  /*
   * Calls the model to get a value to roll that then calls the view to roll with that value
   */
  private void onRoll() {
    List<Integer> diceRoll = modelDiceRollable.rollDice();
    viewDiceRollable.rollDice(diceRoll, turnableModel.getCurrentPlayerId());
    turnableModel.movePlayerUsingDiceRoll(diceRoll);
    super.getCurrButton().setDisable(true);
    accessOtherButtons.getButton("EndTurnButton").getCurrInteractiveFeature().setDisable(false);
  }

}
