package ooga.model.banks;

import java.util.Map;
import ooga.model.Bank;
import ooga.model.players.Player;

/**
 * Handles money transfers, increases, decreases between players and for individual players
 * Depends on Players
 * Example use: Used in NonOwnableGoSpace to add money to a player who lands on the space
 * @author Cameron Jarnot, Delaney Demark
 */
public class ClassicBank extends Bank {

    private Map<Integer, Player> players;

    public ClassicBank(Map<Integer,Player> playersList){
        players = playersList;
    }

    /**
     * Increases player's balance. If the player does not exist, then does nothing
     * @param id is the ID of the player whose balance will be increased
     * @param amount is the amount the player is receiving
     */
    public void increaseBalance(int id,int amount){
        if(players.containsKey(id)){
            players.get(id).increasePlayerBalance(amount);
        }
    }

    /**
     * Decreases player's balance. If the player does not exist, then does nothing
     * @param id is the ID of the player whose balance will be decreased
     * @param amount is the amount the player is paying
     */
    public void decreaseBalance(int id,int amount){
        if(players.containsKey(id)){
            players.get(id).decreasePlayerBalance(amount);
        }
    }

    /**
     * Transfers an amount of money from giver to receiver. If the player does not exist then do nothing
     * ex: If giver pays receiver rent, it updates both balances to reflect that
     * @param giver is the ID of the player who is paying the money
     * @param receiver is the ID of the player who is receiving the money
     * @param amount is the amount being transferred
     */
    public void transferBalance(int giver,int receiver,int amount){
        if(players.containsKey(giver)){
            players.get(giver).decreasePlayerBalance(amount);
        }
        if(players.containsKey(receiver)){
            players.get(receiver).increasePlayerBalance(amount);
        }
    }

    /**
     * Player gets money from all other players. If the player does not exist, then does nothing
     * @param receiver gets money from all players
     * @param amount of money to be transferred between players
     * @return the total amount received from all players
     */
    public int getMoneyFromOtherPlayers(int receiver, int amount){
        int total = 0;
        if(players.containsKey(receiver)){
            for(Integer giver : players.keySet()){
                transferBalance(giver, receiver, amount);
                total += amount;
            }
        }
        return total;
    }

    /**
     * Decrease player's balance for each property they own. If the player does not exist, then does nothing
     * @param giver is the player paying money
     * @param amount is the amount they are paying per property
     * @return the total amount the player must pay based on their properties
     */
    public int payPerProperty(int giver, int amount){
        int total = 0;
        if(players.containsKey(giver)){
            for(int i=0; i<players.get(giver).getPlayerProperties().size(); i++){
                decreaseBalance(giver, amount);
                total += amount;
            }
        }
        return total;
    }

}