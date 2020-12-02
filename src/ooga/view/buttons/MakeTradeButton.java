package ooga.view.buttons;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.gameView.DisplayInitializer;

/**
 * Creates the button for making a trade
 */
public class MakeTradeButton extends BoardButton {

  private final TurnableModel turnableModel;

  public MakeTradeButton(ResourceBundle resources, TurnableModel turnableModel) {
    super(resources, new Button(), "MakeTradeButton");
    this.turnableModel = turnableModel;
    initializeButton();
  }

  public MakeTradeButton(ResourceBundle resources, TurnableModel turnableModel,
      DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay) {
    super(resources, new Button(), "MakeTradeButton");
    this.turnableModel = turnableModel;
    initializeButton(displayInitializer, infoForGamePlay);
  }

  private void initializeButton(DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay) {
    super.initializeButton();
    super.getCurrButton().setOnAction(event ->  turnableModel.makeTrade(displayInitializer, infoForGamePlay));
    //getCurrButton().setDisable(turnableModel.getNumPlayers()==0 || turnableModel.getCurrentPlayer().getPlayerProperties().isEmpty());
  }
}
