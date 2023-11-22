package test;

import exceptions.InvalidPackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import game.*;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class PackTest {

    private static Pack pack;
    private static String path = "src/test/test_pack_1";
    private static int players = 4;

    @BeforeAll
    public static void setUp() throws InvalidPackException, FileNotFoundException {
        pack = new Pack(path, players);
    }

    @Test
    public void getCardsTest(){
        assertEquals(pack.getCards().size(), 32);
    }
    @Test
    public void getTopCardTest(){
        assertEquals(pack.getCards().peek().getFaceValue(), 5);
        assertEquals(pack.getTopCard().getFaceValue(), 5);
    }
    @Test
    public void notEnoughLinesTest(){
        path = "src/test/test_pack_2";
        players = 4;
        InvalidPackException packExceptionThrown = assertThrows(
                InvalidPackException.class,
                PackTest::setUp,
                "Expected setUp to thrown InvalidPackException didnt"
        );
        assertEquals("game.Pack file doesn't have enough lines!", packExceptionThrown.getMessage());
    }
    @Test
    public void invalidPlayerInputTest(){
        path = "src/test/test_pack_1";
        players = 3;
        InvalidPackException packExceptionThrown = assertThrows(
                InvalidPackException.class,
                PackTest::setUp,
                "Expected setUp to thrown InvalidPackException didnt"
        );
        assertEquals("game.Pack file doesn't have enough lines!", packExceptionThrown.getMessage());
    }
    @Test
    public void invalidFileTest(){
        path = "src/test/this_doesnt_exist";
        players = 4;
        FileNotFoundException fileNotFoundThrown = assertThrows(
                FileNotFoundException.class,
                PackTest::setUp,
                "Expected setUp to thrown InvalidPackException didnt"
        );
        Assertions.assertTrue(fileNotFoundThrown.getMessage().contains("(No such file or directory)"));
    }
}
