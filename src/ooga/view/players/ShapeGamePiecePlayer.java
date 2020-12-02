package ooga.view.players;

import java.io.File;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import ooga.view.buttons.PlayerInputLines;

/**
 * Shows the player as a piece on the board. Can be a colored rectangle or given image
 *
 * @author Grace Llewellyn and Anna Diemel
 */
public class ShapeGamePiecePlayer extends DisplayPlayer {

  public static final Color DEFAULT_COLOR = Color.RED;
  public static final List<Integer> GAME_PIECE_SIZES = List.of(10, 10, 40, 40);

  private Rectangle gamePiece;
  private Pair<Integer, Integer> position;

  public ShapeGamePiecePlayer(int location, int playerID, String playerName, String playerPieceFilePath) {
    super(location, playerID, playerName);
    createGamePiece(playerPieceFilePath);
  }

  /**
   * @return game piece visualization
   */
  @Override
  public Node getGamePiece() {
    return gamePiece;
  }

  /**
   * Move this particular player piece
   * @param diceVal is the number of spaces to move
   */
  @Override
  public void move(int diceVal) {
    // move forward diceVal spaces
  }

  /**
   * @param filePath gives the location of the game piece image
   * Creates a rectangle shape to capture the game piece image
   *                 If the file is a color, then the rectangle is filled
   *                 with a solid color. Otherwise, it is filled with
   *                 the related image's image pattern.
   */
  public void createGamePiece(String filePath) {
    Rectangle currentGamePiece = new Rectangle(GAME_PIECE_SIZES.get(0), GAME_PIECE_SIZES.get(1),
        GAME_PIECE_SIZES.get(2), GAME_PIECE_SIZES.get(3));
    try {
      if (filePath.startsWith(PlayerInputLines.COLOR_IDENTIFIER)) {
        String color = filePath.substring(filePath.indexOf(PlayerInputLines.COLOR_IDENTIFIER) +
            PlayerInputLines.COLOR_IDENTIFIER.length());
        currentGamePiece.setFill(Color.web(color));
      }
      else {
        Image pieceImage = new Image(new File(filePath).toURI().toString());
        currentGamePiece.setFill(new ImagePattern(pieceImage));
      }
    } catch(NullPointerException e) {
      currentGamePiece.setFill(DEFAULT_COLOR);
    }
    gamePiece = currentGamePiece;
  }
}
