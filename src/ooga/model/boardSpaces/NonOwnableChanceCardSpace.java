package ooga.model.boardSpaces;

import ooga.model.Bank;
import ooga.model.Cards;
import ooga.model.cards.Card;
import ooga.model.players.Player;
import ooga.model.turns.TurnModel;

/**
 * This class draws a community chest card and takes the appropriate action
 * @author delaneydemark
 */

public class NonOwnableChanceCardSpace extends NonOwnableCardSpace {

    public NonOwnableChanceCardSpace(String spaceId, Bank bank) {
        super(spaceId, bank);
    }

    public NonOwnableChanceCardSpace(String spaceId, Bank bank, Cards decks) {
        super(spaceId, bank, decks);
    }

    /**
     * The action taken when landing on a space
     * @param player is the player who landed on the space
     * @param spaceID is the ID of the space the player landed on (to be passed to front end)
     * @return turn model with information saved that is needed in GamePlay
     */
    @Override
    public TurnModel landOnSpace(Player player, int spaceID) {
        this.setLocation(spaceID);
        Card cardPicked = this.getCardDecks().getTopChanceCard();
        player.drawCard(cardPicked.getId());
        player.addToPlayerCards(cardPicked);
        TurnModel turnModel = new TurnModel(spaceID);
        super.performCardAction(turnModel, player, cardPicked, spaceID);
        return turnModel;
    }


}