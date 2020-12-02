package ooga.model.dice;

import java.util.ArrayList;
import java.util.List;
import ooga.model.Dice;
import ooga.model.dataReaders.InfoForGamePlay;

/**
 * Controls the dice values, holds the value of each die
 *
 * @author Grace Llewellyn
 */
public class ClassicDice extends Dice {

  private static final int DEFAULT_DICE_MAX = 6;
  private static final int DEFAULT_DICE_MIN = 1;
  public static final int DEFAULT_NUM_DICE = 2;

  private final int numDice;
  private final int diceMinNumber;
  private final int diceMaxNumber;
  private List<Integer> lastRoll;

  public ClassicDice(InfoForGamePlay diceInfo) {
    if (diceInfo.userGaveNumDice()) {
      numDice = diceInfo.getNumDice();
    } else {
      numDice = DEFAULT_NUM_DICE;
    }

    if (diceInfo.userGaveDiceMinNumber()) {
      diceMinNumber = diceInfo.getDiceMinNumber();
    } else {
      diceMinNumber = DEFAULT_DICE_MIN;
    }

    if (diceInfo.userGaveDiceMaxNumber()) {
      diceMaxNumber = diceInfo.getDiceMaxNumber();
    } else {
      diceMaxNumber = DEFAULT_DICE_MAX;
    }
  }

  /**
   * Generates a value between 2 and 12 for one dice roll
   *
   * @return the integer sum of the two dice
   */
  public List<Integer> rollDice() {
    List<Integer> diceRollsToReturn = new ArrayList<>();
    for (int i = 0; i < numDice; i++) {
      diceRollsToReturn.add(rollIndividualDice());
    }
    lastRoll = diceRollsToReturn;
    return diceRollsToReturn;
  }

  private int rollIndividualDice() {
    return (int) (Math.random() * (diceMaxNumber - diceMinNumber + 1) + diceMinNumber);
  }

  /**
   * Determines if roll was double of same number
   * @param diceRoll is roll instance and value of each die
   * @return whether or not the roll contains doubles
   */
  public boolean isDoubles(List<Integer> diceRoll) {
    if (diceRoll.isEmpty()) {
      return false;
    }
    int previousRoll = diceRoll.get(0);
    for (int i = 1; i < diceRoll.size(); i++) {
      if (diceRoll.get(i) != previousRoll) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the sum of the dice roll
   */
  public int sumDiceRoll(List<Integer> diceRoll) {
    int sum = 0;
    for (Integer numDice : diceRoll) {
      sum += numDice;
    }
    return sum;
  }

  /**
   * contains information about the last dice roll
   * @return the last roll of the dice
   */
  public List<Integer> getLastRoll() {
    return lastRoll;
  }

}
