package ooga.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import ooga.model.boardSpaces.BoardSpace;
import ooga.model.dataReaders.InfoForDice;
import ooga.model.dataReaders.InfoForGameViewClassInstantiations;
import ooga.model.dataReaders.LoadingFolderControls;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.exceptions.NecessaryFileNotFound;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.ErrorPrinting;
import ooga.view.gameView.SetGameValueResourceable;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.view.gameView.StopGameAble;

/***
 * DataReader API implemented by DataReaderImpl
 * Create objects to pass to the ooga.view and ooga.model classes so that the board can be initialized.
 * These objects are going to be created by reading from a csv, properties file, etc.
 */
public abstract class DataReader implements LoadingFolderControls {

  public static final String RESOURCES_FOLDER = "data/";
  public static final String NAME_PROPERTIES_FILE = "/properties";
  public static final String PROPERTIES_FILE_EXTENSION = ".properties";

  private boolean setupSuccessful = true;
  private String folderPath;
  private SetGameValueResourceable setGameValueResourceable;
  private Properties gameValues;
  private ErrorPrintable errorPrintable;
  private StopGameAble stopGameAble;

  public abstract List<String[]> readFile(InputStream data);

  /**
   * @param csvFile is the input CSV file describing the
   *                Monopoly board
   * @return a Board initialized to the CSV specification
   */
  public abstract List<BoardSpace> makeBoardSpaces(File csvFile, ModelDiceRollable modelDiceRollable, int amountPassingGo)
      throws FileNotFoundException;

  public abstract List<String[]> getBoardSpaceImagePaths(File csvFile) throws FileNotFoundException;

  public abstract List<String> getCommunityCardMessages(File csvFile) throws FileNotFoundException;

  public abstract List<String> getChanceCardMessages(File csvFile) throws FileNotFoundException;

  public abstract List<String> getPropertyNames(File csvFile) throws FileNotFoundException;

  public abstract InfoForGamePlay getInformationForGamePlay();

  public abstract String getDisplayMoneyImagePath();

  public abstract Map<String, String> createPlayerNamesAndGamePieceFiles(Map<String, List<String>>
      playerInformation);

  public void uploadPropertiesFolder(String folderPath) {
    this.folderPath = folderPath;
    try {
      extractInformationPropertiesFolder();
    } catch (NecessaryFileNotFound e) {
      errorPrintable.printErrorMessageAlertWithStringFormat(e.getMessage(), e.getFileName());
    }
  }

  // will need to use a getter to get the current folder path
  public abstract void extractInformationPropertiesFolder();

  /*
   * Given a game type with no strings returns the correct file path to it
   */
  public void uploadGameType(String gameType) {
    folderPath = RESOURCES_FOLDER + gameType;
    try {
      extractInformationPropertiesFolder();
    } catch (NecessaryFileNotFound e) {
      errorPrintable.printErrorMessageAlertWithStringFormat(e.getMessage(), e.getFileName());
      setSetupSuccessfulFalse();
    }
  }

  public boolean getSetUpSuccessful() {
    return setupSuccessful;
  }

  public void setSetupSuccessfulFalse() {
    setupSuccessful = false;
    stopGameAble.stopGame();
  }

  public String getFolderPath() {
    return folderPath;
  }

  public void addInterfaces(SetGameValueResourceable setGameValueResourceable,
      ErrorPrintable errorPrintable, StopGameAble stopGameAble) {
    this.setGameValueResourceable = setGameValueResourceable;
    this.errorPrintable = errorPrintable;
    this.stopGameAble = stopGameAble;
  }

  public SetGameValueResourceable getSetGameValueResourceable() {
    return setGameValueResourceable;
  }

  //used for testing
  public void setFolderPath(String folderPath) {
    this.folderPath = folderPath;
  }

  public abstract String getDisplayCommunityCardPath();

  public abstract String getChanceCardPath();

  public abstract String getDisplayChanceDeckPath();

  public abstract String getDisplayCommunityDeckPath();

  public abstract List<String[]> getDefaultPlayerInformation();

  public abstract double getBoardWidth();

  public abstract double getBoardHeight();

  public abstract Optional<String> getGameRotationType();

  public abstract Optional<String> getGameStartType();

  public void setGameProperties(Properties gameValues) {
    this.gameValues = gameValues;
  }

  public Properties getGameValues() {
    return gameValues;
  }

  public abstract int parseWidthFromBoardInfo(List<String[]> boardInfo, int cornerIndex);

  public abstract int parseHeightFromBoardInfo(List<String[]> boardInfo, int cornerIndex);

  public abstract List<String[]> readFile(String boardDirectory) throws FileNotFoundException;

  public abstract Optional<Map<String,Integer>> getNeighborhoodsMap();

  public abstract InfoForDice getInformationForDice();

  public ErrorPrintable getErrorPrintable() {
    return errorPrintable;
  }

  public InfoForGameViewClassInstantiations getInfoForGameViewClassInstantiations() {
    return new InfoForGameViewClassInstantiations(gameValues);
  }
}
