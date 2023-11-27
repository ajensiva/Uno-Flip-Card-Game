
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AllenAITest {
    private static Card discard;
    private static AllenAI bot1;
    private static AllenAI bot2;
    private static Round round;

    @BeforeAll
    public static void setUp() {
        bot1 = new AllenAI("Bot1");
        bot2 = new AllenAI("Bot2");
        ArrayList<Player> bots  = new ArrayList<>();
        bots.add(bot1);
        bots.add(bot2);
        round = new Round(bots);
        round.setCurrentPlayertoFirstIndex();

    }

    @BeforeEach
    public void set() {
        discard = round.discard.peek();
    }

    @Test
    void allenPlayCard() {


        bot1.getHand().clearHand();
        // make a always playable card the only card in Allen's hand
        Card theSame = new Card(Card.ColorLight.Green, Card.TypeLight.NINE, Card.ColorDark.Teal, Card.TypeDark.FIVE);
        bot1.getHand().addCard(theSame);
        // set the card at the top of the discard stack to be play-on-able
        round.discard.peek().setColorDark("Teal");

        // Allen should play the card and the top of the discard stack should be the card that allen played
        assertTrue(bot1.allenPlayCard(round, bot1.getHand()));
        assertEquals(round.discard.peek(), bot1.getAllenCardPlayed());
    }



    @Test
    void allenDrawCard() {

        bot1.getHand().clearHand();
        // make a non playable card the only card in Allen's hand
        Card notSame = new Card(Card.ColorLight.Green, Card.TypeLight.NINE, Card.ColorDark.Pink, Card.TypeDark.FIVE);
        bot1.getHand().addCard(notSame);
        // set the card at the top of the discard stack to be non playonable
        round.discard.peek().setColorDark("Teal");


        // Card at the top of the discard should be the same and allen shouldnt play a card
        assertFalse(bot1.allenPlayCard(round, bot1.getHand()));
        assertEquals(round.discard.peek(), discard);



    }
    

    @Test
    void allenPlayCardHighest() {
        round.setCurrentPlayertoFirstIndex();


        // create best card in the game and add it to allen's hand, Allen should play that card
        Card best = new Card(null, Card.TypeLight.WILD_DRAW_FOUR, null, Card.TypeDark.DARK_WILD_CARD);
        bot1.getHand().addCard(best);

        // allen should play the higher card
        bot1.allenPlayCard(round, bot1.getHand());
        assertEquals(best, bot1.getAllenCardPlayed());


    }

}
