package ooga.view;

import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import ooga.model.DataReader;
import ooga.view.players.DisplayPlayer;

/***
 * Highest level is abstract. Maintains players' money in view
 *
 * @author Grace Llewellyn
 */
public abstract class DisplayMoney {

  /**
   * @param playerID is playerID, 0 based
   * @param moneyAmount is new amount of players money
   * I plan on refactoring all of this
   */
  public abstract void updateBalance(int playerID, int moneyAmount);

  /**
   * Initializes the money display, ie money images and player balances
   * @param players is a map of all players, which need player balances
   * @param startingBalance is the initial amount given to each player
   * @param dataReader contains information and properties for initialization
   */
  public abstract void initializeMoney(
          Map<Integer, DisplayPlayer> players, int startingBalance, DataReader dataReader);


  protected abstract List<Image> makeMoneyImages();

  /**
   * @return the left Board pane, which contains DisplayMoney components
   */
  public abstract Pane getLeftPane();

  /**
   * @return the right Board pane, which contains DisplayMoney components
   */
  public abstract Pane getRightPane();

  /**
   * @param playerId is the players whose money text is returned
   * @return the player view of their money text
   */
  public abstract String getMoneyText(int playerId);

}
