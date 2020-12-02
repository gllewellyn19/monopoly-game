package ooga.model.boardSpaces;

import ooga.model.Bank;
import ooga.model.players.Player;

import ooga.model.turns.TurnModel;

/**
 * Represents a space of the "Go to Jail" space type. When a player lands on a space of this type, they are updated
 * to represent them being in jail so that they can only take their next turn if they have an OutJail card, roll
 * doubles, or have been in jail for the number of jail turns
 * Example: If a monopoly board csv for a theme contains a space that is the Go To Jail type, it should be represented
 * by an instantiation of this class
 * Dependencies: Bank, Player
 * @author Cameron Jarnot
 */
public class NonOwnableGoToJailSpace extends NonOwnableSpace {

    /**
     * Initializes the space's ID and passes a Bank in.
     * @param spaceId is the space's name
     * @param bankIn is the Bank to be used for transactions.
     */
    public NonOwnableGoToJailSpace(String spaceId, Bank bankIn){
        super(spaceId, bankIn);
    }
    private static final int JAIL = 10;
    /**
     * When the player lands on the space, their jail state is updated to place them in jail
     * @param player is the player who landed on the space
     * @param spaceID is the int ID of the space (to be passed to front end)
     * @return a TurnModel containing any information about the turn taken (no TurnModel changes for this space)
     */
    public TurnModel landOnSpace(Player player, int spaceID){
        this.setLocation(spaceID);
        player.moveToJail(player.getId(),spaceID);
        player.changeInJailState();
        TurnModel model = new TurnModel(spaceID);
        model.updateNewPosition(JAIL);
        return model;
    }

}
