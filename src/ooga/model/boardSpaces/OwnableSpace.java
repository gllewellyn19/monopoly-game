package ooga.model.boardSpaces;

import ooga.model.Bank;
import ooga.model.players.Player;
import ooga.model.players.ComputerPlayer;
import ooga.model.turns.TurnModel;

import java.util.List;
import java.util.Optional;

/***
 * This is an abstract class that is only implemented by board spaces that can be owned by a
 * user which includes railroads, properties, and utilities.
 * Dependencies: This class depends on the Bank, Player, and RealEstate classes, TurnModel, as well as the
 * Promptable interface
 * Example: An OwnablePropertySpace object could be instantiated if the monopoly board csv for a theme contains a
 * space of a Property type so that it will be treated as such
 * @author Cameron Jarnot
 */
public abstract class OwnableSpace extends BoardSpace {

  public static final double HALF = .5;
  public static final double MORTGAGE_FACTOR = 1.1;
  protected final String id;
  protected final int price;
  protected boolean mortgaged;
  protected int rent;
  protected Player owner;
  protected Bank bank;

  /**
   * Initializes the ownable space's ID and price. Passes Bank and ModelDiceRollable
   * into the space. Sets initial values of the owner, mortgaged, and rent.
   * @param ID is the name of the utility
   * @param priceToBuy is the cost to purchase the utility
   * @param bankIn is the Bank to be used for transactions
   */
  public OwnableSpace(String ID,int priceToBuy, Bank bankIn){
    this.setLocation(-1);
    id = ID;
    price = priceToBuy;
    owner = new ComputerPlayer();
    mortgaged = false;
    rent = 0;
    bank = bankIn;
  }

  /**
   * If the space is unowned, prompts the user to buy it. If owned, calculates rent
   * and uses a Bank to transfer the money from the lander to the owner
   * @param player is the player who landed on the space
   * @param spaceID is the ID of the space the player landed on (to be passed to front end)
   * @return a TurnModel representing the changes made during the turn
   */
  @Override
  public TurnModel landOnSpace(Player player, int spaceID){
    this.setLocation(spaceID);
    if(this.owner.getId() == -1){
      return unownedLandOnSpace(player, spaceID);
    }
    else{
      return ownedLandOnSpace(player, spaceID);
    }
  }

  //calculates rent and prompts player to pay it
  private TurnModel ownedLandOnSpace(Player player, int spaceID) {
    calculateRent();
    player.paidRent(player.getId(),owner.getId(), spaceID,rent);
    bank.decreaseBalance(player.getId(),rent);
    return new TurnModel();
  }

  //goes through process of offering players to buy property
  private TurnModel unownedLandOnSpace(Player player, int spaceID) {
    TurnModel turnModel = new TurnModel(spaceID);
    if(player.promptToBuy(player.getId(), spaceID,price)){
      this.owner = player;
      owner.addToPlayerProperties(this);
      bank.decreaseBalance(owner.getId(),price);
    }
    else{
      Optional<List<Integer>> newOwner = player.promptBidding(player.getId(), spaceID);
      if(newOwner.isPresent()){
        int ownerThatBought = newOwner.get().get(0);
        int amountBid = newOwner.get().get(1);
        turnModel.setBidPurchase(ownerThatBought,amountBid);
      }
    }
    return turnModel;
  }

  /**
   * Sets the owner of the OwnableProperty
   * @param ownerPlayer is the new owner
   */
  public void purchase(Player ownerPlayer){
    this.owner = ownerPlayer;
    this.owner.addToPlayerProperties(this);
    this.owner.decreasePlayerBalance(price);
  }

  /**
   * Returns the owner of the property
   * @return the Player who owns the property
   */
  public Player getOwner(){
    return owner;
  }

  /**
   * Updates the owner of the property without sending the player through the process of
   * buying the property
   * Used for situations in which the property is absorbed without money
   * Example: Used for Junior Monopoly rules, when each player is assigned two properties at the
   * beginning of gameplay
   * @param newOwner new owner Player
   */
  public void setOwner(Player newOwner){
    owner = newOwner;
  }

  /**
   * Marks the property as unmortgaged and takes necessary money from player
   */
  public void unmortgage(){
    mortgaged = false;
    int amountToPayMortgage = (int)(HALF *price);
    bank.decreaseBalance(owner.getId(), amountToPayMortgage);
    owner.paidMortgageOff(owner.getId(), amountToPayMortgage);
  }

  /**
   * Marks the property as mortgaged and gives player back corresponding money
   */
  public void mortgage(){
    mortgaged = true;
    int amountBackForMortgage = (int)(HALF*price* MORTGAGE_FACTOR);
    this.bank.decreaseBalance(owner.getId(),amountBackForMortgage);
    owner.collectedMortgageMoney(owner.getId(),amountBackForMortgage);
  }

  /**
   * Returns whether or not the property is mortgaged
   * @return a boolean: true if mortgaged, false if not mortgaged
   */
  public boolean checkIfMortgaged(){
    return mortgaged;
  }

  /**
   * Calculate rent based on the type of OwnableSpace
   */
  protected abstract void calculateRent();


}