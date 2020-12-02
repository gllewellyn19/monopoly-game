package ooga.model.startGame;

import java.util.Random;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/**
 * This class determines the starting player by randomly selecting a player. This class has
 * no assumptions or dependencies. An example of using this class is for choosing the starting player
 *
 * @author Grace Llewellyn
 */
public class RandomPlayerStart extends StartPlayer {

  private final Random randomGenerator;
  private int numberOfPlayers;

  public RandomPlayerStart(Random randomGenerator, TurnableView turnableView, TurnableModel
      turnableModel, Integer numberOfPlayers, Promptable promptable) {
    this.randomGenerator = randomGenerator;
    this.numberOfPlayers = numberOfPlayers;
    determineStartingPlayer(turnableModel, turnableView, promptable);
  }

  /*
   * Disregards diceRoll/ playerId because it is does not pertain to this type of start
   */
  private void determineStartingPlayer(TurnableModel turnableModel, TurnableView turnableView,
      Promptable promptable) {
    int startingPlayerId = randomGenerator.nextInt(numberOfPlayers);
    promptable.alertUser("startingPlayerRandom", startingPlayerId);
    turnableView.showUserTopTextMessage("turnToRoll", startingPlayerId);
    turnableModel.startPlayingGame(startingPlayerId);
  }
}
