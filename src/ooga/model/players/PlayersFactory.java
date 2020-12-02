package ooga.model.players;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import ooga.model.Players;
import ooga.model.dice.ModelDiceRollable;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.Promptable;


/**
 * Can create both a player object and a Players object with information from the user.
 * Uses InteractivePlayer as default if information not provided or incorrect for Player and
 * uses ClassicPlayers as default if information not provided or incorrect for Players
 *
 * @author Grace Llewellyn
 */
public class PlayersFactory {

  public static final String FILE_PATH_PLAYER = "ooga.model.players.";
  public static final String DEFAULT_PLAYERS_TYPE = "ClassicPlayers";

  public Player createPlayer(int playerID, String playerType,Promptable promptable, ErrorPrintable
      errorPrintable) {
    try {
      playerType = playerType.replaceAll(" ", "");
      Class currentGameTypeClass = Class.forName(FILE_PATH_PLAYER+playerType);
      return (Player)currentGameTypeClass.getDeclaredConstructor(Integer.class, Promptable.class).
          newInstance(playerID,promptable);

    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "Player");
      return new InteractivePlayer(playerID, promptable);
    }
  }

  public Players createPlayersClass(Optional<String> playersType, Map<Integer, String> playerIdsAndTypes,
      Optional<String> gameRotationType, ModelDiceRollable modelDiceRollable, Promptable promptable,
      ErrorPrintable errorPrintable) {
    try {
      String playersTypeOrDefault = playersType.isEmpty() ?
          DEFAULT_PLAYERS_TYPE: playersType.get();
      Class playersClass = Class.forName(FILE_PATH_PLAYER + playersTypeOrDefault);
      return (Players) playersClass.getDeclaredConstructor(Map.class, Optional.class,
          ModelDiceRollable.class, Promptable.class, ErrorPrintable.class).newInstance(playerIdsAndTypes,
          gameRotationType, modelDiceRollable, promptable, errorPrintable);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "playersType");
      return new ClassicPlayers(playerIdsAndTypes, gameRotationType, modelDiceRollable,
          promptable, errorPrintable);
    }
  }

}
