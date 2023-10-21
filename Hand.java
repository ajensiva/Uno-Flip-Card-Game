import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;

    public Hand(){

        hand = new ArrayList<Card>();

    }

    public void addCard(Card card){

        hand.add(card);


    }

    public Card removeCard(Card card){

        hand.remove(card);

        return card;
    }


    public String toString(){
        int i = 0;
        String return_String;

        while(i < hand.size()){

            return_String = hand.toString();


        }

        return null;
    }

}
