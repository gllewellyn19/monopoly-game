package ooga.view.buttons;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;

/**
 * Implemented by all clickable buttons in the game. Maintains the button and can set the text
 * and id of it as well as changing the language
 *
 * @author Grace Llewellyn
 */
public abstract class BoardButton extends BoardInteractiveFeature {

  private final ButtonBase currButton;

  public BoardButton(ResourceBundle resources, ButtonBase b, String id) {
    super(resources, id);
    currButton = b;
  }

  /**
   * initialize button by setting its text and ID
   */
  public void initializeButton() {
    setTextAndID();
  }

  /**
   * sets text and ID of the button
   */
  private void setTextAndID() {
    currButton.setText(super.getResources().getString(super.getId()));
    currButton.setId(super.getId());
  }

  /**
   * Updates the resources for the button to reflect the new language and sets the text for the
   * button again
   */
  public void updateLanguageOnFeature() throws MissingResourceException {
    currButton.setText(super.getResources().getString(super.getId()));
  }

  /**
   * @return - current button
   */
  public ButtonBase getCurrButton() {
    return currButton;
  }

  /**
   * @return - Node representing interactive feature
   */
  public Node getCurrInteractiveFeature() {
    return currButton;
  }

}
