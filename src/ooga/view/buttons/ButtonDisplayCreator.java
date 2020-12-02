package ooga.view.buttons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.view.gameView.DisplayInitializer;

/**
 * Creates the button display
 *
 * @author Grace Llewellyn
 */
public class ButtonDisplayCreator {

  private final Optional<Properties> defaultProperties;
  private final SplashScreenButtonsFactory splashScreenButtonsFactory = new SplashScreenButtonsFactory();
  private final GamePlayButtonsFactory gamePlayButtonsFactory = new GamePlayButtonsFactory();
  private boolean fileUploaded = false;

  public ButtonDisplayCreator(Optional<Properties> defaultProperties) {
    this.defaultProperties = defaultProperties;
  }

  /**
   * Creates all needed buttons
   * @param languageResources information related to language choice by user
   * @param interfacesForButtons object with ann needed interfaces for the buttons
   * @param gameValueResources the resources for significant game values
   * @return Map of all interactive features for the board and their IDs
   */
  public Map<String, BoardInteractiveFeature> createAllButtons(ResourceBundle languageResources,
      InterfacesForButtons interfacesForButtons, Properties gameValueResources) {
    Map<String, BoardInteractiveFeature> buttons = new HashMap<>();
    splashScreenButtonsFactory.createButtons(buttons, languageResources, interfacesForButtons,
        gameValueResources, defaultProperties);
    gamePlayButtonsFactory.createButtons(buttons, languageResources, interfacesForButtons,
        gameValueResources, defaultProperties);
    return buttons;
  }

  /**
   * Uploads the rollable dice for the view to be displayed at the top of the screen
   * @param buttons for the screen
   * @param interfacesForButtons all interfaces needed for button interactions
   * @param languageResources information related to language choice by user
   */
  public void uploadModelDiceRollable(Map<String, BoardInteractiveFeature> buttons,
      InterfacesForButtons interfacesForButtons, ResourceBundle languageResources) {
    gamePlayButtonsFactory.uploadModelDiceRollable(buttons, interfacesForButtons, languageResources);
  }

  /**
   * Make the initial input panel which lets the user select the language and set the buttons in
   * the middle of the screen
   */
  public Node makeInitialInputPanel(Map<String, BoardInteractiveFeature> buttons,
      InterfacesForButtons interfacesForButtons, ResourceBundle languageResources,
      PlayerInputLines playerInputLines, Properties gameValueResources) {
    VBox startingButtons = new VBox();
    HBox firstRow = new HBox();
    firstRow.getChildren().addAll(getSplashScreenInteractiveFeatures(buttons));
    if (buttons.containsKey("LaunchGameButton")) {
      buttons.get("LaunchGameButton").getCurrInteractiveFeature().setDisable(true);
    }
    List<HBox> rowsOfPlayers = createPlayerDropDowns(languageResources, playerInputLines,
        interfacesForButtons, gameValueResources);
    startingButtons.getChildren().add(firstRow);
    startingButtons.getChildren().addAll(rowsOfPlayers);
    return startingButtons;
  }

  /*
   * Returns a list of the player drop down nodes after adding the player nodes to the map and
   * deleting all the player nodes that were previously there
   */
  private List<HBox> createPlayerDropDowns(ResourceBundle resources, PlayerInputLines playerInputLines,
      InterfacesForButtons interfacesForButtons, Properties gameValueResources) {
    List<HBox> rowsOfPlayers = new ArrayList<>();
    playerInputLines.clearPlayerInfo();
    for (int i=0; i<interfacesForButtons.getNumberPlayersChangable().getNumberOfPlayers(); i++) {
      HBox newRow = new HBox();
      PlayerTypeDropDown currentPlayerTypeDropDown = new PlayerTypeDropDown(resources,
          "PlayerTypeDropDown"+i, interfacesForButtons.getAccessOtherButtons(),
          interfacesForButtons.getErrorPrintable(), gameValueResources,
          defaultProperties);
      PlayerPieceDropDown currentPlayerPieceDropDown = new PlayerPieceDropDown(resources,
          "PlayerPieceDropDown"+i, interfacesForButtons.getAccessOtherButtons(),
          interfacesForButtons.getErrorPrintable(), gameValueResources,
          defaultProperties);
      /* Stop creating if cannot find the text to put on the drop downs */
      if (currentPlayerTypeDropDown.propertyKeyNotFound() ||
          currentPlayerPieceDropDown.propertyKeyNotFound()) {
        break;
      }
      addPlayerInfoGraphic(rowsOfPlayers, i, newRow, currentPlayerTypeDropDown,
          currentPlayerPieceDropDown, playerInputLines);
    }
    return rowsOfPlayers;
  }

  /*
   * Adds the player information fields to the necessary locations to be displayed
   */
  private void addPlayerInfoGraphic(List<HBox> rowsOfPlayers, int i, HBox newRow,
      PlayerTypeDropDown currentPlayerTypeDropDown,
      PlayerPieceDropDown currentPlayerPieceDropDown, PlayerInputLines playerInputLines) {
    TextField playerName = new TextField();
    newRow.getChildren().add(playerName);
    newRow.getChildren().add(currentPlayerTypeDropDown.getCurrInteractiveFeature());
    newRow.getChildren().add(currentPlayerPieceDropDown.getCurrInteractiveFeature());
    playerInputLines.addPlayer(currentPlayerTypeDropDown, currentPlayerPieceDropDown, playerName,
        ("PlayerName"+ i));
    rowsOfPlayers.add(newRow);
  }

  /*
   * Returns a list of all the interactive features currently in the buttons map
   */
  private List<Node> getSplashScreenInteractiveFeatures(Map<String, BoardInteractiveFeature> buttons) {
    List<Node> featuresToReturn = new ArrayList<>();
    for (String buttonName: splashScreenButtonsFactory.getCreatedButtons()) {
      featuresToReturn.add(buttons.get(buttonName).getCurrInteractiveFeature());
    }
    return featuresToReturn;
  }

  /**
   * Adds all the buttons to the input panel in the top row of monopoly
   *
   * @return  - Node that is our Panel where buttons are contained
   */
  public Node addButtonsToInputPanel(List<String> buttonNames, Map<String, BoardInteractiveFeature>
      buttons) {
    VBox buttonsInputPanel = new VBox();
    buttonsInputPanel.getChildren().add(makeNavigationPanel(buttonNames, buttons));
    return buttonsInputPanel;
  }

  /**
   * Makes the navigation panel of buttons for the top of the screen. Either returns everything
   * for the first row or for the bottom row
   * @return the node that includes the panel
   */
  protected Node makeNavigationPanel(List<String> buttonsForRow, Map<String, BoardInteractiveFeature>
      buttons) {
    HBox nodeForRow = new HBox();
    for (String buttonName: buttonsForRow) {
      nodeForRow.getChildren().add(buttons.get(buttonName).getCurrInteractiveFeature());
    }
    return nodeForRow;
  }

  /**
   * Creates the buttons for the top navigation panel and adds them and disables the correct ones
   * for a start of game setting
   * @return the buttons for the top navigation panel
   */
  public Node makeInputPanel(boolean startingRolls, Map<String, BoardInteractiveFeature> buttons) {
    List<String> buttonsCreated = gamePlayButtonsFactory.getCreatedButtons(startingRolls);
    return addButtonsToInputPanel(buttonsCreated, buttons);
  }

  /**
   * Uploads the initializer of the display
   * @param buttons for the game
   * @param interfacesForButtons includes all interfaces
   * @param resourceBundle button resources
   * @param displayInitializer initializer for the display
   * @param infoForGamePlay information needed to start the game
   */
  public void uploadDisplayInitializer(
      Map<String, BoardInteractiveFeature> buttons,
      InterfacesForButtons interfacesForButtons,
      ResourceBundle resourceBundle,
      DisplayInitializer displayInitializer,
      InfoForGamePlay infoForGamePlay) {
    gamePlayButtonsFactory.uploadDisplayInitializer(buttons, interfacesForButtons, resourceBundle, displayInitializer, infoForGamePlay);
  }
}
