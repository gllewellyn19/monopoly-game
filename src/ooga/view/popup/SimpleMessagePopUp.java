package ooga.view.popup;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class opens a pop up that displays a specified message an an ok button
 *
 * @author Lina Leyhausen
 */
public class SimpleMessagePopUp extends PopUp{
    @Override
    protected void displayText() {
        Text displayText = new Text(message);
        displayText.setFont(new Font(FONTSIZE));
        displayText.setY(TEXTY);
        displayText.setX(TEXTX);
        borderPane.getChildren().add(displayText);
    }
}
