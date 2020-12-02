package ooga.view.buttons;

import javafx.scene.control.ButtonBase;

import java.util.ResourceBundle;

/**
 * Stores a button with the text "Ok"
 *
 * @author Lina Leyhausen
 */
public class OkButton extends BoardButton {
    /**
     * Initializes a new button with the text "Ok" to be used in the pop ups
     *
     * @param resources ResourceBundle that has the text for the button
     * @param b The base that the button will be modifying
     */
    public OkButton(ResourceBundle resources, ButtonBase b) {
        super(resources, b, "OkButton");
        initializeButton();
        getCurrButton().setMinWidth(50);
    }
}
