import java.util.ArrayList;
import java.util.Random;

public class Allen extends Player{
    /**
     * Constructor to create a new player with the given name.
     *
     * @param name The name of the player.
     */


    Card allenCardPlayed;
    
    public Allen(String name) {
        super(name);
    }

    // this will play the card with the highest value
    public boolean allenPlayCard(Round currentRound, Hand hand){
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

        if (allenCards.get(index).getTypeLight().equals(Card.TypeLight.WILDTWO) || allenCards.get(index).getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)){
            Random rand = new Random();
            int colour = rand.nextInt(4);

            if (colour == 0){
                allenCards.get(index).setColorLight("Red");
            }
            if(colour == 1){
                allenCards.get(index).setColorLight("Blue");
            }
            if (colour == 2){
                allenCards.get(index).setColorLight("Yellow");
            }
            if (colour == 3){
                allenCards.get(index).setColorLight("Green");

            }
        }
        // play the card
        currentRound.setPlayCardIndex(index);
        allenCardPlayed = currentRound.getPlayCard();
        return currentRound.cardPlayedLogic();


    }

    public Card getAllenPlayCard(){

        return allenCardPlayed;
    }

}
