package ooga.model.cards;

/**
 * Classic monopoly implementation of the Card API
 * @author delaneydemark
 */
public class ClassicCard extends Card {

  /**
   * uses data from a card data CSV to create a card
   * @param data array of card data that represents a line in a card data CSV
   */
  public ClassicCard(String[] data){
    super(data);
  }

}
