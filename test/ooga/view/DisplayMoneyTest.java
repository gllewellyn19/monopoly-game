package ooga.view;

import static org.junit.jupiter.api.Assertions.*;

import ooga.DukeApplicationTest;
import ooga.view.money.ClassicDisplayMoney;
import org.junit.jupiter.api.Test;


public class DisplayMoneyTest extends DukeApplicationTest {

  @Test
  public void testMoneyImageSizeThreePlayers() {
    DisplayMoney displayMoney = new ClassicDisplayMoney();
    //
    //displayMoney.initializeMoney(3);
    assertEquals(3, displayMoney.makeMoneyImages().size());
    //DisplayMoney displayMoney = new DisplayMoney();
    //
   //displayMoney.initializeMoney(3);
    //assertEquals(3, displayMoney.makeMoneyImages().size());
  }
}
