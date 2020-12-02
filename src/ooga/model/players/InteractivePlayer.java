package ooga.model.players;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ooga.model.realEstates.RealEstate;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.gamePlay.TurnableModel;
import ooga.model.realEstates.LargeEstate;
import ooga.model.realEstates.SmallEstate;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;
import ooga.view.popup.PopUpInformation;

/**
 * Implements the methods related to updating and prompting a human player who is directly interacting
 * with the user interface to make decisions
 * Dependencies: Promptable interface
 * Example: If a player lands on a Go space, the player's collectedMoney() method should be called to update the player
 * on the view of what has happened
 * @author Cameron Jarnot, Delaney Demark, Grace Llewellyn, Anna Diemel
 */
public class InteractivePlayer extends Player {

    public InteractivePlayer(){
        super();
    }
    public InteractivePlayer(Integer playerID, Promptable promptable) {
        super(playerID,promptable);
    }

    /**
     * Moves the player to Jail via frontend
     * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
     * @param playerID is the player to move
     * @param spaceID is the space to move to (where the Jail is)
     */
    @Override
    public void moveToJail(int playerID, int spaceID) {
        this.promptable.promptMoved(playerID,spaceID);
    }

    /**
     * Inform users that player is visiting jail
     * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
     * @param playerID is the player visiting jail
     * @param spaceID is the space ID of the jail
     */
    @Override
    public void visitingJail(int playerID, int spaceID) {
        this.promptable.promptVisitingJail(playerID,spaceID);
    }

    /**
     * Inform users that player collected money
     * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
     * @param playerID is the player who collected money
     * @param spaceID is the ID of the space they landed on to get the money
     * @param amount is the amount they collected
     */
    @Override
    public void collectedMoney(int playerID, int spaceID, int amount) {
        this.promptable.promptGotMoney(playerID,spaceID,amount);
    }

    /**
     * Inform users that player paid money
     * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
     * @param playerID is the player who paid money
     * @param spaceID is the ID of the space they landed on to pay the money
     * @param amount is the amount they paid
     */
    @Override
    public void paidMoney(int playerID, int spaceID, int amount) {
        this.promptable.promptPaidMoney(playerID,spaceID,amount);
    }

    /**
     * Ask user if they would like to buy the property
     * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
     * @param playerID is the ID of the player who can buy the property
     * @param spaceID is the ID of the space they user can buy
     * @param amountToBuy is the cost of the space
     * @return true if they buy it, false if they don't
     */
    @Override
    public boolean promptToBuy(int playerID, int spaceID, int amountToBuy) {
        return this.promptable.promptPurchase(playerID,spaceID,amountToBuy);
    }

    /**
     * Prompt the users to ask if they would like to bid
     * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
     * @param playerID is the ID of the player who did not buy the property
     * @param spaceID is the ID of the space to bid on
     * @return an optional List of ints with playerID, bid amount
     */
    @Override
    public Optional<List<Integer>> promptBidding(int playerID, int spaceID) {
        return this.promptable.promptBidding(playerID,spaceID);
    }

    /**
     * Inform users that a player paid rent
     * Assumption: The payer ID and owner ID must match the corresponding player IDs in the view, the space ID must do
     * the same
     * @param payerID is the player who paid
     * @param ownerID is the owner of the property
     * @param spaceID is the ID of the property
     * @param amount is the amount they paid in rent
     */
    @Override
    public void paidRent(int payerID, int ownerID, int spaceID, int amount) {
        this.promptable.promptPaidRent(payerID,ownerID,spaceID,amount);
    }

    /**
     * Inform users that a player collected mortgage money
     * Assumption: Player ID must match corresponding player ID in view
     * @param playerID is the ID of the player who mortgaged
     * @param amount is the amount they received by mortgaging
     */
    @Override
    public void collectedMortgageMoney(int playerID, int amount) {
        this.promptable.promptMoneyBackForUnmortgage(playerID,amount);
    }

    /**
     * Inform users that a player paid off a mortgage
     * Assumption: Owner ID must match corresponding player ID in view
     * @param ownerID is the ID of the player who paid off the mortgage
     * @param amountPaid is the amount they paid to pay off the mortgage
     */
    @Override
    public void paidMortgageOff(int ownerID, int amountPaid) {
        this.promptable.promptMoneyBackForUnmortgage(ownerID,amountPaid);
    }

    /**
     * Asks a player if they would like to use a stored OutJail card
     * @return true if they use the card, false if they do not
     */
    @Override
    protected boolean askToUseJailCard(){
        return this.promptable.promptToUseGetOutOfJailCard(this.getId());
    }

    /**
     * Inform users that the player used a jail card
     */
    @Override
    protected void useJailCard() {
        this.promptable.promptUsedJailCard(getId());
    }

    /**
     * Inform users that a player remains in jail
     */
    @Override
    protected void stayInJail() {
        this.promptable.stayInJail(getId());
    }

    /**
     * Inform users of whose turn it is to roll
     * @param turnableView is the TurnableView interface to use to reach view
     * @param modelDiceRollable is the ModelDiceRollable to use for dice roll
     * @param turnableModel is the TurnableModel used
     */
    @Override
    protected void turnToRoll(TurnableView turnableView, ModelDiceRollable modelDiceRollable,
        TurnableModel turnableModel) {
        turnableView.updateCurrentUser(super.getId());
    }

    @Override
    public Hashtable<String, String> makeTrade(List<String> playerProperties) {
        PopUpInformation info = promptable.promptTrade(playerProperties, getId());
        if (info == null)
            return null;
        Hashtable<String, String> outputTable = new Hashtable();
        if (!(info.getTradeType() ==null)) {
            outputTable.put("tradeType", info.getTradeType());
        }
        if (!(info.getSelectedProperty() ==null)) {
            outputTable.put("propertyID",
                String.valueOf(getPropertyID(playerProperties, info.getSelectedProperty())));
        }
        if (!(info.getTradeValue() ==null)) {
            outputTable.put("moneyEarned", info.getTradeValue());
        }
        if (!(info.getTradePartnerID() ==null)){
            outputTable.put("tradePartner", info.getTradePartnerID());
        }
        return outputTable;
    }

    private int getPropertyID(List<String> playerProperties, String propertyName) {
        int propertyID = 0;
        for (int spaceID = 0; spaceID < playerProperties.size(); spaceID++) {
            if (playerProperties.get(spaceID).equals(propertyName)) {
                propertyID = spaceID;
            }
        }
        return propertyID;
    }

    @Override
    public Map<Integer, RealEstate> buyRealEstate(List<String> houseNames, List<String> hotelNames,
        InfoForGamePlay infoForGamePlay) {
        PopUpInformation info = promptable.startPurchaseRealEstatePrompt(getPlayerProperties(), getId(), houseNames, hotelNames);
        if (info == null) {
            return null;
        }
        int propertyID;
        RealEstate estate;
        if (info.getSelectedEstateType().equals("House")) {
            propertyID = getPropertyID(houseNames, info.getSelectedProperty());
            estate = new SmallEstate();
            // add small estate to displaySpace
        } else if (info.getSelectedEstateType().equals("Hotel")) {
            propertyID = getPropertyID(hotelNames, info.getSelectedProperty());
            estate = new LargeEstate();
            // add large estate to displaySpace
        } else {
            return null;
        }
        Map<Integer, RealEstate> estateMap = new HashMap<>();
        estateMap.put(propertyID, estate);
        return estateMap;
    }

}