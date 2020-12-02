package ooga.model.players;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ooga.model.boardSpaces.OwnablePropertySpace;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.model.realEstates.RealEstate;
import ooga.model.cards.Card;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/***
 * Highest level is abstract and will have instance variables needed for the user such as a unique ID, player's
 * balance, and player's lists of cards and ownable spaces
 * @author Cameron Jarnot, Delaney Demark, Grace Llewellyn, Anna Diemel
 */
public abstract class Player {

  private List<Card> playerCards;
  private List<OwnableSpace> playerProperties;
  private int playerBalance;
  private int id;
  private int location;
  private boolean inJail;
  private int turnsInJail;
  protected Promptable promptable;

  /**
   * Initializes the computer player balance to 0, inJail to false, cards to an empty list,
   * properties to an empty list, ID to -1, and turnsInJail to 0
   */
  public Player(){
    playerBalance = 0;
    inJail = false;
    playerCards = new ArrayList<>();
    playerProperties = new ArrayList<>();
    id = -1;
    turnsInJail = 0;
  }

  /**
   * Initializes the computer player balance to 0, inJail to false, cards to an empty list,
   * properties to an empty list, ID to playerID, and turnsInJail to 0. Passes in Promptable
   * interface
   * @param playerID is the int ID of the player
   * @param promptableIn is the Promptable interface to access the frontend
   */
  public Player(Integer playerID, Promptable promptableIn){
    id = playerID;
    playerBalance = 0;
    inJail = false;
    playerCards = new ArrayList<>();
    playerProperties = new ArrayList<>();
    promptable = promptableIn;
  }

  /**
   * @return a copy of playerCards
   */
  public List<Card> getPlayerCards(){
    return playerCards;
  };

  /**
   * Adds the card drawn to a player's stored card
   * Particularly useful for OutJail cards
   * @param cardDrawn is the card that was drawn to be stored
   */
  public void addToPlayerCards(Card cardDrawn){
    playerCards.add(cardDrawn);
  }

  /**
   *
   * @return the list of properties that the player owns.
   */
  public List<OwnableSpace> getPlayerProperties(){
    return playerProperties;
  }

  /**
   * Adds a new property when a player has purchased one
   * @param propertyBought the new property to add to the player's collection
   */
  public void addToPlayerProperties(OwnableSpace propertyBought){
    playerProperties.add(propertyBought);
  }

  /**
   * Reverses whether player is in jail
   */
  public void changeInJailState(){
    if(inJail){
      turnsInJail = 0;
    }
    else{
      turnsInJail = 1;
    }
    inJail = !inJail;
  }

  /**
   * @return whether player is in jail
   */
  public boolean isInJail(){
    return inJail;
  }

  /**
   * If player is in jail, returns number of turns they have been there
   * If player is not in jail, returns 0
   * @return the number of turns spent in jail
   */
  public int getTurnsInJail(){
    return turnsInJail;
  }

  /**
   * Returns the amount of money a player has
   * @return the amount of money the player has
   */
  public int getPlayerBalance(){
    return playerBalance;
  }

  /**
   * Changes the player's playerBalance
   * @param amount the amount to set playerBalance to
   */
  public void setPlayerBalance(int amount){
    playerBalance = amount;
  }

  /**
   * Subtracts input amount from current player balance
   * @param amount the amount to subtract from the current player balance
   */
  public void decreasePlayerBalance(int amount){
    playerBalance -= amount;
    //determineIfPlayerLoses();
  }

  /**
   * Adds input amount to current player balance
   * @param amount the amount to add to the current player balance
   */
  public void increasePlayerBalance(int amount){
    playerBalance += amount;
  }

  /**
   * Determine if the player loses by seeing if they have no money and have mortgaged all properties
   */
  public boolean determineIfPlayerLoses(){
    return playerBalance <= 0;
  }

  /**
   * Returns the ID of the player
   * @return int ID of player
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the current location of the player
   * @return int location of player
   */
  public int getLocation(){
    return location;
  }

  /**
   * Moves player to a new location on the backend board, calculates if they passed Go
   * @param newLocation is the new location to move to
   * @param amountPassingGo is the amount the player receives for passing Go in case they did pass Go
   */
  public void sendToLocation(int newLocation, int amountPassingGo) {
    if(newLocation < location && !inJail){
      playerBalance += amountPassingGo;
    }
    if (location >= 0) {
      location = newLocation;
    }
  }

  /**
   * Updates the location of the player based on a number, gives them money if they pass Go.
   * @param numBoardSpaces is the number of total board spaces on the board
   * @param amountToIncreaseBy is the amount of spaces moved, usually by a dice roll
   * @param amountPassingGo is the amount they get if they passed Go
   * @return the int value of the new location
   */
  public int updateLocation(int numBoardSpaces, int amountToIncreaseBy, int amountPassingGo) {

    int newLocation = location + amountToIncreaseBy;
    if (newLocation > numBoardSpaces) {
      newLocation -= numBoardSpaces;
      playerBalance += amountPassingGo;
    }
    /* Dont get the money from passing go because landed on Go so Go deals with it */
    else if (newLocation == numBoardSpaces) {
      newLocation = 0;
    }else if (newLocation < 0){
      newLocation = numBoardSpaces + newLocation;
    }
    location = newLocation;

  return location;
  }

  /**
   * Checks for an OutJail card, the prompts user to ask if they want to use it if they have one.
   * If they don't have one, increments their turns in jail by one and prompts user to tell them they stay there.
   */
  public void takeTurnInJail(){
    boolean hasJailCard = false;
    for(Card card:playerCards){
      if(card.getCardType().toString().equals("OutJail")){
        hasJailCard = true;
      }
    }
    if(hasJailCard){
      boolean cardDecision = askToUseJailCard();
      if(cardDecision){
        changeInJailState();
        useJailCard();
      }
      else{
        turnsInJail += 1;
        stayInJail();
      }
    }
    else{
      turnsInJail += 1;
      stayInJail();
    }
  }

  /**
   * Moves the player to Jail via frontend
   * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
   * @param playerID is the player to move
   * @param spaceID is the space to move to (where the Jail is)
   */
  public abstract void moveToJail(int playerID, int spaceID);

  /**
   * Inform users that player is visiting jail
   * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
   * @param playerID is the player visiting jail
   * @param spaceID is the space ID of the jail
   */
  public abstract void visitingJail(int playerID,int spaceID);

  /**
   * Inform users that player collected money
   * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
   * @param playerID is the player who collected money
   * @param spaceID is the ID of the space they landed on to get the money
   * @param amount is the amount they collected
   */
  public abstract void collectedMoney(int playerID, int spaceID, int amount);

  /**
   * Inform users that player paid money
   * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
   * @param playerID is the player who paid money
   * @param spaceID is the ID of the space they landed on to pay the money
   * @param amount is the amount they paid
   */
  public abstract void paidMoney(int playerID, int spaceID, int amount);

  /**
   * Ask user if they would like to buy the property
   * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
   * @param playerID is the ID of the player who can buy the property
   * @param spaceID is the ID of the space they user can buy
   * @param amountToBuy is the cost of the space
   * @return true if they buy it, false if they don't
   */
  public abstract boolean promptToBuy(int playerID,int spaceID,int amountToBuy);

  /**
   * Prompt the users to ask if they would like to bid
   * Assumption: The playerID must match the corresponding playerID in the view and the same goes for the space IDs
   * @param playerID is the ID of the player who did not buy the property
   * @param spaceID is the ID of the space to bid on
   * @return an optional List of ints with playerID, bid amount
   */
  public abstract Optional<List<Integer>> promptBidding(int playerID, int spaceID);

  /**
   * Display a card to the user
   * Assumption: The card's ID must match the corresponding card ID in the view
   * @param cardID is the ID of the card to show
   */
  public void drawCard(int cardID){
    promptable.showCard(cardID);
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
  public abstract void paidRent(int payerID,int ownerID,int spaceID,int amount);

  /**
   * Inform users that a player collected mortgage money
   * Assumption: Player ID must match corresponding player ID in view
   * @param playerID is the ID of the player who mortgaged
   * @param amount is the amount they received by mortgaging
   */
  public abstract void collectedMortgageMoney(int playerID,int amount);

  /**
   * Inform users that a player paid off a mortgage
   * Assumption: Owner ID must match corresponding player ID in view
   * @param ownerID is the ID of the player who paid off the mortgage
   * @param amountPaid is the amount they paid to pay off the mortgage
   */
  public abstract void paidMortgageOff(int ownerID,int amountPaid);

  public abstract Hashtable<String, String> makeTrade(List<String> displayInitializer);

  public abstract Map<Integer, RealEstate> buyRealEstate(
      List<String> houseNames, List<String> hotelNames,
      InfoForGamePlay infoForGamePlay);

  /**
   * Asks a player if they would like to use a stored OutJail card
   * @return true if they use the card, false if they do not
   */
  protected abstract boolean askToUseJailCard();

  /**
   * Inform users that the player used a jail card
   */
  protected abstract void useJailCard();

  /**
   * Inform users that a player remains in jail
   */
  protected abstract void stayInJail();

  /**
   * Inform users of whose turn it is to roll
   * @param turnableView is the TurnableView interface to use to reach view
   * @param modelDiceRollable is the ModelDiceRollable to use for dice roll
   * @param turnableModel is the TurnableModel used
   */
  protected abstract void turnToRoll(TurnableView turnableView, ModelDiceRollable modelDiceRollable,
      TurnableModel turnableModel);

  /**
   * Checks if a player has a monopoly in a neighborhood
   * @return true if they have monopoly, false if they do not
   */
  public boolean hasMonopoly() {
    for (OwnableSpace property : playerProperties) {
      if (property.getClass() == OwnablePropertySpace.class) {
        if (((OwnablePropertySpace) property).isMonopoly()) {
          return true;
        }
      }
    }
    return false;
  }
}
