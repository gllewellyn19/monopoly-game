package ooga.view.buttons;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates choice box for number of players input
 *
 * @author Grace Llewellyn
 */
public class NumberOfUsersButton extends BoardChoiceBox {

  private final NumberPlayersChangable numberPlayersChangable;
  private final AccessOtherButtons accessOtherButtons;
  private final int numPlayers;

  public NumberOfUsersButton(ResourceBundle resources, NumberPlayersChangable numberPlayersChangable,
      AccessOtherButtons accessOtherButtons, ErrorPrintable errorPrintable, int numPlayers,
      Properties gameValueResources, Optional<Properties> defaultProperties) {
    super(resources, new ChoiceBox<>(), "NumberOfUsersButton", errorPrintable, gameValueResources,
        defaultProperties);
    this.numberPlayersChangable = numberPlayersChangable;
    this.accessOtherButtons = accessOtherButtons;
    this.numPlayers = numPlayers;
    initializeButton();
  }

  /**
   * This isn't relevant to this button because just numbers, no words
   */
  @Override
  public void updateLanguageOnFeature() {

  }

  /**
   * Ignore if the user sets up the game wrong and puts letters where the numbers should be (should
   * never be able to happen unless user overwrites code in this button
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    ChoiceBox<String> thisButton = super.getCurrButton();
    thisButton.setValue(Integer.toString(numPlayers));
    super.initializeButtonWithResourceString("potentialNumberPlayers");
    checkAllNumbers(super.getCurrButton().getItems());
    try {
      thisButton.setOnAction(event -> setNumPlayersAndEnableLaunch());
    } catch (NumberFormatException ignored){
      //just don't add if error
    }
  }

  /*
   * Sets the number of players and enables the launch game button because the user has entered
   * a number of players
   */
  private void setNumPlayersAndEnableLaunch() {
    accessOtherButtons.getButton("LaunchGameButton").getCurrInteractiveFeature().
        setDisable(false);
    numberPlayersChangable.setNumberPlayers(
        Integer.parseInt(super.getCurrButton().getValue()));
  }

  /*
   * Makes sure that all the elements in the given array are numbers
   */
  private void checkAllNumbers(List<String> potentialNumbersOfPlayers) {
    try {
      for (String playerNum: potentialNumbersOfPlayers) {
        Integer.parseInt(playerNum);
      }
    } catch (NumberFormatException e) {
      super.printError("notNumber", "potentialNumberPlayers");
    }
  }
}
