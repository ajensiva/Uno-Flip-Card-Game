package UnoModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player testPlayer;

    @BeforeEach
    public void setUp() {
        // Initialize a test player for each test case
        testPlayer = new Player("Alice");
    }

    @Test
    public void testGetScore() {
        assertEquals(0, testPlayer.getScore());
    }

    @Test
    public void testSetScore() {
        testPlayer.setScore(100);
        assertEquals(100, testPlayer.getScore());

        // Ensure that the score cannot be set to a negative value
        testPlayer.setScore(-50);
        assertEquals(100, testPlayer.getScore()); // Score should remain unchanged
    }

    @Test
    public void testGetHand() {
        assertNotNull(testPlayer.getHand());
        assertTrue(testPlayer.getHand() instanceof Hand);
    }

    @Test
    public void testGetName() {
        assertEquals("Alice", testPlayer.getName());
    }

    @Test
    public void testEquals() {
        // Create a player with the same name and score
        Player equalPlayer = new Player("Alice");
        equalPlayer.setScore(0);

        // Create a player with a different name and score
        Player differentPlayer = new Player("Bob");
        differentPlayer.setScore(50);

        // Verify that the player is equal to the equalPlayer
        assertTrue(testPlayer.equals(equalPlayer));

        // Verify that the player is not equal to the differentPlayer
        assertFalse(testPlayer.equals(differentPlayer));
    }
}