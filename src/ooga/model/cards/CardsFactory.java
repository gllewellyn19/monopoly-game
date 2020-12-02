package ooga.model.cards;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ooga.model.Cards;
import ooga.model.Dice;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ClassicDice;
import ooga.view.gameView.ErrorPrintable;

/**
 * Creates the decks of cards from the data files
 * at the paths provided within infoForGamePlay
 * @author Grace Llewellyn
 */
public class CardsFactory {

  public static final String DEFAULT_CARDS_CLASS = "ClassicCards";
  public static final String FILE_PATH_CARDS = "ooga.model.cards.";

  /**
   * Returns the decks of cards as they are created at the given location
   * @param cardType is the type of deck
   * @param infoForGamePlay provides the locations of the card data in the data folder
   * @param errorPrintable to result in a pop up error if the provided data paths are insufficient
   * @return the decks of cards
   */
  public Cards createCards(Optional<String> cardType, InfoForGamePlay infoForGamePlay,
      ErrorPrintable errorPrintable) {
    try {
      String cardsTypeOrDefault = cardType.isEmpty() ?
          DEFAULT_CARDS_CLASS: cardType.get();
      Class cardsClass = Class.forName(FILE_PATH_CARDS + cardsTypeOrDefault);
      return (Cards) cardsClass.getDeclaredConstructor(InfoForGamePlay.class).
          newInstance(infoForGamePlay);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "cardsType");
      return new ClassicCards(infoForGamePlay);
    }
  }

}
