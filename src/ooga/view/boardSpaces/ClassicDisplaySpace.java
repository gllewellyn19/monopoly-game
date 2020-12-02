package ooga.view.boardSpaces;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/***
 * The space that is held in DisplaySpaces, representing the graphics of a space on the board
 *
 * @author Lina Leyhausen
 */
public class ClassicDisplaySpace {

    Image image;
    String name;
    int width;
    int height;

    /**
     * Initializes the space with the image, name, width, and height of the space
     *
     * @param image image that will be displayed on the board at this space
     * @param name name of this space
     * @param width int width of the graphic
     * @param height int height of the graphic
     */
    public ClassicDisplaySpace(Image image, String name, int width, int height){
        this.image = image;
        this.name = name;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the graphic that will be shown on the board at this space
     * @return image of space
     */
    public Image getImage(){
        return image;
    }

    /**
     * Returns the String name of this space
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a Rectangle with the image, width, and height for this space graphic
     * @return Rectangle object
     */
    public Rectangle getSpace(){
        Rectangle space = new Rectangle(width, height);
        space.setFill(new ImagePattern(image));
        return space;
    }

    /**
     * Unused: add a house graphic to this space
     */
    public void addHouse() {}

    /**
     * Unused: add a hotel graphic to this space
     */
    public void addHotel() {}
}
