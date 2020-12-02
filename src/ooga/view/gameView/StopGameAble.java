package ooga.view.gameView;


/**
 * This interface allows a class to stop the game. This class has no assumptions or dependencies.
 * An example of how to use this class is give it to the a class that should be able to stop the game
 * and disable all buttons if there is a certain warning
 *
 * @author Grace Llewellyn
 */
public interface StopGameAble {

  /**
   * Stops the game and disables all buttons
   */
  void stopGame();

}
