package ooga.view.buttons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import ooga.view.ButtonsMaintainer;
import ooga.view.gameView.ErrorPrintable;
import ooga.controller.Controller;

/**
 * Creates dropdown for choosing player pieces
 *
 * @author Grace Llewellyn
 */
public class PlayerPieceDropDown extends BoardChoiceBox {

  private static final String PROPERTY_KEY = "playerPieces";

  private final AccessOtherButtons accessOtherButtons;

  public PlayerPieceDropDown(ResourceBundle languagesResources, String id, AccessOtherButtons accessOtherButtons,
      ErrorPrintable errorPrintable, Properties gameValueResources, Optional<Properties> defaultProperties) {
    super(languagesResources, new ChoiceBox<>(), id, errorPrintable, gameValueResources, defaultProperties);
    this.accessOtherButtons = accessOtherButtons;
    initializeButton();
  }

  /**
   * initializes dropdown for player pieces
   */
  @Override
  protected void initializeButton(){
    super.initializeButton();
    super.initializeButtonWithResourceString(PROPERTY_KEY);
    //super.getCurrButton().setConverter(makeConverter());
    //super.getCurrButton().setOnAction(event -> accessOtherButtons.checkEnableLaunchGameButton());
  }

  /**
   * @return converted string
   */
  protected StringConverter<String> makeConverter() {
    StringConverter<String> converter = new StringConverter<String>() {
      @Override
      public String toString(String filepath) {
        return getChoiceBoxValueFromFilePath(filepath);
      }
      @Override
      public String fromString(String string) { return ""; }
    };
    return converter;
  }

  private String getChoiceBoxValueFromFilePath(String filepath) {
    String[] fileDir = filepath.split("/");
    String fileName = fileDir[fileDir.length-1];
    String playerView = fileName.substring(0, fileName.indexOf('.'));
    if (playerView.length() == 0) return fileName;
    return playerView.substring(0,1).toUpperCase() + playerView.substring(1);
  }

  /**
   * Returns a list of default game pieces (normally colors)
   */
  public List<String> getDefaultGamePieces() {
    ResourceBundle resources = ResourceBundle.getBundle(Controller.DEFAULT_RESOURCES_PACKAGE +
        ButtonsMaintainer.DEFAULT_STARTING_VAL_FOLDER + "/" +
        ButtonsMaintainer.DEFAULT_STARTING_VAL_FOLDER);
    List<String> defaultPieces = new ArrayList<>();
    Collections.addAll(defaultPieces, resources.getString(PROPERTY_KEY).split(","));
    return defaultPieces;
  }

  /*
   * Needs to remain in english because uses referencing
   */
  @Override
  public void updateLanguageOnFeature() {
    // would create mapping to english ideally if time
  }

}
