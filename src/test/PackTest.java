package test;

import exceptions.InvalidPackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import game.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    public void isValidPack(){
        assertDoesNotThrow(() -> {
            Field fileField = Pack.class.getDeclaredField("myFileObj");
            fileField.setAccessible(true);
            fileField.set(pack, null);
        });

        assertDoesNotThrow(() -> {
            Field readerField = Pack.class.getDeclaredField("myReader");
            readerField.setAccessible(true);

            Scanner mockScanner = mock(Scanner.class);
            final int[] number = {1};

            doAnswer((Answer<String>) invocation -> {
                number[0]++;
                return String.valueOf(number[0]);
            }).when(mockScanner).nextLine();

            doAnswer((Answer<Boolean>) invocation -> number[0] <= 32).when(mockScanner).hasNextLine();
            readerField.set(pack, mockScanner);
        });
    }
}
