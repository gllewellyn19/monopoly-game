package ooga.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.gamePlay.PropertyUpgradeable;
import ooga.model.gamePlay.TurnableModel;
import ooga.model.players.Player;
import ooga.model.turns.TurnModel;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/***
 * Highest level is abstract. Simulates the general play of the game in the model
 *
 * @author Grace Llewellyn
 */
public abstract class GamePlay implements TurnableModel {

  /**
   * Returns the current player
   * @return the Player taking their turn currently
   */
  public abstract Player getCurrentPlayer();

  /**
   * Moves to the next player and it doesn't matter what they rolled or how many turns left (used for
   * starting of the game for example)
   */
  public abstract void moveToNextPlayer();

  /**
   * Returns the ID of the player whose turn it currently is
   * @return the int ID of the current player
   */
  public abstract int getCurrentPlayerId();

  /**
   * Determines if the game is over by seeing if all but one player are out of money and have
   * mortgaged all properties.
   * @return true if the game is over, false if not
   */
  public abstract boolean determineGameOver();

  /**
   * Determines the winning player
   * @return the winning players ID or -1 if no player won
   */
  public abstract int getWinningPlayerID();

  /**
   * Rolls the dice and also moves the player's location. Gives the user 200 dollars if they pass go
   * @param diceRoll is the list of the values of the current dice roll
   */
  public abstract void movePlayerUsingDiceRoll(List<Integer> diceRoll);

  /**
   * Moves the current player to a specific space
   * @param locationToMoveTo is the ID of the location to move the player to
   */
  public abstract void movePlayerToSpace(int locationToMoveTo);

  /**
   * Returns the number of players
   * @return the number of players
   */
  public abstract int getNumPlayers();

  /**
   * Executes the action based on the current space by calling its landOnSpace method,
   * then checking the returned TurnModel
   * @param playerID is the ID of the player who landed on a space
   * @param spaceID is the ID of the space the player landed on
   * @return
   */
  public abstract Optional<TurnModel> doActionOnCurrentLocation(int playerID, int spaceID);

  /**
   * Initializes the game with the starting player in the frontend
   * @param playerId is the ID of the starting player
   */
  public abstract void startPlayingGame(int playerId);

  /**
   * Determines which player will start the game
   * @param turnableView is the interface to tell the frontend whose turn to display
   * @param startPlayerType is the class name of the type of starting player
   * @param promptable is the interface to tell the front end to give information about the turn
   */
  public abstract void startGame(TurnableView turnableView, Optional<String> startPlayerType,
      Promptable promptable);

  /**
   * Determines which player will start the game based on a dice roll
   * @param diceRoll is the highest dice roll
   * @return true if the current player is the player starting
   */
  public abstract boolean determiningStartingPlayerId(List<Integer> diceRoll);

  /**
   * @return the Board instantiated in this class
   */
  public abstract Board getBoard();

  /**
   * @return the ModelDiceRollable instantiated in this class
   */
  public abstract ModelDiceRollable getModelDiceRollable();

  /**
   * Used to initialize the Model
   * @param playerIdsAndTypes is a map of the player IDs to their type
   * @param infoForGamePlay contains the overall information about the game
   * @param turnableView is the interface to reflect turns in the view
   * @param promptable is the interface to reflect user decisions in the view
   * @param errorPrintable is the interface to reflect errors in the view
   */
  public abstract void initializeGame(Map<Integer, String> playerIdsAndTypes, InfoForGamePlay infoForGamePlay,
                                      TurnableView turnableView,
      Promptable promptable, ErrorPrintable errorPrintable);

  /**
   * Returns the PropertyUpgradeable interface instantiated in this class
   * @return the PropertyUpgradeable interface instantiated in this class
   */
  public abstract PropertyUpgradeable getPropertyUpgradeable();

  /**
   * @return the Promptable interface instantiated in this class
   */
  public abstract Promptable getPromptable();

  /**
   * @return the Map of player IDs to Players
   */
  public abstract Map<Integer, Player> getPlayers();

  /**
   * @return the card decks for this game
   */
  public abstract Cards getCards();


}
