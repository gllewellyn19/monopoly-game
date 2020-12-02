package ooga.model;

/**
 * Highest level is abstract. Maintains players' money
 *
 * @author Grace Llewellyn
 */
public abstract class Bank {

    /**
     * Increases player's balance. If the player does not exist, then does nothing
     * @param id is the ID of the player whose balance will be increased
     * @param amount is the amount the player is receiving
     */
    public abstract void increaseBalance(int id,int amount);

    /**
     * Decreases player's balance. If the player does not exist, then does nothing
     * @param id is the ID of the player whose balance will be decreased
     * @param amount is the amount the player is paying
     */
    public abstract void decreaseBalance(int id,int amount);

    /**
     * Transfers an amount of money from giver to receiver. If the player does not exist then do nothing
     * ex: If giver pays receiver rent, it updates both balances to reflect that
     * @param giver is the ID of the player who is paying the money
     * @param receiver is the ID of the player who is receiving the money
     * @param amount is the amount being transferred
     */
    public abstract void transferBalance(int giver,int receiver,int amount);

    /**
     * Player gets money from all other players. If the player does not exist, then does nothing
     * @param receiver gets money from all players
     * @param amount of money to be transferred between players
     * @return the total amount received from all players
     */
    public abstract int getMoneyFromOtherPlayers(int receiver, int amount);

    /**
     * Decrease player's balance for each property they own. If the player does not exist, then does nothing
     * @param giver is the player paying money
     * @param amount is the amount they are paying per property
     * @return the total amount the player must pay based on their properties
     */
    public abstract int payPerProperty(int giver, int amount);
}
