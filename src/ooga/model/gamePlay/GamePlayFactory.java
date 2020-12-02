package ooga.model.gamePlay;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Properties;
import ooga.model.GamePlay;

/**
 * This class serves to initialize the GamePlay of the correct subclass
 * Example: To create a ClassicGamePlay class, the gamePlayType property should be set to Classic
 * @author Grace Llewellyn
 */
public class GamePlayFactory {

  public static final String DEFAULT_GAME_PLAY_CLASS = "ClassicGamePlay";
  public static final String FILE_PATH_GAME_PLAY = "ooga.model.gamePlay.";

  /**
   * This method serves to create a GamePlay instantiation based on the given properties file
   * @param defaultProperties is the properties file to read the gamePlayType from
   * @return the GamePlay of the correct subclass
   */
  public GamePlay createGamePlay(Optional<Properties> defaultProperties) {
    try {
      String gamePlayType = (defaultProperties.isPresent()&&
          defaultProperties.get().getProperty("gamePlayType")==null)?
          defaultProperties.get().getProperty("gamePlayType"):DEFAULT_GAME_PLAY_CLASS;
      Class gamePlayClass = Class.forName(FILE_PATH_GAME_PLAY + gamePlayType);
      return (GamePlay) gamePlayClass.getDeclaredConstructor().newInstance();
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      return new ClassicGamePlay();
    }
  }

}
