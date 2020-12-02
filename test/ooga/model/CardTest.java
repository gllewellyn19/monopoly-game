package ooga.model;

import ooga.model.cards.Card;
import ooga.model.cards.ClassicCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceCardTest {

    @Test
    void savedChanceMessage(){
        String[] data = "Advance to Go (Collect $200),0,MoveGetMoney,200,0,advancetogo.jpg".split(",");
        Card card = new ClassicCard(data);
        assertEquals("Advance to Go (Collect $200)", card.getCardMessage());
    }

    @Test
    void savedChanceCardType(){
        String[] data = "Advance to Go (Collect $200),0,MoveGetMoney,200,0,advancetogo.jpg".split(",");
        Card card = new ClassicCard(data);
        assertEquals("MoveGetMoney", card.getCardType().toString());
    }

    @Test
    void savedChanceMoneyChange(){
        String[] data = "Advance to Go (Collect $200),0,MoveGetMoney,200,0,advancetogo.jpg".split(",");
        Card card = new ClassicCard(data);
        assertEquals(200, card.getMoneyChange());
    }

    @Test
    void savedChanceSpaceChange(){
        String[] data = "Advance to Go (Collect $200),0,MoveGetMoney,200,0,advancetogo.jpg".split(",");
        Card card = new ClassicCard(data);
        assertEquals(0, card.getPositionChange());
    }

    @Test
    void savedCommunityMessage(){
        String[] data = "Bank error in your favor,7,GetMoney,200,0,./././data/ClassicMonopoly/communitycardimages/bank-in-favor.png".split(",");
        Card card = new ClassicCard(data);
        assertEquals("Bank error in your favor", card.getCardMessage());
    }

    @Test
    void savedCommunityCardType(){
        String[] data = "Bank error in your favor,7,GetMoney,200,0,./././data/ClassicMonopoly/communitycardimages/bank-in-favor.png".split(",");
        Card card = new ClassicCard(data);
        assertEquals("GetMoney", card.getCardType().toString());
    }

    @Test
    void savedCommunityMoneyChance(){
        String[] data = "Bank error in your favor,7,GetMoney,200,0,./././data/ClassicMonopoly/communitycardimages/bank-in-favor.png".split(",");
        Card card = new ClassicCard(data);
        assertEquals(200, card.getMoneyChange());
    }

    @Test
    void savedCommunitySpaceChange(){
        String[] data = "Bank error in your favor,7,GetMoney,200,0,./././data/ClassicMonopoly/communitycardimages/bank-in-favor.png".split(",");
        Card card = new ClassicCard(data);
        assertEquals(0, card.getPositionChange());
    }

}