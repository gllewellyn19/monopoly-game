package ooga.model.players;

import java.util.Map;
import java.util.Random;

/**
 * The purpose of this class is to rotation the players in a counterclockwise manner. This class has no
 * assumptions or dependencies. An example of how to use this class is in the players class where
 * a rotation type needs to be determined.
 *
 * @author Grace Llewellyn
 */
public class CounterClockwisePlayerRotation extends PlayerRotation{

  public CounterClockwisePlayerRotation(Map<Integer, Player> players, Random ignore) {
    super(players);
  }

  /**
   * Moves to the next player in counter clockwise manner
   *
   * @param players Mapping of player ids to player
   */
  @Override
  public void moveToNextPlayer(Map<Integer, Player> players) {
    int newIndex = super.getCurrentPlayer().getId() - 1;
    super.setCurrentPlayer(newIndex < 0 ? players.get(players.size()-1): players.get(newIndex));
  }

}
