package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javafx.stage.Stage;
import ooga.DukeApplicationTest;
import ooga.Main;
import ooga.model.cards.Card;
import ooga.model.cards.ClassicCards;
import ooga.model.dataReaders.DataReaderClassic;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.exceptions.CardDataNotFound;
import ooga.model.exceptions.NecessaryFileNotFound;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class CardsTest extends DukeApplicationTest {

    private DataReader dataReader;

    @Start
    public void start (Stage stage) {
        dataReader = new DataReaderClassic();

    }


    @Test
    void initializeChanceDeck(){
        Properties properties = readInPropertiesClassicMonopoly();
        javafxRun(()->dataReader.setGameProperties(properties));
        javafxRun(()->dataReader.setFolderPath(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY));
        InfoForGamePlay info = new InfoForGamePlay(properties, DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY);
        Cards decks = new ClassicCards(info);
        int count = 0;
        for(Card chance: ((ClassicCards) decks).getChanceDeck()){
            if(count==1) {
                assertEquals("Advance to Illinois Ave—If you pass Go collect $200", chance.getCardMessage());
                break;
            }
            count++;
        }
    }

    @Test
    void initializeCommunityDeck(){
        Properties properties = readInPropertiesClassicMonopoly();
        javafxRun(()->dataReader.setGameProperties(properties));
        javafxRun(()->dataReader.setFolderPath(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY));
        InfoForGamePlay info = new InfoForGamePlay(properties, DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY);
        Cards decks = new ClassicCards(info);
        int count = 0;
        for(Card community: ((ClassicCards) decks).getCommunityDeck()){
            if(count==1) {
                assertEquals("Bank error in your favor", community.getCardMessage());
                break;
            }
            count++;
        }

    }

    @Test
    void topChanceCard(){
        Properties properties = readInPropertiesClassicMonopoly();
        javafxRun(()->dataReader.setGameProperties(properties));
        javafxRun(()->dataReader.setFolderPath(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY));
        InfoForGamePlay info = new InfoForGamePlay(properties, DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY);
        Cards decks = new ClassicCards(info);
        int id = decks.getTopChanceCard().getId();
        assertEquals(0, id);
    }

    @Test
    void topCommunityCard(){
        Properties properties = readInPropertiesClassicMonopoly();
        javafxRun(()->dataReader.setGameProperties(properties));
        javafxRun(()->dataReader.setFolderPath(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY));
        InfoForGamePlay info = new InfoForGamePlay(properties, DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY);
        Cards decks = new ClassicCards(info);
        int id = decks.getTopCommunityCard().getId();
        assertEquals(12, id);
    }

    @Test
    void shuffleChanceCards(){
        Properties properties = readInPropertiesClassicMonopoly();
        javafxRun(()->dataReader.setGameProperties(properties));
        javafxRun(()->dataReader.setFolderPath(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY));
        InfoForGamePlay info = new InfoForGamePlay(properties, DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY);
        Cards decks = new ClassicCards(info);
        decks.shuffleChanceDeck();
        int count = 0;
        for(Card card : ((ClassicCards) decks).getChanceDeck()){
            count++;
        }
        assertEquals(12, count);
    }

    @Test
    void shuffleCommunityCards(){
        Properties properties = readInPropertiesClassicMonopoly();
        javafxRun(()->dataReader.setGameProperties(properties));
        javafxRun(()->dataReader.setFolderPath(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY));
        InfoForGamePlay info = new InfoForGamePlay(properties, DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY);
        Cards decks = new ClassicCards(info);
        decks.shuffleCommunityDeck();
        int count = 0;
        for(Card card : ((ClassicCards) decks).getCommunityDeck()){
            count++;
        }
        assertEquals(15, count);
    }

    private Properties readInPropertiesClassicMonopoly() {
        Properties gameValues = new Properties();
        try {
            FileInputStream fis = new FileInputStream(DataReaderTest.FILE_PATH_CLASSIC_MONOPOLY +
                    DataReader.NAME_PROPERTIES_FILE + DataReader.PROPERTIES_FILE_EXTENSION);
            gameValues.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new NecessaryFileNotFound("propertiesFile");
        }
        return gameValues;
    }

}