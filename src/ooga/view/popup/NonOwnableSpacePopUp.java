package ooga.view.popup;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.view.buttons.BoardButton;
import ooga.view.buttons.NoButton;
import ooga.view.buttons.OkButton;
import ooga.view.buttons.YesButton;

import java.util.ResourceBundle;

/**
 * Displays a pop up informing a player that they have landed on a space (non-property)
 * and what happens on that space
 *
 * @author Lina Leyhausen
 */
public class NonOwnableSpacePopUp extends PopUp{
    private static final String ENTER = "\n";
    private String message = "";
    private BoardButton okbutton;

    /**
     * Add message about the space
     * @param message message to be displayed
     */
    @Override
    public void addMessage(String message){
        this.message = message;
    }

    @Override
    protected void displayText() {
        Text displayText = new Text("You have landed on " + name + ENTER + message);
        displayText.setFont(new Font(FONTSIZE));
        displayText.setY(TEXTY);
        displayText.setX(TEXTX);
        borderPane.getChildren().add(displayText);
    }
}
