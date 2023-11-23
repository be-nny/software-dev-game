package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
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
    @Test
    public void isWinTest() throws NoSuchFieldException {
        String methodName = "isWin";

        Field handField = Player.class.getDeclaredField("hand");
        handField.setAccessible(true);

        player = new Player("Player1", 1);
        for (int i = 1; i<5; i++){
            Card card = mock(Card.class);
            when(card.getFaceValue()).thenReturn(1);
            player.addCard(card);
        }

        assertDoesNotThrow(() -> {
            Method playerIsWinMethod = Player.class.getDeclaredMethod(methodName);
            playerIsWinMethod.setAccessible(true);

            playerIsWinMethod.invoke(player);
            boolean result = (boolean) playerIsWinMethod.invoke(player);
            assertTrue(result);
        });
    }

    @Test
    public void notifyWinListenerTest() throws NoSuchFieldException, IllegalAccessException{
        String methodName = "notifyWinListener";

        Field listenersField = Player.class.getDeclaredField("listeners");
        listenersField.setAccessible(true);

        ArrayList<WinListener> listeners = new ArrayList<>();
        WinListener mockListener1 = mock(WinListener.class);
        listeners.add(mockListener1);
        listenersField.set(player, listeners);

        assertDoesNotThrow(() -> {
            Method playerNotifyTest = Player.class.getDeclaredMethod(methodName);
            playerNotifyTest.setAccessible(true);

            playerNotifyTest.invoke(player);
            verify(mockListener1).notifyPlayerWon(player);
        });

    }
}
