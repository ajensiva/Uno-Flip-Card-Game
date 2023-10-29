import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest {
    private Card testCard;

    @BeforeEach
    public void setUp() {
        // Initialize a test card for each test case
        testCard = new Card(Card.ColorLight.Red, Card.TypeLight.ONE, Card.ColorDark.Pink, Card.TypeDark.ONE);
    }

    @Test
    public void testGetColorLight() {
        assertEquals(Card.ColorLight.Red, testCard.getColorLight());
    }

    @Test
    public void testGetColorDark() {
        assertEquals(Card.ColorDark.Pink, testCard.getColorDark());
    }

    @Test
    public void testGetTypeLight() {
        assertEquals(Card.TypeLight.ONE, testCard.getTypeLight());
    }

    @Test
    public void testGetTypeDark() {
        assertEquals(Card.TypeDark.ONE, testCard.getTypeDark());
    }

    @Test
    public void testGetValueInLightMode() {
        // Ensure that the value returned in light mode matches the TypeLight's value
        assertEquals(testCard.getTypeLight().getValue(), testCard.getValue());
    }

    @Test
    public void testGetValueInDarkMode() {
        // Set the game to dark mode
        Round.darkmode = true;
        // Ensure that the value returned in dark mode matches the TypeDark's value
        assertEquals(testCard.getTypeDark().getValue(), testCard.getValue());
        // Reset the game mode to light mode
        Round.darkmode = false;
    }

    @Test
    public void testSetColorLight() {
        // Test setting the color to a different color
        assertTrue(testCard.setColorLight("Blue"));
        assertEquals(Card.ColorLight.Blue, testCard.getColorLight());

        // Test setting the color to an invalid color
        assertFalse(testCard.setColorLight("InvalidColor"));
        assertEquals(Card.ColorLight.Blue, testCard.getColorLight()); // Color should not change
    }

    @Test
    public void testToString() {
        String expectedString = "Color: Red, Type: ONE";
        assertEquals(expectedString, testCard.toString());
    }

    public static class PlayerTest {

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
}
