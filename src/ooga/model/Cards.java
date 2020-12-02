package ooga.model;

import ooga.model.cards.Card;

/**
 * API for handling the use of the card decks in Monopoly
 * @author delaneydemark
 */
public abstract class Cards {

  /**
   * Draws the chance card at the top of the deck
   * Reinitializes the deck if it is empty
   * @return the card object
   */
  public abstract Card getTopChanceCard();

  /**
   * Draws the top community chest card at the top of the deck
   * Reinitializes the deck if it is empty
   * @return the card object drawn
   */
  public abstract Card getTopCommunityCard();

  /**
   * Shuffles the chance card deck
   */
  public abstract void shuffleChanceDeck();
  public abstract void shuffleCommunityDeck();

}
