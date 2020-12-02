package ooga.model.boardSpaces;

import ooga.model.Bank;
import ooga.model.players.Player;
import ooga.model.turns.TurnModel;

/**
 * Represents a space of the "Jail" space type. When a player lands on a space of this type, they are
 * just visiting and nothing happens
 * Example: If a monopoly board csv for a theme contains a space that is the Jail type, it should be represented
 * by an instantiation of this class
 * Dependencies: Player
 * @author Cameron Jarnot
 */
public class NonOwnableJailSpace extends NonOwnableSpace {

    /**
     * Initializes the space's ID, passes a Bank in.
     * @param spaceId is the space's name
     * @param bankIn is the Bank to be used for transactions
     */
    public NonOwnableJailSpace(String spaceId, Bank bankIn){
        super(spaceId, bankIn);
    }

    /**
     * When the player lands on the space, their position is updated
     * @param player is the player who landed on the space
     * @param spaceID is the int ID of the space (to be passed to front end)
     * @return a TurnModel containing any information about the turn taken (no TurnModel changes for this space)
     */
    @Override
    public TurnModel landOnSpace(Player player, int spaceID) {
        this.setLocation(spaceID);
        if(!player.isInJail()){
            player.visitingJail(player.getId(),spaceID);
        }
        return new TurnModel();
    }

}
