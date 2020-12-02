package ooga.view.gameView;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Properties;
import ooga.controller.SceneControls;
import ooga.controller.SetUpable;
import ooga.model.DataReader;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.GameView;

/**
 * Creates a GameView object with information from the user. Uses ClassicGameView as default
 * if information not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class GameViewFactory {

  public static final String DEFAULT_GAME_VIEW_CLASS = "ClassicGameView";
  public static final String FILE_PATH_GAME_VIEW = "ooga.view.gameView.";

  /**
   * All params are passed into a Gameview constructor.
   * @param defaultProperties is optional properties object describing default gameView properties
   * @param sceneControls controls the game's scene
   * @param setUpable helper implemented by Controller that aids in game set up
   * @param dataReader parses information from data files
   * @param turnableModel describes the turns taken during the game
   * @return a new GameView instance
   */
  public GameView createGameView(Optional<Properties> defaultProperties,
      SceneControls sceneControls, SetUpable setUpable, DataReader
      dataReader, TurnableModel turnableModel) {
    try {
      String gameViewType = (defaultProperties.isPresent()&&
          defaultProperties.get().getProperty("gameViewType")==null)?
          defaultProperties.get().getProperty("gameViewType"):DEFAULT_GAME_VIEW_CLASS;
      Class gameViewClass = Class.forName(FILE_PATH_GAME_VIEW + gameViewType);
      return (GameView) gameViewClass.getDeclaredConstructor(SceneControls.class,
          SetUpable.class, DataReader.class, TurnableModel.class,
          Optional.class).newInstance(sceneControls, setUpable, dataReader, turnableModel,
          defaultProperties);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      return new ClassicGameView(sceneControls, setUpable, dataReader, turnableModel,
          defaultProperties);
    }
  }


}
