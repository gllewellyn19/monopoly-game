package ooga.model.boardSpaces;

import java.util.List;
import ooga.model.Bank;
import ooga.model.dice.ModelDiceRollable;

/**
 * Represents a space of the utility type. When a player lands on a space of this type, they pay rent to the
 * owner. If there is no owner, they are given the option to buy the space. If they choose not to buy the space,
 * the other players may bid on it.
 * Dependencies: Bank, Player, TurnModel
 * Example: If a monopoly board csv for a theme contains a space that is the Utility type, it should be
 * represented by an instantiation of this class
 * Note: Rent is calculated by multiplying a dice roll by 4 or 10 depending on ownership
 * @author Cameron Jarnot
 */
public class OwnableUtilitiesSpace extends OwnableSpace {

  private static final int ONE_UTILITIES_RENT = 4;
  private static final int TWO_UTILITIES_RENT = 10;

  private ModelDiceRollable modelDiceRollable;

  /**
   * Initializes the utility space's ID and price. Passes Bank and ModelDiceRollable
   * into the space.
   * @param ID is the name of the utility
   * @param priceToBuy is the cost to purchase the utility
   * @param modelDiceRollable is the dice model to be used for rent calculations
   * @param bankIn is the Bank to be used for transactions
   */
  public OwnableUtilitiesSpace(String ID, int priceToBuy, ModelDiceRollable modelDiceRollable, Bank bankIn) {
    super(ID,priceToBuy,bankIn);
    this.modelDiceRollable = modelDiceRollable;
  }

  /**
   * Calculates rent based on a dice roll to find the amount the player must pay
   * If the owner owns one utility, multiply dice roll by 4
   * If the owner owns two utilities, multiply dice roll by 10
   */
  @Override
  protected void calculateRent(){
    countUtilitiesOwned();
    int rollAmount = getAmountRolled(modelDiceRollable.rollDice());
    if(countUtilitiesOwned() == 1){
      this.rent = ONE_UTILITIES_RENT*rollAmount;
    }
    else if(countUtilitiesOwned() == 2){
      this.rent = TWO_UTILITIES_RENT*rollAmount;
    }
  }

  //Returns the sum of a dice roll
  private int getAmountRolled(List<Integer> diceRoll) {
    int sum=0;
    for (Integer numDice: diceRoll) {
      sum+=numDice;
    }
    return sum;
  }

  //counts how many utilities the player owns
  private int countUtilitiesOwned(){
    int utilitiesOwned = 0;
    if(this.owner.getId() != -1){
      utilitiesOwned = countUtilitiesInProperties();
    }
    return utilitiesOwned;
  }

  //iterates through player's properties to count utilities
  private int countUtilitiesInProperties() {
    int utilsOwned = 0;
    for(OwnableSpace x: this.owner.getPlayerProperties()){
      if(x instanceof OwnableUtilitiesSpace){
        utilsOwned++;
      }
    }
    return utilsOwned;
  }

}
