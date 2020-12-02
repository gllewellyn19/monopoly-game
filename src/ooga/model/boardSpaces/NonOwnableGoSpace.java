package ooga.model.boardSpaces;

import ooga.model.Bank;
import ooga.model.players.Player;
import ooga.model.turns.TurnModel;

/**
 * Represents a space of the "Go" type. When a player lands on a space of this type, they collect the go amount
 * Example: If a monopoly board csv for a theme contains a space that is the Go type, it should be represented by
 * an instantiation of this class
 * Dependencies: Bank, Player
 * @author Cameron Jarnot
 */
public class NonOwnableGoSpace extends NonOwnableSpace {

    private static final int DEFAULT_GO_VALUE = 200;

    private String id;
    private int goAmount;

    /**
     * Initializes the space's ID and sets the amount for passing go. Passes a Bank in.
     * @param spaceId is the space's name
     * @param bankIn is the Bank to be used for adding money to a player
     * @param amountPassingGo is the amount the player receives for passing Go
     */
    public NonOwnableGoSpace(String spaceId, Bank bankIn, int amountPassingGo){
        super(spaceId, bankIn);
        goAmount = amountPassingGo;
    }

    /**
     * Initializes the space's ID and sets the amount for passing go to the default value.
     * Passes a Bank in.
     * @param spaceId is the space's name
     * @param bankIn is the Bank to be used for adding money to a player
     */
    public NonOwnableGoSpace(String spaceId, Bank bankIn){
        super(spaceId, bankIn);
        goAmount = DEFAULT_GO_VALUE;
    }

    /**
     * Adds the go amount to the player's balance via the bank -- if not go amount is given, the player receives $200
     * @param player is the player who landed on the space
     * @param spaceID is the int ID of the space (to be passed to front end)
     * @return a TurnModel containing any information about the turn taken (no TurnModel changes for this space)
     */
    @Override
    public TurnModel landOnSpace(Player player, int spaceID) {
        this.setLocation(spaceID);
        player.setPlayerBalance(player.getPlayerBalance()+goAmount);
        player.collectedMoney(player.getId(),spaceID,goAmount);
        TurnModel turnModel = new TurnModel();
        return turnModel;
    }

}
