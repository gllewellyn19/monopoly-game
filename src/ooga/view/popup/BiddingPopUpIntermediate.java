package ooga.view.popup;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.view.buttons.BoardButton;
import ooga.view.buttons.ContinueButton;
import ooga.view.buttons.OkButton;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Displays a pop up for the intermediate stage of bidding, when players have to decide
 * whether to let the highest bid purchase the property or continue bidding
 *
 * @Author Lina Leyhausen
 */
public class BiddingPopUpIntermediate extends BiddingPopUp {
    private final static String HIGHEST = " has bid the highest amount.";
    private final static String CONTINUE = "\nPress OK to let this player purchase the property. \nPress CONTINUE to keep bidding.";

    private BoardButton continuebutton;

    @Override
    protected void displayText() {
        Text displayText = new Text(message + HIGHEST + CONTINUE);
        displayText.setFont(new Font(FONTSIZE));
        displayText.setY(TEXTY);
        displayText.setX(TEXTX);
        borderPane.getChildren().add(displayText);
    }

    @Override
    protected void displayButtons(ResourceBundle resources) {
        okbutton = new OkButton(resources, new Button());
        continuebutton = new ContinueButton(resources, new Button());
        HBox buttons = new HBox(8);
        buttons.setLayoutY(BUTTONSY);
        buttons.setLayoutX(BUTTONSX);
        ButtonBase button1 = okbutton.getCurrButton();
        ButtonBase button2 = continuebutton.getCurrButton();
        button1.setOnAction(event -> clickOk());
        button2.setOnAction(event -> clickContinue());
        buttons.getChildren().add(button1);
        buttons.getChildren().add(button2);
        borderPane.getChildren().add(buttons);
    }

    @Override
    protected void displaySpaceImage(){

    }

    private void clickOk(){
        closePopUp();
    }

    private void clickContinue(){
        BiddingPopUp bid2 = new BiddingPopUp();
        bid2.openPopUp(name, spaceImage, resources, numPlayers);
        topBid = bid2.getWinningPlayerAndBid().get();
        closePopUp();
    }

    /**
     * Returns winning player ID and bid amount
     * @return List of player int ID and bid amount
     */
    @Override
    public Optional<List<Integer>> getWinningPlayerAndBid(){
        return Optional.ofNullable(topBid);
    }
}
