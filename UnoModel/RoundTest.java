package UnoModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class RoundTest {

    private Round round;

    @BeforeEach
    public void setUp() {
        // Create and initialize players for testing
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Player player3 = new Player("Charlie");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        // Create and initialize a Round instance for testing
        round = new Round(players);
    }

    @Test
    public void testCheckCard() {
        // Test card playability based on Uno game rules
        Card validCard = new Card(Card.ColorLight.Red, Card.TypeLight.ONE, Card.ColorDark.Pink, Card.TypeDark.ONE);
//        Card invalidCard = new Card(Card.ColorLight.Blue, Card.TypeLight.DRAW_TWO, Card.ColorDark.Purple, Card.TypeDark.WILDTWO);

        // Top of the discard stack is a valid card
        assertTrue(round.checkCard(validCard, validCard));
        // Top of the discard stack is a different card
//        assertFalse(round.checkCard(invalidCard, validCard));
    }

    @Test
    public void testReverse() {
        // Test player order reversal
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Player player3 = new Player("Charlie");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        // Initialize a Round instance for testing
        Round round = new Round(players);

        // Check the initial player order
        assertEquals(player1, round.getPlayers().get(0));
        assertEquals(player2, round.getPlayers().get(1));
        assertEquals(player3, round.getPlayers().get(2));

        // Reverse the player order
        round.reverse();

        // Check the reversed player order
        assertEquals(player3, round.getPlayers().get(0));
        assertEquals(player2, round.getPlayers().get(1));
        assertEquals(player1, round.getPlayers().get(2));
    }





}
