package ooga.model.gamePlay;

import java.util.List;
import java.util.Optional;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.players.Player;
import ooga.model.turns.TurnModel;
import ooga.view.gameView.DisplayInitializer;

/**
 * This interface gives methods what they need to take a turn. This interface has no assumptions or
 * dependencies. An example of how to use this interface is give it to the roll dice button so it
 * can proceed with the game
 *
 * @author Grace Llewellyn
 */
public interface TurnableModel {
  Player getCurrentPlayer();

  /**
   * Moves to the next player
   */
  void moveToNextPlayer();

  /**
   * Moves to the next player if the current player didn't roll doubles, etc.
   *
   * @param diceRoll dice roll to check for doubles
   */
  void movePlayerUsingDiceRoll(List<Integer> diceRoll);

  /**
   * Does the action on the current location
   *
   * @param playerID player that landed on the location
   * @param spaceID id of where the player landed
   * @return returns a TurnModel only if necessary to reflect those turns on the board
   */
  Optional<TurnModel> doActionOnCurrentLocation(int playerID,int spaceID);

  /**
   * Starts playing the game
   * @param playerId starting player
   */
  void startPlayingGame(int playerId);

  /**
   * Determines the starting player
   * @param diceRoll last dice roll
   * @return returns true if starting player has been found
   */
  boolean determiningStartingPlayerId(List<Integer> diceRoll);
  int getCurrentPlayerId();

  /**
   * Buys real estate
   *
   * @param displayInitializer graphics class
   * @param infoForGamePlay information about the game play
   */
  void buyRealEstate(DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay);

  /**
   * Makes a trade
   *
   * @param displayInitializer graphics class
   * @param infoForGamePlay information about the game play
   */
  void makeTrade(DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay);
  int getNumPlayers();
}
