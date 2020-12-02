package ooga.view.boardSpaces;

import javafx.scene.image.Image;
import ooga.model.DataReader;
import ooga.model.dataReaders.DataReaderClassic;

/***
 * Has the different types of real estate that can be displayed on the board like house, castle,
 * hotel, cottage, etc.
 *
 * NOTE: this class is a work in progress
 */
public class RealEstateView {

    //Change to take the image file name from a properties/csv
    public String getHouseImagePath() {
        return "ClassicMonopoly.realestateimages.house.jpg";
    }

    /**
     * @param filename is properties/CSV
     * @return house image
     */
    public Image getHouseImage(String filename) {
        DataReader dataReader = new DataReaderClassic();
        //return dataReader.getHouseImage(filename);
        return null; // Implement after we have file input figured out
    }


    //Change to take the image file name from a properties/csv
    public String getHotelImagePath() {
        return "ClassicMonopoly.realestateimages.hotel.jpg";
    }

}
