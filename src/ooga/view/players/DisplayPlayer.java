package ooga.view.players;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Holds basic information about the display player. Game piece is implemented in subclasses
 */
public abstract class DisplayPlayer {

  private int location;
  private final int playerID;
  private final String playerName;

  public DisplayPlayer(int location, int playerID, String playerName) {
    this.location = location;
    this.playerID = playerID;
    this.playerName = playerName;
  }

  /**
   * @return DisplayPlayer's location on the board
   */
  public int getLocation() {
    return location;
  }

  /**
   * Change a DisplayPlayer's location on the board
   * @param newLocation is set to the location variable
   */
  public void setLocation(int newLocation) {
    if (newLocation >= 0) {
      location = newLocation;
    }
  }

  /**
   * @return DisplayPlayer's unique int ID.
   */
  public int getPlayerID() {
    return playerID;
  }

  /**
   * @return the DisplayPlayer's name; either input by players or set to a
   * default value
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * @return gamePiece visualization that is shown on the DisplayBoard
   */
  public abstract Node getGamePiece();

  /**
   * Move the DisplayPlayer across the board in the direction of gameplay
   * @param diceVal is the number of spaces to move
   */
  public abstract void move(int diceVal);

  /**
   * Removes the DisplayPlayer's game piece from a given Pane.
   * @param pane is pane to remove game piece from.
   */
  public void removeFromPane(Pane pane) {
    pane.getChildren().remove(this.getGamePiece());
  }

  /**
   * Add the piece visualization to a new pane on the DisplayBoard
   * @param pane is Pane for the DisplayPlayer game piece to be added to
   */
  public void addToPane(Pane pane) {
    this.getGamePiece().toFront();
    pane.getChildren().add(this.getGamePiece());
  }
}
