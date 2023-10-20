import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;

    Hand(){

        hand = new ArrayList<Card>();


    }

    public void addCard(Card card){

        hand.add(card);


    }

    public Card removeCard(Card card){

        hand.remove(card);

        return card;
    }

    public void getCards(ArrayList<Card> cards){

        //gets all the cards

        for (Card getcards: cards){
            System.out.println(getcards);

        }


    }


    
}
