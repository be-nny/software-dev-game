package test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
public class PlayerTest {
    private Deck deck;
    private Player player;
    @BeforeEach
    public void setUp(){
        deck = new Deck(1);
        for (int i = 1; i<6; i++){
            Card card = mock(Card.class);
            when(card.getFaceValue()).thenReturn(i);
            deck.addCard(card);
        }
        player = new Player("Player1", 1);
        for (int i = 1; i<5; i++){
            Card card = mock(Card.class);
            when(card.getFaceValue()).thenReturn(i);
            player.addCard(card);
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
    @Test
    public void toStringTest(){
        assertEquals(player.toString(), "1 2 3 4 ");
    }
    @Test
    public void turnTest(){
        Class<Integer> parameterType = int.class;
        String methodName = "turn";
        Object[] args = new Object[]{1};
        Method playerTurnMethod;

        try {
            playerTurnMethod = Player.class.getDeclaredMethod(methodName, parameterType);
            playerTurnMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        try {
            playerTurnMethod.invoke(player, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
