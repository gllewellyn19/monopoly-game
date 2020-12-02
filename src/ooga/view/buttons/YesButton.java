package ooga.view.buttons;

import javafx.scene.control.ButtonBase;

import java.util.ResourceBundle;

/**
 * Stores a button with the text "Yes"
 *
 * @author Lina Leyhausen
 */
public class YesButton extends BoardButton {
    /**
     * Initializes a new button with the text "Yes" to be used in the pop ups
     *
     * @param resources ResourceBundle that has the text for the button
     * @param b The base that the button will be modifying
     */
    public YesButton(ResourceBundle resources, ButtonBase b) {
        super(resources, b, "YesButton");
        initializeButton();
        getCurrButton().setMinWidth(50);
    }
}
