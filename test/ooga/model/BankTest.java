package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import ooga.DukeApplicationTest;
import ooga.model.banks.ClassicBank;
import ooga.model.players.InteractivePlayer;
import ooga.model.players.Player;
import org.junit.jupiter.api.Test;

public class BankTest extends DukeApplicationTest {

  private Bank bank;

    @Test
    public void testIncreaseBalance(){
      Map<Integer, Player> players = new HashMap<>();
      Player player0 = new InteractivePlayer();
      player0.setPlayerBalance(100);
      players.put(0, player0);
      bank = new ClassicBank(players);
      bank.increaseBalance(0, 20);
      assertEquals(120, player0.getPlayerBalance());
    }

    @Test
    public void testDecreaseBalance(){
      Map<Integer, Player> players = new HashMap<>();
      Player player0 = new InteractivePlayer();
      player0.setPlayerBalance(100);
      players.put(0, player0);
      bank = new ClassicBank(players);
      bank.decreaseBalance(0, 20);
      assertEquals(80, player0.getPlayerBalance());
    }

    @Test
    public void testTransferBalance(){
      Map<Integer, Player> players = new HashMap<>();
      Player player0 = new InteractivePlayer();
      player0.setPlayerBalance(100);
      players.put(0, player0);
      Player player1 = new InteractivePlayer();
      player1.setPlayerBalance(100);
      players.put(1, player1);
      bank = new ClassicBank(players);
      bank.transferBalance(0, 1,20);
      assertEquals(80, player0.getPlayerBalance());
      assertEquals(120, player1.getPlayerBalance());
    }

    @Test
    public void testGetMoneyFromOtherPlayers() {
      Map<Integer, Player> players = new HashMap<>();
      Player player0 = new InteractivePlayer();
      player0.setPlayerBalance(100);
      players.put(0, player0);
      Player player1 = new InteractivePlayer();
      player1.setPlayerBalance(100);
      players.put(1, player1);
      Player player2 = new InteractivePlayer();
      player2.setPlayerBalance(100);
      players.put(2, player2);
      bank = new ClassicBank(players);
      bank.getMoneyFromOtherPlayers(0, 20);
      assertEquals(140, player0.getPlayerBalance());
      assertEquals(80, player1.getPlayerBalance());
      assertEquals(80, player2.getPlayerBalance());
    }

    @Test
    public void testPayPerProperty() {
      Map<Integer, Player> players = new HashMap<>();
      Player player0 = new InteractivePlayer();
      player0.setPlayerBalance(100);
      players.put(0, player0);
      bank = new ClassicBank(players);
      bank.payPerProperty(0, 20);
      assertEquals(100, player0.getPlayerBalance());
    }
}
