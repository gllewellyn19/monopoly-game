package ooga.view.boardSpaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import ooga.model.dataReaders.DataReaderClassic;

/***
 * Holds all the different spaces on the board and their visual representations
 * based on an actual monopoly board
 *
 * @author Lina Leyhausen
 */
public class ClassicDisplaySpaces extends DisplaySpaces {

    public static final int DIVISOR = 26;
    public static final int HEIGHT = DIVISOR * 3;
    public static final int WIDTH = DIVISOR * 2;

    List<ClassicDisplaySpace> displaySpaces;

    /**
     * Initializes a list of DisplaySpace objects
     *
     * @param filepath path where the csv file storing the board image data can be found
     * @throws FileNotFoundException in case the file is not found
     */
    public ClassicDisplaySpaces(String filepath) throws FileNotFoundException {
        displaySpaces = new ArrayList<>();
        initializeDisplaySpaces(new File(filepath));
    }

    /**
     * Reads in the image data in the csv file using a DataReader
     * Assumption: the csv file will have the image data in the correct order, corresponding
     * to the spaces on the monopoly board
     *
     * @param csvFile file where the space image data can be found
     * @throws FileNotFoundException in case file is not found
     */
    public void initializeDisplaySpaces(File csvFile) throws FileNotFoundException {
        DataReaderClassic reader = new DataReaderClassic();
        List<String[]> spaces = reader.getBoardSpaceImagePaths(csvFile);
        List<Image> spaceImages = makeSpaceImages(spaces);
        List<String> spaceNames = makeSpaceNames(spaces);
        makeSpacesWithImages(spaceImages, spaceNames);
    }

    private void makeSpacesWithImages(List<Image> spaceImages, List<String> spaceNames) {
        Map<String, Integer> dimensions;
        displaySpaces = new ArrayList<>();
        for(int i = 0;i < spaceImages.size(); i++){
            dimensions = getDimensions(i);
            ClassicDisplaySpace temp = new ClassicDisplaySpace(spaceImages.get(i), spaceNames.get(i), dimensions.get("width"), dimensions.get("height"));
            displaySpaces.add(temp);
        }
    }

    private Map<String, Integer> getDimensions(int i) {
        Map<String, Integer> dimensions;
        int[] X = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        int[] Y = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        if(i == 0 || i == 10 || i == 20 || i == 30){
            dimensions = getCornerDimensions();
        } else if((i > 0 && i < 10) || (i>20 && i<30)){
            dimensions = getStandardDimensions();
        } else {
            dimensions = getInvertedDimensions();
        }
        dimensions.put("x", X[i]);
        dimensions.put("y", Y[i]);
        return dimensions;
    }

    private Map<String, Integer> getCornerDimensions() {
        return new HashMap<>(){{
            put("width", HEIGHT);
            put("height", HEIGHT);
        }};
    }

    private Map<String, Integer> getStandardDimensions() {
        return new HashMap<>(){{
            put("width", WIDTH);
            put("height", HEIGHT);
        }};
    }

    private Map<String, Integer> getInvertedDimensions() {
        return new HashMap<>(){{
            put("width", HEIGHT);
            put("height", WIDTH);
        }};
    }

    private List<Image> makeSpaceImages(List<String[]> filepaths) throws FileNotFoundException {
        List<Image> spaceImages = new ArrayList<>();
        for (String[] imageFile : filepaths) {
            Image temp = new Image(new File(imageFile[0]).toURI().toString());
            spaceImages.add(temp);
        }
        return spaceImages;
    }

    private List<String> makeSpaceNames(List<String[]> spaces) {
        List<String> spaceNames = new ArrayList<>();
        for(String[] space: spaces){
            spaceNames.add(space[1]);
        }
        return spaceNames;
    }

    /**
     * Return list of DisplaySpaces containing all initialized board spaces
     *
     * @return list of ClassicDisplaySpace objects
     */
    public List<ClassicDisplaySpace> getDisplaySpaces() {
        return displaySpaces;
    }
}
