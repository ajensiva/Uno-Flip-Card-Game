import java.util.ArrayList;
/**
 * Hand class represents a player's hand of Uno cards.
 *
 * @author Zarif, Arun, Ajen, Jason
 * @version 1.0
 */
public class Hand {

    private ArrayList<Card> hand;

    /**
     * Constructor to create an empty hand.
     */
    public Hand() {
        this.hand = new ArrayList<Card>();
    }

    /**
     * Add a card to the hand.
     *
     * @param card The card to be added to the hand.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Remove a card from the hand.
     *
     * @param card The card to be removed from the hand.
     * @return The card that was removed.
     */
    public Card removeCard(Card card) {
        hand.remove(card);
        return card;
    }

    /**
     * Get the list of cards in the hand.
     *
     * @return The list of Uno cards in the hand.
     */
    public ArrayList<Card> getHandList() {
        return this.hand;
    }

    /**
     * Get a specific card from the hand based on its index.
     *
     * @param card_num The index of the card to retrieve from the hand.
     * @return The card at the specified index.
     */
    public Card getCard(int card_num) {
        return this.hand.get(card_num);
    }

    /**
     * Get all the cards in the hand.
     *
     * @return A list containing all the cards in the hand.
     */
    public ArrayList<Card> getAll() {
        return hand;
    }

    /**
     * Get the number of cards in the hand.
     *
     * @return The number of cards in the hand.
     */
    public int getSize() {
        return this.hand.size();
    }

    /**
     * Override the `toString` method to provide a formatted string representation of the hand.
     *
     * @return A string representation of the hand with card indices and card details.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            sb.append(" [" + hand.indexOf(card) + "] ");
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String args[]){
        Deck deck = new Deck();
        Hand hand = new Hand();
        hand.addCard(deck.pop());
        hand.addCard(deck.pop());
        hand.addCard(deck.pop());
        hand.addCard(deck.pop());
        hand.addCard(deck.pop());
        System.out.println(hand);        
    }
}
