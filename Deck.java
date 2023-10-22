// working on it: karki
import java.util.Random;
import java.util.Stack;

public class Deck {

    //private Stack<Card> deck;
    private Stack<Card> deck;
    private int FULL_DECK = 112;

    // constructor
    public Deck() {
            deck = new Stack<Card>();
            generateDeck();
    }

    private void generateDeck() {
        Random random = new Random();

        while (deck.size() < FULL_DECK) {
            //Generate random enums for ColorLight, ColorDark, TypeLight, and TypeDark.
            Card.ColorLight colorLight = Card.ColorLight.values()[random.nextInt(Card.ColorLight.values().length)];
            Card.TypeLight typeLight = Card.TypeLight.values()[random.nextInt(Card.TypeLight.values().length)];
            Card.ColorDark colorDark = Card.ColorDark.values()[random.nextInt(Card.ColorDark.values().length)];
            Card.TypeDark typeDark = Card.TypeDark.values()[random.nextInt(Card.TypeDark.values().length)];

            // Create a new card with the generated values.
            Card newCard = new Card(colorLight, typeLight, colorDark, typeDark);

            // Add the card to the deck.
            deck.add(newCard);
        }
    }

    public Card pop() {

        return deck.pop();

    }

    public int getSize(){

        return deck.size();

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : deck) {
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String args[]){
        Deck deck = new Deck();
        System.out.println(deck);
    }
}


