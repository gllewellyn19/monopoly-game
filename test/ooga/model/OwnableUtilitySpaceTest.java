/**
 *tests from previous iterations of work on OwnableSpaces
 */
//package ooga.model;
//
//import ooga.model.boardSpaces.OwnablePropertySpace;
//import ooga.model.players.InteractivePlayer;
//import ooga.model.players.Player;
//import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class OwnableUtilitySpaceTest extends DukeApplicationTest {
//
//    @Test
//    public void testLandOnSpaceAlreadyOwnedByOtherPlayer(){
//        Player owner = new InteractivePlayer(3);
//        Player lander = new InteractivePlayer(4);
//        OwnablePropertySpace space = new OwnablePropertySpace("Utility",60,"Green",3,3,3,3,3,3,3);
//        owner.setPlayerBalance(65);
//        space.purchase(owner);
//        lander.setPlayerBalance(100);
//        space.landOnSpace(lander);
//        assertTrue(lander.getPlayerBalance() < 100);
//        assertTrue(owner.getPlayerBalance() > 5);
//    }
//
//    @Test
//    public void testLandOnSpaceAlreadyOwnedBySamePlayer(){
//        Player owner = new InteractivePlayer(3);
//        OwnablePropertySpace space = new OwnablePropertySpace("Utility",60,"Green",3,3,3,3,3,3,3);
//        owner.setPlayerBalance(65);
//        space.purchase(owner);
//        space.landOnSpace(owner);
//        assertEquals(5,owner.getPlayerBalance());
//    }
//
//}