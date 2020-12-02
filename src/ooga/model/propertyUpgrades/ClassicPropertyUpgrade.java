package ooga.model.propertyUpgrades;

import ooga.model.*;
import ooga.model.boardSpaces.BoardSpace;
import ooga.model.boardSpaces.OwnablePropertySpace;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.model.players.Player;
import ooga.view.tradeModel.TradeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassicPropertyUpgrade extends PropertyUpgrade {

    private final Board board;
    private final Players players;
    private final Bank bank;

    public ClassicPropertyUpgrade(Board boardIn, Players playersIn, Bank bankIn){
        board = boardIn;
        players = playersIn;
        bank = bankIn;
    }

    /**
     * Searches player property list for properties that can be mortgaged
     * @param playerID is the unique ID of the player whose properties
     *                 could be mortgaged
     * @return a list of property spaceIDs that could be mortgaged
     */
    @Override
    public List<Integer> findMortgageableProperties(int playerID) {
        return getMortgagedOrUnmortgagedList(playerID,false);
    }

    /**
     * Searches player property list for properties that are currently mortgaged
     * @param playerID is the unique ID of the player whose properties
     *                 might be mortgaged
     * @return a list of property spaceIDs that are mortgaged
     */
    @Override
    public List<Integer> findMortgagedProperties(int playerID) {
        return getMortgagedOrUnmortgagedList(playerID,true);
    }

    private List<Integer> getMortgagedOrUnmortgagedList(int playerID,boolean mortgaged){
        List<OwnableSpace> ownableSpaces = board.getOwnableBoardSpaces();
        int i=0;
        List<Integer> spacesToMortgage = new ArrayList<>();
        while(i<ownableSpaces.size() && spacesToMortgage.size() == 0){
            OwnableSpace currentSpace = ownableSpaces.get(i);
            if(currentSpace.getOwner().getId() == playerID && (currentSpace.checkIfMortgaged()==mortgaged)){
                int currentSpaceLocation = currentSpace.getLocation();
                if(currentSpaceLocation != -1){
                    spacesToMortgage.add(currentSpaceLocation);
                }
            }
        }
        return spacesToMortgage;
    }

    /**
     * Find playerID's properties that can have a house placed on them
     * @param playerID is the unique ID of the players whose properties
     *                 are analyzed
     * @return a list of property space IDs that can have a house placed
     *          list is empty if no properties are eligible
     */
    @Override
    public List<Integer> findHouseEligibleProperties(int playerID) {
        List<Integer> houseEligibleProperties = new ArrayList<>();
        List<OwnablePropertySpace> properties = board.getOwnablePropertyBoardSpaces();
        for (OwnablePropertySpace currentProperty : properties) {
            if (currentProperty.getOwner().getId() == playerID) {
                if (currentProperty.canBuildHouse()) {
                    houseEligibleProperties.add(currentProperty.getLocation());
                }
            }
        }
        return houseEligibleProperties;
    }

    /**
     * Find playerID's properties that can have a hotel placed on them
     * @param playerID is the unique ID of the players whose properties
     *                 are analyzed
     * @return a list of property space IDs that can have a hotel placed
     *          list is empty if no properties are eligible
     */
    @Override
    public List<Integer> findHotelEligibleProperties(int playerID) {
        List<Integer> hotelEligibleProperties = new ArrayList<>();
        List<OwnablePropertySpace> properties = board.getOwnablePropertyBoardSpaces();
        for (OwnablePropertySpace currentProperty : properties) {
            if (currentProperty.getOwner().getId() == playerID) {
                if (currentProperty.canBuildHotel()) {
                    hotelEligibleProperties.add(currentProperty.getLocation());
                }
            }
        }
        return hotelEligibleProperties;
    }

    /**
     * Find all properties owned by a given player
     * @param playerID is the ID of the player whose properties will be found
     * @return a list of space IDs that the player owns
     */
    @Override
    public List<Integer> findAllPlayerProperties(int playerID) {
        List<Integer> playerProperties = new ArrayList<>();
        List<OwnableSpace> ownableSpaces = board.getOwnableBoardSpaces();
        for(OwnableSpace space:ownableSpaces){
            if(space.getOwner().getId() == playerID){
                playerProperties.add(space.getLocation());
            }
        }
        return playerProperties;
    }

    /**
     * Convert the property at the given ID to a mortgage
     * @param spaceID represents the space to be mortgaged
     */
    @Override
    public void mortgageProperty(int spaceID) {
        List<OwnableSpace> ownableSpaces = board.getOwnableBoardSpaces();
        int i=0;
        OwnableSpace spaceToMortgage = null;
        while(i<ownableSpaces.size() && spaceToMortgage == null){
            if(ownableSpaces.get(i).getLocation() == spaceID){
                spaceToMortgage = ownableSpaces.get(i);
                spaceToMortgage.mortgage();
            }
        }
    }

    /**
     * Revert the property at the given ID to be unmortgaged
     * @param spaceID represents the space to be unmortgaged
     */
    @Override
    public void unmortgageProperty(int spaceID) {
        BoardSpace property = board.getBoardSpaceAtLocation(spaceID);
        if(property instanceof OwnableSpace){
            ((OwnableSpace) property).unmortgage();
        }
    }

    /**
     * Adds a house to a given property if the property is an ownable
     * property space
     * @param spaceID is the location and ID of the space on the board
     */
    @Override
    public void addHouseToProperty(int spaceID) {
        BoardSpace currentSpace = board.getBoardSpaceAtLocation(spaceID);
        if(currentSpace instanceof OwnablePropertySpace){
            ((OwnablePropertySpace)currentSpace).addSmallEstateToProperty();
        }
    }

    /**
     * Adds a hotel to a given property if the property is an ownable
     * property space
     * @param spaceID is the location and ID of the space on the board
     */
    @Override
    public void addHotelToProperty(int spaceID) {
        BoardSpace currentSpace = board.getBoardSpaceAtLocation(spaceID);
        if(currentSpace instanceof OwnablePropertySpace){
            ((OwnablePropertySpace)currentSpace).addLargeEstateToProperty();
        }
    }

    /**
     * Changes ownership of traded properties and updates the
     * involved players' bank balances
     * @param tradeModel describes the trade to be made, including
     *                   the players and properties involved and the
     *                   value of the trade
     */
    @Override
    public void tradeProperty(TradeModel tradeModel) {
        int tradeAmount = tradeModel.getTradeAmount();
        int trader = tradeModel.getAcceptor();
        int acceptor = tradeModel.getAcceptor();

        swapOwnership(acceptor, tradeModel.getAcceptorNewProperties());
        swapOwnership(trader, tradeModel.getTraderNewProperties());
        bank.decreaseBalance(trader,tradeAmount);
        bank.increaseBalance(trader,tradeAmount);

    }

    private void swapOwnership(int newOwner, List<Integer> newPropertyList) {
        Map<Integer, Player> playerMap = players.getPlayers();
        if (playerMap.containsKey(newOwner)) {
            for (int spaceID : newPropertyList) {
                ((OwnableSpace) board.getBoardSpaceAtLocation(spaceID)).setOwner(playerMap.get(newOwner));
            }
        }
    }

}
