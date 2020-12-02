package ooga.view.buttons;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.LanguageControls;
import ooga.view.gameView.ClassicGameView;

/**
 * Creates the button for choosing the language of the game
 *
 * @author Grace Llewellyn
 */
public class ChooseLanguageButton extends BoardChoiceBox{

  public static final List<String> POTENTIAL_LANGUAGES = List.of("English", "Spanish", "Portuguese");

  private final LanguageControls languageControls;

  /**
   * @param resources - ResourceBundle file with text with wanted language of buttons
   * @param languageControls - interface object with setLanguage and getLanguage methods
   */
  public ChooseLanguageButton(ResourceBundle resources, LanguageControls languageControls,
      ErrorPrintable errorPrintable, Properties gameValueResources, Optional<Properties> defaultProperties) {
    super(resources, new ChoiceBox<>(), "ChooseLanguageButton", errorPrintable, gameValueResources,
        defaultProperties);
    this.languageControls = languageControls;
    initializeButton();
  }


  /**
   * Initializing the text and id of the button from the resources bundle and sets the action of the
   * button to be uploading a new language
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    ChoiceBox<String> thisButton = super.getCurrButton();
    thisButton.setValue(ClassicGameView.DEFAULT_STARTING_LANGUAGE);
    thisButton.getItems().addAll(POTENTIAL_LANGUAGES);
    thisButton.setOnAction(
        e -> languageControls.updateLanguage(thisButton.getValue()));
  }

  /**
   * This feature is only shown on the splash screen at the beginning, so the language does not need
   * to be changes for it
   */
  public void updateLanguageOnFeature() {
  }

}
