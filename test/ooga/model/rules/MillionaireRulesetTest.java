package ooga.model.rules;

import ooga.model.players.Player;
import ooga.model.Ruleset;
import ooga.model.players.InteractivePlayer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MillionaireRulesetTest {

    @Test
    void gameOver() {
        Ruleset rules = new MillionaireRuleset();
        Player a = new InteractivePlayer();
        Player b = new InteractivePlayer();
        a.setPlayerBalance(10000000);
        b.setPlayerBalance(1);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1,a);
        players.put(2,b);
        assertTrue(rules.isGameOver(players));
    }

    @Test
    void gameNotOver(){
        Ruleset rules = new MillionaireRuleset();
        Player a = new InteractivePlayer();
        Player b = new InteractivePlayer();
        a.setPlayerBalance(1000);
        b.setPlayerBalance(1);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1,a);
        players.put(2,b);
        assertFalse(rules.isGameOver(players));
    }

    @Test
    void getWinnerGameWon() {
        Ruleset rules = new MillionaireRuleset();
        Player a = new InteractivePlayer();
        Player b = new InteractivePlayer();
        a.setPlayerBalance(10000000);
        b.setPlayerBalance(1);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1,a);
        players.put(2,b);
        assertEquals(-1,rules.determineWinner(players));
    }

    @Test
    void getWinnerGameNotWon() {
        Ruleset rules = new MillionaireRuleset();
        Player a = new InteractivePlayer();
        Player b = new InteractivePlayer();
        a.setPlayerBalance(10);
        b.setPlayerBalance(1);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1,a);
        players.put(2,b);
        assertEquals(-1,rules.determineWinner(players));
    }
}