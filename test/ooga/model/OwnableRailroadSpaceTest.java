/**
 *tests from previous iterations of work on OwnableSpaces
 */
//package ooga.model;
//
//import ooga.model.boardSpaces.OwnablePropertySpace;
//import ooga.model.boardSpaces.OwnableRailroadSpace;
//import ooga.model.players.InteractivePlayer;
//import ooga.model.players.Player;
//import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class OwnableRailroadSpaceTest extends DukeApplicationTest {
//
//    @Test
//    public void testLandOnSpaceAlreadyOwnedByOtherPlayer(){
//        Player owner = new InteractivePlayer(3);
//        Player lander = new InteractivePlayer(4);
//        OwnableRailroadSpace space = new OwnableRailroadSpace("Railroad",60,"Green",3,3,3,3,3,3,3);
//        space.purchase(owner);
//        lander.setPlayerBalance(5);
//        owner.setPlayerBalance(5);
//        space.landOnSpace(lander);
//        assertEquals(3,lander.getPlayerBalance());
//        assertEquals(7,owner.getPlayerBalance());
//    }
//
//    @Test
//    public void testLandOnSpaceAlreadyOwnedBySamePlayer(){
//        Player owner = new InteractivePlayer(3);
//        OwnablePropertySpace space = new OwnablePropertySpace("Railroad",60,"Green",3,3,3,3,3,3,3);
//        owner.setPlayerBalance(65);
//        space.purchase(owner);
//        space.landOnSpace(owner);
//        assertEquals(5,owner.getPlayerBalance());
//    }
//
//}
