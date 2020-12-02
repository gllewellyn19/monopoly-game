package ooga.view;

import java.util.List;
import java.util.Optional;
import javafx.scene.shape.Rectangle;
import ooga.model.DataReader;

/***
 * Reflects the decks of cards and the drawn card in the view
 * Not only shows the cards in the middle but also shows the card that is on top when the user clicks a card
 * @author delaneydemark
 */
public abstract class DisplayCards {


  /**
   * @param dataReader to get image paths
   * @return the images of tops of the decks
   */
  public abstract List<Rectangle> getDeckImages(DataReader dataReader);

  /**
   * creates the card images based on the paths to get the card images
   * @param dataReader with card image paths
   */
  public abstract void initializeCards(DataReader dataReader);

  /**
   * @param cardID unique id of the card drawn in the model
   * @return a rectangle with the image of the card drawn
   */
  public abstract Rectangle showDrawnCard(int cardID);

  /**
   * @param dataReader to get the deck image location
   * @return the rectangle with the image of the top of the chance deck
   */
  public abstract Optional<Rectangle> getChanceDeckImage(DataReader dataReader);

  /**
   * @param dataReader to get the deck image location
   * @return the rectangle with the image of the top of the community chest deck
   */
  public abstract Optional<Rectangle> getCommunityChestDeckImage(DataReader dataReader);
}
