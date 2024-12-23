package src;

import java.io.Serializable;
import java.util.Random;

import java.util.Stack;

/**
 * Deck class : Generates 112 cards to be used to during play
 * @author Arun, Ajen, Zarif, Jason
 * @version 4.0
 */
public class Deck implements Serializable {

    private Stack<Card> deck; // deck of all cards available
    public static final int FULL_DECK = 112; // max size of deck can only be 112

    /**
     * Deck constructor : Instatiates deck and generates cards
     */
    public Deck() {
            deck = new Stack<Card>();
            generateDeck();
    }

    /**
     * generateDeck : generates a Deck of 112 cards
     */
    private void generateDeck() {
        Random random = new Random();
        while (deck.size() < FULL_DECK) {
            //Generate random enums for ColorLight, ColorDark, TypeLight, and TypeDark.

            Card.TypeLight typeLight = Card.TypeLight.values()[random.nextInt(Card.TypeLight.values().length)];
            Card.TypeDark typeDark = Card.TypeDark.values()[random.nextInt(Card.TypeDark.values().length)];

            Card.ColorLight colorLight = Card.ColorLight.values()[random.nextInt(Card.ColorLight.values().length)];
            Card.ColorDark colorDark = Card.ColorDark.values()[random.nextInt(Card.ColorDark.values().length)];


            if (Card.TypeLight.WILD_DRAW_FOUR.equals(typeLight)) {

                Card newCard = new Card(null, typeLight, colorDark, typeDark);

                deck.add(newCard);
            }
            else if (Card.TypeDark.DARK_WILD_CARD.equals(typeDark)){
                // Create a new card with the generated values.

                Card newCard = new Card(colorLight, typeLight, null, typeDark);

                // Add the card to the deck.
                deck.add(newCard);
            }
            else {

                Card newCard = new Card(colorLight, typeLight, colorDark, typeDark);
                deck.add(newCard);
            }
        }
    }

    /**
     * pop : returns the card at the top of the deck
     * @return Card
     */
    public Card pop() {
        return deck.pop();
    }

    /**
     * Retrieves Card at the top of the deck
     * @return
     */

    public Card peek(){
        return deck.peek();
    }

    /**
     * Retrieves the size of the deck
     * @return
     */
    public int getSize() {
        return this.deck.size();
    }
    
    /**
     * toString : Provides a way to display teh deck of cards in String format
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : deck) {
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }

}


