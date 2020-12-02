package ooga.model.boardSpaces;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ooga.model.Bank;
import ooga.model.Cards;
import ooga.model.cards.Card;
import ooga.model.players.Player;
import ooga.model.exceptions.ModelException;
import ooga.model.turns.TurnModel;


/**
 * This class holds the methods that card spaces call when a player lands on a space
 * @author delaneydemark
 */
public abstract class NonOwnableCardSpace extends NonOwnableSpace {

    private Cards cardDecks;

    public NonOwnableCardSpace(String spaceId, Bank bankIn){
        super(spaceId, bankIn);
    }

    public NonOwnableCardSpace(String spaceId, Bank bankIn, Cards decks){
        super(spaceId, bankIn);
        cardDecks = decks;
    }

    /**
     * The action taken when landing on a space
     * @param player is the player who landed on the space
     * @param spaceID is the ID of the space the player landed on (to be passed to front end)
     * @return turn model with information saved that is needed in GamePlay
     */
    @Override
    public abstract TurnModel landOnSpace(Player player, int spaceID);

    /**
     * Performs the action of the card drawn
     * @param turn holder action information
     * @param player in turn
     * @param cardPicked from the deck
     * @param spaceID of current space
     */
    protected void performCardAction(TurnModel turn, Player player, Card cardPicked, int spaceID){
        try {
            Class[] methodParameters = new Class[5];
            methodParameters[0] = TurnModel.class;
            methodParameters[1] = Player.class;
            methodParameters[2] = int.class;
            methodParameters[3] = int.class;
            methodParameters[4] = int.class;
            Method takeCardAction = NonOwnableCardSpace.this.getClass().getSuperclass().
                getDeclaredMethod("player"+cardPicked.getCardType().toString(), methodParameters);
            takeCardAction.invoke(NonOwnableCardSpace.this, turn, player, cardPicked.
                getMoneyChange(), cardPicked.getPositionChange(), spaceID);
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ModelException("invalidCardType");
        }
    }

    /**
     * Gives the player the provided amount of money
     * @param turn turn holder action information
     * @param player in turn
     * @param amount of money
     * @param spaces to move
     * @param spaceID of current space
     */
    protected void playerGetMoney(TurnModel turn, Player player, int amount, int spaces, int spaceID){
        if(amount>=0) bank.increaseBalance(player.getId(), amount);
        player.collectedMoney(player.getId(), spaceID, amount);
    }

    /**
     * Player pays the provided amount of money
     * @param turn turn holder action information
     * @param player in turn
     * @param amount of money
     * @param spaces to move
     * @param spaceID of current space
     */
    protected void playerPayMoney(TurnModel turn, Player player, int amount, int spaces, int spaceID){
        if(amount>=0) bank.decreaseBalance(player.getId(), amount);
        player.paidMoney(player.getId(), spaceID, amount);
    }

    /**
     * Player moves the given number of spaces
     * @param turn turn holder action information
     * @param player in turn
     * @param amount of money
     * @param spaces to move
     * @param spaceID of current space
     */
    protected void playerMove(TurnModel turn, Player player, int amount, int spaces, int spaceID){
        turn.updateNewPosition(spaceID + spaces);
    }

    /**
     * Player moves to new location and updates money if passing go
     * @param turn turn holder action information
     * @param player in turn
     * @param amount of money
     * @param spaces to move
     * @param spaceID of current space
     */
    protected void playerMoveGetMoney(TurnModel turn, Player player, int amount, int spaces, int spaceID){
        turn.updateNewPosition(spaces);
    }

    /**
     * Player is put in jail
     * @param turn turn holder action information
     * @param player in turn
     * @param amount of money
     * @param spaceID of current space
     */
    protected void playerInJail(TurnModel turn, Player player, int amount, int spaceMove, int spaceID){
        if(!player.isInJail()) player.changeInJailState();
        player.moveToJail(player.getId(), spaceID);
        turn.updateNewPosition(spaceMove);
    }

    /**
     * Player is released from jail
     * @param turn turn holder action information
     * @param player in turn
     * @param amount of money
     * @param spaces to move
     * @param spaceID of current space
     */
    protected void playerOutJail(TurnModel turn, Player player, int amount, int spaces, int spaceID){
        if(player.isInJail()) player.changeInJailState();
    }

    /**
     * Player receives the amount of money from each player
     * @param turn turn holder action information
     * @param player in turn
     * @param amount of money
     * @param spaces to move
     * @param spaceID of current space
     */
    protected void playerGetMoneyFromPlayers(TurnModel turn, Player player, int amount, int spaces, int spaceID){
        int totalReceived = bank.getMoneyFromOtherPlayers(player.getId(), amount);
        player.collectedMoney(player.getId(), spaceID, totalReceived);
    }

    /**
     * Player pays the given amount per owned property
     * @param turn turn holder action information
     * @param player in turn
     * @param amount of money
     * @param spaces to move
     * @param spaceID of current space
     */
    protected void playerPayPerProperty(TurnModel turn, Player player, int amount, int spaces, int spaceID){
        int totalPaid = bank.payPerProperty(player.getId(), amount);
        player.paidMoney(player.getId(), spaceID, totalPaid);
    }

    /**
     * Player gets property in given indices if available
     * @param turn turn holder action information
     * @param player in turn
     * @param spaceID of current space
     */
    protected void playerGetProperty(TurnModel turn, Player player, int spaceOne, int spaceTwo, int spaceID){
        List<Integer> possibleProperties = new ArrayList<>();
        possibleProperties.add(spaceOne);
        possibleProperties.add(spaceTwo);
        turn.setPossibleProperties(possibleProperties);
        turn.setGettingProperty();
    }

    protected Cards getCardDecks(){
        return cardDecks;
    }

}
