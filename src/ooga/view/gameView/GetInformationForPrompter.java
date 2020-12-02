package ooga.view.gameView;

import java.util.Map;
import java.util.ResourceBundle;
import ooga.view.DisplayBoard;
import ooga.view.DisplayCards;
import ooga.view.players.DisplayPlayer;

/**
 * This interface allows the prompter to get necessary information. This class has no assumptions or dependencies.
 * An example of how to use this class is give it to the pop up class so that the prompter
 * displays it correctly
 *
 * @author Grace Llewellyn
 */
public interface GetInformationForPrompter {

  // all getter methods
  ResourceBundle getLanguageResources();
  Map<Integer, DisplayPlayer> getPlayers();
  DisplayBoard getDisplayBoard();
  DisplayCards getDisplayCards();

}
