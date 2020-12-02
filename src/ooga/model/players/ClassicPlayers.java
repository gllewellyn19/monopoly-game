package ooga.model.players;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import ooga.model.Players;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.gamePlay.TurnableModel;
import ooga.model.startGame.PlayerStartTypeFactory;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;
import ooga.model.startGame.StartPlayer;

/**
 * This class maintains the list of players as well as keeping track of the current player. This class
 * also calls the factory that creates all the players. This class has no exceptions or dependencies
 * An example of how to use this class would be to have GamePlay have an instance of it so that
 * you can keep track of all the players
 *
 * @author Grace Llewellyn
 */
public class ClassicPlayers extends Players implements SetCurrentPlayerable {

  public static final int MAX_NUMBER_ROLLS = 3;

  private final PlayerRotation playerRotation;
  private final Map<Integer, Player> players;
  private StartPlayer startPlayer;
  private final ModelDiceRollable modelDiceRollable;
  private final PlayersFactory playersFactory = new PlayersFactory();
  private final PlayerStartTypeFactory playerStartTypeFactory = new PlayerStartTypeFactory();
  private int numberOfConsecutiveRolls = 0;
  private boolean startingRollsMode = false;
  private final ErrorPrintable errorPrintable;


  public ClassicPlayers(Map<Integer, String> playerIdsAndTypes, Optional<String> gameRotationType,
      ModelDiceRollable modelDiceRollable,Promptable promptable, ErrorPrintable errorPrintable) {
    this.modelDiceRollable = modelDiceRollable;
    this.errorPrintable = errorPrintable;
    players =  getStartingPlayers(playerIdsAndTypes,promptable);
    PlayerRotationTypeFactory playerRotationTypeFactory = new PlayerRotationTypeFactory();
    playerRotation = playerRotationTypeFactory.createRotationPlayer(gameRotationType, new Random(),
        players, errorPrintable);
  }

  /**
   * Creates the starting player so that its constructor determines who the starting player is
   *
   * @param turnableModel interface for the GamePlay class with necessary information for turns
   * @param turnableView interface for the GameView class with necessary information for turns
   * @param startPlayerType An optional string to represent the start player type if not given uses
   *                        defaults
   * @param promptable interface used to prompt the user
   */
  public void determineStartingPlayer(TurnableModel turnableModel,
      TurnableView turnableView, Optional<String> startPlayerType, Promptable promptable) {
    startPlayer = playerStartTypeFactory.createStartPlayer(startPlayerType, new Random(),
        turnableView, turnableModel, players.size(), promptable, errorPrintable);
  }

  /**
   * Determines if the starting player was already determined
   *
   * @param turnableModel interface for the GamePlay class with necessary information for turns
   * @param diceRoll the sum of the last dice roll
   * @return returns true if the starting player was already determined
   */
  public boolean determiningStartingPlayerId(TurnableModel turnableModel, int diceRoll) {
    startingRollsMode = startPlayer.determiningStartingPlayerId(turnableModel, diceRoll,
        this);
    return startingRollsMode;
  }

  /**
   * Sets the current player id to the one given
   *
   * @param newId id to set to
   */
  public void setCurrentPlayerId(int newId) {
    if (players.containsKey(newId)) {
      playerRotation.setCurrentPlayer(players.get(newId));
    }
  }

  /**
   * Moves to the next player's turn based on the player rotation
   *
   * @param diceRoll the dice roll
   * @param turnableView interface for the GameView class with necessary information for turns
   * @param turnableModel interface for the GamePlay class with necessary information for turns
   */
  public void moveToNextPlayer(List<Integer> diceRoll, TurnableView turnableView,
      TurnableModel turnableModel) {
    if (numberOfConsecutiveRolls < MAX_NUMBER_ROLLS && modelDiceRollable.isDoubles(diceRoll)) {
      numberOfConsecutiveRolls++;
      playerRotation.getCurrentPlayer().turnToRoll(turnableView, modelDiceRollable, turnableModel);
    }
    else {
      moveToNextPlayer(turnableView, turnableModel);
    }
  }

  //only move to the next player if not in starting players mode because starting players mode sets
  // the current player itself
  private void moveToNextPlayer(TurnableView turnableView, TurnableModel turnableModel) {
    numberOfConsecutiveRolls = 0;
    if (!startingRollsMode) {
      playerRotation.moveToNextPlayer(players);
    }
    playerRotation.getCurrentPlayer().turnToRoll(turnableView, modelDiceRollable, turnableModel);
  }

  public Player getCurrentPlayer() {
    return playerRotation.getCurrentPlayer();
  }

  public int getNumberOfPlayers() {
    return players.size();
  }

  public Map<Integer, Player> getPlayers() {
    return players;
  }


  /*
   * Creates a mapping of the player ID to the player object
   */
  private Map<Integer, Player> getStartingPlayers(Map<Integer, String> playerIdsAndTypes,Promptable promptable) {
    Map<Integer, Player> players = new HashMap<>();
    for (Map.Entry<Integer,String> player : playerIdsAndTypes.entrySet()) {
      int playerID = player.getKey();
      players.put(playerID, playersFactory.createPlayer(playerID, player.getValue(),promptable,
          errorPrintable));
    }
    return players;
  }

  /**
   * sets the current player to the given id
   *
   * @param newPlayerId new player id to set to
   */
  @Override
  public void setCurrentPlayer(int newPlayerId) {
    if (players.get(newPlayerId) != null) {
      playerRotation.setCurrentPlayer(players.get(newPlayerId));
    }
  }
}
