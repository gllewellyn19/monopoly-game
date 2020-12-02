package ooga.model;

import ooga.model.players.Player;

import java.util.Map;

/**
 * Handles the rules for ending the game
 * @author delaneydemark
 */
public abstract class Ruleset {


    /**
     * @param players in the game
     * @return if the game is over
     */
    public abstract boolean isGameOver(Map<Integer, Player> players);

    /**
     * @param players in the game
     * @return integer id of the winning player
     */
    public abstract int determineWinner(Map<Integer, Player> players);

}
