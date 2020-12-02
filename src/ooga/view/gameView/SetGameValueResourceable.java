package ooga.view.gameView;

import java.util.Properties;

/**
 * This interface allows a class to set the game value properties. This class has no assumptions or dependencies.
 * An example of how to use this class is give it to the data reader class so that is can upload
 * a new properties file about the games
 *
 * @author Grace Llewellyn
 */
public interface SetGameValueResourceable {

  void setGameValueResources(Properties gameValueResources);
}
