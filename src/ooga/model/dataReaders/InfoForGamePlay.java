package ooga.model.dataReaders;

import java.util.Optional;
import java.util.Properties;
import javafx.scene.paint.Color;
import ooga.model.exceptions.PropertiesFileException;

public class InfoForGamePlay {

  public static final String NEIGHBORHOOD = "neighborhood";
  private int numDice;
  private int diceMinNumber;
  private int diceMaxNumber;
  private int amountPassingGo;
  private int startingFunds;
  private String dataDirectory;
  private String boardDirectory;
  private String endType;
  private Optional<Color> diceColor = Optional.empty();
  private Optional<String> diceType = Optional.empty();
  private Optional<String> cardsType = Optional.empty();
  private Optional<String> bankType = Optional.empty();
  private Optional<String> boardType = Optional.empty();
  private Optional<String> playersType = Optional.empty();
  private Optional<String> gameRotationType = Optional.empty();
  private boolean setNumDice;
  private boolean setDiceMinNumber;
  private boolean setDiceMaxNumber;
  private boolean setPassingGoAmount;
  private Properties properties;
  private String folderPath;

  //We recognize that this should be refactored to use optional

  public InfoForGamePlay(Properties properties, String folderPath) {
    this.properties = properties;
    this.folderPath = folderPath;
    if (properties.getProperty("dataDirectory")!=null) {
      setDataDirectory(properties.getProperty("dataDirectory"));
    }
    initialize();
  }

  private void initialize() {
    String currentGoAmountProperty = properties.getProperty("amountPassingGo");
    if (currentGoAmountProperty != null) {
      setAmountPassingGo(checkPropertyNumberAndPositive(currentGoAmountProperty, "amountPassingGo"));
    }
    String currentEndType = properties.getProperty("endType");
    if (currentEndType != null){
      setEndType(currentEndType);
    } else {
      setEndType("default");
    }
    String currentBoardPath = properties.getProperty("boardPath");
    if(currentBoardPath != null){
      setBoardDirectory("data" + dataDirectory + currentBoardPath);
    }
    String currentStartingFunds = properties.getProperty("startingFunds");
    if(currentStartingFunds != null){
      setStartingFunds(checkPropertyNumberAndPositive(currentStartingFunds, "startingFunds"));
    }
    initializerHelper();

    boardPropertiesInitializer();
  }

  private void boardPropertiesInitializer() {
    if (properties.getProperty("diceType")!=null) {
      diceType = Optional.of(properties.getProperty("diceType"));
    }
    if (properties.getProperty("boardType")!=null) {
      boardType = Optional.of(properties.getProperty("boardType"));
    }
    if (properties.getProperty("bankType")!=null) {
      bankType = Optional.of(properties.getProperty("bankType"));
    }
    if (properties.getProperty("cardsType")!=null) {
      cardsType = Optional.of(properties.getProperty("cardsType"));
    }
    if (properties.getProperty("playersType")!=null) {
      playersType = Optional.of(properties.getProperty("playersType"));
    }
    if (properties.getProperty("gameRotationType")!=null) {
      gameRotationType = Optional.of(properties.getProperty("gameRotationType"));
    }
  }

  private void initializerHelper() {
    String currentDiceProperty = properties.getProperty("numDice");
    if (currentDiceProperty != null) {
      setNumDice(checkPropertyNumberAndPositive(currentDiceProperty, "numDice"));
    }
    currentDiceProperty = properties.getProperty("diceMinNumber");
    if (currentDiceProperty != null) {
      setDiceMinNumber(checkPropertyNumberAndPositive(currentDiceProperty, "diceMinNumber"));
    }
    currentDiceProperty = properties.getProperty("diceMaxNumber");
    if (currentDiceProperty != null) {
      setDiceMaxNumber(checkPropertyNumberAndPositive(currentDiceProperty, "diceMaxNumber"));
    }
    currentDiceProperty = properties.getProperty("diceColor");
    if (currentDiceProperty != null) {
      setDiceColor(checkPropertyValidColor(currentDiceProperty));
    }
  }

  /*
   * Creates an integer out of the given property and throws an exception if the property is not a
   * number or is negative
   */
  private int checkPropertyNumberAndPositive(String property, String propertyName) throws PropertiesFileException{
    int propertyToReturn = Integer.parseInt(property);
    if (propertyToReturn < 0) {
      throw new PropertiesFileException(propertyName);
    }
    return propertyToReturn;
  }

  private Color checkPropertyValidColor(String property) {
    try {
      return Color.web(property);
    } catch (IllegalArgumentException e) {
      throw new PropertiesFileException("diceColor");
    }
  }

  /*
   * protected setters because sole reason for existence is transferring information about the dice
   */
  protected void setNumDice(int numDice) {
    this.numDice = numDice;
    setNumDice = true;
  }

  /**
   * @return the number of dice involved in gameplay
   */
  public int getNumDice() {
    return numDice;
  }

  /**
   * @return the minimum number of dice required for gameplay
   */
  public int getDiceMinNumber() {
    return diceMinNumber;
  }

  protected void setDiceMinNumber(int diceMinNumber) {
    this.diceMinNumber = diceMinNumber;
    setDiceMinNumber = true;
  }

  /**
   * @return the maximum number of dice allowed for gameplay
   */
  public int getDiceMaxNumber() {
    return diceMaxNumber;
  }

  protected void setDiceMaxNumber(int diceMaxNumber) {
    this.diceMaxNumber = diceMaxNumber;
    setDiceMaxNumber = true;
  }

  protected void setDiceColor(Color color) {
    diceColor = Optional.of(color);
  }

  private void setEndType(String endType){
    this.endType = endType;
  }

  /**
   * @return the end type of the game
   */
  public String getEndType(){
    return endType;
  }

  /**
   * @return whether or not the user has specified the number of dice for gameplay
   */
  public boolean userGaveNumDice() {
    return setNumDice;
  }

  /**
   * @return whether or not the user has specified the minimum number of dice for gameplay
   */
  public boolean userGaveDiceMinNumber() {
    return setDiceMinNumber;
  }

  /**
   * @return whether or not the user has specified the maximum number of dice for gameplay
   */
  public boolean userGaveDiceMaxNumber() {
    return setDiceMaxNumber;
  }

  /**
   * @return whether or not the user has specified the amount collected passing Go
   */
  public boolean userGavePassingGoAmount() {
    return setPassingGoAmount;
  }

  private void setStartingFunds(int funds){
    startingFunds = funds;
  }

  /**
   * @return the amount allocated to players at the beginning of gameplay
   */
  public int getStartingFunds(){
    return startingFunds;
  }

  /**
   * @return the amount collected by a player when passing Go
   */
  public int getAmountPassingGo() {
    return amountPassingGo;
  }

  protected void setAmountPassingGo(int amountPassingGo) {
    this.amountPassingGo = amountPassingGo;
    setPassingGoAmount = true;
  }

  /**
   * @return the data directoy filepath from which information for game play is pulled
   */
  public String getDataDirectory(){
    return dataDirectory;
  }

  protected void setDataDirectory(String directory){
    dataDirectory = directory;
  }

  /**
   * @return the filepath for the money image
   */
  public String getMoneyImagePath() { return properties.getProperty("moneyImagePath"); }

  /**
   * @return the directory containing player piece images
   */
  public String getPlayerPiecePath() { return properties.getProperty("pieceImagePath"); }

  /**
   * @return the text heading each CSV, e.g. "Type,Value,Neighborhood"
   */
  public String getCSVHeader() { return properties.getProperty("csvHeader"); }

  /**
   * @return the specified dice color, if it exists
   */
  public Optional<Color> getDiceColor() {
    return diceColor;
  }

  private void setBoardDirectory(String path) {
    boardDirectory = path;
  }

  /**
   * @return the directory for the board and displayBoard
   */
  public String getBoardDirectory(){
    return boardDirectory;
  }

  /**
   * @return the filepath for community card images, if the cards exist
   */
  public String getCommunityCardPath() {
    if(!properties.containsKey("communityCardPath")) return null;
    return properties.getProperty("communityCardPath");
  }

  /**
   * @return the filepath for chance card images, if the cards exist
   */
  public String getChanceCardPath() {
    if(!properties.containsKey("chanceCardPath")) return null;
    return properties.getProperty("chanceCardPath");
  }

  /**
   * @return the filepath for the chance deck image, if the deck exists
   */
  public String getChanceDeckImagePath() {
    if(!properties.containsKey("chanceDeckImagePath")) return null;
    return properties.getProperty("chanceDeckImagePath");
  }
  /**
   * @return the filepath for the community chest deck image, if the deck exists
   */
  public String getCommunityDeckImagePath() {
    if(!properties.containsKey("communityDeckImagePath")) return null;
    return properties.getProperty("communityDeckImagePath");
  }

  /**
   * Query the game value resources about a given property.
   * @param key is the property being inquired about
   * @return the property value
   */
  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  /**
   * @return the index in the board csv corresponding to neighborhood
   * @param boardHeader is the CSV header and column descriptions
   */
  public int getNeighborhoodIndex(String[] boardHeader) {
    for (int i = 0; i < boardHeader.length; i++) {
      if (boardHeader[i].equals(NEIGHBORHOOD)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * @return the folder path for information for game play
   */
  public String getFolderPath() {
    return folderPath;
  }

  /**
   * @return the dice type, if the String exists
   */
  public Optional<String> getDiceType() {
    return diceType;
  }

  /**
   * @return the cards type, if the String exists
   */
  public Optional<String> getCardsType() {
    return cardsType;
  }

  /**
   * @return the bank type, if the String exists
   */
  public Optional<String> getBankType() {
    return bankType;
  }

  /**
   * @return the board type, if the String exists
   */
  public Optional<String> getBoardType() {
    return boardType;
  }

  /**
   * @return the players type, if the String exists
   */
  public Optional<String> getPlayersType() {
    return playersType;
  }

  /**
   * @return the game rotation type, if the String exists
   */
  public Optional<String> getGameRotationType() {
    return gameRotationType;
  }
}
