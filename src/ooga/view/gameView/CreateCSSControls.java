package ooga.view.gameView;

import java.util.Optional;
import javafx.scene.Scene;

/**
 * This interface allows a class to upload a CSS. This class has no assumptions or dependencies.
 * An example of how to use this class is give it to the change stylesheet button
 *
 * @author Grace Llewellyn
 */
public interface CreateCSSControls {

  /**
   * Makes a scene with the default CSS
   *
   * @return returns a Scene if it could be created
   */
  Optional<Scene> makeASceneWithInitialCSS();

  /**
   * Makes a scene with the given CSS
   *
   * @return returns a Scene if it could be created
   */
  Optional<Scene> changeCSS(String cssFile);

  /**
   * Makes a splash scene with the default CSS
   *
   * @return returns a Scene if it could be created
   */
  Optional<Scene> makeAnInitialScene(int width, int height);

}
