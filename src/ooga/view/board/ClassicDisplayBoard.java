package ooga.view.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ooga.model.exceptions.ModelException;
import ooga.model.exceptions.NecessaryFileNotFound;
import ooga.view.DisplayBoard;
import ooga.view.gameView.ErrorPrintable;
import ooga.model.DataReader;
import ooga.view.boardSpaces.ClassicDisplaySpace;
import ooga.view.boardSpaces.ClassicDisplaySpaces;

/***
 * Manages the DisplaySpaces for the board, initializes a GridPane with the correct locations
 * of the DisplaySpaces and adds center images to the board
 *
 * @author Lina Leyhausen, Anna Diemel
 */
public class ClassicDisplayBoard extends DisplayBoard {

  private final GridPane boardGrid;
  private ClassicDisplaySpaces displaySpaces;
  private final List<Pane> panes;
  private final Pane boardLayout;

  /**
   * Initializes a DisplayBoard and the Pane objects in it
   */
  public ClassicDisplayBoard() {
    panes = new ArrayList<>();
    boardGrid = new GridPane();
    boardLayout = new Pane();
  }

  /**
   * Sets up the board with the display space image data found at the given filePath
   *
   * @param errorPrintable ErrorPrintable to handle issues with the data
   * @param filePath the location of the csv file that stores the image file names and order of images
   * @param dataReader DataReader that can read in the image files
   * @return true if the board was successfully initialized, false if not
   */
  public boolean initializeBoard(ErrorPrintable errorPrintable, String filePath, DataReader dataReader) {
    try {
      setBoardLayout(dataReader);
      makeDisplaySpaces(filePath);
      List<List<Integer>> XY = getBoardSpacePositions(dataReader);
      for (int i = 0; i < displaySpaces.getDisplaySpaces().size(); i++) {
        Pane temp = makePaneFromDisplaySpace(displaySpaces.getDisplaySpaces().get(i));
        panes.add(temp);
        boardGrid.add(temp, XY.get(0).get(i), XY.get(1).get(i));
      }
      addCenterImage(dataReader);
      boardLayout.getChildren().add(boardGrid);
      return true;
    } catch(FileNotFoundException | NecessaryFileNotFound e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("fileNotFound", "displayBoard");
      return false;
    }
  }

  /**
   * If there is a board-center image, add it to the board.
   */
  private void addCenterImage(DataReader dataReader) {
    if (dataReader.getInformationForGamePlay().getProperty("boardCenterPath") == null) {
      return;
    }
    String imageFilepath = dataReader.getFolderPath() + dataReader.getInformationForGamePlay().getProperty("boardCenterPath");
    Image centerImage = new Image(new File(imageFilepath).toURI().toString());
    Rectangle background = makeBackgroundFromImage(centerImage);
    boardLayout.getChildren().add(background);
  }

  private Rectangle makeBackgroundFromImage(Image centerImage) {
    double offset = ClassicDisplaySpaces.HEIGHT;
    double width = boardLayout.getMaxWidth() - offset;
    Rectangle background = new Rectangle(offset, offset, width, width);
    background.setFill(new ImagePattern(centerImage));
    return background;
  }

  private List<List<Integer>> getBoardSpacePositions(DataReader dataReader) throws
      NecessaryFileNotFound, FileNotFoundException{
      List<String[]> boardInfo = dataReader.readFile(dataReader.getInformationForGamePlay().getBoardDirectory());
    int cornerIndex = dataReader.getInformationForGamePlay().getNeighborhoodIndex(boardInfo.get(0));
    int width = dataReader.parseWidthFromBoardInfo(boardInfo, cornerIndex);
    int height = dataReader.parseHeightFromBoardInfo(boardInfo, cornerIndex);
    List<Integer> xPositions = getXPositions(width, height);
    List<Integer> yPositions = getYPositions(width, height);

      return new ArrayList<>(){{ add(xPositions); add(yPositions);}};
  }

  private List<Integer> getXPositions(int width, int height) {
    List<Integer> positions = new ArrayList<>();
    for (int i = width - 1; i >= 0; i--) {
      positions.add(i);
    }
    positions.addAll(Collections.nCopies(height-1,0));
    for (int k = 1; k < width; k++) {
      positions.add(k);
    }
    positions.addAll(Collections.nCopies(height-2,width-1));
    return positions;
  }

  private List<Integer> getYPositions(int width, int height) {
    List<Integer> positions = new ArrayList<>(Collections.nCopies(width, height - 1));
    for (int j = height - 2; j >= 0; j--) {
      positions.add(j);
    }
    positions.addAll(Collections.nCopies(width-1, 0));
    for (int l = 1; l < height - 1; l++) {
      positions.add(l);
    }
    return positions;
  }

  private void setBoardLayout(DataReader dataReader) {
    boardGrid.setMaxWidth(dataReader.getBoardWidth());
    boardGrid.setMaxHeight(dataReader.getBoardHeight());
    boardLayout.setMaxWidth(dataReader.getBoardWidth());
    boardLayout.setMaxHeight(dataReader.getBoardHeight());
  }

  private void makeDisplaySpaces(String filePath) throws FileNotFoundException {
    displaySpaces = new ClassicDisplaySpaces(filePath);
  }

  private Pane makePaneFromDisplaySpace(ClassicDisplaySpace displaySpace) {
    Pane temp = new StackPane();
    temp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    temp.getChildren().add(displaySpace.getSpace());
    return temp;
  }

  /**
   * Returns a DisplaySpaces that stores DisplaySpace objects, respresenting each space on the board
   * @return DisplaySpaces, which stores all spaces on the board
   */
  public ClassicDisplaySpaces getDisplaySpaces() {
    return displaySpaces;
  }

  /**
   * Returns a grid pane containing the DisplaySpaces in the correct layout
   * @return a GridPane with the correct layout of DisplaySpaces
   */
  public GridPane getBoardGrid(){
    return boardGrid;
  }

  /**
   * Returns a pane that stores the overall board layout, including the center images
   * @return Pane with the whole board layout
   */
  public Pane getBoardLayout() {
    return boardLayout;
  }

  /**
   * Returns Pane of the given space on the board
   * @param n is index of pane we retrieve
   * @return the nth pane on the board
   */
  public Pane getNthPane(int n) {
    if (n < panes.size()) {
      return panes.get(n);
    }
    throw new ModelException("emptyBoard");
  }

  /**
   * Returns the Pane of the space where the players start
   * @return Pane of the Go space
   */
  public Pane getStartPane() {
    return getNthPane(0);
  }

  /**
   * Returns the DisplaySpace at the given location
   *
   * @param location space on board to return
   * @return DisplaySpace at the given location
   */
  public ClassicDisplaySpace getSpaceAtLocation(int location){
    return displaySpaces.getDisplaySpaces().get(location);
  }
}
