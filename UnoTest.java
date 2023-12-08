import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
        unoGame.addPlayer("Alice", false);

        assertEquals(5, Uno.players.size());
        assertEquals("Alice", Uno.players.get(0).getName());

        assertEquals(5, Uno.players.size());
        assertEquals("Alice", Uno.players.get(0).getName());

    }



    @Test
    public void testRound() {
        // Add players

        unoGame.addPlayer("Alice", false);
        unoGame.addPlayer("Bob", false);

        // Start a round
        unoGame.round();
        assertNotNull(unoGame.currentRound);
    }

    @Test
    public void testSaveGame() throws IOException {
        unoGame.addPlayer("Alice", false);
        unoGame.addPlayer("Bob", false);
        unoGame.round();
        unoGame.saveGame();
        assertTrue(Files.exists(Path.of("saveGameXML.xml")), "XML file not created");
        String xmlContent = Files.readString(Path.of("saveGameXML.xml"));

        String expected = unoGame.unoToXML();

        assertEquals(expected, xmlContent, "Unexpected XML content");

    }

    @Test
    public void TestSavePlayerMove() throws IOException{
        unoGame.addPlayer("Alicia", true);
        unoGame.addPlayer("Bobby", true);
        unoGame.round();
        unoGame.savePlayerMove();
        assertTrue(Files.exists(Path.of("savePlayerMoveXML.xml")), "XML file not created");
        String xmlContent = Files.readString(Path.of("savePlayerMoveXML.xml"));

        String expected = unoGame.unoToXML();

        assertEquals(expected, xmlContent, "Unexpected XML content:"  );



    }







}