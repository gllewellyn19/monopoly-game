package ooga.model.dice;

import java.util.List;

/**
 * This class gives buttons access to roll the dice. This class has no
 * assumptions or dependencies. An example of how to use this class is give it to the roll dice button
 * so that it can roll the dice
 *
 * @author Grace Llewellyn
 */
public interface ModelDiceRollable {

  /**
   * Rolls the dice and returns the dice roll
   *
   * @return dice roll
   */
  List<Integer> rollDice();

  /**
   * Sums the given dice roll
   *
   * @param diceRoll dice roll to sum
   * @return sum of the dice roll
   */
  int sumDiceRoll(List<Integer> diceRoll);

  /**
   * Determines if the dice roll is doubles
   *
   * @param diceRoll dice roll to determine if doubles
   * @return true if the dice roll is doubles and false if not
   */
  boolean isDoubles(List<Integer> diceRoll);

}
