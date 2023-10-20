import java.util.LinkedList;
import java.util.Stack;


public class Round {

    private LinkedList<Player> Players;
    private Deck deck;
    private Stack<Card> discard;

    private static final boolean darkmode = false;

    private Player playcurrentPlayer;

    Round(LinkedList<Player> players){}

    public Deck getDeck() {
        return deck;
    }

    public void distributeHand(){}

    public Stack getDiscardStack(){

        return discard;
    }

    public void playRound(){}

    public void displayCard(){}

    public void playCard(){}
    public boolean checkCard(Card card1, Card card2){

        return false;
    }

    public boolean takeCardFromDeck(Player player, int n){

        return false;
    }

    public void skipPlayer() {}

    public void reverse(){}

    public void wildCard(Card card){}

    public boolean checkWinner(){return false;}

    public void calculatePoints(){}


}

