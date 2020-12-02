package ooga.view.popup;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import ooga.view.buttons.BoardButton;
import ooga.view.buttons.NoButton;
import ooga.view.buttons.YesButton;
import ooga.view.popup.PopUp;

import java.util.ResourceBundle;

/**
 * Opens a pop up displaying the image of the property space and asking the player
 * if they want to buy the property.
 *
 * @author Lina Leyhausen
 */
public class PropertyPopUp extends PopUp {
    private final static String LANDED = "You have landed on ";
    private final static String BUY = ". \nWould you like to buy this property?";
    private BoardButton yesbutton;
    private BoardButton nobutton;
    private boolean buyproperty;
    private boolean isHandled;

    @Override
    protected void displayText() {
        Text displayText = new Text(LANDED + name + BUY);
        displayText.setFont(new Font(FONTSIZE));
        displayText.setY(TEXTY);
        displayText.setX(TEXTX);
        borderPane.getChildren().add(displayText);
    }

    @Override
    protected void displayButtons(ResourceBundle resources){
        yesbutton = new YesButton(resources, new Button());
        nobutton = new NoButton(resources, new Button());
        HBox buttons = new HBox(8);
        buttons.setLayoutY(BUTTONSY);
        buttons.setLayoutX(BUTTONSX);
        ButtonBase button1 = yesbutton.getCurrButton();
        ButtonBase button2 = nobutton.getCurrButton();
        button1.setOnAction(event -> buyProperty());
        buttons.getChildren().add(button1);
        button2.setOnAction(event -> doNotBuy());
        buttons.getChildren().add(button2);
        borderPane.getChildren().add(buttons);
    }

    /**
     * Opens a pop up with a message asking the user if they want to buy the property,
     * the image of the property space, and yes and no buttons for the user to click.
     *
     * @param name name of the space
     * @param image image of the space
     * @param resources ResourceBundle for button text
     */
    @Override
    public void openPopUp(String name, Image image, ResourceBundle resources) {
        this.name = name;
        this.spaceImage = image;
        displayWindow();
        displayText();
        displaySpaceImage();
        displayButtons(resources);
        stage.showAndWait();
    }

    private void buyProperty(){
        buyproperty = true;
        closePopUp();
    }

    private void doNotBuy(){
        buyproperty = false;
        closePopUp();
    }

    /**
     * Returns true if the user wants to buy the property and false otherwise
     *
     * @return boolean indicating if the user wants to buy the property
     */
    @Override
    public boolean isBuying(){
        return buyproperty;
    }
}
