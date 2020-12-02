package ooga.view.popup;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class opens a pop up allowing a user to trade their properties with other players
 * or mortgage them to the bank
 *
 * @author Anna Diemel
 */
public class MakeTradePopUp extends PopUp {

  private static final String TRADE_TEXT = "Select a property to trade or mortgage";

  /**
   * Returns PopUpInformation with information for class
   *
   * @return PopUpInformation
   */
  public PopUpInformation getPopUpInformation() {
    return popUpInformation;
  }

  private PopUpInformation popUpInformation;

  @Override
  protected void displayText() {
    Text displayText = new Text(TRADE_TEXT);
    displayText.setFont(new Font(FONTSIZE));
    displayText.setY(TEXTY);
    displayText.setX(TEXTX);
    borderPane.getChildren().add(displayText);
    stage.setTitle("Trade Your Property");
  }

  @Override
  protected void displayButtons(int playerID, List<String> ownedProperties) {
    String[] tradeOptions = {"Trade this property", "Mortgage this property"};
    popUpInformation = new PopUpInformation();
    Pane buttons = makeTradeButtons(ownedProperties, tradeOptions);
    borderPane.getChildren().add(buttons);
  }

  private Pane makeTradeButtons(List<String> propertyNames, String[] tradeOptions) {
    ChoiceBox<String> tradeableProperties = getPropertyChoiceBox(propertyNames);
    ChoiceBox<String> propertyAction = makeActionChoice(tradeOptions);
    Button launchButton = makeLaunchButton();
    Button backButton = makeBackButton();

    tradeableProperties.setOnAction(event -> {
      propertyAction.setDisable(false); });
    propertyAction.setOnAction(event -> {
      launchButton.setDisable(false); });
    launchButton.setOnAction(event -> {
      popUpInformation.setSelectedProperty(tradeableProperties.getValue());
      popUpInformation.setTradeType(propertyAction.getValue());
      closePopUp();
     });
    VBox buttonColumn = new VBox(tradeableProperties, propertyAction, launchButton, backButton);
    positionButtonColumn(buttonColumn);
    return new Pane(buttonColumn);
  }

  private ChoiceBox<String> getPropertyChoiceBox(List<String> propertyNames) {
    List<String> nonEmptyNames = new ArrayList<>();
    for (String name : propertyNames) {
      if (! name.isBlank()) {
        nonEmptyNames.add(name);
      }
    }
    ChoiceBox<String> tradeableProperties = new ChoiceBox(FXCollections.observableArrayList(nonEmptyNames));
    tradeableProperties.setTooltip(new Tooltip("Select a property to trade"));
    return tradeableProperties;
  }

  private Button makeBackButton() {
    Button button = new Button("Back");
    button.setOnAction(event -> {
      closePopUp();
    });
    button.setTooltip(new Tooltip("Go back to the board"));
    return button;
  }

  private ChoiceBox<String> makeActionChoice(String[] tradeOptions) {
    ChoiceBox<String> propertyAction = new ChoiceBox(FXCollections.observableArrayList(tradeOptions));
    propertyAction.setDisable(true);
    propertyAction.setTooltip(new Tooltip("Choose trade type"));
    return propertyAction;
  }

  private Button makeLaunchButton() {
    Button launchButton = new Button("Next");
    launchButton.setDisable(true);
    launchButton.setTooltip(new Tooltip("Proceed with trade"));
    return launchButton;
  }

  private void positionButtonColumn(VBox buttonColumn) {
    buttonColumn.setLayoutX(borderPane.getWidth()/4);
    buttonColumn.setLayoutY(borderPane.getHeight()/3);
    buttonColumn.setSpacing(15);
  }
}
