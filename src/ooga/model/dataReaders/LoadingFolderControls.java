package ooga.model.dataReaders;

/**
 * This class gives buttons access to load in a new data file. This class has no
 * assumptions or dependencies. An example of how to use this class is give it to the game theme button
 * so that it can choose a new game folder
 *
 * @author Grace Llewellyn
 */
public interface LoadingFolderControls {

  /**
   * Uploaded a new properties folder to the datareader
   * @param folderPath file path for properties file
   */
  void uploadPropertiesFolder(String folderPath);

  /**
   * Uploads a new preestablished game type to the datareader. Assumes that this is located
   * in the data folder (doesn't crash if not)
   * @param gameType type of game to switch to
   */
  void uploadGameType(String gameType);
  boolean getSetUpSuccessful();

}
