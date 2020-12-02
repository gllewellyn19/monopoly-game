package ooga.view.gameView;


/**
 * This interface allows a class to change and get a language. This class has no assumptions or dependencies.
 * An example of how to use this class is give it to the change language button so it can change the
 * language
 *
 * @author Grace Llewellyn
 */
public interface LanguageControls {

  /**
   * Updates the language
   * @param language language to change to in game view
   */
  void updateLanguage(String language);

  String getLanguage();
}
