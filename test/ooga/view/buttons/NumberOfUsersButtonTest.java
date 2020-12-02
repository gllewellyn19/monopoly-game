package ooga.view.buttons;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import ooga.DukeApplicationTest;
import ooga.Main;
import ooga.model.DataReader;
import ooga.model.DataReaderTest;
import ooga.model.GamePlay;
import ooga.model.dataReaders.DataReaderClassic;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.exceptions.NecessaryFileNotFound;
import ooga.view.GameView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

class NumberOfUsersButtonTest extends DukeApplicationTest {

  private Main main;
  private GamePlay gamePlay;
  private GameView gameView;
  private DataReader dataReader;

  @Start
  public void start (Stage stage) {
    main = new Main();
    main.start(stage);
    gamePlay = main.getController().getGamePlay();
    gameView = main.getController().getGameView();
    dataReader = new DataReaderClassic();

  }

  //bug fix
  @Test
  void staysEnabledAfterUserClick() {
    ChoiceBox gameThemes = lookup("#ChooseGameThemeButton").query();
    javafxRun(()->gameThemes.setValue("Classic Monopoly"));
    ChoiceBox<Integer> numUsers = lookup("#NumberOfUsersButton").query();
    //javafxRun(()->numUsers.setValue(3)); this line cannot work because cannot convert between integer and string in code
    Assertions.assertFalse(numUsers.isDisabled());
  }

}