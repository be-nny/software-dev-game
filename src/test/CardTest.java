package test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;

public class CardTest {

    private static Card card1;
    private static Card card2;
    private static Card card3;

    @BeforeEach
    public void setUp(){
        card1 = new Card(14);
        card2 = new Card(1);
        card3 = new Card(50);
    }
    @Test
    public void getFaceValueTest(){
        assertEquals(card1.getFaceValue(), 14);
        assertEquals(card2.getFaceValue(), 1);
        assertEquals(card3.getFaceValue(), 50);
    }
}