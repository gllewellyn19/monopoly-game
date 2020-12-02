package ooga.model.rules;

import ooga.model.players.Player;

import java.util.Map;
import ooga.model.Ruleset;

/**
 * The rules for ending the game for the Classic version of Monopoly
 * @author delaneydemark
 */
public class ClassicRuleset extends Ruleset {

    /**
     * @param players in the game
     * @return if the game is over based on if one player is left in the game
     */
    @Override
    public boolean isGameOver(Map<Integer, Player> players) {
        int playersStillInGame = 0;
        for (Map.Entry<Integer, Player> entry : players.entrySet()) {
            if (!entry.getValue().determineIfPlayerLoses()) {
                playersStillInGame++;
            }
        }
        return playersStillInGame <= 1;
    }

    /**
     * @param players in the game
     * @return integer id of the winning player
     */
    @Override
    public int determineWinner(Map<Integer, Player> players) {
        for (Map.Entry<Integer,Player> entry : players.entrySet()) {
            if (!entry.getValue().determineIfPlayerLoses()) {
                return entry.getValue().getId();
            }
        }
        return -1;
    }

}
