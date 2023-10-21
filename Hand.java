import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;

    public Hand(){

        this.hand = new ArrayList<Card>();

    }

    public void addCard(Card card){
        hand.add(card);
    }

    public Card removeCard(Card card){
        hand.remove(card);
        return card;
    }


    public Card getCard(int card_num){

        return this.hand.get(card_num);
    }


    public int getSize(){

        return this.hand.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
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
