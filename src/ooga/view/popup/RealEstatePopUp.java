package ooga.view.popup;

import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.model.PropertyUpgrade;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.view.gameView.GetInformationForPrompter;

/**
 * Opens pop up that allows user to purchase houses and hotels for their owned properties
 *
 * @author Anna Diemel
 */
public class RealEstatePopUp extends PopUp {

  public static final String BUY_TEXT = "";
  private PropertyUpgrade propertyUpgrade;
  private GetInformationForPrompter resources;
  private Button doneButton;

  /**
   * Returns PopUpInformation with necessary information for this class
   * @return PopUpInformation
   */
  public PopUpInformation getInformationFromPopUp() {
    return informationFromPopUp;
  }

  private PopUpInformation informationFromPopUp;

  /**
   * Returns whether the player is done purchasing real estate or not
   * @return boolean to indicate that the player is done with purchases
   */
  public boolean isComplete() {
    return isComplete;
  }

  private boolean isComplete;

  @Override
  protected void displayText() {
    Text displayText = new Text(BUY_TEXT);
    displayText.setFont(new Font(FONTSIZE));
    displayText.setY(TEXTY);
    displayText.setX(TEXTX);
    borderPane.getChildren().add(displayText);
    stage.setTitle("Buy Real Estate");
  }

  @Override
  protected void displayButtons(ResourceBundle resources) {
  }

  @Override
  protected void displayButtons(int playerID, GetInformationForPrompter resources,
      List<OwnableSpace> properties,
      List<String> houseNames, List<String> hotelNames) {
    this.resources = resources;
    this.isComplete = false;
    Pane buttons = makeChoiceButtons(properties, playerID, houseNames, hotelNames);
    borderPane.getChildren().add(buttons);
  }

  private Pane makeChoiceButtons(List<OwnableSpace> properties, int playerID,
      List<String> houseNames, List<String> hotelNames) {
    String[] estateOptions = {"House", "Hotel"};

    Button backButton = makeBackButton();
    Button buyButton = makeBuyButton();
    ChoiceBox<String> chooseEstateType = makeEstateTypeButton(estateOptions);
    ChoiceBox<String> chooseProperty = makePropertyChooser();

    chooseEstateType.setOnAction(event -> {
      if (chooseEstateType.getValue().equals("House")) {
        setChoiceForSmallRealEstate(chooseProperty, houseNames);
      } else if (chooseEstateType.getValue().equals("Hotel")) {
        setChoiceForLargeRealEstate(chooseProperty, hotelNames);
      }
    });
    chooseProperty.setOnAction(event -> {
      buyButton.setDisable(false);
    });
    buyButton.setOnAction(event -> {
      String selectedProperty = chooseProperty.getValue();
      String estateType = chooseEstateType.getValue();
      informationFromPopUp = new PopUpInformation(selectedProperty, estateType);
      isComplete = true;
      closePopUp();
    });

    VBox buttonColumn = new VBox(chooseEstateType, chooseProperty, buyButton, backButton);
    positionButtonColumn(buttonColumn);
    return new Pane(buttonColumn);
  }

  private void setChoiceForLargeRealEstate(ChoiceBox chooseProperty, List<String> hotelProperties) {
    chooseProperty.setItems(FXCollections.observableArrayList(hotelProperties));
  }

  private void setChoiceForSmallRealEstate(ChoiceBox chooseProperty, List<String> houseProperties) {
    chooseProperty.setItems(FXCollections.observableArrayList(houseProperties));
  }

  private Button makeBuyButton() {
    Button buyButton = new Button("Buy and Place");
    buyButton.setTooltip(new Tooltip("Buy a real estate for a property"));
    buyButton.setDisable(true);
    return buyButton;
  }

  private ChoiceBox<String> makePropertyChooser() {
    ChoiceBox<String> propertyChooser = new ChoiceBox<String>();
    propertyChooser.setTooltip(new Tooltip(""));
    return propertyChooser;
  }

  private ChoiceBox<String> makeEstateTypeButton(String[] estateOptions) {
    ChoiceBox<String> estateChooser = new ChoiceBox(FXCollections.observableArrayList(estateOptions));
    estateChooser.setTooltip(new Tooltip("Choose the real estate type you would like to purchase"));
    return estateChooser;
  }

  private Button makeBackButton() {
    Button button = new Button("Back");
    button.setOnAction(event -> {
      isComplete = true;
      closePopUp();
    });
    button.setTooltip(new Tooltip("Go back to the board"));
    return button;
  }

  private void positionButtonColumn(VBox buttonColumn) {
    buttonColumn.setLayoutX(borderPane.getWidth()/4);
    buttonColumn.setLayoutY(borderPane.getHeight()/3);
    buttonColumn.setSpacing(15);
  }
}
