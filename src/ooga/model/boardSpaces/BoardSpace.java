package ooga.model.boardSpaces;

import ooga.model.players.Player;
import ooga.model.turns.TurnModel;

/***
 * This is the highest level abstract board space. Represents the different spaces on the board that have
 * different functions
 * Example: If a monopoly board csv for a theme contains a Go space, Property space, etc. it will be represented
 * by a BoardSpace object of the corresponding subclass
 * Dependencies: TurnModel, Player
 * @author Cameron Jarnot
 */
public abstract class BoardSpace {

  private int location;

  /**
   * Executes the action that occurs when a player lands on the space
   * @param player is the player who landed on the space
   * @param spaceID is the ID of the space the player landed on (to be passed to front end)
   * @return a TurnModel containing any information about the turn taken
   */
  public abstract TurnModel landOnSpace(Player player, int spaceID);

  /**
   * Returns the location ID of the space
   * @return int location ID of space
   */
  public int getLocation() {
    return location;
  }

  /**
   * Sets the location ID of the space
   * @param spaceID int location ID of space
   */
  public void setLocation(int spaceID){
    location = spaceID;
  }

}