package ooga.view;

import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.DukeApplicationTest;
import ooga.controller.SceneControls;
import ooga.controller.Controller;

public class DisplayBoardTest extends DukeApplicationTest {

  public static final String PROPERTIES = "data/ClassicMonopoly/";

  private Controller gameController;
  private GameView gameView;

  @Override
  public void start(Stage stage) throws FileNotFoundException {

    SceneControls controls = new SceneControls() {
      @Override
      public void setScene(Scene scene) {

      }

      @Override
      public Stage getStage() {
        return null;
      }

      @Override
      public void restart() {

      }
    };

    //gameView = gameController.getGameView();
  }
}
