package ooga.model.players;

import java.util.Map;
import java.util.Random;

/**
 * The purpose of this class is to rotation the players in a random manner. This class has no
 * assumptions or dependencies. An example of how to use this class is in the players class where
 * a rotation type needs to be determined.
 *
 * @author Grace Llewellyn
 */
public class RandomPlayerRotation extends PlayerRotation{

  private final Random randomGenerator;

  public RandomPlayerRotation(Map<Integer, Player> players, Random randomGenerator) {
    super(players);
    this.randomGenerator = randomGenerator;
  }

  /**
   * Moves to the next player in random manner
   *
   * @param players Mapping of player ids to player
   */
  @Override
  public void moveToNextPlayer(Map<Integer, Player> players) {
    super.setCurrentPlayer(players.get(randomGenerator.nextInt(players.size())));
  }

}
