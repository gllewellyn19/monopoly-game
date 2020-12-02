package ooga.model.rules;

import ooga.model.gamePlay.ClassicGamePlay;
import ooga.model.players.Player;

import java.util.Map;
import ooga.model.Ruleset;

/**
 * The rules for ending the game for the Millionaire version of Monopoly
 * @author delaneydemark
 */
public class MillionaireRuleset extends Ruleset {

    /**
     * @param players in the game
     * @return if the game is over based on if at least one player has one million dollars
     */
    @Override
    public boolean isGameOver(Map<Integer, Player> players) {
        for (Map.Entry<Integer, Player> entry : players.entrySet()) {
            if(entry.getValue().getPlayerBalance() >= ClassicGamePlay.MILLION){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the id of the player with the most money
     * @param players in the game
     * @return integer id of the winning player
     */
    @Override
    public int determineWinner(Map<Integer, Player> players) {
        for (Map.Entry<Integer, Player> entry : players.entrySet()) {
            if(entry.getValue().getPlayerBalance() >= ClassicGamePlay.MILLION){
                return entry.getValue().getId();
            }
        }
        return -1;
    }
}
