package ooga.model.dataReaders;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ooga.model.Bank;
import ooga.model.Cards;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.boardSpaces.BoardSpace;
import ooga.model.boardSpaces.*;
import ooga.model.exceptions.BoardSpaceException;

/**
 * This class is used to instantiate all of the board spaces from a CSV with each row
 * formatted as: Name,Type,Value,Neighborhood,House cost,Hotel cost
 */
public class BoardSpaceFactory {

  private static final String[] OWNABLE_BOARD_TYPES = {"Property", "Railroad", "Utilities"};
  private static final String SPACE_CLASS_PATH = "ooga.model.boardSpaces.";
  private static final int BOARD_SPACE_NAME = 0;
  private static final int BOARD_SPACE_TYPE = 1;
  private static final int BOARD_SPACE_VALUE = 2;
  private static final int BOARD_SPACE_NEIGHBORHOOD = 3;
  private static final int HOUSE_VALUE = 4;
  private static final int HOTEL_VALUE = 5;
  private static final String SPACE_CHARACTER = " ";
  private static final String EMPTY = "";
  private static final String NON_OWNABLE = "NonOwnable";
  private static final String CARD_SPACE = "CardSpace";
  private static final String SPACE = "Space";
  private static final String COMMUNITY_CHEST = "CommunityChest";
  private static final String CHANCE = "Chance";
  private static final String OWNABLE_PROPERTY_SPACE = "OwnablePropertySpace";
  private static final String OWNABLE_RAILROAD_SPACE = "OwnableRailroadSpace";
  private static final String OWNABLE_UTILITIES_SPACE = "OwnableUtilitiesSpace";

  private ModelDiceRollable modelDiceRollable;
  private Bank bank;
  private Map<String,Integer> neighborhoods;
  private Cards cardDecks;

  /**
   * Initializes the bank, card decks, and neighborhoods to be used in spaces
   * @param newBank is the Bank passed into every space
   * @param decks are the decks to be passed into the card spaces
   * @param neighborhoodsIn is a map of neighborhoods to number of properties in
   *                        the neighborhood
   */
  public BoardSpaceFactory(Bank newBank, Cards decks, Optional<Map<String,Integer>> neighborhoodsIn){
    bank = newBank;
    cardDecks = decks;
    neighborhoods = neighborhoodsIn.orElseGet(HashMap::new);
  }

  /**
   * Initializes the factory
   */
  public BoardSpaceFactory(){}

  /**
   * @param boardData follows the structure given in CSV row 1, which is
   *                  [text,type,value,neighborhood,1 house rent,2 house rent,
   *                  3 house rent,4 house rent,hotel rent,house,hotel]
   * @return new BoardSpace from data
   */
  public BoardSpace makeBoardSpaceFromBoardData(String[] boardData, ModelDiceRollable modelDiceRollable,
                                                int amountPassingGo) {
    String boardSpaceType = getTypeFromBoardData(boardData);
    this.modelDiceRollable = modelDiceRollable;
    return makeBoardSpaceWithType(boardSpaceType, boardData, amountPassingGo);
  }

  //makes a space based on boardType
  private BoardSpace makeBoardSpaceWithType(String boardType, String[] boardData, int amountPassingGo) {
    try {
      String spaceClass;
      BoardSpace boardSpace;
      boolean boardSpaceIsOwnable = isOwnableType(boardType);
      boardType = boardType.replaceAll(" ", "");
      if (boardSpaceIsOwnable) {
        spaceClass = "Ownable" + boardType + "Space";
        boardSpace = makeOwnableSpace(spaceClass, boardData);
      } else {
        spaceClass = getClassNameForNonOwnable(boardData);
        boardSpace = makeNonOwnableSpace(spaceClass, getTypeFromBoardData(boardData),amountPassingGo);
      }
      return boardSpace;

    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
        InstantiationException | InvocationTargetException | IndexOutOfBoundsException |
        NoClassDefFoundError e) {
      throw new BoardSpaceException(boardType);
    }
  }

  //creates class name for non-ownable space
  private String getClassNameForNonOwnable(String[] boardData) {
    String boardType = getTypeFromBoardData(boardData);
    boardType = boardType.replaceAll(SPACE_CHARACTER, EMPTY);
    //String boardText = getTextFromBoardData(boardData);
    if (isCardSpace(boardType)) {
      return NON_OWNABLE + boardType + CARD_SPACE;
    }
    return NON_OWNABLE + boardType + SPACE;
  }

  //returns true if it is of type Chance or Community Chest
  private boolean isCardSpace(String boardType) {
    return (boardType.equalsIgnoreCase(COMMUNITY_CHEST) ||
            boardType.equalsIgnoreCase(CHANCE));
  }

  //makes a non-onwable space based on spaceClass
  private NonOwnableSpace makeNonOwnableSpace(String spaceClass, String boardType, int amountPassingGo)
          throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Class boardSpaceClass = Class.forName(SPACE_CLASS_PATH + spaceClass);
      boardType = boardType.replaceAll(SPACE_CHARACTER, EMPTY);
    if(isCardSpace(boardType)) {
      return (NonOwnableSpace) boardSpaceClass.getDeclaredConstructor(String.class, Bank.class, Cards.class).newInstance(boardType, bank, cardDecks);
    }
    return (NonOwnableSpace) boardSpaceClass.getDeclaredConstructor(String.class, Bank.class).newInstance(boardType, bank);
  }

  //makes a new ownable space based on spaceClass
  private OwnableSpace makeOwnableSpace(String spaceClass, String[] boardData) {
    if (spaceClass.equalsIgnoreCase(OWNABLE_PROPERTY_SPACE)) {
      return makeOwnablePropertySpace(boardData);
    } else if (spaceClass.equalsIgnoreCase(OWNABLE_RAILROAD_SPACE)) {
      return makeOwnableRailroadSpace(boardData);
    } else if (spaceClass.equalsIgnoreCase(OWNABLE_UTILITIES_SPACE)) {
      return makeOwnableUtilitiesSpace(boardData);
    }
    return null;
  }

  //creates new ownable utility space
  private OwnableUtilitiesSpace makeOwnableUtilitiesSpace(String[] boardData) {
    return new OwnableUtilitiesSpace(getTextFromBoardData(boardData),
            getValueFromBoardData(boardData), modelDiceRollable, bank);
  }

  //creates new ownable railroad space
  private OwnableRailroadSpace makeOwnableRailroadSpace(String[] boardData) {
    return new OwnableRailroadSpace(getTextFromBoardData(boardData),
            getValueFromBoardData(boardData), bank);
  }

  //creates new ownable property space
  private OwnablePropertySpace makeOwnablePropertySpace(String[] boardData) {
    return new OwnablePropertySpace(getTextFromBoardData(boardData),
            getValueFromBoardData(boardData),
            getNeighborhoodFromBoardData(boardData),
            getHouseCostFromBoardData(boardData),
            getHotelCostFromBoardData(boardData), bank, neighborhoods);
  }

  //returns true if the space is ownable, false if not
  private boolean isOwnableType(String boardType) {
    return Arrays.asList(OWNABLE_BOARD_TYPES).contains(boardType);
  }

  /**
   * Helper functions that parse constructor input parameters from row taken from CSV
   */
  private String getTextFromBoardData(String[] csvLine) {
    return csvLine[BOARD_SPACE_NAME];
  }

  private String getTypeFromBoardData(String[] csvLine) { return csvLine[BOARD_SPACE_TYPE]; }

  private int getValueFromBoardData(String[] csvLine) {
    return Integer.parseInt(csvLine[BOARD_SPACE_VALUE]);
  }

  private String getNeighborhoodFromBoardData(String[] csvLine) {
    return csvLine[BOARD_SPACE_NEIGHBORHOOD];
  }

  private int getHouseCostFromBoardData(String[] csvLine) { return Integer.parseInt(csvLine[HOUSE_VALUE]);}

  private int getHotelCostFromBoardData(String[] csvLine) { return Integer.parseInt(csvLine[HOTEL_VALUE]);}
}
