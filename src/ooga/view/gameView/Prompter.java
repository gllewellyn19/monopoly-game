package ooga.view.gameView;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.view.popup.BiddingPopUp;
import ooga.view.popup.CardPopUp;
import ooga.view.popup.ComputerPlayerPopUp;
import ooga.view.popup.MakeTradePopUp;
import ooga.view.popup.NonOwnableSpacePopUp;
import ooga.view.popup.PopUp;
import ooga.view.popup.PopUpInformation;
import ooga.view.popup.PropertyPopUp;
import ooga.view.popup.RealEstatePopUp;
import ooga.view.popup.SimpleMessagePopUp;
import ooga.view.popup.UseCardPopUp;

/**
 * This class handles prompts for the users when a player lands on a space, regardless
 * of whether they are a computer, interactive, or other player
 * Implements the Promptable interface to allow information in from the model
 * Assumption: The IDs passed from the model should match those of the view
 * Example: When an InteractivePlayer lands on a NonOwnableJailSpace, the method promptJustVisitingJail
 * would be called in the InteractivePlayer class to inform the user that this has happened
 * Comment: This class is primarily referenced from the Player classes in model via the Promptable interface
 * @author Lina Leyhausen, Anna Diemel, Cameron Jarnot, Delaney Demark
 */
public class Prompter implements Promptable {

  private final GetInformationForPrompter getInformationForPrompter;

  public Prompter(GetInformationForPrompter getInformationForPrompter) {
    this.getInformationForPrompter = getInformationForPrompter;
  }

  /**
   * This method prompts the user to give a text input
   * Assumption: The playerID from the backend matches that of the same player
   * in the view
   * @param promptKey is the message to give the user
   * @param playerId is the ID of the player who is receiving the prompt
   * @return the response of the user
   */
  @Override
  public Optional<String> promptUser(String promptKey, Optional<Integer> playerId) {
    ResourceBundle languageResources = getInformationForPrompter.getLanguageResources();
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(languageResources.getString("headerForPrompts"));
    dialog.setHeaderText(languageResources.getString("headerForPrompts"));
    String contentText = languageResources.getString(promptKey);
    contentText+= playerId.isEmpty() ? "": getInformationForPrompter.getPlayers().
        get(playerId.get()).getPlayerName();
    dialog.setContentText(contentText);
    return dialog.showAndWait();
  }

  /**
   * This method is used to alert the user of something happening when no response is expected
   * Assumption: The playerID from the backend matches that of the same player
   * in the view
   * @param promptKey is the message to give the user
   * @param playerId is the ID of the player who is receiving the prompt
   */
  @Override
  public void alertUser(String promptKey, int playerId) {
    ResourceBundle languageResources = getInformationForPrompter.getLanguageResources();
    Alert errorAlert = new Alert(AlertType.INFORMATION);
    errorAlert.setHeaderText(languageResources.getString("headerForAlerts"));
    errorAlert.setContentText(languageResources.getString(promptKey) + ": " +
        getInformationForPrompter.getPlayers().get(playerId).getPlayerName());
    errorAlert.showAndWait();
  }

  /**
   * This method begins the purchase process of decisions for the user on the frontend
   * Assumption: The spaceID from the backend matches that of the same space
   * in the view
   * @param location is the ID of thespace which they are being asked if they would like
   *                 to purchase
   */
  @Override
  public void startPurchasePrompt(int location){
    PopUp purchaseSpace = new PropertyPopUp();
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(location).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(location).getName();
    purchaseSpace.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method opens a popup for the user displaying the space they landed on
   * Assumption: The spaceID from the model matches that of the same space
   * in the view
   * @param location is the space ID from the model
   */
  @Override
  public void startUserPromptForSpace(int location){
    PopUp spacePopUp = new NonOwnableSpacePopUp();
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(location).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(location).getName();
    spacePopUp.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method prompts the user to tell them they received money after landing on a
   * space, with an Okay button
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player who received money
   * @param spaceID is the ID of the space the player landed on
   * @param amount is the amount of money the player received
   */
  @Override
  public void promptGotMoney(int playerID, int spaceID, int amount){
    PopUp spacePopUp = new NonOwnableSpacePopUp();
    //spacePopUp.addPlayerName(player)
    spacePopUp.addMessage("Collect $" + amount + ".");
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getName();
    spacePopUp.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method alerts the users when a computer player executes an action, as they cannot visibly
   * see the computer player's actions otherwise
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the computer player
   * @param spaceID is the ID of the space the computer player landed on
   * @param message is the message to tell the users what the computer player did
   */
  @Override
  public void alertOfComputerPlayerAction(int playerID, int spaceID, String message){
    PopUp spacePopUp = new ComputerPlayerPopUp();
    String playerName = getInformationForPrompter.getPlayers().get(playerID).getPlayerName();
    spacePopUp.addPlayerName(playerName);
    spacePopUp.addMessage(message);
  }

  /**
   * This method alerts the users when a computer player rolls the dice, as they cannot visibly see the
   * computer player's action otherwise
   * Assumption: The playerID from the model matches that of the same player in the view
   * @param diceRoll is the list of dice values the player rolled
   * @param playerId is the player ID of the computer player
   */
  @Override
  public void alertOfComputerPlayerRoll(List<Integer> diceRoll, int playerId) {
    PopUp spacePopUp = new ComputerPlayerPopUp();
    spacePopUp.addPlayerName(Integer.toString(playerId));
    spacePopUp.addMessage("Computer player rolled a "+diceRoll);
    //spacePopUp.openPopUp(getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method serves to tell a player that they are being moved, with no
   * response expected
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player who is being moved
   * @param spaceID is the ID of the space the player is being moved to
   */
  @Override
  public void promptMoved(int playerID, int spaceID) {
    PopUp spacePopUp = new NonOwnableSpacePopUp();
    spacePopUp.addMessage("You are being moved.");
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getName();
    spacePopUp.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method displays the card that was drawn when they land on a NonOwnableCardSpace
   * Assumption: The card ID from the model match that of the same card in the view
   * @param cardID is the ID of the card that was pulled
   */
  public void showCard(int cardID) {
    PopUp spacePopUp = new CardPopUp();
    Rectangle drawnCard = getInformationForPrompter.getDisplayCards().showDrawnCard(cardID);
    spacePopUp.openPopUp("", drawnCard, getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method prompts the user to tell them they paid money after landing on a space,
   * with an Okay button
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player who paid money
   * @param spaceID is the ID of the space the player landed on
   * @param amount is the amount of money the player paid
   */
  public void promptPaidMoney(int playerID, int spaceID, int amount) {
    PopUp spacePopUp = new NonOwnableSpacePopUp();
    //spacePopUp.addPlayerName(player)
    spacePopUp.addMessage("Pay $" + amount + ".");
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getName();
    spacePopUp.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources());

  }

  /**
   * This method prompts to tell the user that they have landed on the NonOwnableJailSpace and are just
   * visiting there
   * Assumption: The spaceID and playerID from the model match those of the same space and
   * player in the view
   * @param playerID is the ID of the player that landed on the space
   * @param spaceID is the ID of the space where the player landed
   */
  @Override
  public void promptVisitingJail(int playerID,int spaceID){
    PopUp spacePopUp = new NonOwnableSpacePopUp();
    spacePopUp.addMessage("You are just visiting.");
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getName();
    spacePopUp.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources());

  }

  /**
   * This method prompts a pop-up allowing the player to make a trade
   * Called after the current player presses the "Make Trade" button
   * Assumption: The playerID from the model match that of the same player in the view
   * @param ownedProperties is the list of names of properties owned by the player
   * @param playerID is the ID of the player
   * @return a PopUpInformation object containing the results of the trade
   */
  @Override
  public PopUpInformation promptTrade(List<String> ownedProperties, int playerID) {
    MakeTradePopUp tradePropUp = new MakeTradePopUp();
    tradePropUp.addMessage("Trade something");
    tradePropUp.openPopUp(ownedProperties, playerID);
    return tradePropUp.getPopUpInformation();
  }

  /**
   * This method prompts a pop-up allowing the player to purchase real estate on any properties
   * for which they have a monopoly of the neighborhood
   * Called after the current player presses the "Buy Real Estate" button
   * Assumption: The playerID from the model match that of the same player in the view
   * @param ownedProperties is the list of spaces the player can build real estate on
   * @param playerID is the ID of the player who is purchasing real estate
   * @param houseNames is the list of small real estate the player already owns
   * @param hotelNames is the list of large real estate the player already owns
   * @return a PopUpInformation object containing the results of the real estate purchase
   */
  @Override
  public PopUpInformation startPurchaseRealEstatePrompt(List<OwnableSpace> ownedProperties, int playerID,
      List<String> houseNames, List<String> hotelNames) {
    RealEstatePopUp purchaseRealEstatePopUp = new RealEstatePopUp();
    purchaseRealEstatePopUp.addMessage("Buy real estate");
    purchaseRealEstatePopUp.openPopUp(playerID, ownedProperties, getInformationForPrompter, houseNames, hotelNames);
    return purchaseRealEstatePopUp.getInformationFromPopUp();
  }

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
  @Override
  public boolean promptPurchase(int playerID, int spaceID, int amountToBuy) {
    //give yes or no option, return true if yes, false if no
    PopUp purchaseSpace = new PropertyPopUp();
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getName();
    purchaseSpace.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources());
    return purchaseSpace.isBuying();
  }

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
  @Override
  public Optional<List<Integer>> promptBidding(int playerID, int spaceID) {
    //When they put in bids, if they bid less than $10 print error that they can't bid less than $10
    //Return the int ID of the player that wins or Optional.empty() if nobody bids
    int numPlayers = getInformationForPrompter.getPlayers().size();
    BiddingPopUp spacePopUp = new BiddingPopUp();
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getName();
    spacePopUp.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources(), numPlayers);
    return spacePopUp.getWinningPlayerAndBid();
  }

  /**
   * This method informs the user via a pop-up that they just paid rent on the space they landed on
   * Assumption: The space ID and player IDs from the model match those of the same space and
   * players in the view
   * @param payerId is the ID of the player who paid rent
   * @param ownerID is the ID of the player to whom the rent was paid
   * @param spaceID is the ID of the space on which they landed to pay rent
   * @param amount is the amount the payer paid in rent to the owner
   */
  @Override
  public void promptPaidRent(int payerId, int ownerID, int spaceID, int amount) {
    PopUp spacePopUp = new NonOwnableSpacePopUp();
    spacePopUp.addMessage("Pay $" + amount + " in rent.");
    Image spaceImage = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getImage();
    String spaceName = getInformationForPrompter.getDisplayBoard().getSpaceAtLocation(spaceID).getName();
    spacePopUp.openPopUp(spaceName, spaceImage, getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method alerts the interactive players when a computer player puts a mortgage on a property,
   * as they would otherwise be unaware this had happened
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the computer player who mortgaged their property
   * @param amount is the amount the computer player received by mortgaging their property
   */
  @Override
  public void alertOfComputerPlayerMortgage(int playerID, int amount) {
    PopUp spacePopUp = new SimpleMessagePopUp();
    String playerName = getInformationForPrompter.getPlayers().get(playerID).getPlayerName();
    spacePopUp.addMessage(playerName + " received $" + amount + " for this mortgage.");
    spacePopUp.openPopUp(getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method alerts the interactive players when a computer player pays off a mortgage on a property,
   * as they would otherwise be unaware this had happened
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the computer player who paid off the mortgage
   * @param amount is the amount the computer player paid to lift their mortgage
   */
  @Override
  public void alertOfComputerPlayerUnmortgage(int playerID, int amount) {
    PopUp spacePopUp = new SimpleMessagePopUp();
    String playerName = getInformationForPrompter.getPlayers().get(playerID).getPlayerName();
    spacePopUp.addMessage(playerName + " paid $" + amount + " for this mortgage.");
    spacePopUp.openPopUp(getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method opens a pop-up to tell the user how much money they received for mortgaging a property
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who mortgaged their property
   * @param amount is the amount the player received by mortgaging their property
   */
  @Override
  public void promptMoneyBackForMortgage(int playerID, int amount) {
    PopUp spacePopUp = new SimpleMessagePopUp();
    String playerName = getInformationForPrompter.getPlayers().get(playerID).getPlayerName();
    spacePopUp.addMessage(playerName + " received $" + amount + " for this mortgage.");
    spacePopUp.openPopUp(getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method opens a pop-up to tell the user how much money they paid to lift a mortgage on a property
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who paid to lift a mortgage on their property
   * @param amount is the amount the player paid to lift the mortgage on their property
   */
  @Override
  public void promptMoneyBackForUnmortgage(int playerID, int amount) {
    PopUp spacePopUp = new SimpleMessagePopUp();
    String playerName = getInformationForPrompter.getPlayers().get(playerID).getPlayerName();
    spacePopUp.addMessage(playerName + " paid $" + amount + " for this mortgage.");
    spacePopUp.openPopUp(getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method prompts the user to make a decision of whether or not to use an OutJail card if they go to
   * jail and have one available
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who may use their OutJail card
   * @return true if they would like to use the card, false if they would not
   */
  @Override
  public boolean promptToUseGetOutOfJailCard(int playerID) {
    PopUp spacePopUp = new UseCardPopUp();
    String playerName = getInformationForPrompter.getPlayers().get(playerID).getPlayerName();
    spacePopUp.addMessage(playerName + ", would you like to use your get out of jail free card?");
    spacePopUp.openPopUp(getInformationForPrompter.getLanguageResources());
    return spacePopUp.isBuying();
  }

  /**
   * This method is used to tell the player that they will remain in jail for this turn
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who will remain in jail
   */
  @Override
  public void stayInJail(int playerID) {
    PopUp spacePopUp = new SimpleMessagePopUp();
    String playerName = getInformationForPrompter.getPlayers().get(playerID).getPlayerName();
    spacePopUp.addMessage(playerName + ", you must stay in jail for another round.");
    spacePopUp.openPopUp(getInformationForPrompter.getLanguageResources());
  }

  /**
   * This method prompts an alert to inform the user that they used an OutJail card and will no longer remain
   * in jail
   * Assumption: The playerID from the model match that of the same player in the view
   * @param playerID is the ID of the player who got out of jail by using their OutJail card
   */
  @Override
  public void promptUsedJailCard(int playerID) {
    PopUp spacePopUp = new SimpleMessagePopUp();
    String playerName = getInformationForPrompter.getPlayers().get(playerID).getPlayerName();
    spacePopUp.addMessage(playerName + ", you have used your Get Out Of Jail Free card.");
    spacePopUp.openPopUp(getInformationForPrompter.getLanguageResources());
  }

}
