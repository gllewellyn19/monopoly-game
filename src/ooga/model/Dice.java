package ooga.model;

import java.util.List;
import ooga.model.dice.ModelDiceRollable;

/***
 * Highest level is abstract. Maintains dice in model that generate each random value
 *
 * @author Grace Llewellyn
 */
public abstract class Dice implements ModelDiceRollable {

  /**
   * Generates a value between 2 and 12 for one dice roll
   *
   * @return the integer sum of the two dice
   */
  public abstract List<Integer> rollDice();

  /**
   * Determines if roll was double of same number
   * @param diceRoll is roll instance and value of each die
   * @return whether or not the roll contains doubles
   */
  public abstract boolean isDoubles(List<Integer> diceRoll);

  /**
   * Returns the sum of the dice roll
   */
  public abstract int sumDiceRoll(List<Integer> diceRoll);

  /**
   * contains information about the last dice roll
   * @return the last roll of the dice
   */
  public abstract List<Integer> getLastRoll();

}
