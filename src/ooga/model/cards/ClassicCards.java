package ooga.model.cards;


import ooga.model.Cards;
import ooga.model.dataReaders.DataReaderClassic;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.exceptions.CardDataNotFound;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/***
 * Maintains a stack of cards for the game (most likely a collection of chance and community cards)
 * The classic implementation for a game of Monopoly
 * @author delaneydemark
 */
public class ClassicCards extends Cards {

  private static final String DATA_DIRECTORY = "././././data";

  private final Collection<Card> communityCards;
  private final Collection<Card> chanceCards;
  protected DataReaderClassic cardData;
  private String chanceDataPath;
  private String communityDataPath;

  /**
   * Creates decks of cards with the card data in the provided paths
   * @param dataLocations provides the paths for the card CSVs
   */
  public ClassicCards(InfoForGamePlay dataLocations){
    cardData = new DataReaderClassic();
    communityCards = new ArrayList<>();
    chanceCards = new ArrayList<>();
    initializeDecks(dataLocations);
  }

  private void dataToDeck(Collection<Card> deck, String dataPath){
    try {
      String absoluteDataPath = DATA_DIRECTORY + dataPath;
      List<String[]> deckMessages = cardData.readFile(new FileInputStream(absoluteDataPath));
      deckMessages.remove(0);
      for(String[] chanceMessage: deckMessages){
        deck.add(new ClassicCard(chanceMessage));
      }
    } catch (FileNotFoundException e) {
      throw new CardDataNotFound("Chance and Community Chest Data Not Found");
    }
  }

  private void initializeDecks(InfoForGamePlay dataLocations) {
      if(dataLocations.getChanceCardPath()!=null){
        chanceDataPath = dataLocations.getDataDirectory() + dataLocations.getChanceCardPath();
        dataToDeck(chanceCards, chanceDataPath);
      }
      if(dataLocations.getCommunityCardPath()!=null) {
        communityDataPath = dataLocations.getDataDirectory() + dataLocations.getCommunityCardPath();
        dataToDeck(communityCards, communityDataPath);
      }
  }

  /**
   * @return copy of list of chance cards in stack
   */
  public Collection<Card> getChanceDeck(){
    return chanceCards;
  }

  /**
   * @return copy of list of community chest cards in stack
   */
  public Collection<Card> getCommunityDeck(){
    return communityCards;
  }

  /**
   * draws the top chance card
   * reinitializes the deck if it is empty
   * @return the card drawn
   */
  public Card getTopChanceCard(){
    if(chanceCards.isEmpty()) dataToDeck(chanceCards, chanceDataPath);
    Card topCard = (Card) chanceCards.toArray()[0];
    chanceCards.remove(topCard);
    return topCard;
  }

  /**
   * draws the top community chest card
   * reinitializes the deck if it is empty
   * @return the card drawn
   */
  public Card getTopCommunityCard(){
    if(communityCards.isEmpty()) dataToDeck(communityCards, communityDataPath);
    Card topCard = (Card) communityCards.toArray()[0];
    communityCards.remove(topCard);
    return topCard;
  }

  /**
   * shuffles the chance card deck
   */
  public void shuffleChanceDeck(){
    Collections.shuffle((List<?>) chanceCards);
  }

  /**
   * shuffles the community chest card deck
   */
  public void shuffleCommunityDeck(){
    Collections.shuffle((List<?>) communityCards);
  }
}
