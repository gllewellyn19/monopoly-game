package ooga.view;

import java.util.List;
import java.util.Optional;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ooga.model.exceptions.PropertiesFileException;

/***
 * Highest level is abstract. Maintains the dice of the game and the action of rolling them
 *
 * @author Grace Llewellyn
 */
public abstract class DisplayDice {

  /**
   * Creates dice visualization
   * @param numDice is amount of dice to create
   * @param colorDice is optional color of the dice
   */
  public abstract void initializeDice(Optional<Integer> numDice, Optional<Color> colorDice) throws
  PropertiesFileException;

  /**
   * Show the numbers on the dice changing on the screen
   * @param diceRoll represents the value of the dice
   */
  public abstract void rollDice(List<Integer> diceRoll);

  /**
   * Adds dice to HBOX for visualization
   * @param row is HBox to add to
   * @return HBox with dice visualization added to its child list
   */
  public abstract HBox addDiceToHBox(HBox row);

}
