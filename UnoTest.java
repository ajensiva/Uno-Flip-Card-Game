import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class UnoTest {

    private Uno unoGame;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // Set up the Uno game and redirect the standard output
        unoGame = new Uno();
        System.setOut(new PrintStream(outputStreamCaptor)); // Capture System.out output
    }

    @AfterEach
    public void tearDown() {
        // Clean up and reset the standard output to its original
        System.setOut(System.out);
    }

    @Test
    public void testAddPlayer() {
        // Test adding a player
//        unoGame.addPlayer("Alice");

        assertEquals(3, Uno.players.size());
        assertEquals("Alice", Uno.players.get(0).getName());

//        assertEquals(3, Uno.players.size());
//        assertEquals("Alice", Uno.players.get(0).getName());

    }

    @Test
    public void testPrintPlayers() {
        // Add some players
//        unoGame.addPlayer("Alice");
//        unoGame.addPlayer("Bob");
        // Print players
        unoGame.printPlayers();
        String printedContent = outputStreamCaptor.toString().trim();
        assertTrue(printedContent.contains("Alice"));
        assertTrue(printedContent.contains("Bob"));
    }

    @Test
    public void testRound() {
        // Add players



//        unoGame.addPlayer("Alice");
//        unoGame.addPlayer("Bob");

        // Start a round
        unoGame.round();
        assertNotNull(unoGame.currentRound);
    }

}