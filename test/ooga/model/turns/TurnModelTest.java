package ooga.model.turns;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnModelTest {

    @Test
    void updateNewPositionPositive() {
        TurnModel turn = new TurnModel(0);
        turn.updateNewPosition(5);
        assertEquals(5, turn.getNewLocation());
    }

    @Test
    void updateNewPositionNegative(){
        TurnModel turn = new TurnModel(0);
        turn.updateNewPosition(-1);
        assertEquals(-1, turn.getNewLocation());
    }

    @Test
    void positionNotChanged(){
        TurnModel turn = new TurnModel(0);
        assertEquals(turn.getCurrentLocation(), turn.getNewLocation());
    }

    @Test
    void getCurrentLocation() {
        TurnModel turn = new TurnModel(5);
        assertEquals(5, turn.getCurrentLocation());
    }

    @Test
    void locationNotGiven(){
        TurnModel turn = new TurnModel();
        assertEquals(-1, turn.getCurrentLocation());
    }


    @Test
    void setGoToJail() {
        TurnModel turn = new TurnModel(5);
        turn.setGoToJail();
        assertTrue(turn.checkGoToJail());
    }

    @Test
    void notGoToJail(){
        TurnModel turn = new TurnModel(5);
        assertFalse(turn.checkGoToJail());
    }

    @Test
    void setBidPurchase() {
        TurnModel turn = new TurnModel(5);
        turn.setBidPurchase(1,3);
        assertEquals(1, turn.getBidPurchaser());
    }

    @Test
    void bidPurchaserNotInitializes(){
        TurnModel turn = new TurnModel(5);
        assertEquals(-1, turn.getBidPurchaser());
    }

    @Test
    void setPossibleProperties() {
        TurnModel turn = new TurnModel(5);
        List<Integer> propertyIDs = new ArrayList<>();
        propertyIDs.add(0);
        propertyIDs.add(1);
        turn.setPossibleProperties(propertyIDs);
        assertEquals(0, turn.getPossibleProperties().get(0));
        assertEquals(1, turn.getPossibleProperties().get(1));
    }

    @Test
    void getPossiblePropertiesEmpty(){
        TurnModel turn = new TurnModel(5);
        assertEquals(0, turn.getPossibleProperties().size());
    }

    @Test
    void setGettingProperty() {
        TurnModel turn = new TurnModel(5);
        turn.setGettingProperty();
        assertTrue(turn.isGettingProperty());
    }

    @Test
    void notGettingProperty(){
        TurnModel turn = new TurnModel(5);
        assertFalse(turn.isGettingProperty());
    }

    @Test
    void isGettingProperty() {
        TurnModel turn = new TurnModel(5);
        assertFalse(turn.isGettingProperty());
        turn.setGettingProperty();
        assertTrue(turn.isGettingProperty());
    }

}