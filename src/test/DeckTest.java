package test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;

public class DeckTest {
    private static Deck deck;
    public static Card card1= new Card (1);
    public static Card card2= new Card (2);
    public static Card card3= new Card (3);
    public static Card card4= new Card (4);
    public static Card card5= new Card (5);

    public static Card card6 = new Card (6);
    public static Card card7= new Card (7);

    @BeforeEach
    public void setUp(){
        deck = new Deck(1);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);
    }
    @Test
    public void drawTest() throws InterruptedException {
        assertEquals(deck.draw(), card5);
    }
    @Test
    public void addCardTest() throws InterruptedException {
        deck.addCard(card6);
        assertEquals(deck.draw(), card6);
    }
    @Test
    public void addTest() throws InterruptedException{
        deck.discard(card7);
        for (int i=0; i<5; i++){
            deck.draw();
        }
        assertEquals(deck.draw(), card7);
    }



}


