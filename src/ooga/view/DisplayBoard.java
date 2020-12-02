package ooga.view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ooga.model.DataReader;
import ooga.view.boardSpaces.ClassicDisplaySpace;
import ooga.view.boardSpaces.ClassicDisplaySpaces;
import ooga.view.gameView.ErrorPrintable;

/***
 * Highest level is abstract. Maintains the display board of the game
 *
 * @author Grace Llewellyn
 */
public abstract class DisplayBoard {

  /**
   * Sets up the board with the display space image data found at the given filePath
   *
   * @param errorPrintable ErrorPrintable to handle issues with the data
   * @param filePath the location of the csv file that stores the image file names and order of images
   * @param dataReader DataReader that can read in the image files
   * @return true if the board was successfully initialized, false if not
   */
  public abstract boolean initializeBoard(ErrorPrintable errorPrintable, String filePath,
      DataReader dataReader);

  /**
   * Returns a DisplaySpaces that stores DisplaySpace objects, respresenting each space on the board
   * @return DisplaySpaces, which stores all spaces on the board
   */
  public abstract ClassicDisplaySpaces getDisplaySpaces();

  /**
   * Returns a grid pane containing the DisplaySpaces in the correct layout
   * @return a GridPane with the correct layout of DisplaySpaces
   */
  public abstract GridPane getBoardGrid();

  /**
   * Returns a pane that stores the overall board layout, including the center images
   * @return Pane with the whole board layout
   */
  public abstract Pane getBoardLayout();

  /**
   * Returns Pane of the given space on the board
   * @param n is index of pane we retrieve
   * @return the nth pane on the board
   */
  public abstract Pane getNthPane(int n);

  /**
   * Returns the Pane of the space where the players start
   * @return Pane of the Go space
   */
  public abstract Pane getStartPane();

  /**
   * Returns the DisplaySpace at the given location
   *
   * @param location space on board to return
   * @return DisplaySpace at the given location
   */
  public abstract ClassicDisplaySpace getSpaceAtLocation(int location);
}
