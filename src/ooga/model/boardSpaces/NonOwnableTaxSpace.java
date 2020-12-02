package ooga.model.boardSpaces;

import ooga.model.Bank;
import ooga.model.players.Player;

import ooga.model.turns.TurnModel;

/**
 * Represents a space of the "Tax" space type. When a player lands on this space, they pay an amount in taxes
 * to Free Parking
 * Example: If a monopoly board csv for a theme contains a space that is the Tax type, it should be represented
 * by an instantiation of this class
 * Dependencies: Player, Bank
 * @author Cameron Jarnot
 */
public class NonOwnableTaxSpace extends NonOwnableSpace {

    private static final int DEFAULT_TAX_AMOUNT = 100;

    private int taxAmount;

    /**
     * Initializes the default tax amount and space ID. Passes in a Bank object.
     * @param id is the name of the space
     * @param bankIn is the Bank to be used for transactions
     */
    public NonOwnableTaxSpace(String id, Bank bankIn){
        super(id, bankIn);
        taxAmount = DEFAULT_TAX_AMOUNT;
    }

    /**
     * Initializes the tax amount and space ID. Passes in a Bank object.
     * @param id is the name of the space
     * @param bankIn is the Bank to be used for transactions
     * @param taxAmountIn is the amount to tax when someone lands on the space
     */
    public NonOwnableTaxSpace(String id, Bank bankIn, int taxAmountIn){
        super(id, bankIn);
        taxAmount = taxAmountIn;
    }

    /**
     * When the player lands on the space, they pay taxAmount to free parking
     * @param player is the player who landed on the space
     * @param spaceID is the int ID of the space (to be passed to front end)
     * @return a TurnModel containing any information about the turn taken (for
     * this case, the turn model is updated to contain the tax amount paid
     */
    @Override
    public TurnModel landOnSpace(Player player, int spaceID){
        this.setLocation(spaceID);
        this.bank.decreaseBalance(player.getId(), taxAmount);
        player.paidMoney(player.getId(),spaceID, taxAmount);
        TurnModel turnModel = new TurnModel();
        turnModel.freeMoneyAdded(taxAmount);
        return turnModel;
    }

}