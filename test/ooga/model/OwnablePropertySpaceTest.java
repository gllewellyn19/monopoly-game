/**
 *tests from previous iterations of work on OwnableSpaces
 */
//package ooga.model;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import ooga.model.boardSpaces.OwnablePropertySpace;
//import ooga.model.players.InteractivePlayer;
//import ooga.model.players.Player;
//import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
//
//public class OwnablePropertySpaceTest extends DukeApplicationTest {
//
//    @Test
//    public void testLandOnSpaceAlreadyOwnedByOtherPlayer(){
//        Player owner = new InteractivePlayer(3);
//        Player lander = new InteractivePlayer(4);
//        OwnablePropertySpace space = new OwnablePropertySpace("Property",60,"Green",3,3,3,3,3,3,3);
//        owner.setPlayerBalance(65);
//        space.purchase(owner);
//        lander.setPlayerBalance(5);
//        space.landOnSpace(lander);
//        assertEquals(3,lander.getPlayerBalance());
//        assertEquals(7,owner.getPlayerBalance());
//    }
//
//    @Test
//    public void testLandOnSpaceAlreadyOwnedBySamePlayer(){
//        Player owner = new InteractivePlayer(3);
//        OwnablePropertySpace space = new OwnablePropertySpace("Property",60,"Green",3,3,3,3,3,3,3);
//        owner.setPlayerBalance(65);
//        space.purchase(owner);
//        space.landOnSpace(owner);
//        assertEquals(5,owner.getPlayerBalance());
//    }
//
//    /**
//     * Cannot add a house to an ownable
//     * property without a monopoly
//     */
//    @Test
//    public void testAddSmallEstateUnhappy() {
//        OwnablePropertySpace space = new OwnablePropertySpace("Property",60,"Green",3,3,3,3,3,3,3);
//        space.addSmallEstateToProperty();
//        space.addSmallEstateToProperty();
//        assertFalse(space.getNumSmallEstates() == 2);
//    }
//
//    /**
//     * Won't work unless checkForMonopoly() is functional
//     */
//    @Test
//    public void testAddSmallEstateWithMonopoly() {
//        Player owner = new InteractivePlayer(3);
//        owner.setPlayerBalance(300);
//        OwnablePropertySpace medAve = new OwnablePropertySpace("Mediterranean Avenue",60,"Purple",10,30,90,160,250,50,50);
//        OwnablePropertySpace balticAve = new OwnablePropertySpace("Baltic Avenue",60,"Purple",20,60,180,320,450,50,50);
//        medAve.purchase(owner);
//        balticAve.purchase(owner);
//        medAve.addSmallEstateToProperty();
//        assertEquals(1, medAve.getNumSmallEstates());
//        balticAve.addLargeEstateToProperty();
//        assertTrue(balticAve.getNumLargeEstates() == 0);
//    }
//}
