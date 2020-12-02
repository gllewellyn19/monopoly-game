package ooga.model.players;

/**
 * This class lets the player chose a current player. This class has no
 * assumptions or dependencies. An example of how to use this class is give it to the start player
 * so that it can set the current player for starting dice rolls
 *
 * @author Grace Llewellyn
 */
public interface SetCurrentPlayerable {

  /**
   * Sets the current player to the one with the given id
   *
   * @param newPlayerId new id to set to
   */
  void setCurrentPlayer(int newPlayerId);
}
