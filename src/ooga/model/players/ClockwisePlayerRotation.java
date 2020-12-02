package ooga.model.players;

import java.util.Map;
import java.util.Random;

/**
 * The purpose of this class is to rotation the players in a clockwise manner. This class has no
 * assumptions or dependencies. An example of how to use this class is in the players class where
 * a rotation type needs to be determined.
 *
 * @author Grace Llewellyn
 */
public class ClockwisePlayerRotation extends PlayerRotation {

  public ClockwisePlayerRotation(Map<Integer, Player> players, Random ignore) {
    super(players);
  }

  /**
   * Moves to the next player in clockwise manner
   *
   * @param players Mapping of player ids to player
   */
  @Override
  public void moveToNextPlayer(Map<Integer, Player> players) {
    int newIndex = super.getCurrentPlayer().getId() + 1;
    super.setCurrentPlayer(newIndex >= players.size() ? players.get(0): players.get(newIndex));
  }
}
