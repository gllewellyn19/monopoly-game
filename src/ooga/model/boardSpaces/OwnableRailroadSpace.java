package ooga.model.boardSpaces;

import ooga.model.Bank;

/**
 * Represents a space of the property type. When a player lands on a space of this type, they pay rent to the
 * owner. If there is no owner, they are given the option to buy the space. If they choose not to buy the space,
 * the other players may bid on it.
 * Dependencies: Bank, Player, TurnModel
 * Example: If a monopoly board csv for a theme contains a space that is the Railroad type, it should be
 * represented by an instantiation of this class
 * Note: Rent is calculated by dividing the property value by 4, then multiplying by 2^(number of railroads owned)
 * @author Cameron Jarnot
 */
public class OwnableRailroadSpace extends OwnableSpace {

  private static final int BASE_RENT_FACTOR = 4;
  private static final int ADDITIONAL_RENT_FACTOR = 2;

  /**
   * Initializes the ID and price of the railroad. Passes in a Bank.
   * @param ID is the name of the railroad
   * @param priceToBuy is the cost to purchase the railroad
   * @param bankIn is the bank to be used for transactions
   */
  public OwnableRailroadSpace(String ID, int priceToBuy, Bank bankIn) {
    super(ID,priceToBuy,bankIn);
  }

  /**
   * Calculates rent based on the number of railroads owned
   */
  @Override
  protected void calculateRent(){
    if(!this.mortgaged){
      countRailroadsOwned();
      this.rent = (this.price/ BASE_RENT_FACTOR)*(int)Math.pow(ADDITIONAL_RENT_FACTOR,countRailroadsOwned()-1);
    }
    else{
      this.rent = 0;
    }
  }

  //counts how many railroads the player owns
  private int countRailroadsOwned(){
    int railroadsOwned = 0;
    if(this.owner != null){
      railroadsOwned = countRailroadsInProperties();
    }
    return railroadsOwned;
  }

  //iterates through the player's properties to count their railroads
  private int countRailroadsInProperties() {
    int railsOwned = 0;
    for(OwnableSpace x: this.owner.getPlayerProperties()){
      if(x instanceof OwnableRailroadSpace){
        railsOwned++;
      }
    }
    return railsOwned;
  }

}