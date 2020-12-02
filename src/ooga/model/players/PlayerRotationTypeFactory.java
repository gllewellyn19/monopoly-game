package ooga.model.players;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates a PlayerRotation object with information from the user. Uses Clockwise as default if
 * information not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class PlayerRotationTypeFactory {

  public static final String FILE_PATH_START_GAME = "ooga.model.players.";
  public static final String DEFAULT_PLAYER_ROTATION = "Clockwise";
  public static final String ROTATION_TYPE_EXTENSION = "PlayerRotation";

  public PlayerRotation createRotationPlayer(Optional<String> playerRotationType, Random randomGenerator,
      Map<Integer, Player> players, ErrorPrintable errorPrintable) {
    try {
      String rotationPlayerTypeOrDefault = playerRotationType.isEmpty() ?
          DEFAULT_PLAYER_ROTATION: playerRotationType.get();
      rotationPlayerTypeOrDefault = rotationPlayerTypeOrDefault + ROTATION_TYPE_EXTENSION;
      Class currentRotationPlayerClass = Class.forName(FILE_PATH_START_GAME +
          rotationPlayerTypeOrDefault);
      return (PlayerRotation) currentRotationPlayerClass.getDeclaredConstructor(Map.class,
          Random.class).newInstance(players, randomGenerator);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "gameRotationType");
      return new ClockwisePlayerRotation(players, randomGenerator);
    }
  }

}
