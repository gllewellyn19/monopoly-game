package ooga.model.startGame;

import ooga.model.players.SetCurrentPlayerable;
import ooga.model.gamePlay.TurnableModel;


/**
 * This class determines the starting player. This class has
 * no assumptions or dependencies. An example of using this class is for choosing the starting player
 *
 * @author Grace Llewellyn
 */
public abstract class StartPlayer {

  /**
   *  Typically only used by rolling player when this method needs to be called from elsewhere in
   *  the code and not just the start player task
   *
   * @param turnableModel interface from GamePlay used for taking turns
   * @param diceRoll sum of the last player
   * @param setCurrentPlayerable interface to set the next player to roll
   * @return returns true if a new player was selected
   */
  public boolean determiningStartingPlayerId(TurnableModel turnableModel, int diceRoll,
      SetCurrentPlayerable setCurrentPlayerable) {
    return false;
  }

}
