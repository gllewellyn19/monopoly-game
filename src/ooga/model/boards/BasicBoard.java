package ooga.model.boards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ooga.model.Bank;
import ooga.model.Board;
import ooga.model.Cards;
import ooga.model.boardSpaces.BoardSpace;
import ooga.model.DataReader;
import ooga.model.boardSpaces.NonOwnableFreeParkingSpace;
import ooga.model.boardSpaces.OwnablePropertySpace;
import ooga.model.boardSpaces.OwnableSpace;
import ooga.model.dataReaders.DataReaderClassic;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.dice.ModelDiceRollable;
import ooga.model.exceptions.ModelException;
import ooga.view.gameView.ErrorPrintable;

/**
 * Stores the information about all BoardSpaces on the board
 *
 * @author Lina Leyhausen, Cameron Jarnot
 */
public class BasicBoard extends Board {
    List<BoardSpace> spaces;

    /**
     * Initializes board model with game info, bank, cards, and dice. Uses the data reader
     * to initialize all boardspaces on the board from data
     * Assumption: the data reader will be able to correctly and in the right order get the
     * board space data from file
     *
     * @param infoForGamePlay contains data for the board
     * @param bank game bank
     * @param decks game card decks
     * @param modelDiceRollable game dice
     * @param amountPassingGo amount players get for passing go
     * @throws FileNotFoundException if files are not found
     */
    public BasicBoard(InfoForGamePlay infoForGamePlay, Bank bank, Cards decks, ModelDiceRollable modelDiceRollable,
                      Integer amountPassingGo) throws FileNotFoundException {
        spaces = new ArrayList<>();
        DataReader input = new DataReaderClassic(bank, decks);
        String file = infoForGamePlay.getBoardDirectory();
        input.setFolderPath(infoForGamePlay.getFolderPath());
            List<BoardSpace> boardSpaces = input.makeBoardSpaces(new File(file), modelDiceRollable, amountPassingGo);
            for(BoardSpace space: boardSpaces){
                addBoardSpace(space);
            }
    }

    /**
     * Return the BoardSpace at the specified location
     *
     * @param location int ID of board location to return BoardSpace of
     * @return BoardSpace at location
     */
    @Override
    public BoardSpace getBoardSpaceAtLocation(int location) {
        return spaces.get(location);
    }

    /**
     * Size of the board
     *
     * @return number of board spaces (board size)
     */
    public int boardSize(){
        return spaces.size();
    }

    private void addBoardSpace(BoardSpace boardSpace) {
        spaces.add(boardSpace);
    }

    /**
     * Finds all OwnableSpace objects on the board
     * @return a List of the OwnableSpace objects
     */
    @Override
    public List<OwnableSpace> getOwnableBoardSpaces(){
        List<OwnableSpace> ownableSpaces = new ArrayList<>();
        for(BoardSpace space:spaces){
            if(space instanceof OwnableSpace){
                ownableSpaces.add((OwnableSpace)space);
            }
        }
        return ownableSpaces;
    }

    /**
     * Finds all OwnablePropertySpace objects on the board
     * @return a List of the OwnablePropertySpace objects
     */
    @Override
    public List<OwnablePropertySpace> getOwnablePropertyBoardSpaces(){
        List<OwnablePropertySpace> ownableSpaces = new ArrayList<>();
        for(BoardSpace space:getOwnableBoardSpaces()){
            if(space instanceof OwnablePropertySpace){
                ownableSpaces.add((OwnablePropertySpace)space);
            }
        }
        return ownableSpaces;
    }

    /**
     * Finds the first NonOwnableFreeParkingSpace on the board
     * @return the integer position of the first NonOwnableFreeParkingSpace
     */
    @Override
    public int getFreeParkingPosition(){
        for(int i=0; i<spaces.size(); i++){
            if(spaces.get(i) instanceof NonOwnableFreeParkingSpace){
                return i;
            }
        }
        return -1;
    }
}
