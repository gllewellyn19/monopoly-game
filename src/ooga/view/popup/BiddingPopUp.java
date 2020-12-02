package ooga.view.popup;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.view.buttons.OkButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This creates a pop up which handles the bidding process when a player chooses not
 * to buy a space
 *
 * @author Lina Leyhausen
 */
public class BiddingPopUp extends PopUp {
    private final static String BID = "Players, please enter your bids \nfor this property.";
    private int buttonylocation = BUTTONSY;
    protected List<Integer> bids = new ArrayList<Integer>();
    protected List<TextField> textfields = new ArrayList<>();
    protected int numPlayers;
    protected List<Integer> topBid;

    /**
     * Opens a pop up alerting the users that bidding has started and allowing everyone
     * to place their bids in text boxes
     *
     * @param name name of current turn's player
     * @param image image of board space
     * @param resources ResourceBundle with button text
     * @param numPlayers number of players in the game
     */
    @Override
    public void openPopUp(String name, Image image, ResourceBundle resources, int numPlayers){
        this.numPlayers = numPlayers;
        this.name = name;
        this.spaceImage = image;
        this.resources = resources;
        displayWindow();
        displayText();
        displaySpaceImage();
        displayButtons(resources);
        stage.showAndWait();
    }

    @Override
    protected void displayText() {
        Text displayText = new Text(BID);
        displayText.setFont(new Font(FONTSIZE));
        displayText.setY(TEXTY);
        displayText.setX(TEXTX);
        borderPane.getChildren().add(displayText);
    }

    @Override
    protected void displaySpaceImage(){
        VBox fields = new VBox(2);
        for(int i=0;i<numPlayers;i++){
            TextField in = new TextField();
            in.setPromptText("Player " + (i + 1) + " bid");
            in.setMinWidth(100);
            in.setMinHeight(20);
            in.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            fields.getChildren().add(in);
            textfields.add(in);
        }
        fields.setLayoutX(IMAGEX);
        fields.setLayoutY(IMAGEY);
        borderPane.getChildren().add(fields);
    }

    @Override
    protected void displayButtons(ResourceBundle resources) {
        okbutton = new OkButton(resources, new Button());
        HBox buttons = new HBox(8);
        buttons.setLayoutY(BUTTONSY);
        buttons.setLayoutX(BUTTONSX);
        ButtonBase button = okbutton.getCurrButton();
        button.setOnAction(event -> onClick());
        buttons.getChildren().add(button);
        borderPane.getChildren().add(buttons);
    }

    private void onClick(){
        int max = 0;
        int maxPlayer = 0;
        int numNotBidding = 0;
        for(int i=0;i<numPlayers;i++){
            int temp = getMoneyAmount(textfields.get(i).getText());
            if(temp == -1){
                Text errorText = new Text ("A bid is incorrectly formatted \n or less than $10.");
                errorText.setLayoutY(BUTTONSY + 40);
                errorText.setLayoutX(BUTTONSX);
                borderPane.getChildren().add(errorText);
            }
            if(temp == 0){
                numNotBidding++;
            }
            bids.add(temp);
            if(temp > max){
                max = temp;
                maxPlayer = i;
            }
        }

        if(numNotBidding != numPlayers) {
            topBid = new ArrayList<>();
            topBid.add(maxPlayer);
            topBid.add(max);
            BiddingPopUp int1 = new BiddingPopUpIntermediate();
            int1.addMessage("Player " + (maxPlayer + 1));
            int1.openPopUp(name, spaceImage, resources, numPlayers);
            topBid = int1.getWinningPlayerAndBid().get();
        }
        closePopUp();
    }

    private int getMoneyAmount(String input){
        if(input == null || input.equals("")){
            return 0;
        }
        int ans;
        try{
            ans = Integer.parseInt(input);
        } catch(NumberFormatException nfe) {
            return -1;
        }
        if(ans < 10){
            return -1;
        }
        return ans;
    }

    /**
     * Returns an optional integer list, with the winning player's int ID and bid amount
     *
     * @return List of winning player's int ID and bid amount
     */
    public Optional<List<Integer>> getWinningPlayerAndBid(){
        return Optional.ofNullable(topBid);
    }
}
