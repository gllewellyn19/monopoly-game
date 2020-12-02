package ooga.view.money;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ooga.controller.Controller;
import ooga.model.DataReader;
import ooga.view.DisplayMoney;
import ooga.view.players.DisplayPlayer;

/***
 * Shows the money for the user- shows a generic picture of money and show the
 * user their balance
 *
 * @author Grace Llewellyn and Anna Diemel
 */
public class ClassicDisplayMoney extends DisplayMoney {

  public static final int IMAGE_HEIGHT = 90;
  public static final int IMAGE_WIDTH = 90;
  private static final int TEXT_Y_OFFSET = 100;
  private static final int TEXT_X_OFFSET = 10;
  public static final String MONEY_SIGN = "$";
  public static final String SEPARATOR_SHOWING_MONEY = ":";
  public static final int OFFSET_LEFT_MONEY = 20;

  private List<Image> moneyImages;
  private List<Integer> imagePositionsX;
  private List<Integer> imagePositionsY;
  private Pane leftPane;
  private Pane rightPane;
  private final Map<Integer, Text> playerBalances = new HashMap<>(); //mapping of player balances where unique player id maps to text

  public ClassicDisplayMoney() {
    initializeLists();
    initializePanes();
  }

  private void initializeLists() {
    moneyImages = new ArrayList<>();
    imagePositionsX = new ArrayList<>();
    imagePositionsY = new ArrayList<>();
  }

  private void initializePanes() {
    leftPane = new Pane();
    rightPane = new Pane();

    leftPane.setPrefSize(Controller.DEFAULT_SIDE_SIZE.width, Controller.DEFAULT_SIDE_SIZE.height);
    rightPane.setPrefSize(Controller.DEFAULT_SIDE_SIZE.width, Controller.DEFAULT_SIDE_SIZE.height);
  }

  /**
   * @param playerID is playerID, 0 based
   * @param moneyAmount is new amount of players money
   * I plan on refactoring all of this
   */
  public void updateBalance(int playerID, int moneyAmount) {
    Text balanceToUpdate = playerBalances.get(playerID);
    balanceToUpdate.setText(balanceToUpdate.getText().substring(0, balanceToUpdate.getText().indexOf
        (SEPARATOR_SHOWING_MONEY)) + SEPARATOR_SHOWING_MONEY+MONEY_SIGN+moneyAmount);
  }

  /*
   * Initialize the money display- ie the money images and player balance
   */
  public void initializeMoney(Map<Integer, DisplayPlayer> players, int startingBalance, DataReader dataReader) {
    makeMoneyImages(players.size(), dataReader);
    positionDisplayMoney();
    showMoney(players, startingBalance);
  }

  private void makeMoneyImages(int numPlayers, DataReader dataReader) {
    String imagePath = dataReader.getDisplayMoneyImagePath();
    Image moneyImage = new Image(new File(imagePath).toURI().toString());
    moneyImages =  Collections.nCopies(numPlayers, moneyImage);
  }

  /*
   * Creates the locations of where to put the money on the grid
   */
  private void positionDisplayMoney() {
    int numMoneyImagesEachSide = moneyImages.size() / 2 + 1;
    int yIncrement = Controller.DEFAULT_SIZE.height / numMoneyImagesEachSide;
    int amountMoneyOnLeft = moneyImages.size()/2;
    if (moneyImages.size()%2==1) {
      amountMoneyOnLeft+=1;
    }
    //positions right half
    for (int i = 0; i < amountMoneyOnLeft; i++) {
      imagePositionsX.add(0);
      imagePositionsY.add(i * yIncrement);
    }
    //positions left half
    int count=0;
    for (int i = amountMoneyOnLeft; i < moneyImages.size(); i++) {
      imagePositionsX.add(Controller.DEFAULT_SIZE.width - IMAGE_WIDTH - OFFSET_LEFT_MONEY);
      imagePositionsY.add(count * yIncrement);
      count++;
    }
  }

  private void showMoney(Map<Integer, DisplayPlayer> players, int startingBalance) {
    for (int i = 0; i < players.size(); i++) {
      makePlayerDisplayMoney(leftPane, i, players, startingBalance);
    }
  }

  private boolean isTrailingDisplayMoney(int i) {
    return (moneyImages.size() % 2 == 1 && (i+1) >= moneyImages.size() / 2);
  }

  private void makePlayerDisplayMoney(Pane pane, int i, Map<Integer, DisplayPlayer> players,
      int startingBalance) {
    addRectangleWithIndex(i, pane);
    addTextWithIndex(i, pane, players, startingBalance);
  }

  /*
   * Adds the player text for the given index
   */
  private void addTextWithIndex(int i, Pane pane, Map<Integer, DisplayPlayer> players, int startingBalance) {
    Text playerText = new Text(players.get(i).getPlayerName()+SEPARATOR_SHOWING_MONEY+startingBalance);
    playerText.setX(imagePositionsX.get(i) + TEXT_X_OFFSET);
    playerText.setY(imagePositionsY.get(i) + TEXT_Y_OFFSET);
    pane.getChildren().add(playerText);
    playerBalances.put(i, playerText);
  }

  /*
   * Creates a rectangle with a money image for the specified index
   */
  private void addRectangleWithIndex(int index, Pane pane) {
    Rectangle rectangle = new Rectangle(IMAGE_WIDTH, IMAGE_HEIGHT);
    rectangle.setFill(new ImagePattern(moneyImages.get(index)));
    rectangle.setX(imagePositionsX.get(index));
    rectangle.setY(imagePositionsY.get(index));
    pane.getChildren().add(rectangle);
  }

  protected List<Image> makeMoneyImages() { return moneyImages; }

  public Pane getLeftPane() {
    return leftPane;
  }

  public Pane getRightPane() {
    return rightPane;
  }

  // for testing
  public String getMoneyText(int playerId) {
    return playerBalances.get(playerId).getText();
  }
}
