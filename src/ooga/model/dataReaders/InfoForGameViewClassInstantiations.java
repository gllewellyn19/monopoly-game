package ooga.model.dataReaders;

import java.util.Optional;
import java.util.Properties;

public class InfoForGameViewClassInstantiations {

  private Optional<String> displayBoardType = Optional.empty();
  private Optional<String> displayCardsType = Optional.empty();
  private Optional<String> displayDiceType = Optional.empty();
  private Optional<String> displayMoneyType = Optional.empty();



  public InfoForGameViewClassInstantiations(Properties properties) {
    if (properties.getProperty("displayBoardType") != null) {
      displayBoardType = Optional.of(properties.getProperty("displayBoardType"));
    }
    if (properties.getProperty("displayCardsType") != null) {
      displayCardsType = Optional.of(properties.getProperty("displayCardsType"));
    }
    if (properties.getProperty("displayDiceType") != null) {
      displayDiceType = Optional.of(properties.getProperty("displayDiceType"));
    }
    if (properties.getProperty("displayMoneyType") != null) {
      displayMoneyType = Optional.of(properties.getProperty("displayMoneyType"));
    }
  }

  /**
   * @return the displayBoard type, if it exists.
   */
  public Optional<String> getDisplayBoardType() {
    return displayBoardType;
  }

  /**
   * @return the displayCards type, if it exists.
   */
  public Optional<String> getDisplayCardsType() {
    return displayCardsType;
  }

  /**
   * @return the displayDice type, if it exists.
   */
  public Optional<String> getDisplayDiceType() {
    return displayDiceType;
  }

  /**
   * @return the displayMoney type, if it exists.
   */
  public Optional<String> getDisplayMoneyType() {
    return displayMoneyType;
  }
}
