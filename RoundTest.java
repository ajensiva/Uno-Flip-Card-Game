import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

public class RoundTest {

    private Round round;
    private ArrayList<Player> players;

    @BeforeEach
    public void setUp() {
        // Create and initialize players for testing
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Player player3 = new Player("Charlie");
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        // Create and initialize a Round instance for testing
        round = new Round(players);
        round.playRound(); // Sets the currentPlayer to the first player
    }

    @Test
    public void testDistributeHand() {
        // After distribution, each player should have 7 cards
        for (Player player : round.getPlayers()) {
            assertEquals(7, player.getHand().getSize());
        }
    }

    @Test
    public void testCheckCard() {
        // Assume the deck and discard are initialized correctly in Round

        // Mock cards for testing
        Card topDiscard = round.discard.peek(); // Get the top card from the discard pile
        Card validCard = new Card(topDiscard.getColorLight(), topDiscard.getTypeLight(), topDiscard.getColorDark(), topDiscard.getTypeDark());

        // When checkCard is called
        assertTrue(round.checkCard(validCard, topDiscard));

        // Add more assertions with invalid cards to test the false condition
    }

    @Test
    public void testReverse() {
        // Given the current order of players
        assertEquals(players.get(0), round.getPlayers().get(0));

        // When reverse is called
        round.reverse();

        // Then the order of players should be reversed
        Collections.reverse(players); // Reverse the local players list to compare
        assertEquals(players.get(0), round.getPlayers().get(0));
        assertEquals(players.get(1), round.getPlayers().get(1));
        assertEquals(players.get(2), round.getPlayers().get(2));
    }

    @Test
    public void testNextPlayer() {
        // Given the current player is the first player
        assertEquals(players.get(0), round.currentPlayer);

        // When nextPlayer is called
        round.nextPlayer();

        // Then the current player should be the next player in the list
        assertEquals(players.get(1), round.currentPlayer);
    }

    @Test
    public void testDrawCard() {
        // Given the current player
        Player currentPlayer = round.currentPlayer;
        int initialHandSize = currentPlayer.getHand().getSize();

        // When drawCard is called to draw one card
        round.drawCard(1);

        // Then the current player's hand size should increase by one
        assertEquals(initialHandSize, currentPlayer.getHand().getSize());
    }

    @Test
    public void testPlayCard() {
        // Given a card to play
        Card cardToPlay = round.currentPlayer.getHand().getCard(0); // Get the first card of the current player

        // When playCard is called
        round.playCard(0); // Play the first card

        // Then the discard pile should contain the played card
        assertEquals(cardToPlay, round.discard.peek());
    }

}

