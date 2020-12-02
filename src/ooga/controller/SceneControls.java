package ooga.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class gives buttons access to set new scenes and restart the game. This class has no
 * assumptions or dependencies. An example of how to use this class is give it to the back button
 * so that it can reset the game
 *
 * @author Grace Llewellyn
 */
public interface SceneControls {

  /**
   * Sets the scene in main
   *
   * @param scene scene to set
   */
  void setScene(Scene scene);
  Stage getStage();

  /**
   * Restarts the game, used for back button
   */
  void restart();

}
