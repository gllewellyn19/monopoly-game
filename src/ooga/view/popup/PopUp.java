package ooga.view.popup;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.view.buttons.BoardButton;
import ooga.view.buttons.OkButton;
import ooga.view.gameView.GetInformationForPrompter;

/**
 * Opens a pop up to give the users information and allow the users to make choices
 *
 * @author Lina Leyhausen
 */
public abstract class PopUp {
    protected final static int TEXTY = 25;
    protected final static int TEXTX = 10;
    protected final static int FONTSIZE = 15;
    private final static int WIDTH = 300;
    protected final static int IMAGEHEIGHT = 120;
    private final static int IMAGEWIDTH = 80;
    protected final static int IMAGEX = 10;
    protected final static int IMAGEY = 65;
    protected final static int BUTTONSY = IMAGEY + IMAGEHEIGHT + 20;
    protected final static int BUTTONSX = 10;

    protected BorderPane borderPane;
    protected Scene scene;
    protected Stage stage;
    protected String name;
    protected Image spaceImage;
    protected BoardButton okbutton;
    protected String message;
    protected ResourceBundle resources;

    protected void displayWindow(){
        borderPane = new BorderPane();
        scene = new Scene(borderPane, WIDTH, WIDTH);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Landed On");
    }

    protected abstract void displayText();

    protected void displaySpaceImage(){
        ImageView image = new ImageView(spaceImage);
        image.setX(IMAGEX);
        image.setY(IMAGEY);
        image.setFitHeight(IMAGEHEIGHT);
        image.setFitWidth(IMAGEWIDTH);
        borderPane.getChildren().add(image);
    }

    protected void displayButtons(ResourceBundle resources) {
        okbutton = new OkButton(resources, new Button());
        HBox buttons = new HBox(8);
        buttons.setLayoutY(BUTTONSY);
        buttons.setLayoutX(BUTTONSX);
        ButtonBase button = okbutton.getCurrButton();
        button.setOnAction(event -> closePopUp());
        buttons.getChildren().add(button);
        borderPane.getChildren().add(buttons);
    }

    /**
     * Opens a pop up with just a message and buttons
     * @param resources ResourceBundle for the buttons
     */
    public void openPopUp(ResourceBundle resources){
        displayText();
        displayButtons(resources);
        stage.show();
    }

    /**
     * Displays a pop up with the name and image of the current space as well as a
     * message and buttons
     *
     * @param name name of the current space
     * @param image image of the current space
     * @param resources ResourceBundle for the buttons
     */
    public void openPopUp(String name, Image image, ResourceBundle resources) {
        this.name = name;
        this.spaceImage = image;
        displayWindow();
        displayText();
        displaySpaceImage();
        displayButtons(resources);
        stage.show();
    }

    /**
     * Displays a pop up with a message, buttons, and the name of the current space
     * @param name the name of the current space
     * @param resources ResourceBundle for the buttons
     */
    public void openPopUp(String name, ResourceBundle resources) {
        this.name = name;
        displayWindow();
        displayText();
        displayButtons(resources);
        stage.show();
    }

    /**
     * Opens a pop up with the name of the current space, an image displayed from the
     * given Rectangle object, and buttons
     *
     * @param name the name of the current space
     * @param image a Rectangle object holding an image
     * @param resources ResourceBundle for the buttons
     */
    public void openPopUp(String name, Rectangle image, ResourceBundle resources) {
        this.name = name;
        displayWindow();
        displayText();
        displayRectangleImage(image);
        displayButtons(resources);
        stage.show();
    }

    /**
     * Opens a pop up with the name of the player, the player's owned properties,
     * and the options the player has for buying houses and hotels.
     *
     * @param playerID id of the current player
     * @param ownedProperties the properties the current player owns
     * @param resources ResourceBundle for the buttons
     * @param houseNames the names of the houses
     * @param hotelNames the names of the hotels
     */
    public void openPopUp(int playerID, List<OwnableSpace> ownedProperties,
        GetInformationForPrompter resources,
        List<String> houseNames, List<String> hotelNames) {
        displayWindow();
        displayText();
        displayButtons(playerID, resources, ownedProperties, houseNames, hotelNames);
        stage.showAndWait();
    }

    /**
     * Opens a pop up with the current player and their owned properties.
     *
     * @param ownedProperties player's owned properties
     * @param playerID current player
     */
    public void openPopUp(List<String> ownedProperties, int playerID) {
        displayWindow();
        displayText();
        displayButtons(playerID, ownedProperties);
        stage.showAndWait();
    }

    protected void displayButtons(int playerID, List<String> ownedProperties) {}

    protected void displayButtons(int playerID, GetInformationForPrompter resources,
        List<OwnableSpace> ownedProperties,
        List<String> houseNames, List<String> hotelNames) {}

    /**
     * Opens a pop up with a message and input boxes for each player
     *
      * @param name name of the current space
     * @param image image of the current space
     * @param resources ResourceBundle for the buttons
     * @param numPlayers number of players in the game
     */
    public void openPopUp(String name, Image image, ResourceBundle resources, int numPlayers){
        openPopUp(name, image, resources);
    }

    protected void closePopUp(){
        stage.hide();
    }

    private void displayRectangleImage(Rectangle image){
        image.setX(IMAGEX);
        image.setY(IMAGEY);
        borderPane.getChildren().add(image);
    }

    /**
     * Adds a message that will be displayed in the pop up
     *
     * @param message message to be displayed
     */
    public void addMessage(String message){
        this.message = message;
    }

    /**
     * Adds a player name
     * @param playerName name of current player
     */
    public void addPlayerName(String playerName){

    }

    /**
     * Gets a boolean in some types of pop ups indicating whether a player is
     * making a purchase/using a card/etc
     * @return boolean player's choice
     */
    public boolean isBuying() {
        return false;
    }
}
