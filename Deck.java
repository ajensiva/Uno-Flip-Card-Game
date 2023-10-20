// working on it: karki
import java.util.Stack;

public class Deck {

    //private Stack<Card> deck;
    private Stack<Card> deck;
    private int FULL_DECK = 112;

    // constructor
    public Deck(){

        deck = new Stack<Card>();

        for(Card.ColorLight color : Card.ColorLight.values()){
            
        }

    }

    public static void main(String args[]){
        Deck deck = new Deck();        
    }
}


