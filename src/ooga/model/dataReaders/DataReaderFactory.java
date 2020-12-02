package ooga.model.dataReaders;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Properties;
import ooga.model.DataReader;

/**
 * Creates a data reader object with information from the user. Uses DataReaderClassic as default if
 * information not provided or incorrect
 *
 * @author Grace Llewellyn
 */
public class DataReaderFactory {

  public static final String FILE_PATH_START_GAME = "ooga.model.dataReaders.";
  public static final String DEFAULT_FILE_READER = "DataReaderClassic";

  /**
   * Creates a new DataReader instance
   * @param defaultProperties is an optional description for the DataReader properties
   *                          if not present, return the default dataReaderType DataReaderClassic
   * @return a new instance of DataReader based on the input properties or default settings
   */
  public DataReader createDataReader(Optional<Properties> defaultProperties) {
    try {
      String dataReaderTypeOrDefault = DEFAULT_FILE_READER;
      if (defaultProperties.isPresent() && defaultProperties.get().getProperty("dataReaderType") != null) {
        dataReaderTypeOrDefault = defaultProperties.get().getProperty("dataReaderType");
      }
      Class dataReaderClass = Class.forName(FILE_PATH_START_GAME + dataReaderTypeOrDefault);
      return (DataReader) dataReaderClass.getDeclaredConstructor().newInstance();
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      return new DataReaderClassic();
    }
  }

}
