package ooga.model.dataReaders;

import java.util.Optional;
import java.util.Properties;
import javafx.scene.paint.Color;
import ooga.model.exceptions.PropertiesFileException;

public class InfoForDice {

  private Optional<Color> diceColor;
  private Optional<Integer> numberOfDice;

  public InfoForDice(Properties properties) throws PropertiesFileException {
    diceColor = (properties.getProperty("diceColor")==null)? Optional.empty():
        Optional.of(checkPropertyValidColor(properties.getProperty("diceColor")));
    numberOfDice = (properties.getProperty("numDice")==null)? Optional.empty():
        Optional.of(checkPropertyNumberAndPositive(properties.getProperty("numDice")));
  }

  private Color checkPropertyValidColor(String property) throws PropertiesFileException {
    try {
      return Color.web(property);
    } catch (IllegalArgumentException e) {
      throw new PropertiesFileException("diceColor");
    }
  }

  /*
   * Creates an integer out of the given property and throws an exception if the property is not a
   * number or is negative
   */
  private int checkPropertyNumberAndPositive(String property) throws PropertiesFileException{
    int propertyToReturn = Integer.parseInt(property);
    if (propertyToReturn < 0) {
      throw new PropertiesFileException("numDice");
    }
    return propertyToReturn;
  }

  /**
   * @return the color of the dice
   */
  public Optional<Color> getDiceColor() {
    return diceColor;
  }

  /**
   * @return the number of die used in gameplay
   */
  public Optional<Integer> getNumberOfDice() {
    return numberOfDice;
  }

}
