package ooga.model.startGame;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Random;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/**
 * Creates a StartPlayer object with information from the user. Uses Random as default if
 * information not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class PlayerStartTypeFactory {

  private static final String FILE_PATH_START_GAME = "ooga.model.startGame.";
  private static final String DEFAULT_PLAYER_START = "Random";
  private static final String PLAYER_START_EXTENSION = "PlayerStart";
  private static final String OPTIONAL_PROPERTIES_INVALID = "optionalPropertiesInvalid";
  private static final String GAME_START_TYPE = "gameStartType";

  public StartPlayer createStartPlayer(Optional<String> playerStartType, Random randomGenerator,
      TurnableView turnableView, TurnableModel turnableModel, Integer numberOfPlayers, Promptable
      promptable, ErrorPrintable errorPrintable) {
    try {
      String startPlayerTypeOrDefault = playerStartType.isEmpty() ?
          DEFAULT_PLAYER_START: playerStartType.get();
      startPlayerTypeOrDefault = startPlayerTypeOrDefault + PLAYER_START_EXTENSION;
      Class currentPlayerStartTypeClass = Class.forName(FILE_PATH_START_GAME + startPlayerTypeOrDefault);
      return (StartPlayer)currentPlayerStartTypeClass.getDeclaredConstructor(Random.class,
          TurnableView.class, TurnableModel.class, Integer.class, Promptable.class).
          newInstance(randomGenerator, turnableView, turnableModel, numberOfPlayers, promptable);

    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat(OPTIONAL_PROPERTIES_INVALID,
              GAME_START_TYPE);
    }
    return null;
  }

}
