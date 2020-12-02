package ooga;

import java.awt.Dimension;
import java.io.File;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.SceneControls;
import ooga.view.GameView;

/**
 * The main launches the game and deals with the basic of an application such as a scene and a stage
 * This method assumes that cheat key a is the only one that the user would like to implement. This
 * class does not depend on anything. An example of how to use this class is to put it at the highest
 * level of your program and call it when you wish to launch the game
 *
 * @author Grace Llewellyn and Anna Diemel
 */
public class Main extends Application implements SceneControls {

  public static final String RESOURCES = "resources/";
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  public static final Dimension DEFAULT_SIZE = new Dimension(800, 800);
  public static final String DEFAULT_TITLE = "Monopoly Game";

  private Stage stage;
  private Scene scene;
  private Controller gameController;

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Starts the game- needs this because overrides Application
   * @param stage Stage to set the scene of
   */
  @Override
  public void start(Stage stage) {
    gameController = new Controller(this);
    GameView gameView = gameController.getGameView();
    Optional<Scene> startingScene = gameView.makeAnInitialScene(DEFAULT_SIZE.width,
        DEFAULT_SIZE.height);
    if (startingScene.isPresent()) {
      scene = startingScene.get();
      scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
      stage.setScene(scene);
      stage.setTitle(DEFAULT_TITLE);
      stage.show();
      this.stage = stage;
    }
  }

  /**
   * Called whenever the back button is called and restarts the game with the current stage
   */
  @Override
  public void restart() {
    start(stage);
  }

  /**
   * Creates a default game when the user presses the a cheat key
   *
   * @param code "A" is a cheat key that jumps through the initial splash screen
   */
  private void handleKeyInput(KeyCode code) {
    if (code == KeyCode.A) {
      gameController.setUpDefaultGame();
    }
  }

  //methods only given access through interfaces

  /**
   * Sets the current scene and only classes with interfaces can do this since no classes gets
   * the entire main class
   *
   * @param s scene to set current scene too
   */
  public void setScene(Scene s) {
    this.scene = s;
    stage.setScene(scene);
  }

  @Override
  public Stage getStage() {
    return stage;
  }

  //used for testing purposes
  public Controller getController() {
    return gameController;
  }

}
