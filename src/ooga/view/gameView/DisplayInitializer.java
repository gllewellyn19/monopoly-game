package ooga.view.gameView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ooga.controller.Controller;
import ooga.model.DataReader;
import ooga.model.dataReaders.InfoForDice;
import ooga.model.dataReaders.InfoForGameViewClassInstantiations;
import ooga.model.exceptions.CardDataNotFound;
import ooga.view.ButtonsMaintainer;
import ooga.view.DisplayBoard;
import ooga.view.DisplayCards;
import ooga.view.DisplayDice;
import ooga.view.DisplayMoney;
import ooga.view.board.ClassicDisplayBoard;
import ooga.view.board.DisplayBoardFactory;
import ooga.view.cards.DisplayCardsFactory;
import ooga.view.dice.DisplayDiceFactory;
import ooga.view.money.DisplayMoneyFactory;
import ooga.view.players.DisplayPlayer;
import ooga.view.players.DisplayPlayerFactory;

public class DisplayInitializer {

  private DisplayBoard displayBoard;
  private DisplayCards displayCards;
  private DisplayMoney displayMoney;
  private DisplayDice displayDice;
  private Map<Integer, DisplayPlayer> players;

  public DisplayInitializer(ErrorPrintable errorPrintable) {
    //temporary until input is given by the user
    displayDice = new DisplayDiceFactory().createDisplayDice(Optional.empty(), errorPrintable);
    displayMoney = new DisplayMoneyFactory().createDisplayMoney(Optional.empty(), errorPrintable);
    displayBoard = new DisplayBoardFactory().createDisplayBoard(Optional.empty(), errorPrintable);
    displayCards = new DisplayCardsFactory().createDisplayCards(Optional.empty(), errorPrintable);
    players = new HashMap<>();
  }

  /**
   * initializes everything in the board view (money, board, cards, etc)
   */
  public void initializeBoardView(
      InfoForGameViewClassInstantiations infoForGameViewClassInstantiations,
      Map<String, Integer> playerNamesAndIds,
      Map<String, String> playerNamesAndGamePieceFiles, DataReader dataReader,
      String boardData, int startingFunds, ErrorPrintable errorPrintable, InfoForDice infoForDice) {
    try {
      displayBoard = new DisplayBoardFactory()
          .createDisplayBoard(infoForGameViewClassInstantiations.
              getDisplayBoardType(), errorPrintable);
      displayCards = new DisplayCardsFactory()
          .createDisplayCards(infoForGameViewClassInstantiations.
              getDisplayCardsType(), errorPrintable);
      displayMoney = new DisplayMoneyFactory()
          .createDisplayMoney(infoForGameViewClassInstantiations.
              getDisplayMoneyType(), errorPrintable);
      displayDice = new DisplayDiceFactory().createDisplayDice(infoForGameViewClassInstantiations.
          getDisplayDiceType(), errorPrintable);
      initializePlayers(playerNamesAndIds, playerNamesAndGamePieceFiles);
      if (!hasInitializedBoard()) {
        displayBoard.initializeBoard(errorPrintable, boardData, dataReader);
      }
      addPlayersToBoard();
      displayMoney.initializeMoney(players, startingFunds, dataReader);
      displayDice.initializeDice(infoForDice.getNumberOfDice(),
          infoForDice.getDiceColor());
      displayCardDecks(dataReader);
    } catch (CardDataNotFound e) {
      errorPrintable.printErrorMessageAlert(e.getMessage());
    }
  }

  private void initializePlayers(Map<String, Integer> playerNamesAndIds,
      Map<String, String> playerNamesAndGamePieceFiles) {
    DisplayPlayerFactory playerFactory = new DisplayPlayerFactory();
    players = playerFactory.createPlayerPieces(playerNamesAndIds, playerNamesAndGamePieceFiles);
  }

  /**
   * @return whether or not the displayBoard variable has been initialized
   */
  public boolean hasInitializedBoard() {
    return displayBoard.getBoardLayout().getChildren().size() != 0;
  }

  /**
   * Adds the players to the board along with their pieces and their names
   */
  protected void addPlayersToBoard() {
    for (Map.Entry<Integer, DisplayPlayer> entry : players.entrySet()) {
      addPlayerToNthPane(entry.getValue(), entry.getValue().getLocation());
    }
  }

  protected void addPlayerToNthPane(DisplayPlayer player, int n) {
    player.setLocation(n);
    Pane temp = displayBoard.getNthPane(n);
    temp.getChildren().add(player.getGamePiece());
  }

  /**
   * Displays card decks in the middle of the board
   */
  protected void displayCardDecks(DataReader dataReader) {
    displayCards.initializeCards(dataReader);
    Optional<Rectangle> chanceDeck = displayCards.getChanceDeckImage(dataReader);
    chanceDeck.ifPresent(rectangle -> {
      displayBoard.getBoardLayout().getChildren().add(rectangle);
    });
    Optional<Rectangle> communityChestDeck = displayCards.getCommunityChestDeckImage(dataReader);
    communityChestDeck.ifPresent(rectangle -> {
      displayBoard.getBoardLayout().getChildren().add(communityChestDeck.get());
    });
  }

  protected Optional<Scene> makeASceneWithCSS(String cssFile, Text topText, ButtonsMaintainer
      buttonsMaintainer, ErrorPrinting errorPrinting, String boardData, DataReader dataReader) {
    try {
      int width = Controller.DEFAULT_SIZE.width;
      int height = Controller.DEFAULT_SIZE.height;
      BorderPane displayLayout = createButtonsTop(topText, buttonsMaintainer, width,
          height);
      displayLayout.setCenter(displayBoard.getBoardLayout());
      displayLayout.setLeft(displayMoney.getLeftPane());
      displayLayout.setRight(displayMoney.getRightPane());
      return Optional.of(uploadCSSFile(width, height, cssFile, displayLayout, errorPrinting));
    } catch (MissingResourceException e) {
      errorPrinting.printErrorMessageAlert(e.getMessage());
    }
    return Optional.empty();
  }

  /*
   * Creates the buttons at the top of the display
   */
  private BorderPane createButtonsTop(Text topText, ButtonsMaintainer buttonsMaintainer, int width,
      int height) {
    BorderPane displayLayout = new BorderPane();
    displayLayout.setPrefSize(width, height);
    HBox firstRow = new HBox();
    HBox secondRow = new HBox();
    firstRow.getChildren().add(topText);
    displayDice.addDiceToHBox(firstRow);
    firstRow.setAlignment(Pos.CENTER);
    secondRow.getChildren().add(buttonsMaintainer.makeInputPanel());
    secondRow.setAlignment(Pos.CENTER);
    displayLayout.setTop(firstRow);
    displayLayout.setBottom(secondRow);
    return displayLayout;
  }

  /**
   * @param width  - width of new scene
   * @param height -  height of new scene
   * @return - starting scene with the default css that appears in the center of the screen
   */
  public Optional<Scene> makeAnInitialScene(int width, int height, ErrorPrinting errorPrinting,
      Text topText, Properties gameValueResources, ResourceBundle languageResources,
      ButtonsMaintainer buttonsMaintainer) {
    try {
      String topTextContent = languageResources.getString("startingMessage") + " ";
      topTextContent += (gameValueResources != null)?gameValueResources.getProperty("gameName"):
          languageResources.getString("startingGame");
      topText.setText(topTextContent);
      BorderPane.setAlignment(topText, Pos.TOP_CENTER);
      BorderPane displayLayout = new BorderPane(getInitialCenterPane(buttonsMaintainer, gameValueResources), topText,
          null, null, null);
      return Optional.of(uploadCSSFile(width, height, ClassicGameView.STARTING_STYLESHEET,
          displayLayout, errorPrinting));
    } catch (MissingResourceException | IOException e) {
      errorPrinting.printErrorMessageAlert(e.getMessage());
    }
    return Optional.empty();
  }

  private VBox getInitialCenterPane(ButtonsMaintainer buttonsMaintainer, Properties properties) throws IOException {
    VBox initialInputBoxes = new VBox();
    initialInputBoxes.getChildren().add(buttonsMaintainer.makeInitialInputPanel());
    if (properties != null) {
      Text startHelpText = new Text();
      String startText = properties.getProperty("startHelpText");
      startHelpText.setText(startText);
      initialInputBoxes.getChildren().add(startHelpText);
    }
    initialInputBoxes.setAlignment(Pos.TOP_CENTER);
    return initialInputBoxes;
  }

  /**
   * @param width   - width of returned scene
   * @param height  - height of returned scene
   * @param cssFile - string with name of wanted css file
   * @return - scene with a new style sheet for the ooga.view
   */
  public Scene uploadCSSFile(int width, int height, String cssFile, BorderPane displayLayout,
      ErrorPrinting errorPrinting) {
    Scene scene = new Scene(displayLayout, width, height);
    try {
      scene.getStylesheets()
          .add(getClass().getResource(Controller.DEFAULT_RESOURCE_FOLDER +
              ClassicGameView.STYLESHEETS_FOLDER + cssFile).toExternalForm());
    } catch (NullPointerException e) {
      errorPrinting.printErrorMessageAlert("CSSNotFound", cssFile);
    }
    return scene;
  }

  /**
   *
   * @return DisplayBoard board visualization maintained by the class
   */
  public DisplayBoard getDisplayBoard() {
    return displayBoard;
  }

  protected DisplayMoney getDisplayMoney() {
    return displayMoney;
  }

  protected DisplayDice getDisplayDice() {
    return displayDice;
  }

  protected DisplayCards getDisplayCards() {
    return displayCards;
  }

  protected Map<Integer, DisplayPlayer> getPlayers() {
    return players;
  }

  protected void createNewDisplayBoard() {
    displayBoard = new ClassicDisplayBoard();
  }
}
