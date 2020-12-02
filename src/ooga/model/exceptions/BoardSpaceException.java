package ooga.model.exceptions;

/**
 * This exception is thrown if there is a problem setting up the board. This makes the assumption
 * that the key the user would like to use is invalidBoardSpace. There are no dependencies. An
 * example of how to use this class would be if the user enters a board space in the csv with an
 * invalid type
 *
 * @author Grace Llewellyn
 */
public class BoardSpaceException extends RuntimeException {

  private String boardSpace;

  public BoardSpaceException(String boardSpace){
    super("invalidBoardSpace");
    this.boardSpace = boardSpace;
  }

  public String getBoardSpace() {
    return boardSpace;
  }


}
