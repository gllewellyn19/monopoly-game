package ooga.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import ooga.model.gamePlay.TurnableModel;
import ooga.model.players.Player;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/***
 * Highest level is abstract. Maintains a collection of players, the current player and the way
 * that the game starts
 *
 * @author Grace Llewellyn
 */
public abstract class Players {

  /**
   * Creates the starting player so that its constructor determines who the starting player is
   *
   * @param turnableModel interface for the GamePlay class with necessary information for turns
   * @param turnableView interface for the GameView class with necessary information for turns
   * @param startPlayerType An optional string to represent the start player type if not given uses
   *                        defaults
   * @param promptable interface used to prompt the user
   */
  public abstract void determineStartingPlayer(TurnableModel turnableModel,
      TurnableView turnableView, Optional<String> startPlayerType, Promptable promptable);

  /**
   * Determines if the starting player was already determined
   *
   * @param turnableModel interface for the GamePlay class with necessary information for turns
   * @param diceRoll the sum of the last dice roll
   * @return returns true if the starting player was already determined
   */
  public abstract boolean determiningStartingPlayerId(TurnableModel turnableModel, int diceRoll);

  /**
   * Sets the current player id to the one given
   *
   * @param newId id to set to
   */
  public abstract void setCurrentPlayerId(int newId);

  /**
   * Moves to the next player's turn based on the player rotation
   *
   * @param diceRoll the dice roll
   * @param turnableView interface for the GameView class with necessary information for turns
   * @param turnableModel interface for the GamePlay class with necessary information for turns
   */
  public abstract void moveToNextPlayer(List<Integer> diceRoll, TurnableView turnableView,
      TurnableModel turnableModel);

  /**
   * Gets the current player, ie the player whose turn it is
   * @return the Player object representing the current player
   */
  public abstract Player getCurrentPlayer();

  /**
   * Gets the number of players in the current game
   * @return the number of players in the game
   */
  public abstract int getNumberOfPlayers();

  /**
   * Gets a mapping of players indexed by their unique id
   * @return a map representation of the players with the given player IDs
   */
  public abstract Map<Integer, Player> getPlayers();

  /**
   * sets the current player to the given id
   *
   * @param newPlayerId new player id to set to
   */
  public abstract void setCurrentPlayer(int newPlayerId);


}
