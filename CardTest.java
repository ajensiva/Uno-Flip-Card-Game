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
}
