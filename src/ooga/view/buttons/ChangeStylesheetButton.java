package ooga.view.buttons;
/**
 * Notes about why this is good design:
 * The purpose of this code is to show a button that lets the user change the stylesheet of the game
 * This was one of the hardest buttons to write. Changing the language was difficult as well as
 * letting this button be able to actually change the CSS. In this example I use interface
 * segregation principle well because I only give this drop down button access to the methods that it
 * needs. I also read the names of the stylesheets in from a resource bundle to better follow open
 * closed principle. This class also shows a good job of overriding the methods of its super class
 * to implement its own capabilities without breaking the application.
 * This code for this is on the more complex side, but it shows
 * that I did not just give up because creating a drop down in a different language was difficult-
 * I went above and beyond. If this was all in english I could
 * just have the labels on the drop down refer to the titles of the stylesheets that are in english.
 * However, when the language is a different language this will not work because the values in the
 * drop down don't match with the CSS file names that are in english.
 * Instead, I have to create a mapping of the english
 * name of the stylesheet to the name in another language. That way I can look up the english
 * stylesheet even when this button is in another language.
 * I also had many bugs with this class and had to use the changingLanguage boolean to prevent these
 * errors. This function also shows good exception handling. This function follows dependency inversion
 * principle because its superclass does not depend on the implementation details of this class
 */

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import ooga.view.gameView.CreateCSSControls;
import ooga.view.gameView.ErrorPrintable;
import ooga.controller.SceneControls;
import ooga.controller.Controller;
import ooga.view.gameView.ClassicGameView;

/**
 * Creates the button to change the style sheet for the game screen
 *
 * @author Grace Llewellyn
 */
public class ChangeStylesheetButton extends BoardChoiceBox {

  public static final String BUTTON_OPTIONS_IDENTIFIER = "NameStyleSheet";

  private final CreateCSSControls createCSSControls;
  private final SceneControls sceneControls;
  private Map<String, String> englishCSSMappings;
  private boolean changingLanguage = false;

  public ChangeStylesheetButton(ResourceBundle resources, CreateCSSControls createCSSControls,
      ErrorPrintable errorPrintable, SceneControls sceneControls, Properties gameValueResources,
      Optional<Properties> defaultProperties) {
    super(resources, new ChoiceBox<>(), "UploadNewStyleButton", errorPrintable,
        gameValueResources, defaultProperties);
    this.createCSSControls = createCSSControls;
    this.sceneControls = sceneControls;
    initializeButton();
  }


  /**
   * Initializing the text and id of the button from the resources bundle and sets the action of the
   * button to be uploading a new CSS file to gameView
   */
  @Override
  public void initializeButton() {
    super.initializeButton();
    ChoiceBox<String> thisButton = super.getCurrButton();
    addOptionsToButton(thisButton);
    thisButton.valueProperty().addListener(e -> readInCSSFile(thisButton.getValue()));
  }

  /**
   * Adds the different style options to the button from the resource file while there are still
   * options to add in (displayed in the resources file as BUTTON_OPTIONS_IDENTIFIER + the number
   * of the stylesheet starting with 0)
   * @param styleButton choice box for the style button
   */
  private void addOptionsToButton(ChoiceBox<String> styleButton) {
    englishCSSMappings = new HashMap<>();
    try {
      ResourceBundle englishResources = ResourceBundle.getBundle(
          Controller.DEFAULT_RESOURCES_PACKAGE +
          ClassicGameView.LANGUAGE_FOLDER + ClassicGameView.DEFAULT_STARTING_LANGUAGE);
      addOptionsCSSAndEnglish(styleButton, englishResources);
      styleButton.setValue(super.getResources().getString("NameStyleSheet0"));
    } catch (MissingResourceException e) {
      super.printError(e.getMessage());
    }
  }

  /**
   * Adds the options to the button and the css file in the current language to a mapping that
   * maps to the english language to find the CSS later
   * @param styleButton choice box for the style button
   * @param englishResources resources for english language use in game
   */
  private void addOptionsCSSAndEnglish(ChoiceBox<String> styleButton,
      ResourceBundle englishResources) {
    boolean stillButtonsToAdd = true;
    int count = 0;
    while (stillButtonsToAdd) {
      try {
        count = addButtonOption(styleButton, englishResources, count);
      } catch (MissingResourceException e) {
        stillButtonsToAdd = false;
      }
    }
  }

  /**
   * Adds a single option to the button and the css file in the current language to a mapping that
   * maps to the english language to find the CSS later
   * @param styleButton choice box for the style button
   * @param englishResources resources for english language use in game
   * @param count of options
   */
  private int addButtonOption(ChoiceBox<String> styleButton, ResourceBundle englishResources,
      int count) {
    String option = super.getResources().getString(BUTTON_OPTIONS_IDENTIFIER + count);
    String englishOption = englishResources.getString(BUTTON_OPTIONS_IDENTIFIER + count);
    englishCSSMappings.put(option, englishOption);
    styleButton.getItems().add(option);
    count++;
    return count;
  }


  /**
   * Read in the property file and pass it into the main if it is not null and enable the buttons
   * if the setup was successful. CSS sometimes comes in as null, but just ignore because will be
   * passed in as actual value later
   * @param cssFile css file for style sheets
   */
  private void readInCSSFile(String cssFile) {
    if (cssFile!=null && !changingLanguage) {
      String cssFileEnglish = englishCSSMappings.get(cssFile).replaceAll(" ", "");
      Optional<Scene> scene = createCSSControls.changeCSS(cssFileEnglish);
      scene.ifPresent(sceneControls::setScene);
    }
  }

  /**
   * updates language of that button's text
   */
  public void updateLanguageOnFeature() {
    changingLanguage = true;
    super.getCurrButton().getItems().clear();
    addOptionsToButton(super.getCurrButton());
    changingLanguage = false;
  }

}
