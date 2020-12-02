package ooga.view.boardSpaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import ooga.view.boardSpaces.ClassicDisplaySpace;

/**
 * Abstract representation of the DisplaySpaces class, which initializes and stores the
 * view of all board spaces
 *
 * @author Lina Leyhausen
 */
public abstract class DisplaySpaces {

  /**
   * This method initializes all DisplaySpace object on the board based on the data
   * contained in the given csv file.
   * Assumption: the csv file contains the names of all image files, the string names of
   * the spaces, and everything is in the correct order (first space is the Go/Start space,
   * last space is the "Boardwalk"/last property space)
   *
   * @param csvFile file where the board graphics data can be found
   * @throws FileNotFoundException in case the file is not found
   */
  public abstract void initializeDisplaySpaces(File csvFile) throws FileNotFoundException;

  /**
   * Returns a list of DisplaySpace object, which each have been initialized with the correct
   * image and name
   *
   * @return List of DisplaySpace objects
   */
  public abstract List<ClassicDisplaySpace> getDisplaySpaces();

}
