package ooga.view.buttons;

import java.util.ResourceBundle;
import javafx.scene.Node;

/**
 * Higher level class for components of the board that are interactive (i.e. buttons and choice boxes)
 *
 * @author Grace Llewellyn
 */
public abstract class BoardInteractiveFeature {

  private ResourceBundle resources;
  private String id;

  public BoardInteractiveFeature(ResourceBundle resources, String id) {
    this.resources = resources;
    this.id = id;
  }

  /**
   * initializes the button on the interactive feature
   */
  protected abstract void initializeButton();

  /**
   * @return - ResourceBundle object
   */
  protected ResourceBundle getResources() {
    return resources;
  }

  /**
   * @param newLanguageResources - name of ResourceBundle of new specified language
   */
  public void updateLanguage(ResourceBundle newLanguageResources) {
    this.resources = newLanguageResources;
    updateLanguageOnFeature();
  }

  /**
   * Updates the language
   */
  public abstract void updateLanguageOnFeature();

  /**
   * @return the current interactive feature related to the board
   */
  public abstract Node getCurrInteractiveFeature();

  /**
   * @return the id of the feature
   */
  protected String getId() {
    return id;
  }


}
