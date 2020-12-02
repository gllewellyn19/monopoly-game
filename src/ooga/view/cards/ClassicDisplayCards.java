package ooga.view.cards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ooga.controller.Controller;
import ooga.model.DataReader;
import ooga.model.exceptions.CardDataNotFound;
import ooga.view.DisplayCards;


/***
 * Reflects the decks of cards and the drawn card in the view
 * Not only shows the cards in the middle but also shows the card that is on top when the user clicks a card
 * @author delaneydemark
 */
public class ClassicDisplayCards extends DisplayCards {

  public static final int CHANCE_DECK_WIDTH = 115;
  public static final int DECK_HEIGHT = 75;
  public static final int CHANCE_DECK_X_POS = 145;
  public static final int CHANCE_DECK_Y_POS = 160;
  public static final int COMMUNITY_DECK_WIDTH = 120;
  public static final int COMMUNITY_DECK_X_POS = 420;
  public static final int COMMUNITY_DECK_Y_POS = 455;
  private Map<Integer, String> cardImages;
  private String communityCardPath;
  private String chanceCardPath;

  /**
   * initializes card images map
   */
  public ClassicDisplayCards(){
    cardImages = new HashMap<>();
  }

  /**
   * @param cardID unique id of the card drawn in the model
   * @return a rectangle with the image of the card drawn
   */
  public Rectangle showDrawnCard(int cardID) {
    if(!cardImages.containsKey(cardID)) return null;
    // throw new CardImageNotFoundException("Image for card drawn not found");
    Rectangle drawnCard = getCardImage(cardImages.get(cardID), 150, 125);
    drawnCard.setX(-25);
    drawnCard.setY(Controller.DEFAULT_SIDE_SIZE.height/2);
    return drawnCard;
  }

  private Rectangle getCardImage(String filename, int width, int height){
    Rectangle card = new Rectangle(width, height);
    Image cardImage = new Image(new File(filename).toURI().toString());
    card.setFill(new ImagePattern(cardImage));
    return card;
  }

  /**
   * @param dataReader to get image paths
   * @return the images of tops of the decks
   */
  public List<Rectangle> getDeckImages(DataReader dataReader){
    List<Rectangle> images = new ArrayList<>();
    if(dataReader.getDisplayChanceDeckPath()!=null) {
      images.add(getCardImage(dataReader.getDisplayChanceDeckPath(), 140, 100));
    }
    if(dataReader.getDisplayCommunityDeckPath()!=null) {
      images.add(getCardImage(dataReader.getDisplayCommunityDeckPath(), 140, 100));
    }
    return images;
  }

  /**
   * @param dataReader to get the deck image location
   * @return the rectangle with the image of the top of the chance deck
   */
  public Optional<Rectangle> getChanceDeckImage(DataReader dataReader) {
    if(dataReader.getDisplayChanceDeckPath()!=null) {
      return Optional.of(getCardImage(dataReader.getDisplayChanceDeckPath(), CHANCE_DECK_WIDTH,
          DECK_HEIGHT, CHANCE_DECK_X_POS, CHANCE_DECK_Y_POS));
    }
    return Optional.empty();
  }

  /**
   * @param dataReader to get the deck image location
   * @return the rectangle with the image of the top of the community chest deck
   */
  public Optional<Rectangle> getCommunityChestDeckImage(DataReader dataReader) {
    if(dataReader.getDisplayChanceDeckPath()!=null) {
      return Optional.of(getCardImage(dataReader.getDisplayCommunityDeckPath(),
          COMMUNITY_DECK_WIDTH, DECK_HEIGHT, COMMUNITY_DECK_X_POS, COMMUNITY_DECK_Y_POS));
    }
    return Optional.empty();
  }

  private Rectangle getCardImage(String displayChanceDeckPath, int width, int height, int xPos, int yPos) {
    Rectangle image = getCardImage(displayChanceDeckPath, width, height);
    image.setX(xPos);
    image.setY(yPos);
    return image;
  }

  /**
   * creates the card images based on the paths to get the card images
   * @param dataReader with card image paths
   */
  public void initializeCards(DataReader dataReader) {
    communityCardPath = dataReader.getDisplayCommunityCardPath();
    createCardImages(communityCardPath, dataReader); // if (rules) - no community cards for junior
    chanceCardPath = dataReader.getChanceCardPath();
    createCardImages(chanceCardPath, dataReader);
  }

  private void createCardImages(String dataFile, DataReader dataReader) {
    try {
      if(isNotNull(dataFile)) {
        List<String[]> deckData = dataReader.readFile(new FileInputStream(dataFile));
        deckData.remove(0);
        for (String[] card : deckData) {
          cardImages.put(Integer.parseInt(card[1]), card[5]);
        }
      }
    }catch(FileNotFoundException e){
      throw new CardDataNotFound("cardDataNotFound");
    }
  }

  /**
   * checks against poorly formed strings, which end with "null"
   */
  private boolean isNotNull(String dataFile) {
    return (dataFile != null) && (!dataFile.contains("null"));
  }

}
