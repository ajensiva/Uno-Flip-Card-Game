import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {

    private Hand hand;

    @BeforeEach
    public void setUp() {
        hand = new Hand();
    }

    @Test
    public void testAddCard() {
        Card card = new Card(Card.ColorLight.Red, Card.TypeLight.ONE, Card.ColorDark.Pink, Card.TypeDark.ONE);
        hand.addCard(card);

        assertEquals(1, hand.getSize());
        assertEquals(card, hand.getCard(0));
    }

    @Test
    public void testRemoveCard() {
        Card card1 = new Card(Card.ColorLight.Red, Card.TypeLight.ONE, Card.ColorDark.Pink, Card.TypeDark.ONE);
        Card card2 = new Card(Card.ColorLight.Blue, Card.TypeLight.TWO, Card.ColorDark.Teal, Card.TypeDark.TWO);
        hand.addCard(card1);
        hand.addCard(card2);

        Card removedCard = hand.removeCard(card1);

        assertEquals(1, hand.getSize());
        assertEquals(card2, hand.getCard(0));
        assertEquals(card1, removedCard);
    }

    @Test
    public void testGetHandList() {
        Card card1 = new Card(Card.ColorLight.Red, Card.TypeLight.ONE, Card.ColorDark.Pink, Card.TypeDark.ONE);
        Card card2 = new Card(Card.ColorLight.Blue, Card.TypeLight.TWO, Card.ColorDark.Teal, Card.TypeDark.TWO);
        hand.addCard(card1);
        hand.addCard(card2);

        ArrayList<Card> handList = hand.getHandList();

        assertEquals(2, handList.size());
        assertTrue(handList.contains(card1));
        assertTrue(handList.contains(card2));
    }

    @Test
    public void testGetCard() {
        Card card1 = new Card(Card.ColorLight.Red, Card.TypeLight.ONE, Card.ColorDark.Pink, Card.TypeDark.ONE);
        Card card2 = new Card(Card.ColorLight.Blue, Card.TypeLight.TWO, Card.ColorDark.Teal, Card.TypeDark.TWO);
        hand.addCard(card1);
        hand.addCard(card2);

        assertEquals(card1, hand.getCard(0));
        assertEquals(card2, hand.getCard(1));
    }

    @Test
    public void testGetSize() {
        Card card1 = new Card(Card.ColorLight.Red, Card.TypeLight.ONE, Card.ColorDark.Pink, Card.TypeDark.ONE);
        Card card2 = new Card(Card.ColorLight.Blue, Card.TypeLight.TWO, Card.ColorDark.Teal, Card.TypeDark.TWO);
        hand.addCard(card1);
        hand.addCard(card2);

        assertEquals(2, hand.getSize());
    }

    @Test
    public void testToString() {
        Card card1 = new Card(Card.ColorLight.Red, Card.TypeLight.ONE, Card.ColorDark.Pink, Card.TypeDark.ONE);
        Card card2 = new Card(Card.ColorLight.Blue, Card.TypeLight.TWO, Card.ColorDark.Teal, Card.TypeDark.TWO);
        hand.addCard(card1);
        hand.addCard(card2);

        String expectedString = " [0] Color: Red, Type: ONE\n [1] Color: Blue, Type: TWO\n";
        assertEquals(expectedString, hand.toString());
    }
}
