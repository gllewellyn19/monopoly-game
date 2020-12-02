package ooga.view.popup;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.HBox;
import ooga.view.buttons.OkButton;

import java.util.ResourceBundle;

/**
 * This opens a pop up asking the player if they want to use one of their cards.
 *
 * @author Lina Leyhausen
 */
public class UseCardPopUp extends SimpleMessagePopUp {
    private boolean useCard;

    @Override
    protected void displayButtons(ResourceBundle resources){
        okbutton = new OkButton(resources, new Button());
        HBox buttons = new HBox(8);
        buttons.setLayoutY(BUTTONSY);
        buttons.setLayoutX(BUTTONSX);
        ButtonBase button = okbutton.getCurrButton();
        button.setOnAction(event -> onClick());
        buttons.getChildren().add(button);
        borderPane.getChildren().add(buttons);
    }

    /**
     * Opens a pop up asking a player if they want to use one of their cards, and displays
     * yes and no buttons for the player to make their choice
     *
     * @param resources ResourceBundle with button text
     */
    @Override
    public void openPopUp(ResourceBundle resources){
        displayText();
        displayButtons(resources);
        stage.showAndWait();
    }

    /**
     * Returns true if the player wants to use their card and false otherwise
     * @return boolean indicating if player wants to use their card
     */
    @Override
    public boolean isBuying(){
        return useCard;
    }

    private void onClick(){
        useCard = true;
        closePopUp();
    }
}
