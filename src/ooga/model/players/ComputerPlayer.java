package ooga.model.players;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ooga.model.realEstates.RealEstate;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/**
 * Implements the methods related to updating and prompting an AI player who is not directly interacting
 * with the user interface to make decisions
 * @author Cameron Jarnot, Delaney Demark, Grace Llewellyn, Anna Diemel
 */
public class ComputerPlayer extends Player {

  private static final String WENT_TO_JAIL = "went to jail";
  private static final String IS_JUST_VISITING = "is just visiting.";
  private static final String COLLECTED_$ = "collected $";
  private static final String PAID_$ = "paid $";
  private static final String BOUGHT_THIS_SPACE_FOR_$ = "bought this space for $";
  private static final String IN_RENT = " in rent.";
  private static final String JAIL_CARD = "used a get out of jail card.";
  private static final String STAYED_IN_JAIL = " stayed in jail.";
  public static final int DOUBLE = 2;

  /**
   * Initializes the computer player balance to 0, inJail to false, cards to an empty list,
   * properties to an empty list, ID to -1, and turnsInJail to 0
   */
  public ComputerPlayer(){
    super();
  }
  public ComputerPlayer(Integer playerID, Promptable promptable) {
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
    this.promptable.alertOfComputerPlayerAction(playerID,spaceID,WENT_TO_JAIL);
    checkBalance();
  }

  /**
   * Inform users that player is visiting jail
   * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
   * @param playerID is the player visiting jail
   * @param spaceID is the space ID of the jail
   */
  @Override
  public void visitingJail(int playerID, int spaceID) {
    this.promptable.alertOfComputerPlayerAction(playerID,spaceID,IS_JUST_VISITING);
    checkBalance();
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
    String message = COLLECTED_$ +amount;
    this.promptable.alertOfComputerPlayerAction(playerID,spaceID,message);
    checkBalance();
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
    String message = PAID_$ +amount;
    this.promptable.alertOfComputerPlayerAction(playerID,spaceID,message);
    checkBalance();
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
    boolean purchaseDecision = this.getPlayerBalance() >= DOUBLE*amountToBuy;
    String message = BOUGHT_THIS_SPACE_FOR_$+amountToBuy;
    this.promptable.alertOfComputerPlayerAction(playerID,spaceID,message);
    checkBalance();
    return purchaseDecision;
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
    checkBalance();
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
    String message = PAID_$+amount+ IN_RENT;
    this.promptable.alertOfComputerPlayerAction(payerID,spaceID,message);
    checkBalance();
  }

  /**
   * Inform users that a player collected mortgage money
   * Assumption: Player ID must match corresponding player ID in view
   * @param playerID is the ID of the player who mortgaged
   * @param amount is the amount they received by mortgaging
   */
  @Override
  public void collectedMortgageMoney(int playerID, int amount) {
    this.promptable.alertOfComputerPlayerMortgage(playerID,amount);
    checkBalance();
  }

  /**
   * Inform users that a player paid off a mortgage
   * Assumption: Owner ID must match corresponding player ID in view
   * @param ownerID is the ID of the player who paid off the mortgage
   * @param amountPaid is the amount they paid to pay off the mortgage
   */
  @Override
  public void paidMortgageOff(int ownerID, int amountPaid) {
    this.promptable.alertOfComputerPlayerUnmortgage(ownerID,amountPaid);
    checkBalance();
  }

  //Work in progress
  @Override
  public Hashtable<String, String> makeTrade(List<String> displayInitializer) {
    return null;
  }

  //Work in progress
  @Override
  public Map<Integer, RealEstate> buyRealEstate(List<String> houseNames, List<String> hotelNames,
      InfoForGamePlay infoForGamePlay) {
    return null;
  }

  /**
   * Asks a player if they would like to use a stored OutJail card
   * @return true if they use the card, false if they do not
   */
  protected boolean askToUseJailCard(){
    return true;
  }

  /**
   * Inform users that the player used a jail card
   */
  @Override
  protected void useJailCard() {
    this.promptable.alertOfComputerPlayerAction(getId(),getLocation(), JAIL_CARD);
    checkBalance();
  }

  /**
   * Inform users that a computer player remains in jail
   */
  @Override
  protected void stayInJail() {
    this.promptable.alertOfComputerPlayerAction(getId(),getLocation(), STAYED_IN_JAIL);
    checkBalance();
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
    List<Integer> diceRoll = modelDiceRollable.rollDice();
    turnableModel.movePlayerUsingDiceRoll(diceRoll);
    this.promptable.alertOfComputerPlayerRoll(diceRoll,super.getId());
  }

  private void checkBalance(){
    if(this.getPlayerBalance() <= 0){
      for(OwnableSpace space: this.getPlayerProperties()){
        if(!space.checkIfMortgaged()){
          space.mortgage();
        }
      }
    }
  }


}
