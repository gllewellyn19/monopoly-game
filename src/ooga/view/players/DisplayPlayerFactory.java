package ooga.view.players;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import ooga.view.gameView.ClassicGameView;

/**
 * Creates a map of DisplayPlayer objects with information from the user mapping to the unique
 * player ids
 *
 * @author Grace Llewellyn
 */
public class DisplayPlayerFactory {

  /**
   * Factory method for creating DisplayPlayers for all players in the game
   * @param playerNamesAndIds is a map of all players names, indexed by a unique
   *                          integer ID key
   * @param playerNamesAndGamePieceFiles is a map of all player game piece files,
   *                          indexed by the input player names
   * @return a map of the created DisplayPlayers, indexed by the unique ID keys
   * used in the playerNamesAndIds map
   */
  public Map<Integer, DisplayPlayer> createPlayerPieces(Map<String, Integer> playerNamesAndIds,
      Map<String, String> playerNamesAndGamePieceFiles) {
    Map<Integer, DisplayPlayer> players = new HashMap<>();
    for (Entry<String, Integer> entry : playerNamesAndIds.entrySet()) {
      String playerName = entry.getKey();
      String playerPieceFilePath = playerNamesAndGamePieceFiles.get(playerName);
      DisplayPlayer playerPieceShape = new ShapeGamePiecePlayer(ClassicGameView.STARTING_LOCATION,
          entry.getValue(), playerName, playerPieceFilePath);
      players.put(entry.getValue(), playerPieceShape);
    }
    return players;
  }

}
