package ooga.view.buttons;

/**
 * This class provides buttons access to other buttons. This class has no
 * assumptions or dependencies. An example of how to use this class is give it to the game theme button
 * so that it can enable the number of players button
 *
 * @author Grace Llewellyn
 */
public interface AccessOtherButtons {

  BoardInteractiveFeature getButton(String type);

  /**
   * Checks is the enable launch game button should be enabled and enables it if it should
   */
  void checkEnableLaunchGameButton();

  /**
   * Checks if all names entered are unique
   * @return true if all names entered are unique
   */
  boolean checkAllNamesEnteredAreUnique();


  /**
   * Checks if all pieces entered are unique
   * @return true if all pieces entered are unique
   */
  boolean checkAllPiecesUnique();

}
