package ooga.model.dataReaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import ooga.controller.Controller;
import ooga.model.Bank;
import ooga.model.Cards;
import ooga.model.boardSpaces.BoardSpace;
import ooga.model.DataReader;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.exceptions.BoardSpaceException;
import ooga.model.exceptions.NecessaryFileNotFound;
import ooga.view.buttons.PlayerInputLines;

public class DataReaderClassic extends DataReader {

  public static final String DEFAULT_PLAYER_THREE = "defaultPlayerThree";
  private static final String BOARD_HEIGHT = "boardHeight";
  private static final String BOARD_WIDTH = "boardWidth";
  public static final String COMMA = ",";
  public static final String SPACE = " ";
  public static final String EMPTY = "";
  public static final String NEIGHBORHOODS = "neighborhoods";
  public static final String NEIGHBORHOOD_SIZES = "neighborhoodSizes";
  public static final String BOARD_DIRECTORY_1 = "boardDirectory1";
  public static final String BOARD_DIRECTORY_2 = "boardDirectory2";
  public static final String DEFAULT_PLAYER_ONE = "defaultPlayerOne";
  public static final String DEFAULT_PLAYER_TWO = "defaultPlayerTwo";
  public static final String GAME_ROTATION_TYPE = "gameRotationType";
  private final BoardSpaceFactory boardSpaceFactory;
  private static final String DEFAULT_PATH = "src/resources/defaultStartingValues/defaultStartingValues.properties";

  public DataReaderClassic(Bank bank, Cards decks) {
    boardSpaceFactory = new BoardSpaceFactory(bank, decks, getNeighborhoodsMap());
  }

  public DataReaderClassic() {
    boardSpaceFactory = new BoardSpaceFactory();
  }

  private InputStream getStream(File file) throws FileNotFoundException {
    return new FileInputStream(file);
  }

  /**
   * Reads in a given file and converts it to a list of strings
   * @param filename is the file to be opened and read
   * @return a list of String[]s, which correspond to each file line
   * @throws NecessaryFileNotFound if no such file at filename exists
   */
  public List<String[]> readFile(String filename) throws NecessaryFileNotFound {
    if (getInformationForGamePlay().getBoardDirectory() == null) {
      throw new NecessaryFileNotFound(BOARD_DIRECTORY_1);
    }
    try {
      File boardFile = new File(getInformationForGamePlay().getBoardDirectory());
      return readFile(getStream(boardFile));
    } catch (FileNotFoundException e) {
      throw new NecessaryFileNotFound(BOARD_DIRECTORY_2);
    }
  }

  /**
   * Reads a file's contents given an input stream of that file
   * @param data is the contents of the file to be converted
   * @return a list of String[]s representing each line in the file,
   * intended to be used for CSVs
   */
  @Override
  public List<String[]> readFile(InputStream data) {
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(data))) {
      return csvReader.readAll();
    } catch (IOException | CsvException e) {
      return Collections.emptyList();
    }
  }

  /**
   * Creates board spaces from a CSV file.
   * @param csvFile is the input CSV file describing the
   *                Monopoly board
   * @param modelDiceRollable is the interface handling dice rolls
   * @param amountPassingGo is the value collected from passing "GO"
   * @return a list of created board spaces. This should include all
   * board spaces described in the CSV file, and all board spaces needed
   * to complete a board
   * @throws FileNotFoundException
   * @throws BoardSpaceException
   */
  @Override
  public List<BoardSpace> makeBoardSpaces(File csvFile, ModelDiceRollable modelDiceRollable,
      int amountPassingGo)
      throws FileNotFoundException, BoardSpaceException {
    List<String[]> boardDataList = getBoardData(csvFile);
    List<BoardSpace> boardSpaceList = new ArrayList<>();
    for (String[] strings : boardDataList) {
      BoardSpace currentBoardSpace = boardSpaceFactory
          .makeBoardSpaceFromBoardData(strings, modelDiceRollable, amountPassingGo);
      boardSpaceList.add(currentBoardSpace);
    }
    return boardSpaceList;
  }

  private List<String[]> getBoardData(File csvFile) throws FileNotFoundException {
    List<String[]> boardData = readFile(getStream(csvFile));
    assert (boardData != null);
    if (boardData.get(0)[0].equals(getInformationForGamePlay().getCSVHeader())) {
      boardData.remove(0);
    }
    return boardData;
  }

  /**
   * @return the filepath of the money image
   * This is the image displayed in tandem with player's bank balance
   */
  @Override
  public String getDisplayMoneyImagePath() {
    return super.getFolderPath() + getInformationForGamePlay().getMoneyImagePath();
  }

  /**
   * Creates a mapping of player name to the file path of their game piece
   */
  @Override
  public Map<String, String> createPlayerNamesAndGamePieceFiles(
      Map<String, List<String>> playerInformation) {
    Map<String, String> playerNamesAndGamePieceFiles = new HashMap<>();
    try {
      for (Map.Entry<String, List<String>> entry : playerInformation.entrySet()) {
        String gamePiece = entry.getValue().get(PlayerInputLines.INDEX_PLAYER_PIECE);
        if (!gamePiece.startsWith(PlayerInputLines.COLOR_IDENTIFIER)) {
          gamePiece = findFile(gamePiece,
              new File(getFolderPath() + getInformationForGamePlay().getPlayerPiecePath()));
        }
        playerNamesAndGamePieceFiles.put(entry.getKey(), gamePiece);
      }

    } catch (NecessaryFileNotFound e) {
      super.getErrorPrintable()
          .printErrorMessageAlertWithStringFormat(e.getMessage(), e.getFileName());
    }

    return playerNamesAndGamePieceFiles;
  }

  /*
   * Returns the file path with the given name in a directory
   */
  private String findFile(String name, File directory) {
    File[] list = directory.listFiles();
    if (list != null) {
      for (File file : list) {
        if (name.equalsIgnoreCase(file.getName().substring(0, file.getName().indexOf(".")))) {
          return file.getPath();
        }
      }
    }
    throw new NecessaryFileNotFound(name);
  }


  /**
   * Reads in the folder name and path from the abstract class
   * Generates a Properties object based on the finalized filepath
   */
  @Override
  public void extractInformationPropertiesFolder() {
    Properties gameValues = new Properties();
    try {
      FileInputStream fis = new FileInputStream(super.getFolderPath() + NAME_PROPERTIES_FILE
          + PROPERTIES_FILE_EXTENSION);
      gameValues.load(fis);
      fis.close();
    } catch (IOException e) {
      throw new NecessaryFileNotFound("propertiesFile");
    }
    super.setGameProperties(gameValues);
    super.getSetGameValueResourceable().setGameValueResources(gameValues);
  }

  /**
   * @return the file path for the community card images
   */
  @Override
  public String getDisplayCommunityCardPath() {
    return getFolderPath() + getInformationForGamePlay().getCommunityCardPath();
  }

  /**
   * @return the file path for the chance card images
   */
  @Override
  public String getChanceCardPath() {
    return getFolderPath() + getInformationForGamePlay().getChanceCardPath();
  }

  /**
   * @return the file path for the chance card deck image
   */
  @Override
  public String getDisplayChanceDeckPath() {
    return getFolderPath() + getInformationForGamePlay().getChanceDeckImagePath();
  }

  /**
   * @return the file path for the community card deck image
   */
  @Override
  public String getDisplayCommunityDeckPath() {
    return getFolderPath() + getInformationForGamePlay().getCommunityDeckImagePath();
  }

  /**
   * Creates a list of relevant board space image filepaths.
   * @param csvFile is the file with pathnames to parse
   * @return a list of file paths for the board spaces detailled in the CSV
   * @throws FileNotFoundException
   */
  @Override
  public List<String[]> getBoardSpaceImagePaths(File csvFile) throws FileNotFoundException {
    List<String[]> boardSpaceImages = new ArrayList<>();
    List<String[]> boardData = readFile(getStream(csvFile));
    String dataPath = boardData.get(0)[0];
    for (int i = 1; i < boardData.size(); i++) {
      String[] imageFile = new String[2];
      imageFile[0] = dataPath + boardData.get(i)[0];
      imageFile[1] = boardData.get(i)[1];
      boardSpaceImages.add(imageFile);
    }
    return boardSpaceImages;
  }

  /**
   * Retrieves the messages associated with each community card
   * @param csvFile is the File containing the card messages
   * @return a list of these messages
   * @throws FileNotFoundException
   */
  @Override
  public List<String> getCommunityCardMessages(File csvFile) throws FileNotFoundException {
    return getMessage(csvFile);
  }

  /**
   * Retrieves the messages associated with each chance card
   * @param csvFile is the File containing the card messages
   * @return a list of these messages
   * @throws FileNotFoundException
   */
  @Override
  public List<String> getChanceCardMessages(File csvFile) throws FileNotFoundException {
    return getMessage(csvFile);
  }

  /**
   * Retrieves the names of the properties for the game
   * @param csvFile is the File containing the property descriptions
   * @return a list of these names
   * @throws FileNotFoundException
   */
  @Override
  public List<String> getPropertyNames(File csvFile) throws FileNotFoundException {
    return getMessage(csvFile);
  }

  private List<String> getMessage(File csvFile) throws FileNotFoundException {
    List<String> messages = new ArrayList<>();
    List<String[]> boardData = readFile(getStream(csvFile));
    for (int i = 1; i < boardData.size(); i++) {
      messages.add(boardData.get(i)[0]);
    }
    return messages;
  }

  /**
   * Gets the information from the properties file that go needs for game play
   */
  public InfoForGamePlay getInformationForGamePlay() {
    try {
      Properties properties = getPropertiesFromFile(super.getFolderPath() + NAME_PROPERTIES_FILE
          + PROPERTIES_FILE_EXTENSION);
      return new InfoForGamePlay(properties, super.getFolderPath());
    } catch (NecessaryFileNotFound e) {
      super.getErrorPrintable().printErrorMessageAlertWithStringFormat(e.getMessage(), e.getFileName());
      super.setSetupSuccessfulFalse();
    }
    return null;
  }

  private Properties getDefaultProperties() {
    return getPropertiesFromFile(DEFAULT_PATH);
  }

  /**
  Hard coded to get default player information from classic monopoly in defaultStartingValues.
   */
  public List<String[]> getDefaultPlayerInformation() {
    List<String[]> playerInfo = new ArrayList<>();
    uploadGameType("ClassicMonopoly");
    //setFolderPath("data/ClassicMonopoly/");
    //setFolderPath("data/ClassicMonopoly");
    playerInfo.add(getDefaultProperties().getProperty(DEFAULT_PLAYER_ONE).split(COMMA));
    playerInfo.add(getDefaultProperties().getProperty(DEFAULT_PLAYER_TWO).split(COMMA));
    playerInfo.add(getDefaultProperties().getProperty(DEFAULT_PLAYER_THREE).split(COMMA));
    return playerInfo;
  }

  /**
   * @return the game board width specified by InformationForGamePlay
   */
  @Override
  public double getBoardWidth() {
    try {
      return Integer.parseInt(getInformationForGamePlay().getProperty(BOARD_WIDTH));
    } catch (NullPointerException | NumberFormatException e) {
      return Controller.DEFAULT_SIZE.width;
    }
  }

  /**
   * @return the game board height specified by InformationForGamePlay
   */
  @Override
  public double getBoardHeight() {
    try {
      return Integer.parseInt(getInformationForGamePlay().getProperty(BOARD_HEIGHT));
    } catch (NullPointerException | NumberFormatException e) {
      return Controller.DEFAULT_SIZE.height;
    }
  }

  /**
   * @param boardInfo is parsed file information containing one file line per String[]
   * @param cornerIndex is index of the corner spaces on the board
   * @return the height of the board as determined by the number of spaces between two corners
   */
  @Override
  public int parseHeightFromBoardInfo(List<String[]> boardInfo, int cornerIndex) {
    String goSpaceType = boardInfo.get(1)[cornerIndex]; // GO space, hardcoded in CSV
    int firstCorner = -1;
    for (int i = 2; i < boardInfo.size(); i++) {
      String neighborhoodType = boardInfo.get(i)[cornerIndex];
      if (neighborhoodType.equals(goSpaceType) && firstCorner == -1) {
        firstCorner = i;
      } else if (neighborhoodType.equals(goSpaceType)) {
        return (i - firstCorner + 1);
      }
    }
    return -1;
  }

  /**
   * @param boardInfo is parsed file information containing one file line per String[]
   * @param cornerIndex is index of the corner spaces on the board
   * @return the width of the board as determined by the number of spaces between two corners
   */
  public int parseWidthFromBoardInfo(List<String[]> boardInfo, int cornerIndex) {
    String goSpaceType = boardInfo.get(1)[cornerIndex];
    for (int i = 2; i < boardInfo.size(); i++) {
      String neighborhoodType = boardInfo.get(i)[cornerIndex];
      if (neighborhoodType.equals(goSpaceType)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * @return the game rotation type, if it exists.
   * Returns Optional.empty() otherwise
   */
  @Override
  public Optional<String> getGameRotationType() {
    String gameRotationType = super.getGameValues().getProperty(GAME_ROTATION_TYPE);
    return gameRotationType == null ? Optional.empty() : Optional.of(gameRotationType);
  }

  /**
   * @return the game start type, if it exists.
   * Returns Optional.empty() otherwise
   */
  @Override
  public Optional<String> getGameStartType() {
    String gameStartType = super.getGameValues().getProperty("gameStartType");
    return gameStartType == null ? Optional.empty() : Optional.of(gameStartType);
  }

  /*
   * Returns a new properties object from the given file path
   */
  private Properties getPropertiesFromFile(String propertiesFilePath) {
    Properties properties = new Properties();
    try {
      FileInputStream fis = new FileInputStream(propertiesFilePath);
      properties.load(fis);
      fis.close();
    } catch (IOException e) {
      throw new NecessaryFileNotFound("properties");
    }
    return properties;
  }

  /**
   * Read in neighborhoods and neighborhoodSizes properties from the current
   * theme's property file to determine the map of neighborhoods to number of
   * properties in the neighborhood for this board.
   * Map is later passed in as a parameter of OwnablePropertySpace.
   * @return an optional Map of type String, Integer mapping neighborhood colors
   * to the number of properties of that color
   */
  @Override
  public Optional<Map<String,Integer>> getNeighborhoodsMap() {
    Map<String,Integer> neighborhoodMap = new HashMap<>();
    try{
      String neighborhoodNames = getGameValues().getProperty(NEIGHBORHOODS);
      String[] neighborhoods = neighborhoodNames.split(COMMA);
      for(String neighborhood: neighborhoods){
        neighborhood.replaceAll(SPACE, EMPTY);
      }
      String neighborhoodValues = getGameValues().getProperty(NEIGHBORHOOD_SIZES);
      String[] neighborhoodValuesStringList = neighborhoodValues.split(COMMA);
      int[] neighborhoodValuesIntList = new int[neighborhoodValuesStringList.length];
      for (String s : neighborhoodValuesStringList) {
        int newVal = Integer.parseInt(s.replaceAll(SPACE, EMPTY));

      }
      for (int i = 0; i < neighborhoods.length; i++) {
        neighborhoodMap.putIfAbsent(neighborhoods[i], neighborhoodValuesIntList[i]);
      }
    }catch (NullPointerException e) {
        return Optional.empty();
    }
    return Optional.of(neighborhoodMap);
  }

  /**
   * @return InfoForDice which contains the necessary information for
   * dice rolling and management
   */
  @Override
  public InfoForDice getInformationForDice() {
    return new InfoForDice(super.getGameValues());
  }
}
