package ooga.view.buttons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.TextField;
import ooga.model.exceptions.PropertiesFileException;
import ooga.view.gameView.ErrorPrintable;

/**
 * creates player input lines that are present at the beginning of the game for the user to write
 * information about themselves like their name and type
 *
 * @author Grace Llewellyn
 */
public class PlayerInputLines {

  public static final int INDEX_PLAYER_PIECE = 1;
  public static final int INDEX_PLAYER_TYPE = 0;
  public static final String COLOR_IDENTIFIER = "COLOR:";

  private final List<PlayerTypeDropDown> playerTypeDropDowns;
  private final List<PlayerPieceDropDown> playerPieceDropDowns;
  private final List<TextField> playerNames;
  private final ErrorPrintable errorPrintable;

  public PlayerInputLines(ErrorPrintable errorPrintable) {
    this.errorPrintable = errorPrintable;
    playerTypeDropDowns = new ArrayList<>();
    playerPieceDropDowns = new ArrayList<>();
    playerNames = new ArrayList<>();
  }

  /**
   * Adds player to the game
   * @param playerTypeDropDown Dropdown of the player type
   * @param playerPieceDropDown Dropdown of the player piece
   * @param playerNameTextField Text field with the user name
   * @param idForText id of the TextField to set
   */
  public void addPlayer(PlayerTypeDropDown playerTypeDropDown, PlayerPieceDropDown
      playerPieceDropDown, TextField playerNameTextField, String idForText) {
    playerTypeDropDowns.add(playerTypeDropDown);
    playerPieceDropDowns.add(playerPieceDropDown);
    playerNames.add(playerNameTextField);
    playerNameTextField.setId(idForText);
  }

  /**
   * Clears player information
   */
  public void clearPlayerInfo() {
    playerTypeDropDowns.clear();
    playerPieceDropDowns.clear();
    playerNames.clear();
  }

  /**
   * Extracts the player information from fields. If player piece is a color, extract it with an
   * identifier and if player piece is null then gets a player piece from the possible player pieces
   */
  public List<String> getPlayerInfo(int i) {
    String playerPiece = playerPieceDropDowns.get(i).getValue();
    if (playerPiece==null) {
      playerPiece = getPlayerPieceIfNotChosen(i);
      playerPieceDropDowns.get(i).getCurrButton().setValue(playerPiece);
    }

    if (playerPieceDropDowns.get(i).getDefaultGamePieces().contains(playerPiece)) {
      playerPiece=COLOR_IDENTIFIER+playerPiece.toLowerCase();
    }
    return  List.of(playerTypeDropDowns.get(i).getValue(), playerPiece.replaceAll(" ",
        ""));
  }

  private String getPlayerPieceIfNotChosen(int i) {
    List<String> pieces = playerPieceDropDowns.get(i).getCurrButton().getItems();
    for (String piece: pieces) {
      if (!checkPieceInUse(piece, i)) {
        return piece;
      }
    }
    throw new PropertiesFileException("notEnoughPieces");
  }

  /*
   * Returns true if the piece is in use and false otherwise
   */
  private boolean checkPieceInUse(String piece, int index) {
    for (int i=0; i<playerPieceDropDowns.size(); i++) {
      if (i!=index && playerPieceDropDowns.get(i).getValue()!=null &&
          piece.equals(playerPieceDropDowns.get(i).getValue())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Extracts the player information when the launch game button is clicked and sends it to model
   * to create the players
   */
  public Map<String, List<String>> extractPlayerInformation() {
    Map<String, List<String>> playerInfo = new HashMap<>();
    int count=1;
    for (int i=0; i<playerTypeDropDowns.size(); i++) {
      String playerName = playerNames.get(i).getText();
      playerInfo.put(playerName.equals("") ? ("p"+count):playerName, getPlayerInfo(i));
      count++;
    }
    return playerInfo;
  }

  /**
   * Prints an exception if all the player pieces are not unique and returns false so that the user
   * cannot move on
   */
  public boolean checkAllPiecesUnique() {
    List<String> playerPiecesAlreadyPulled = new ArrayList<>();
    for (PlayerPieceDropDown playerPieceDropDown : playerPieceDropDowns) {
      String playerPiece = playerPieceDropDown.getValue();
      if (playerPiece != null && playerPiecesAlreadyPulled.contains(playerPiece)) {
        errorPrintable.printErrorMessageAlert("playerUniqueGamePiece");
        return false;
      }
      playerPiecesAlreadyPulled.add(playerPiece);
    }
    return true;
  }

  /**
   * Returns true if all the names that are entered are unique and false otherwise. Does not require
   *  the user to have entered all names
   */
  public boolean checkAllNamesEnteredAreUnique() {
    Map<String, String> playerInfo = new HashMap<>();
    for (int i=0; i<playerNames.size(); i++) {
      String playerName = playerNames.get(i).getText();
      if (!playerName.equals("") && playerInfo.put(playerName,
          playerTypeDropDowns.get(i).getValue()) != null) {
        errorPrintable.printErrorMessageAlert("playerUniqueName");
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if should enable the launch game button which means that
   */
  public boolean checkEnableLaunchGameButton(){
    for (PlayerTypeDropDown playerTypeDropDown : playerTypeDropDowns) {
      if (playerTypeDropDown.getValue() == null) {
        return true;
      }
    }
    return false;
  }

}
