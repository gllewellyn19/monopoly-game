package ooga.model.boardSpaces;

import ooga.model.Bank;
import ooga.model.players.Player;
import ooga.model.turns.TurnModel;

/**
 * Represents a space of the "Free Parking" type. When a player lands on a space of this type, they collect
 * the money that has been paid in taxes
 * Dependencies: Bank, Player, TurnModel
 * Example: If a monopoly board csv for a theme contains a space that is the Free Parking type, it should be
 * represented by an instantiation of this class
 * Note: Stores the amount that players pay via taxes as the freeMoney value
 * @author Cameron Jarnot
 */
public class NonOwnableFreeParkingSpace extends NonOwnableSpace {

    private int freeMoney;

    /**
     * Initializes the space ID and free money value to zero. Passes a Bank in.
     * @param spaceId is the space's name
     * @param bankIn is the Bank to be used for transactions
     */
    public NonOwnableFreeParkingSpace(String spaceId, Bank bankIn){
        super(spaceId, bankIn);
        freeMoney = 0;
    }

    /**
     * Uses bank to transfer money from free parking to the player who landed there and resets the amount in
     * free parking money to zero
     * @param player is the player who landed on the space
     * @param spaceID is the int ID of the space (to be passed to front end)
     * @return a TurnModel containing any information about the turn taken (no TurnModel changes for this space)
     */
    public TurnModel landOnSpace(Player player, int spaceID){
        this.setLocation(spaceID);
        bank.increaseBalance(player.getId(), freeMoney);
        player.collectedMoney(player.getId(),spaceID,freeMoney);
        freeMoney = 0;
        return new TurnModel();
    }

    /**
     * Adds the parameter amount to the free parking money total when a player pays taxes
     * @param moneyToAdd is the amount of money being added to free parking
     */
    public void addMoneyToFreeParking(int moneyToAdd){
        freeMoney+=moneyToAdd;
    }

}
