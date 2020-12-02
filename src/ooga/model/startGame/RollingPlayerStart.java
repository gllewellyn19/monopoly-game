package ooga.model.startGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import ooga.model.players.SetCurrentPlayerable;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/**
 * This class determines the starting player by having all the players rollings and determing who
 * rolls the most. This class has
 * no assumptions or dependencies. An example of using this class is for choosing the starting player
 *
 * @author Grace Llewellyn
 */
public class RollingPlayerStart extends StartPlayer {

  private Map<Integer, Integer> playerRolls;
  private Queue<Integer> playerIdsToAskToRoll;
  private int lastPlayerAskedToRoll;

  public RollingPlayerStart(Random randomGenerator, TurnableView turnableView, TurnableModel
      turnableModel, Integer numberOfPlayers, Promptable promptable) {
    playerIdsToAskToRoll = new LinkedList<>();
    playerRolls = new HashMap<>();
    for (int i=0; i<numberOfPlayers; i++) {
      playerRolls.put(i, -1);
      playerIdsToAskToRoll.add(i);
    }
    turnableView.startingRollsMode(playerIdsToAskToRoll.remove());
  }

  /**
   *  Continues prompting a user until it is determined all users have rolled and there is a player
   *  with a highest score. Anytime the output of this is -1, it is ignored because still waiting on
   *  a player to roll. Everyone initially has to roll once by being on the queue of people to ask to roll
   *
   * @param turnableModel interface from GamePlay used for taking turns
   * @param diceRoll sum of the last player
   * @param setCurrentPlayerable interface to set the next player to roll
   * @return returns true if a new player was selected
   */
  @Override
  public boolean determiningStartingPlayerId(TurnableModel turnableModel, int diceRoll,
      SetCurrentPlayerable setCurrentPlayerable) {
    playerRolls.put(lastPlayerAskedToRoll, diceRoll);
    if (playerIdsToAskToRoll.isEmpty()) {
      List<Integer> playersWithHighestRoll = determinePlayersWithHighestRolls();
      if (playersWithHighestRoll.size() == 1) {
        turnableModel.startPlayingGame(playersWithHighestRoll.get(0));
        return false;
      } else {
        playerIdsToAskToRoll.addAll(playersWithHighestRoll);
        playerRolls.clear();
      }
    }
    lastPlayerAskedToRoll = playerIdsToAskToRoll.remove();
    setCurrentPlayerable.setCurrentPlayer(lastPlayerAskedToRoll);
    return true;
  }

  /*
   * Creates a list of the players with the ids of the highest rolls
   */
  private List<Integer> determinePlayersWithHighestRolls() {
    int highestRoll = Integer.MIN_VALUE;
    List<Integer> playersWithHighestRoll = new ArrayList<>();
    for (Map.Entry<Integer, Integer> playerRoll : playerRolls.entrySet()) {
      if (playerRoll.getValue() > highestRoll) {
        highestRoll = playerRoll.getValue();
        playersWithHighestRoll.clear();
        playersWithHighestRoll.add(playerRoll.getKey());
      } else if (playerRoll.getValue() == highestRoll) {
        playersWithHighestRoll.add(playerRoll.getKey());
      }
    }
    return playersWithHighestRoll;
  }
}
