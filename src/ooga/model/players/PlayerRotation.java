package ooga.model.players;

import java.util.Map;

/**
 * This class should be implemented by any type of player rotation. It also keeps track of the current
 * player. This class has no assumptions or dependencies. An example of this class is random
 * player rotation
 *
 * @author Grace Llewellyn
 */
public abstract class PlayerRotation {

  private Player currentPlayer;

  public PlayerRotation(Map<Integer, Player> players) {
    currentPlayer = players.get(0);
  }

  /**
   * Moves to the next player in a certain manner
   *
   * @param players Mapping of player ids to the player objects
   */
  public abstract void moveToNextPlayer(Map<Integer, Player> players);

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(Player newPlayer) {
    currentPlayer = newPlayer;
  }

}
