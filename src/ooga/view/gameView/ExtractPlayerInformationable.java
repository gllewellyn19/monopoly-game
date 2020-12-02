package ooga.view.gameView;

import java.util.List;
import java.util.Map;

/**
 * This interface allows a class to extract information about the players from the splash screen.
 * This class has no assumptions or dependencies.
 * An example of how to use this class is give it to the launch game button so that it can create
 * the game
 *
 * @author Grace Llewellyn
 */
public interface ExtractPlayerInformationable {

  /**
   * Extracts the player information to a list of strings. Determined by user how this is implemented
   * @return a mapping of the player name to its information
   */
  Map<String, List<String>> extractPlayerInformation();
}
