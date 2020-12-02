package ooga.view;

import javafx.scene.image.Image;
import ooga.controller.Controller;
import ooga.view.popup.PopUp;
import ooga.view.popup.PropertyPopUp;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ResourceBundle;

public class PopUpTest {
    @Test
    void testPropertyPopUp(){
        ResourceBundle languageResources = ResourceBundle.getBundle(
                Controller.DEFAULT_RESOURCES_PACKAGE + "languages/English");
        PopUp spacePopUp = new PropertyPopUp();
        Image image = new Image(new File("data/ClassicMonopoly/boardspaceimages/atlantic-ave.jpg").toURI().toString());
        spacePopUp.openPopUp("yeet", image, languageResources);
    }
}
