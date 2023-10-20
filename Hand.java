import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cards;

    Hand(ArrayList<Card> cards){

        this.cards = cards;
    }

    public void addCard(Card card){

        cards.add(card);


    }

    public Card removeCard(Card card){

        cards.remove(card);

        return card;
    }

    public void getCards(ArrayList<Card> cards){

        //gets all the cards

        for (Card getcards: cards){
            System.out.println(getcards);

        }


    }


    
}
