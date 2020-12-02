package ooga.model.startGame;

import java.util.Optional;
import java.util.Random;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/**
 * This class determines the starting player by asking for the youngest player. This class has
 * no assumptions or dependencies. An example of using this class is for choosing the starting player
 *
 * @author Grace Llewellyn
 */
public class YoungestPlayerStart extends StartPlayer {

  public YoungestPlayerStart(Random randomGenerator, TurnableView turnableView, TurnableModel
      turnableModel, Integer numberOfPlayers, Promptable promptable) {
    determineStartingPlayer(turnableModel, turnableView, promptable);
  }

  /*
   * Continues prompting the user to enter a player name until they enter a valid name
   * Ignores diceRoll and playerId because that does not pertain to this type of starting
   */
  private void determineStartingPlayer(TurnableModel turnableModel, TurnableView turnableView,
      Promptable promptable) {
    Optional<String> startingPlayerName = Optional.empty();
    Optional<Integer> startingPlayerId = Optional.empty();
    while(startingPlayerName.isEmpty() || startingPlayerId.isEmpty()) {
      startingPlayerName = promptable.promptUser("enterYoungestPlayer", Optional.empty());
      if (startingPlayerName.isPresent()) {
        startingPlayerId = turnableView.getPlayerIdFromName(startingPlayerName.get());
      }
    }
    turnableModel.startPlayingGame(startingPlayerId.get());
  }
}
