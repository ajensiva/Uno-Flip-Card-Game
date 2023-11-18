import java.util.ArrayList;

public class Allen extends Player{
    /**
     * Constructor to create a new player with the given name.
     *
     * @param name The name of the player.
     */
    
    public Allen(String name) {
        super(name);
    }

    // this will play the card with the highest value
    public void allenPlayCard(Round currentRound, Hand hand){
        /*
            get the bot's hand to see all which cards are playable first
            play the card with the most value
         */
        ArrayList<Card> allenCards = hand.getHandList();
        int index = 0;

        for(Card card : allenCards){
            boolean canPlay = currentRound.checkCard(card, currentRound.discard.peek());
            // playable
            if(canPlay && (card.getValue() >= allenCards.get(index).getValue())){ // if the value of current value > old then update new highest card
                index = allenCards.indexOf(card);
            }
        }
        // play the card
        currentRound.setPlayCardIndex(index);
        currentRound.cardPlayedLogic();
    }
}
