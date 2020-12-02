package ooga.view.gameView;

import java.util.List;
import java.util.Optional;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.view.popup.PopUpInformation;

/**
 * This class is used to prompt user input or alert users of actions that occur when a player
 * lands on a board space
 * Dependencies: This class is implemented by the Players class
 * Assumptions: This class assumes that the player IDs and space IDs in the model match those of
 * the view, which should always be the case so long as they are initialized together in Controller
 * Example: When a player lands on an unowned property, startPurchasePrompt can be used to ask the user
 * if they would like to buy that property or not
 * @author Cameron Jarnot, Lina Leyhausen, Delaney Demark, Anna Diemel
 */
public interface Promptable {

  /**
   * This method prompts the user to give a text input
   * Assumption: The playerID from the backend matches that of the same player
   * in the view
   * @param promptKey is the message to give the user
   * @param playerId is the ID of the player who is receiving the prompt
   * @return the response of the user
   */
  Optional<String> promptUser(String promptKey, Optional<Integer> playerId);

  /**
   * This method is used to alert the user of something happening when no response is expected
   * Assumption: The playerID from the backend matches that of the same player
   * in the view
   * @param promptKey is the message to give the user
   * @param playerId is the ID of the player who is receiving the prompt
   */
  void alertUser(String promptKey, int playerId);

  /**
   * This method begins the purchase process of decisions for the user on the frontend
   * Assumption: The spaceID from the backend matches that of the same space
   * in the view
   * @param location is the ID of thespace which they are being asked if they would like
   *                 to purchase
   */
  void startPurchasePrompt(int location);

  /**
   * This method opens a popup for the user displaying the space they landed on
   * Assumption: The spaceID from the model matches that of the same space
   * in the view
   * @param location is the space ID from the model
   */
  void startUserPromptForSpace(int location);

  /**
   * This method prompts the user to tell them they received money after landing on a
   * space, with an Okay button
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player who received money
   * @param spaceID is the ID of the space the player landed on
   * @param amount is the amount of money the player received
   */
  void promptGotMoney(int playerID, int spaceID, int amount);

  /**
   * This method alerts the users when a computer player executes an action, as they cannot visibly
   * see the computer player's actions otherwise
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the computer player
   * @param spaceID is the ID of the space the computer player landed on
   * @param message is the message to tell the users what the computer player did
   */
  void alertOfComputerPlayerAction(int playerID, int spaceID, String message);

  /**
   * This method alerts the users when a computer player rolls the dice, as they cannot visibly see the
   * computer player's action otherwise
   * Assumption: The playerID from the model matches that of the same player in the view
   * @param diceRoll is the list of dice values the player rolled
   * @param playerId is the player ID of the computer player
   */
  void alertOfComputerPlayerRoll(List<Integer> diceRoll, int playerId);

  /**
   * This method serves to tell a player that they are being moved, with no
   * response expected
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player who is being moved
   * @param spaceID is the ID of the space the player is being moved to
   */
  void promptMoved(int playerID,int spaceID);

  /**
   * This method prompts a pop-up allowing the player to make a trade
   * Called after the current player presses the "Make Trade" button
   * Assumption: The playerID from the model match that of the same player in the view
   * @param promptKey is the list of names of properties owned by the player
   * @param playerID is the ID of the player
   * @return a PopUpInformation object containing the results of the trade
   */
  PopUpInformation promptTrade(List<String> promptKey, int playerID);

  /**
   * This method prompts a pop-up allowing the player to purchase real estate on any properties
   * for which they have a monopoly of the neighborhood
   * Called after the current player presses the "Buy Real Estate" button
   * Assumption: The playerID from the model match that of the same player in the view
   * @param promptKey is the list of spaces the player can build real estate on
   * @param playerID is the ID of the player who is purchasing real estate
   * @param houseNames is the list of small real estate the player already owns
   * @param hotelNames is the list of large real estate the player already owns
   * @return a PopUpInformation object containing the results of the real estate purchase
   */
  PopUpInformation startPurchaseRealEstatePrompt(List<OwnableSpace> promptKey, int playerID,
      List<String> houseNames, List<String> hotelNames);

  /**
   * This method displays the card that was drawn when they land on a NonOwnableCardSpace
   * Assumption: The card ID from the model match that of the same card in the view
   * @param cardID is the ID of the card that was pulled
   */
  void showCard(int cardID);

  /**
   * This method prompts the user to tell them they paid money after landing on a space,
   * with an Okay button
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player who paid money
   * @param spaceID is the ID of the space the player landed on
   * @param amount is the amount of money the player paid
   */
  void promptPaidMoney(int playerID, int spaceID, int amount);

  /**
   * This method prompts to tell the user that they have landed on the NonOwnableJailSpace and are just
   * visiting there
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player that landed on the space
   * @param spaceID is the ID of the space where the player landed
   */
  void promptVisitingJail(int playerID,int spaceID);

  /**
   * This method prompts the user to decide whether or not they would like to purchase the space
   * they have landed on. They respond by clicking on a Yes or No button
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player that landed on the space
   * @param spaceID is the ID of the space the player landed on
   * @param amountToBuy is the cost of purchasing the space
   * @return true if they choose to purchase, false if they choose not to purchase
   */
  boolean promptPurchase(int playerID,int spaceID,int amountToBuy);

  /**
   * This method prompts the players to enter bids if the player who landed on a space decided
   * not to buy it
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player that landed on the space
   * @param spaceID is the ID of the space that is up for bidding
   * @return a List of Integers with the int ID of the player that wins in the 0th element and the
   * amount they paid for the property in the 1st element or Optional.empty() if nobody bids
   */
  Optional<List<Integer>> promptBidding(int playerID, int spaceID);

  /**
   * This method informs the user via a pop-up that they just paid rent on the space they landed on
   * Assumption: The space ID and player IDs from the model match those of the same space and
   * players in the view
   * @param payerId is the ID of the player who paid rent
   * @param ownerID is the ID of the player to whom the rent was paid
   * @param spaceID is the ID of the space on which they landed to pay rent
   * @param amount is the amount the payer paid in rent to the owner
   */
  void promptPaidRent(int payerId,int ownerID,int spaceID,int amount);

  /**
   * This method alerts the interactive players when a computer player puts a mortgage on a property,
   * as they would otherwise be unaware this had happened
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the computer player who mortgaged their property
   * @param amount is the amount the computer player received by mortgaging their property
   */
  void alertOfComputerPlayerMortgage(int playerID,int amount);

  /**
   * This method alerts the interactive players when a computer player pays off a mortgage on a property,
   * as they would otherwise be unaware this had happened
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the computer player who paid off the mortgage
   * @param amount is the amount the computer player paid to lift their mortgage
   */
  void alertOfComputerPlayerUnmortgage(int playerID,int amount);

  /**
   * This method opens a pop-up to tell the user how much money they received for mortgaging a property
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who mortgaged their property
   * @param amount is the amount the player received by mortgaging their property
   */
  void promptMoneyBackForMortgage(int playerID,int amount);

  /**
   * This method opens a pop-up to tell the user how much money they paid to lift a mortgage on a property
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who paid to lift a mortgage on their property
   * @param amount is the amount the player paid to lift the mortgage on their property
   */
  void promptMoneyBackForUnmortgage(int playerID,int amount);

  /**
   * This method prompts the user to make a decision of whether or not to use an OutJail card if they go to
   * jail and have one available
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who may use their OutJail card
   * @return true if they would like to use the card, false if they would not
   */
  boolean promptToUseGetOutOfJailCard(int playerID);

  /**
   * This method is used to tell the player that they will remain in jail for this turn
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who will remain in jail
   */
  void stayInJail(int playerID);

  /**
   * This method prompts an alert to inform the user that they used an OutJail card and will no longer remain
   * in jail
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who got out of jail by using their OutJail card
   */
  void promptUsedJailCard(int playerID);

}
