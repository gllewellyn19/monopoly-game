package ooga.model.boardSpaces;

import ooga.model.Bank;
import ooga.model.players.Player;
import ooga.model.turns.TurnModel;

/**
 * This is an abstract class that is only implemented by board spaces that cannot be owned by a
 * user which includes Go, Jail, Free Parking, etc.
 * Example: If a monopoly board csv for a theme contains a space that is the Go type, it should be represented
 * by an instantiation of this class' OwnableUtilitySpace subclass
 * Dependencies: Player, Bank
 * @author Cameron Jarnot
 */
public abstract class NonOwnableSpace extends BoardSpace {

    protected final String id;
    protected Bank bank;

    /**
     * Initializes the non-ownable space's ID. Passes Bank in.
     * @param spaceId is the name of the space
     * @param bankIn is the Bank to be used for transactions
     */
    public NonOwnableSpace(String spaceId, Bank bankIn){
        this.setLocation(-1);
        id = spaceId;
        bank = bankIn;
    }

    /**
     * Initializes the non-ownable space's ID and passing go amount. Passes Bank in.
     * @param spaceId is the name of the space
     * @param bankIn is the Bank to be used for transactions
     * @param amountPassingGo is the amount received for passing Go
     */
    public NonOwnableSpace(String spaceId, Bank bankIn,int amountPassingGo){
        this(spaceId,bankIn);
    }

    /**
     * Executes the behavior expected when a player lands on the space
     * @param player is the player who landed on the space
     * @param spaceID is the ID of the space the player landed on (to be passed to front end)
     * @return a TurnModel representing what happened when they landed there
     */
    public abstract TurnModel landOnSpace(Player player, int spaceID);

}
