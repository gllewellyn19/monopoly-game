package ooga.model.gamePlay;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ooga.model.Bank;
import ooga.model.Board;
import ooga.model.Cards;
import ooga.model.Dice;
import ooga.model.GamePlay;
import ooga.model.Players;
import ooga.model.PropertyUpgrade;
import ooga.model.Ruleset;
import ooga.model.banks.BankFactory;
import ooga.model.boardSpaces.BoardSpace;
import ooga.model.boardSpaces.NonOwnableFreeParkingSpace;
import ooga.model.boardSpaces.OwnablePropertySpace;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.model.realEstates.RealEstate;
import ooga.model.boards.BoardFactory;
import ooga.model.cards.CardsFactory;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.DiceFactory;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.exceptions.BoardSpaceException;
import ooga.model.exceptions.ModelException;
import ooga.model.players.Player;
import ooga.model.players.PlayersFactory;
import ooga.model.propertyUpgrades.ClassicPropertyUpgrade;
import ooga.model.rules.RulesetFactory;
import ooga.model.turns.TurnModel;
import ooga.view.gameView.DisplayInitializer;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.Promptable;
import ooga.view.gameView.TurnableView;

/***
 * This class serves to initialize the model at the beginning of the game and control the model
 * throughout the game's turns
 * Dependencies: Implements all of the other model subclasses in the banks, boards, boardSpaces,
 * cards, dataReaders, dice, exceptions, gamePlay, and players packages
 * Example: The Controller class can implement this class to control the backend of the game
 * @author Cameron Jarnot, Grace Llewellyn, Anna Diemel, Lina Leyhausen, Delaney Demark
 */
public class ClassicGamePlay extends GamePlay implements TurnableModel {

  public static final int DEFAULT_AMOUNT_PASSING_GO = 200;
  public static final int MILLION = 1_000_000;
  public static final int TURNS_IN_JAIL = 3;

  private Board board;
  private Players players;
  private Ruleset rules;
  private Dice dice;
  private Cards cardDecks;
  private Bank bank;
  private int amountForPassingGo;
  private String endType;
  private TurnableView turnableView;
  private Promptable promptable;
  private PropertyUpgrade propertyUpgrade;
  private ErrorPrintable errorPrintable;

  /**
   * Used to initialize the Model
   * @param playerIdsAndTypes is a map of the player IDs to their type
   * @param infoForGamePlay contains the overall information about the game
   * @param turnableView is the interface to reflect turns in the view
   * @param promptableIn is the interface to reflect user decisions in the view
   * @param errorPrintable is the interface to reflect errors in the view
   */
  public void initializeGame(Map<Integer, String> playerIdsAndTypes, InfoForGamePlay infoForGamePlay,
                             TurnableView turnableView, Promptable promptableIn, ErrorPrintable errorPrintable) {
    this.promptable = promptableIn;
    this.turnableView = turnableView;
    this.errorPrintable = errorPrintable;
    if (infoForGamePlay!=null) {
      createBankCardsBoardDice(infoForGamePlay, promptableIn, playerIdsAndTypes);
      determineRuleset();
      if (infoForGamePlay.userGavePassingGoAmount()) {
        amountForPassingGo = infoForGamePlay.getAmountPassingGo();
      } else {
        amountForPassingGo = DEFAULT_AMOUNT_PASSING_GO;
      }
      propertyUpgrade = new ClassicPropertyUpgrade(board, players, bank);
    }
  }

  /*
   * Creates the bank, cards, board and dice for the game via referencing so that the user
   * can choose which type to do
   */
  private void createBankCardsBoardDice(InfoForGamePlay infoForGamePlay, Promptable promptableIn,
      Map<Integer, String> playerIdsAndTypes) {
    dice = new DiceFactory()
        .createDice(infoForGamePlay.getDiceType(), infoForGamePlay, errorPrintable);
    this.players = new PlayersFactory().createPlayersClass(infoForGamePlay.getPlayersType(),
        playerIdsAndTypes, infoForGamePlay.getGameRotationType(), dice, promptableIn,
        errorPrintable);
    setStartingFunds(infoForGamePlay.getStartingFunds());
    cardDecks = new CardsFactory()
        .createCards(infoForGamePlay.getCardsType(), infoForGamePlay,
            errorPrintable);
    endType = infoForGamePlay.getEndType();
    bank = new BankFactory().createBank(infoForGamePlay.getBankType(), players.getPlayers(),
        errorPrintable);
    try {
      board = new BoardFactory().createBoard(infoForGamePlay.getBoardType(), infoForGamePlay, bank,
          cardDecks, errorPrintable, dice, amountForPassingGo);
    } catch (ModelException | FileNotFoundException | BoardSpaceException e) {
      errorPrintable.printErrorMessageAlert(e.getMessage());
      turnableView.stopGame();
    }
  }

  /**
   * Returns the PropertyUpgradeable interface instantiated in this class
   * @return the PropertyUpgradeable interface instantiated in this class
   */
  @Override
  public PropertyUpgradeable getPropertyUpgradeable() {
    return propertyUpgrade;
  }

  //makes the Ruleset class based on the type in properties
  private void determineRuleset() {
    RulesetFactory makeRuleset = new RulesetFactory();
    rules = makeRuleset.determineRuleset(endType);
  }

  /**
   * Returns the current player
   * @return the Player taking their turn currently
   */
  public Player getCurrentPlayer() {
    return players.getCurrentPlayer();
  }

  /**
   * Moves to the next player and it doesn't matter what they rolled or how many turns left (used for
   * starting of the game for example)
   */
  @Override
  public void moveToNextPlayer() {
    players.moveToNextPlayer(dice.getLastRoll(), turnableView, this);
  }

  /**
   * Returns the ID of the player whose turn it currently is
   * @return the int ID of the current player
   */
  @Override
  public int getCurrentPlayerId() {
    return getCurrentPlayer().getId();
  }

  /**
   * Purchases real estate for the current player
   * @param displayInitializer is the interface that initializes the view
   * @param infoForGamePlay contains the information about the game format
   */
  @Override
  public void buyRealEstate(DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay) {
    ClassicPropertyUpgrade propertyUpgrade = new ClassicPropertyUpgrade(board, players, bank);
    List<Integer> houseProperties = propertyUpgrade.findHouseEligibleProperties(getCurrentPlayerId());
    List<String> housePropertyNames = getEstatePropertyNames(displayInitializer, houseProperties);
    List<Integer> hotelProperties = propertyUpgrade.findHotelEligibleProperties(getCurrentPlayerId());
    List<String> hotelPropertyNames = getEstatePropertyNames(displayInitializer, hotelProperties);
    Map<Integer, RealEstate> estatesToPlace = getCurrentPlayer().buyRealEstate(housePropertyNames, hotelPropertyNames, infoForGamePlay);
    if (estatesToPlace == null || estatesToPlace.isEmpty()) return;
    placeRealEstate(estatesToPlace);
  }

  //returns a list of properties with real estate on them
  private List<String> getEstatePropertyNames(DisplayInitializer displayInitializer,
      List<Integer> hotelProperties) {
    List<String> hotelPropertyNames = new ArrayList<>();
    for (int propertyId : hotelProperties) {
      hotelPropertyNames.add(propertyId,
          displayInitializer.getDisplayBoard().getSpaceAtLocation(propertyId).getName());
    }
    return hotelPropertyNames;
  }

  //adds real estate to properties
  private void placeRealEstate(Map<Integer, RealEstate> estatesToPlace) {
    for (int key : estatesToPlace.keySet()) {
      RealEstate estate = estatesToPlace.get(key);
      BoardSpace space = board.getBoardSpaceAtLocation(key);
      if (space.getClass() == OwnablePropertySpace.class) {
        ((OwnablePropertySpace) space).addEstateToProperty(estate);
      }
    }
  }

  /**
   * Makes a trade of properties between two players
   * @param displayInitializer is the interface that initializes the view
   * @param infoForGamePlay contains the information about the game format
   */
  @Override
  public void makeTrade(DisplayInitializer displayInitializer, InfoForGamePlay infoForGamePlay) {
    List<OwnableSpace> spaces = getCurrentPlayer().getPlayerProperties();
    List<String> properties = getAllProperties(displayInitializer, spaces, board.boardSize());
    Hashtable<String, String> tradeDescription = getCurrentPlayer().makeTrade(properties);
    if (tradeDescription == null || tradeDescription.isEmpty()) return;
    int propertyID = Integer.parseInt(tradeDescription.get("propertyID"));
    if (board.getBoardSpaceAtLocation(propertyID).getClass() != OwnableSpace.class) {
      return;
    }
    OwnableSpace property = (OwnableSpace) board.getBoardSpaceAtLocation(propertyID);
    if (tradeDescription.get("tradeType").equals("Mortgage")) {
      property.mortgage();
    } else if (tradeDescription.get("tradeType").equals("Trade")) {
      tradeProperty(tradeDescription, propertyID, property);
    }
  }

  //trades properties
  private void tradeProperty(Hashtable<String, String> tradeDescription, int propertyID,
      OwnableSpace property) {
    int tradeMoney = Integer.parseInt(tradeDescription.get("moneyEarned"));
    int traderPartnerID = Integer.parseInt(tradeDescription.get("tradePartner"));
    getCurrentPlayer().collectedMoney(traderPartnerID, propertyID, tradeMoney);
    getCurrentPlayer().getPlayerProperties().remove(property);
    players.getPlayers().get(traderPartnerID).getPlayerProperties().add(property);
  }

  //returns all of a player's properties
  private List<String> getAllProperties(DisplayInitializer displayInitializer,
      List<OwnableSpace> spaces, int numSpaces) {
    List<String> properties = new ArrayList<>(Collections.nCopies(numSpaces, ""));
    for (OwnableSpace space : spaces) {
      int spaceID = space.getLocation();
      properties.add(spaceID, displayInitializer.getDisplayBoard().getSpaceAtLocation(spaceID).getName());
    }
    return properties;
  }

  //sets each player's starting money
  private void setStartingFunds(int funds) {
    for (int i = 0; i < players.getNumberOfPlayers(); i++) {
      players.getPlayers().get(i).increasePlayerBalance(funds);
    }
  }

  /**
   * Determines if the game is over by seeing if all but one player are out of money and have
   * mortgaged all properties.
   * @return true if the game is over, false if not
   */
  public boolean determineGameOver() {
    return rules.isGameOver(players.getPlayers());
  }

  /**
   * Determines the winning player
   * @return the winning players ID or -1 if no player won
   */
  public int getWinningPlayerID() {
    return rules.determineWinner(players.getPlayers());
  }

  /**
   * Rolls the dice and also moves the player's location. Gives the user 200 dollars if they pass go
   * @param diceRoll is the list of the values of the current dice roll
   */
  @Override
  public void movePlayerUsingDiceRoll(List<Integer> diceRoll) {
    Player currentPlayer = getCurrentPlayer();
    if (!currentPlayer.isInJail() || currentPlayer.getTurnsInJail() == TURNS_IN_JAIL || dice.isDoubles(diceRoll)) {
      if (currentPlayer.isInJail()) {
        currentPlayer.changeInJailState();
      }
      int newLocation = currentPlayer.updateLocation(board.boardSize(), dice.sumDiceRoll(diceRoll), amountForPassingGo);
      turnableView.movePlayer(newLocation, currentPlayer.getId());
      Optional<TurnModel> turnData = doActionOnCurrentLocation(currentPlayer.getId(), newLocation);
      if (turnData.isPresent()) {
        updateMoneyToFrontend();
        checkTurnModelSpecialCases(turnData.get(),newLocation);
      }
    } else {
      currentPlayer.takeTurnInJail();
    }
  }

  //checks special cases of the turn
  private void checkTurnModelSpecialCases(TurnModel turnData,int newLocation) {
    if (turnData.getNewLocation() != turnData.getCurrentLocation()) {
      movePlayerToSpace(turnData.getNewLocation() - turnData.getCurrentLocation());
    }
    if (turnData.isGettingProperty()) {
      getAProperty(turnData.getPossibleProperties());
    }
    if (turnData.checkIfBidPurchased()) {
      if (board.getBoardSpaceAtLocation(turnData.getCurrentLocation()) instanceof OwnableSpace space) {
        int bidPurchaser = turnData.getBidPurchaser();
        Player bidder = players.getPlayers().get(bidPurchaser);
        bidder.addToPlayerProperties((OwnableSpace)board.getBoardSpaceAtLocation(newLocation));
        bank.decreaseBalance(bidPurchaser,turnData.getBidAmount());
        space.setOwner(bidder);
      }
    }
    if(turnData.getFreeMoneyAdded() != 0){
      ((NonOwnableFreeParkingSpace)board.getBoardSpaceAtLocation(board.getFreeParkingPosition())).
              addMoneyToFreeParking(turnData.getFreeMoneyAdded());
    }
    updateMoneyToFrontend();
  }

  //calls frontend to update view of money
  private void updateMoneyToFrontend() {
    List<Integer> newBalances = new ArrayList<>();
    Map<Integer, Player> playerMap = players.getPlayers();
    List<Integer> playersList = new ArrayList<>(playerMap.keySet());
    Collections.sort(playersList);
    for (Integer playerId : playersList) {
      newBalances.add(players.getPlayers().get(playerId).getPlayerBalance());
    }
    turnableView.updateMoney(newBalances);
  }

  /**
   * Moves the current player to a specific space
   * @param locationUpdate is the ID of the location to move the player to
   */
  public void movePlayerToSpace(int locationUpdate) {
    int newLocation = players.getCurrentPlayer().updateLocation(board.boardSize(),
        locationUpdate, amountForPassingGo);
    turnableView.movePlayer(newLocation, players.getCurrentPlayer().getId());
    doActionOnCurrentLocation(players.getCurrentPlayer().getId(), newLocation);
  }

  private void getAProperty(List<Integer> possibleProperties) {
    Player currentPlayer = players.getCurrentPlayer();
    for (Integer boardIndex : possibleProperties) {
      boolean propertyNotOwned = currentPlayer.getPlayerProperties()
          .contains(board.getBoardSpaceAtLocation(boardIndex));
      if (propertyNotOwned) {
        OwnableSpace boughtSpace = (OwnableSpace) board.getBoardSpaceAtLocation(boardIndex);
        currentPlayer.addToPlayerProperties(boughtSpace);
        boughtSpace.setOwner(currentPlayer);
        break;
      }
    }
  }

  /**
   * Returns the number of players
   * @return the number of players
   */
  @Override
  public int getNumPlayers() {
    return (players == null) ? 0 : players.getNumberOfPlayers(); }

  /**
   * Executes the action based on the current space by calling its landOnSpace method,
   * then checking the returned TurnModel
   * @param playerID is the ID of the player who landed on a space
   * @param spaceID is the ID of the space the player landed on
   * @return TurnModel object if turn needs to be reflected on the display board
   */
  public Optional<TurnModel> doActionOnCurrentLocation(int playerID, int spaceID) {
    try {
      BoardSpace currentSpace = board.getBoardSpaceAtLocation(spaceID);
      Player currentPlayer = players.getPlayers().get(playerID);
      return Optional.of(currentSpace.landOnSpace(currentPlayer, spaceID));
    } catch (ModelException e) {
      errorPrintable.printErrorMessageAlert(e.getMessage());
      return Optional.empty();
    }
  }

  /**
   * Initializes the game with the starting player in the frontend
   * @param playerId is the ID of the starting player
   */
  @Override
  public void startPlayingGame(int playerId) {
    if (turnableView.getSetUpSuccessful()) {
      turnableView.showUserTopTextMessage("startingPlayer", playerId);
      players.setCurrentPlayerId(playerId);
      turnableView.playingRollsMode();
    }
  }

  /**
   * Determines which player will start the game
   * @param turnableView is the interface to tell the frontend whose turn to display
   * @param startPlayerType is the class name of the type of starting player
   * @param promptable is the interface to tell the front end to give information about the turn
   */
  public void startGame(TurnableView turnableView, Optional<String> startPlayerType,
        Promptable promptable) {
    players.determineStartingPlayer(this, turnableView, startPlayerType, promptable);
  }

  /**
   * Determines which player will start the game based on a dice roll
   * @param diceRoll is the highest dice roll
   * @return true if the current player is the player starting
   */
  @Override
  public boolean determiningStartingPlayerId(List<Integer> diceRoll) {
    return players.determiningStartingPlayerId(this, dice.sumDiceRoll(diceRoll));
  }

  /**
   * @return the Board instantiated in this class
   */
  public Board getBoard() {
    return board;
  }

  /**
   * @return the ModelDiceRollable instantiated in this class
   */
  public ModelDiceRollable getModelDiceRollable() {
    return dice;
  }

  /**
   * @return the Promptable interface instantiated in this class
   */
  public Promptable getPromptable() {
    return promptable;
  }

  /**
   * @return the Map of player IDs to Players
   */
  public Map<Integer, Player> getPlayers(){
    return players.getPlayers();
  }

  /**
   * @return the card decks for this game
   */
  public Cards getCards(){
    return cardDecks;
  }
}