package test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exceptions.InvalidPackException;
import org.junit.jupiter.api.*;
import game.*;
import exceptions.InvalidPackException;

import java.io.FileNotFoundException;

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
    @Disabled
    public void isValidPackTest(){
        path = "src/test/test_pack_2";
        players = 4;
        InvalidPackException packExceptionThrown = assertThrows(
                InvalidPackException.class,
                PackTest::setUp,
                "Expected setUp to thrown InvalidPackException didnt"
        );
        assertEquals("game.Pack file doesn't have enough lines!", packExceptionThrown.getMessage());

        path = "src/test/test_pack_1";
        players = 3;
        packExceptionThrown = assertThrows(
                InvalidPackException.class,
                PackTest::setUp,
                "Expected setUp to thrown InvalidPackException didnt"
        );
        assertEquals("game.Pack file doesn't have enough lines!", packExceptionThrown.getMessage());

        path = "src/test/this_doesnt_exist";
        players = 4;
        FileNotFoundException fileNotFoundThrown = assertThrows(
                FileNotFoundException.class,
                PackTest::setUp,
                "Expected setUp to thrown InvalidPackException didnt"
        );
        Assertions.assertTrue(fileNotFoundThrown.getMessage().contains("(No such file or directory)"));
    }

    @Test
    @Disabled
    public void createTest(){
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
