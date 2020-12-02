package ooga.view.gameView;

import java.util.List;
import java.util.Optional;


/**
 * This interface allows a class to have its turn reflected in the view. This class has no
 * assumptions or dependencies.
 * An example of how to use this class is give it to the GamePlay class so that the turns can
 * be reflected in the view
 *
 * @author Grace Llewellyn
 */
public interface TurnableView {

  /**
   * Shows the user a message at the top of the page
   * @param message key to reference the message
   * @param playerId player to show the message to
   */
  void showUserTopTextMessage(String message, int playerId);

  /**
   * Gets the player id from the name
   * @param playerName name of get id from
   * @return player id if name is found
   */
  Optional<Integer> getPlayerIdFromName(String playerName);

  /**
   * Puts the buttons into starting rolls mode
   * @param firstPlayerRolling first person to start the rolling modes
   */
  void startingRollsMode(int firstPlayerRolling);

  /**
   * Puts the buttons into rolling mode like normal game play
   */
  void playingRollsMode();

  /**
   * updates the current user
   *
   * @param newId id of the new current player
   */
  void updateCurrentUser(int newId);

  /**
   * moves the player to the desired location
   * @param newLocation location to move to
   * @param playerId player to move
   */
  void movePlayer(int newLocation, int playerId);

  /**
   * updates the money
   *
   * @param playerBalances balances to update to
   */
  void updateMoney(List<Integer> playerBalances);

  /**
   * stops the game and disables all buttons
   */
  void stopGame();

  /**
   * returns if the setup of the game view was successful and game play should continue
   * @return returns true if the setup of the game view was successful
   */
  boolean getSetUpSuccessful();
}
