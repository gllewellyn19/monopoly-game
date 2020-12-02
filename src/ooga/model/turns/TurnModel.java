package ooga.model.turns;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores data about what occurs when a player lands on a space when the turn affects other spaces
 * or players, particularly in the cases of bidding, going to jail, moving the player's location, and paying money
 * to be put in free parking.
 * Example: If a player purchases a space, the corresponding Player object must be set as the new owner of the
 * property, but the BoardSpaces classes do not have access to all players, so this class should be used to show
 * that this should occur.
 * Note: This class is primarily a storage device
 * @author Cameron Jarnot, Delaney Demark
 */
public class TurnModel {

  private static final int NO_UPDATE = -1;
  private int spaceID_number;
  private boolean goToJail;
  private int newLocation;
  private int bidPurchaser;
  private boolean gettingProperty;
  private List<Integer> possibleProperties;
  private int newFreeMoney;
  private int bidAmount;

  public TurnModel(int spaceID_numberIn){
    spaceID_number = spaceID_numberIn;
    newLocation = spaceID_numberIn;
    goToJail = false;
    bidPurchaser = NO_UPDATE;
    possibleProperties = new ArrayList<>();
    gettingProperty = false;
    newFreeMoney = 0;
    bidAmount = 0;
  }

  public TurnModel(){
    this(NO_UPDATE);
  }

  /**
   * This method stores the new position that a player has been moved to in the newLocation instance variable
   * @param newSpaceID is the ID of the new space where the player has been moved
   */
  public void updateNewPosition(int newSpaceID) {
    newLocation = newSpaceID;
  }

  /**
   * This method sets the boolean gettingProperty to true
   * This should be called when a player who did not land on the property purchases the property
   */
  public void setGettingProperty(){
    gettingProperty = true;
  }

  /**
   * This method sets the boolean goToJail to true
   * This method should be called when a player is sent to jail as a result of a card drawn, landing on Go To Jail, or
   * for any other reason
   */
  public void setGoToJail(){
    goToJail = true;
  }

  /**
   * This method stores the information about a purchase that is made via bidding
   * @param bidderID is the ID of the new owner of the property
   * @param bidAmountIn is the amount the bidder paid for the property
   */
  public void setBidPurchase(int bidderID, int bidAmountIn){
    bidPurchaser = bidderID;
    bidAmount = bidAmountIn;
  }

  /**
   * This methods holds a list of the IDs of possible properties that the player can
   * buy, as determined by the action of the GetProperty CardType
   * @param spaceIDs
   */
  public void setPossibleProperties(List<Integer> spaceIDs){
    possibleProperties = spaceIDs;
  }

  /**
   * This method returns the current location of the player whose turn it is
   * @return the int ID of the space where the player currently resides
   */
  public int getCurrentLocation(){
    return spaceID_number;
  }

  /**
   * This method is used to determine if a player was moved to jail during a turn
   * @return true if the player was moved to jail, false if the player was not moved to jail
   */
  public boolean checkGoToJail(){
    return goToJail;
  }

  /**
   * This method returns the location to which the player should be moved
   * @return the int ID of the space where the player should be moved
   */
  public int getNewLocation(){
    return newLocation;
  }

  /**
   * This method should be used to determine whether or not a bid purchase was made
   * @return true if a bid purchase was made, false if a bid purchase was not made
   */
  public boolean checkIfBidPurchased(){
    return bidPurchaser != -1;
  }

  /**
   * This method returns the ID of the player who won a property by bidding
   * @return the ID of the player who won a bidding war, or -1 if no bidding war occurred
   */
  public int getBidPurchaser(){
    return bidPurchaser;
  }

  /**
   * This method returns the list of IDs of properties that the player could possibly buy
   * Used for CardType GetProperty
   * @return List of property IDs
   */
  public List<Integer> getPossibleProperties(){
    return possibleProperties;
  }

  /**
   * This method returns true if a player is being given a property or false otherwise
   * @return true if the current player is getting a property, false otherwise
   */
  public boolean isGettingProperty(){
    return gettingProperty;
  }

  /**
   * This method should be called if a player paid taxes or otherwise added to the free parking money
   * @param amount is the int amount of money added to free parking
   */
  public void freeMoneyAdded(int amount){
    newFreeMoney = amount;
  }

  /**
   * This method returns true if the free parking money was added to
   * @return the int amount of money added to free parking or 0 if none was added
   */
  public int getFreeMoneyAdded(){
    return newFreeMoney;
  }

  /**
   * This method returns the amount of money the winning bidder paid for the property
   * @return the int amount of money the winning bidder paid for the property
   */
  public int getBidAmount(){
    return bidAmount;
  }

}
