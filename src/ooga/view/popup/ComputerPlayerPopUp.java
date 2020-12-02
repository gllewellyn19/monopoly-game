package ooga.view.popup;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

/**
 * Displays a message about what the computer player is doing
 *
 * @author Lina Leyhausen
 */
public class ComputerPlayerPopUp extends PopUp {
    private static final String PLAYER_MESSAGE1  = " has landed on ";
    private static final String PLAYER_MESSAGE2 = " and ";

    private String message = "";
    private String playerName = "";

    @Override
    protected void displayText() {
        Text displayText = new Text(playerName = PLAYER_MESSAGE1 + name + PLAYER_MESSAGE2 + message);
        displayText.setFont(new Font(FONTSIZE));
        displayText.setY(TEXTY);
        displayText.setX(TEXTX);
        borderPane.getChildren().add(displayText);
    }

    /**
     * Adds a message about what the computer player is doing
     * @param message message to be displayed
     */
    @Override
    public void addMessage(String message){
        this.message = message;
    }

    /**
     * Adds a player name
     * @param playerName name of current player
     */
    public void addPlayerName(String playerName){
        this.playerName = playerName;
    }

    @Override
    protected void displayButtons(ResourceBundle resources) {

    }
}
