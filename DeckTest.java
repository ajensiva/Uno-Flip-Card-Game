import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    private Deck testDeck;

    @BeforeEach
    public void setUp() {
        // Initialize a test deck for each test case
        testDeck = new Deck();
    }

    @Test
    public void testPop() {
        int initialSize = testDeck.FULL_DECK;
        Card poppedCard = testDeck.pop();

        // Ensure that a card is popped from the deck
        assertNotNull(poppedCard);

        int finalSize = initialSize - 1;
        assertEquals(finalSize, testDeck.getSize());
    }

    @Test
    public void testDeckSize() {
        assertEquals(testDeck.FULL_DECK, testDeck.getSize());
    }

    @Test
    public void testToString() {
        String deckString = testDeck.toString();
        String[] cardStrings = deckString.split("\n");

        // Ensure that the number of cards in the deck matches the expected size
        assertEquals(testDeck.FULL_DECK, cardStrings.length);

        for (String cardString : cardStrings) {
            // Verify that each card in the deck is represented correctly in the deck's string format
            assertNotNull(cardString);
            assertTrue(cardString.matches("Color:\s +, Type: + \s"));
        }
    }
}