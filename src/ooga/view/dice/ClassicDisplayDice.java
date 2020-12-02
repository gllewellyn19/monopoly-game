package ooga.view.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import ooga.model.dice.ClassicDice;
import ooga.model.exceptions.PropertiesFileException;
import ooga.view.DisplayDice;

/***
 * Maintains all of the ooga.view for the game including the board and the graphics of the Cards
 */
public class ClassicDisplayDice extends DisplayDice {

  public static final List<Integer> COORDINATE_DICE = List.of(200, 0, 30, 30);
  public static final Color DEFAULT_COLOR_DICE = Color.RED;
  public static final int DEFAULT_NUMBER_DICE = 2;

  private final List<Shape> diceShapes;
  private final List<StackPane> diceWithNumbers;

  public ClassicDisplayDice() {
    diceShapes = new ArrayList<>();
    diceWithNumbers = new ArrayList<>();
  }

  /**
   * Creates dice visualization
   * @param numDice is amount of dice to create
   * @param colorDice is optional color of the dice
   */
  @Override
  public void initializeDice(Optional<Integer> numDice, Optional<Color> colorDice) {
    int numberOfDice = (numDice.isEmpty())? ClassicDice.DEFAULT_NUM_DICE: numDice.get();
    Color diceColor;
    try {
      diceColor = (colorDice.isEmpty()) ? DEFAULT_COLOR_DICE : colorDice.get();
    } catch (IllegalArgumentException e) {
      throw new PropertiesFileException("diceColor");
    }
    createDice(numberOfDice, diceColor);
  }

  /*
   * Creates the dice given the number of the dice and the color of the dice
   */
  private void createDice(int numberOfDice, Color diceColor) {
    for (int i=0; i< numberOfDice; i++) {
      Rectangle currentEmptyDice = new Rectangle(COORDINATE_DICE.get(0), COORDINATE_DICE.get(1),
          COORDINATE_DICE.get(2), COORDINATE_DICE.get(3));
      currentEmptyDice.setFill(diceColor);
      diceShapes.add(currentEmptyDice);
      StackPane diceWithNumber = new StackPane();
      diceWithNumber.getChildren().addAll(diceShapes.get(i));
      diceWithNumbers.add(diceWithNumber);
    }
  }

  /**
   * Show the numbers on the dice changing on the screen
   * @param diceRoll represents the value of the dice
   */
  public void rollDice(List<Integer> diceRoll) {
    for (int i=0; i<diceShapes.size(); i++) {
      Text currentDiceRoll = new Text(Integer.toString(diceRoll.get(i)));
      StackPane diceWithNumber = diceWithNumbers.get(i);
      diceWithNumber.getChildren().clear();
      diceWithNumber.getChildren().addAll(diceShapes.get(i), currentDiceRoll);
    }
  }

  /**
   * Adds dice to HBOX for visualization
   * @param row is HBox to add to
   * @return HBox with dice visualization added to its child list
   */
  @Override
  public HBox addDiceToHBox(HBox row) {
    row.getChildren().addAll(diceWithNumbers);
    return row;
  }
}
