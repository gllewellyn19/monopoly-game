package ooga.model.gamePlay;

import ooga.view.tradeModel.TradeModel;

import java.util.List;

public interface PropertyUpgradeable {

    List<Integer> findMortgageableProperties(int playerID);
    List<Integer> findMortgagedProperties(int playerID);
    List<Integer> findHouseEligibleProperties(int playerID);
    List<Integer> findHotelEligibleProperties(int playerID);
    List<Integer> findAllPlayerProperties(int playerID);
    void mortgageProperty(int spaceID);
    void unmortgageProperty(int spaceID);
    void addHouseToProperty(int spaceID);
    void addHotelToProperty(int spaceID);
    void tradeProperty(TradeModel tradeModel);

}