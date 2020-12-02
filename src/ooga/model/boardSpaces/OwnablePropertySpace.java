package ooga.model.boardSpaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.Bank;
import ooga.model.realEstates.LargeEstate;
import ooga.model.realEstates.RealEstate;
import ooga.model.realEstates.SmallEstate;

/**
 * Represents a space of the property type. When a player lands on a space of this type, they pay rent to the
 * owner. If there is no owner, they are given the option to buy the space. If they choose not to buy the space,
 * the other players may bid on it.
 * Dependencies: This class depends on the Bank, Player, and RealEstate classes, TurnModel, as well as the
 * Promptable interface
 * Assumption: The price to buy the property must be greater than 40 or the base rent value will be negative
 * Example: This class could be instantiated if the monopoly board csv for a theme contains a space of a Property type
 * so that it will be treated as such
 * Note: The base rent is calculated by dividing the value by 10 and subtracting 4. If real estate is added to
 * the property, rent increases proportionally.
 * @author Cameron Jarnot, Anna Diemel
 */
public class OwnablePropertySpace extends OwnableSpace {

  private static final double SMALL_ESTATE_FACTOR = 2.5;
  private static final int LARGE_ESTATE_FACTOR = 50;
  private static final int MAX_HOUSES = 4;
  private static final int MAX_HOTELS = 1;
  private static final Map<String,Integer> DEFAULT_NEIGHBORHOODS = new HashMap<>() {{
    put("Light Blue", 3);
    put("Purple", 2);
    put("Pink",3);
    put("Orange",3);
    put("Red",3);
    put("Yellow",3);
    put("Green",3);
    put("Blue",2);
  }};

  private boolean monopoly;
  private final String neighborhood;
  private final int housePrice;
  private final int hotelPrice;
  private List<RealEstate> propertyEstates;
  private final int baseRent;
  private final Map<String,Integer> neighborsColorToSize;

  /**
   * Initializes the ID and price of the railroad. Passes in a Bank.
   * @param id is the name of the property
   * @param priceToBuy is the cost to purchase the railroad
   * @param neighborhoodColor is the string representation of the neighbor
   * @param houseCost is the cost of a small estate on the property
   * @param hotelCost is the cost of a large estate on the property
   * @param bankIn is the Bank to be used for transactions related to the space
   * @param neighborhoods is the map of all neighborhoods to how many properties of that color exist
   */
  public OwnablePropertySpace(String id, int priceToBuy, String neighborhoodColor, int houseCost, int hotelCost, Bank bankIn, Map<String,Integer> neighborhoods){
    super(id,priceToBuy,bankIn);
    neighborhood = neighborhoodColor;
    baseRent = priceToBuy/10-4;
    housePrice = houseCost;
    hotelPrice = hotelCost;
    if(neighborhoods.size() == 0){
      neighborsColorToSize = DEFAULT_NEIGHBORHOODS;
    }
    else{
      neighborsColorToSize = neighborhoods;
    }
    propertyEstates = new ArrayList<>();
  }

  /**
   * Getter method for the monopoly status of the property
   * @return true if the player has a monopoly based on neighborsColorToSize,
   * false otherwise
   */
  public boolean isMonopoly() { return monopoly; }

  /**
   * Mortgages the property and gives the owner the corresponding amount
   * of money based on how many small/large estates they own
   */
  @Override
  public void mortgage() {
    int amountReturned = (int)(.5*this.price*(-1.1));
    for(int i=0; i< getNumSmallEstates(); i++){
      amountReturned+=(int)(.5*housePrice);
    }
    for(int i=0; i< getNumLargeEstates(); i++){
      amountReturned+=(int)(.5*hotelPrice);
    }
    this.bank.increaseBalance(owner.getId(),amountReturned);
  }

  /**
   * Adds a house or hotel to the property
   * @param estate is the estate of the type to add
   */
  public void addEstateToProperty(RealEstate estate) {
    if (estate.getClass() == SmallEstate.class) {
      addSmallEstateToProperty();
    } else if (estate.getClass() == LargeEstate.class) {
      addLargeEstateToProperty();
    }
  }

  /**
   * Adds small estate (house in Classic Monopoly) to increase rent on the property
   */
  public void addSmallEstateToProperty(){
    checkForMonopoly();
    if(monopoly && !this.mortgaged && getNumSmallEstates() < MAX_HOUSES){
      propertyEstates.add(new SmallEstate());
      this.bank.decreaseBalance(owner.getId(),housePrice);
    }
  }

  /**
   * Adds large estate (hotel in Classic Monopoly) to increase rent on the property, removes all houses
   */
  public void addLargeEstateToProperty(){
    checkForMonopoly();
    if(monopoly && !this.mortgaged && getNumLargeEstates() < MAX_HOTELS){
      propertyEstates.removeAll(getSmallEstatesOnProperty());
      propertyEstates.add(new LargeEstate());
      this.bank.decreaseBalance(owner.getId(),hotelPrice);
    }
  }

  /**
   * Returns which neighborhood the property is in
   * @return the String representing the neighborhood
   */
  public String getNeighborhood(){
    return neighborhood;
  }

  /**
   * Calculates the rent value of the property based one number and type
   * of estates, monopoly, and the value to purchase the property
   */
  @Override
  protected void calculateRent(){
    checkForMonopoly();
    if(getNumSmallEstates() != 0){
      this.rent = (int)(baseRent*Math.pow(getNumSmallEstates(),SMALL_ESTATE_FACTOR));
    }
    else if(getNumLargeEstates() != 0){
      this.rent = (baseRent* LARGE_ESTATE_FACTOR *getNumLargeEstates());
    }
//    this.rent = (int)(baseRent*Math.pow(getNumSmallEstates(),SMALL_ESTATE_FACTOR));
//    this.rent = (baseRent* LARGE_ESTATE_FACTOR *getNumLargeEstates());
    if(this.mortgaged){
      this.rent = 0;
    }
  }

  //checks if the player has a monopoly and updates rent accordingly
  private void checkForMonopoly(){
    int ownedInNeighborhood = 0;
    if(this.owner.getId() != -1){
      for(OwnableSpace x: this.owner.getPlayerProperties()){
        if(x instanceof OwnablePropertySpace && ((OwnablePropertySpace) x).getNeighborhood().equals(this.neighborhood)){
          ownedInNeighborhood++;
        }
      }
    }
    if(neighborsColorToSize.containsKey(neighborhood)) {
      monopoly = ownedInNeighborhood == neighborsColorToSize.get(neighborhood);
    }
    else{
      monopoly = false;
    }
    setNoHouseRentBasedOnMonopoly();
  }

  //sets the rent if there are no houses or hotels
  private void setNoHouseRentBasedOnMonopoly() {
    if(monopoly){
      this.rent = (this.price/10-4)*2;
    }
    else{
      this.rent = (this.price/10-4);
    }
  }

  //returns a list of the real estates on the property
  private List<RealEstate> getSmallEstatesOnProperty() {
    return getEstatesOfType(SmallEstate.class);
  }

  /**
   * Counts the number of small estates on the property
   * @return int value of number of small estates
   */
  public int getNumSmallEstates() {
    return getSmallEstatesOnProperty().size();
  }

  //returns a list of the large estates
  private List<RealEstate> getLargeEstatesOnProperty() {
    return getEstatesOfType(LargeEstate.class);
  }

  /**
   * Counts the number of large estates on the property
   * @return int value of number of large estates
   */
  public int getNumLargeEstates() {
    return getLargeEstatesOnProperty().size();
  }

  //returns a list of the real estates of the type estateClass
  private List<RealEstate> getEstatesOfType(Class estateClass) {
    List<RealEstate> estateList = new ArrayList<>();
    if (propertyEstates == null) {
      return estateList;
    }
    for (RealEstate estate : propertyEstates) {
      if (estate.getClass() == estateClass) {
        estateList.add(estate);
      }
    }
    return estateList;
  }

  /**
   * Checks whether or not the property is available for a small estate
   * @return true if it is not mortgaged, they have a monopoly, and they
   * have not maximized their number of small estates
   */
  public boolean canBuildHouse(){
    checkForMonopoly();
    return !mortgaged && monopoly && getNumSmallEstates()<MAX_HOUSES;
  }

  /**
   * Checks whether or not the property is available for a large estate
   * @return true if it is not mortgaged, they have a monopoly, and they
   * have just maximized their number of small estates
   */
  public boolean canBuildHotel(){
    checkForMonopoly();
    return !mortgaged && monopoly && getNumSmallEstates()==MAX_HOUSES;
  }

  /**
   * This method returns the current rent value of the property
   * @return integer rent
   */
  public int getRent(){
    return rent;
  }

}