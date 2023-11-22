import java.util.ArrayList;
import java.util.Random;

public class AllenAI extends Player {
    /**
     * Constructor to create a new player with the given name.
     *
     * @param name The name of the player.
     */

    Card allenCardPlayed;

    public AllenAI(String name) {
        super(name);
    }

    // this will play the card with the highest value
    public boolean allenPlayCard(Round currentRound, Hand hand) {
        /*
         * get the bot's hand to see all which cards are playable first
         * play the card with the most value
         */
        ArrayList<Card> allenCards = hand.getHandList();
        int index = 0;

        for (Card card : allenCards) {
            boolean canPlay = currentRound.checkCard(card, currentRound.discard.peek());
            // playable
            if (canPlay && (card.getValue() >= allenCards.get(index).getValue())) { // if the value of current value >
                                                                                    // old then update new highest card
                index = allenCards.indexOf(card);
            }

        }

        if (allenCards.get(index).getTypeLight().equals(Card.TypeLight.WILDTWO)
                || allenCards.get(index).getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)) {
            Random rand = new Random();
            int colour = rand.nextInt(4);

            if (colour == 0) {
                allenCards.get(index).setColorLight("Red");
                currentRound.discard.peek().setColorLight("Red");

            }
            if (colour == 1) {
                allenCards.get(index).setColorLight("Blue");
                currentRound.discard.peek().setColorLight("Blue");

            }
            if (colour == 2) {
                allenCards.get(index).setColorLight("Yellow");
                currentRound.discard.peek().setColorLight("Yellow");

            }
            if (colour == 3) {
                allenCards.get(index).setColorLight("Green");
                currentRound.discard.peek().setColorLight("Green");

            }

        }

        if (allenCards.get(index).getTypeDark().equals(Card.TypeDark.DARK_WILD_CARD)
                || allenCards.get(index).getTypeDark().equals(Card.TypeDark.WILD_DRAW_COLOR)) {
            Random rand = new Random();
            int colour = rand.nextInt(4);

            if (colour == 0) {
                allenCards.get(index).setColorLight("Orange");
                currentRound.discard.peek().setColorLight("Orange");

            }
            if (colour == 1) {
                allenCards.get(index).setColorLight("Teal");
                currentRound.discard.peek().setColorLight("Teal");

            }
            if (colour == 2) {
                allenCards.get(index).setColorLight("Purple");
                currentRound.discard.peek().setColorLight("Purple");

            }
            if (colour == 3) {
                allenCards.get(index).setColorLight("Pink");
                currentRound.discard.peek().setColorLight("Pink");

            }
        }
        // play the card
        currentRound.setPlayCardIndex(index);
        allenCardPlayed = currentRound.getPlayCard();
        return currentRound.cardPlayedLogic();

    }

    public Card getAllenPlayCard() {

        return allenCardPlayed;
    }

}
