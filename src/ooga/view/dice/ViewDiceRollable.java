package ooga.view.dice;

import java.util.List;

/**
 * Interface describing the rollable dice visualization
 */
public interface ViewDiceRollable {

  void rollDice(List<Integer> diceRoll, int playerId);

}
