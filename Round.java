import java.util.LinkedList;
import java.util.Stack;


public class Round {

    private static LinkedList<Player> players;
    private Deck deck;
    private Stack<Card> discard;

    private static final boolean darkmode = false;

    private Player playcurrentPlayer;
    private final int DEALTCARDS = 7;

    Round(LinkedList<Player> players){
        this.players = players;
        deck = new Deck();
        discard = new Stack<Card>();
    }

    public Deck getDeck() {
        return deck;
    }

    public void distributeHand(){
        int i = 0;
        while (i < DEALTCARDS) {
            for (Player player : players){
                player.getHand().addCard(deck.pop());
            }
            i++;
        }
    }

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

    public void checkWinner(){}

    public void calculatePoints(){}


    public static void main (String args[]){

        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        Hand hand4 = new Hand();

        Player AJ = new Player("AJ", hand1);
        Player Jason = new Player("Jason", hand2);
        Player Zarif = new Player("Zarif", hand3);
        Player Arun = new Player("Arun", hand4);
        LinkedList<Player> players1 = new LinkedList<>();
        players1.add(AJ);
        players1.add(Jason);
        players1.add(Zarif);
        players1.add(Arun);


        Round round = new Round(players1);
        round.distributeHand();
        }




    }

