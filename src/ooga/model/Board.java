package ooga.model;

import ooga.model.boardSpaces.BoardSpace;
import ooga.model.boardSpaces.OwnablePropertySpace;
import ooga.model.boardSpaces.OwnableSpace;

import java.util.List;

/***
 * Highest level is abstract. Maintains a collection of board spaces to represent the board and
 * returns a BoardSpace based on where the user landed
 *
 * @author Grace Llewellyn
 */
public abstract class Board {

  public abstract BoardSpace getBoardSpaceAtLocation(int location);

  /**
   * Finds all OwnableSpace objects on the board
   * @return a List of the OwnableSpace objects
   */
  public abstract List<OwnableSpace> getOwnableBoardSpaces();

  /**
   * Finds all OwnablePropertySpace objects on the board
   * @return a List of the OwnablePropertySpace objects
   */
  public abstract List<OwnablePropertySpace> getOwnablePropertyBoardSpaces();
  public abstract int boardSize();

  /**
   * Finds the first NonOwnableFreeParkingSpace on the board
   * @return the integer position of the first NonOwnableFreeParkingSpace
   */
  public abstract int getFreeParkingPosition();
}