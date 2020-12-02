package ooga.view.buttons;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.gameView.DisplayInitializer;

/**
 * Creates the button to buy real estate
 *
 * @author Anna Diemel
 */
public class BuyRealEstateButton extends BoardButton {

  private final TurnableModel turnableModel;

  /**
   * Initializes real estate button
   * @param resources
   * @param turnableModel
   */
  public BuyRealEstateButton(ResourceBundle resources, TurnableModel turnableModel) {
    super(resources, new Button(), "BuyRealEstateButton");
    this.turnableModel = turnableModel;
    initializeButton();
  }

  /**
   * Initializes real estate button using information for the start of the game and the display initializer
   * @param resources for buying real estate
   * @param turnableModel turns handled within the model
   * @param displayInitializer initializes the display
   * @param infoForGamePlay holds information for game play from properties
   */
  public BuyRealEstateButton(ResourceBundle resources, TurnableModel turnableModel,
      DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay) {
    super(resources, new Button(), "BuyRealEstateButton");
    this.turnableModel = turnableModel;
    initializeButton(displayInitializer, infoForGamePlay);
  }

  private void initializeButton(DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay) {
    super.initializeButton();
    super.getCurrButton().setOnAction(event -> {
      turnableModel.buyRealEstate(displayInitializer, infoForGamePlay);
    });
    getCurrButton().setDisable(turnableModel.getNumPlayers()==0 || turnableModel.getCurrentPlayer().hasMonopoly());
  }
}
