package UnoModel;

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
    public void testRandomDeck() {
        Deck deck = new Deck();

        // Check if the deck has exactly 112 cards
        assertEquals(112, deck.getSize());

        // Define arrays for valid colors and types
        Card.ColorLight[] validColorsLight = Card.ColorLight.values();
        Card.TypeLight[] validTypesLight = Card.TypeLight.values();


        // Check if each card in the deck has a valid color and type
        for (int i = 0; i < deck.getSize(); i++) {
            Card card = deck.pop();
            assertTrue(isValidColor(card.getColorLight(), validColorsLight));
            assertTrue(isValidType(card.getTypeLight(), validTypesLight));

        }
    }

    // Helper method to check if a color is valid
    private boolean isValidColor(Card.ColorLight color, Card.ColorLight[] validColors) {
        for (Card.ColorLight validColor : validColors) {
            if (color == validColor) {
                return true;
            }
        }
        return false;
    }

    // Helper method to check if a type is valid
    private boolean isValidType(Card.TypeLight type, Card.TypeLight[] validTypes) {
        for (Card.TypeLight validType : validTypes) {
            if (type == validType) {
                return true;
            }
        }
        return false;
    }
}
