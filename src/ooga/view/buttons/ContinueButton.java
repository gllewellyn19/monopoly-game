package ooga.view.buttons;

import javafx.scene.control.ButtonBase;

import java.util.ResourceBundle;

/**
 * Stores a button with the text "Continue"
 *
 * @author Lina Leyhausen
 */
public class ContinueButton extends BoardButton {
    /**
     * Initializes a new button with the text "Continue" to be used in the pop ups
     *
     * @param resources ResourceBundle that has the text for the button
     * @param b The base that the button will be modifying
     */
    public ContinueButton(ResourceBundle resources, ButtonBase b) {
        super(resources, b, "ContinueButton");
        initializeButton();
        super.getCurrButton().setMinWidth(50);
    }
}
