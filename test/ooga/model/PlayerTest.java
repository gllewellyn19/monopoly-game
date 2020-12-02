package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ooga.model.banks.ClassicBank;
import ooga.model.boardSpaces.OwnablePropertySpace;
import ooga.model.cards.ClassicCard;
import ooga.model.players.InteractivePlayer;
import ooga.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class PlayerTest {

    @Test
    void addToPlayerCards() {
        Player player = new InteractivePlayer();
        String[] data = "Bank error in your favor,GetMoney,200, bank-in-favor.png".split(",");
        player.addToPlayerCards(new ClassicCard(data));
        assertEquals("Bank error in your favor", player.getPlayerCards().get(0).getCardMessage());
    }

    @Test
    void changeInJailState(){
        Player player = new InteractivePlayer();
        player.changeInJailState();
        assertTrue(player.isInJail());
    }

    @Test
    void addToPlayerProperties() {
        Player player = new InteractivePlayer();
        Player a = new InteractivePlayer();
        Player b = new InteractivePlayer();
        a.setPlayerBalance(-1);
        b.setPlayerBalance(1);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1,a);
        players.put(2,b);
        OwnablePropertySpace property = new OwnablePropertySpace("Boardwalk", 100, "Blue", 100, 120, new ClassicBank(players), new HashMap<>());
        player.addToPlayerProperties(property);
    }

    @Test
    void decreasePlayerBalance() {
        Player player = new InteractivePlayer();
        player.increasePlayerBalance(100);
        player.decreasePlayerBalance(50);
        assertEquals(50, player.getPlayerBalance());
    }

    @Test
    void increasePlayerBalance() {
        Player player = new InteractivePlayer();
        player.increasePlayerBalance(100);
        assertEquals(100, player.getPlayerBalance());
    }

    @Test
    void determineIfPlayerLoses() {
        Player player = new InteractivePlayer();
        player.setPlayerBalance(-50);
        assertTrue(player.determineIfPlayerLoses());
    }

    @Test
    void sendToLocationNotPasingGo(){
        Player player = new InteractivePlayer();
        player.setPlayerBalance(100);
        player.sendToLocation(20, 200);
        assertEquals(20, player.getLocation());
        assertEquals(100, player.getPlayerBalance());
    }

    @Test
    void sendToLocationPassGo(){
        Player player = new InteractivePlayer();
        player.sendToLocation(20, 100);
        player.setPlayerBalance(100);
        player.sendToLocation(2, 200);
        assertEquals(2, player.getLocation());
        assertEquals(300, player.getPlayerBalance());
    }

    @Test
    void updateLocationNegative(){
        Player player = new InteractivePlayer();
        player.sendToLocation(20, 200);
        player.updateLocation(40, -5, 200);
        assertEquals(15, player.getLocation());
        assertEquals(0, player.getPlayerBalance());
    }

    @Test
    void updateLocationOnGo(){
        Player player = new InteractivePlayer();
        player.sendToLocation(20, 200);
        player.updateLocation(40, 20, 200);
        assertEquals(0, player.getLocation());
        assertEquals(0, player.getPlayerBalance());
    }

    @Test
    void updateLocationForward(){
        Player player = new InteractivePlayer();
        player.sendToLocation(20, 200);
        player.updateLocation(40, 2, 200);
        assertEquals(22, player.getLocation());
        assertEquals(0, player.getPlayerBalance());
    }

    @Test
    void updateLocationPassGo(){
        Player player = new InteractivePlayer();
        player.sendToLocation(20, 200);
        player.updateLocation(40, 22, 200);
        assertEquals(2, player.getLocation());
        assertEquals(200, player.getPlayerBalance());
    }
}