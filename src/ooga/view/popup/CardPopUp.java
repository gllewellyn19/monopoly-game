package ooga.view.popup;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

/**
 * Displays a pop up about the card that the user has drawn, with the image of the card
 *
 * @author Lina Leyhausen
 */
public class CardPopUp extends PopUp {
    private final static String MESSAGE = "You have drawn this card:";
    private Image cardImage;

    @Override
    protected void displayText() {
        Text displayText = new Text(MESSAGE);
        displayText.setFont(new Font(FONTSIZE));
        displayText.setY(TEXTY);
        displayText.setX(TEXTX);
        borderPane.getChildren().add(displayText);
    }
}
