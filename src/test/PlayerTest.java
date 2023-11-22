package test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
public class PlayerTest {
    private static Deck deck;
    private static Player player;
    @BeforeEach
    public void setUp(){
        deck = new Deck(1);
        player = new Player("Player1", 1);
        for (int i = 1; i<6; i++){
            Card card = mock(Card.class);
            when(card.getFaceValue()).thenReturn(i);
            deck.addCard(card);
        }
    }
    @Test
    public void addCardTest() throws InterruptedException {
        Card card = mock(Card.class);
        deck.addCard(card);
        assertEquals(deck.draw(), card);
    }
    @Test
    public void getHandTest(){
        for (int i = 1; i<5; i++){
            Card card = mock(Card.class);
            when(card.getFaceValue()).thenReturn(i);
            player.addCard(card);
        }
        assertEquals(player.toString(), "1 2 3 4 ");
    }
    @Test
    public void getNumberTest(){
        assertEquals(player.getNumber(), 1);
    }
    @Test
    public void getNameTest(){
        assertEquals(player.getName(), "Player1");
    }
    @Test
    public void getOutputFileTest(){
        assertEquals(player.getOutputFilePath(), "Player1_output.txt");
    }
    @Test void toStringTest(){
        for (int i = 1; i<6; i++) {
            Card card = mock(Card.class);
            when(card.getFaceValue()).thenReturn(i);
            player.addCard(card);
        }
        assertEquals(player.toString(), "1 2 3 4 5 ");
    }

}
