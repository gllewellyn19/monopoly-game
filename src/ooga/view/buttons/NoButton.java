package ooga.view.buttons;

import javafx.scene.control.ButtonBase;

import java.util.ResourceBundle;

/**
 * Stores a button with the text "No"
 *
 * @author Lina Leyhausen
 */
public class NoButton extends BoardButton {
    /**
     * Initializes a new button with the text "No" to be used in the pop ups
     *
     * @param resources ResourceBundle that has the text for the button
     * @param b The base that the button will be modifying
     */
    public NoButton(ResourceBundle resources, ButtonBase b) {
        super(resources, b, "NoButton");
        initializeButton();
        getCurrButton().setMinWidth(50);
    }
}
