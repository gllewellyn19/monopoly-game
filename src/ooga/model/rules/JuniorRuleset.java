package ooga.model.rules;

import ooga.model.players.Player;

import java.util.Map;
import ooga.model.Ruleset;

/**
 * The rules for ending the game for the Junior version of Monopoly
 * @author delaneydemark
 */
public class JuniorRuleset extends Ruleset {

    /**
     * @param players in the game
     * @return if the game is over based on if all but one player has gone bankrupt
     */
    @Override
    public boolean isGameOver(Map<Integer, Player> players) {
        for (Map.Entry<Integer, Player> entry : players.entrySet()) {
            if(entry.getValue().getPlayerBalance() <= 0){
                return true;
            }
        }
        return false;
    }

    /**
     * @param players in the game
     * @return integer id of the winning player
     */
    @Override
    public int determineWinner(Map<Integer, Player> players) {
        int maxBalance = 0;
        int winningPlayerID = -1;
        for (Map.Entry<Integer,Player> entry : players.entrySet()) {
            if (entry.getValue().getPlayerBalance()>maxBalance) {
                maxBalance = entry.getValue().getPlayerBalance();
                winningPlayerID = entry.getValue().getId();
            }
        }
        return winningPlayerID;
    }


}
