import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.util.Collections;


public class Round {

    private static ArrayList<Player> players;
    private Deck deck;
    private Stack<Card> discard;

    public static boolean darkmode = false;

    private Player playcurrentPlayer;
    private final int DEALTCARDS = 7;

    Round(ArrayList<Player> players){
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

    public Card getDiscardStack(){

        return discard.pop();
    }

    public void playRound() {


        playcurrentPlayer = players.get(0);
        //Zarif and AJ

        int i = 0; // keep track of current player

        discard.add(deck.pop());
        //
        while (!(checkWinner())) {


            if (darkmode) {
                System.out.println("on dark side!\n");
            } else {
                System.out.println("on light side!\n");
            }

            System.out.println();
            playcurrentPlayer = players.get(i);

            System.out.println(playcurrentPlayer.getName());
            /* 
            int Card_to_play = askUser(playcurrentPlayer);
            System.out.println(discard.peek());

            while (!(0 <= Card_to_play && Card_to_play < playcurrentPlayer.getHand().getSize())) {

                System.out.println("You are stupid");
                Card_to_play = askUser(playcurrentPlayer);

            }
            */
            //////
            int Card_to_play = 0;//askUser(playcurrentPlayer);
            while(true){
                Card_to_play = askUser(playcurrentPlayer);
                System.out.println(discard.peek());
                if((0 <= Card_to_play && Card_to_play < playcurrentPlayer.getHand().getSize())){
                    System.out.println("done");
                    break;
                }
            }
            /////


            // check can you even play that card, or in fact, any card!

            Card PlayCard;

            PlayCard = playcurrentPlayer.getHand().getCard(Card_to_play);

            //if you have cards to play, move on to play an card!


            if ((checkCard(PlayCard, discard.peek()))) {


                playCard(Card_to_play);
                System.out.println("Card has been played!");
                playcurrentPlayer.getHand().removeCard(PlayCard);


                if (!(darkmode)) {

                    if (PlayCard.getTypeLight() == (Card.TypeLight.WILDTWO)) {

                        wildCard(PlayCard);

                    }


                    if (PlayCard.getTypeLight() == Card.TypeLight.REVERSE) {
                        reverse();
                    }

                    if (PlayCard.getTypeLight() == Card.TypeLight.SKIP) {

                        skipPlayer();

                    }
                    if (PlayCard.getTypeLight() == Card.TypeLight.FLIP) {

                        darkmode = !(darkmode);
                    }
                    if (PlayCard.getTypeLight() == Card.TypeLight.DRAW_TWO) {

                        Draw(2);

                    }

                    if (PlayCard.getTypeLight() == Card.TypeLight.WILD_DRAW_FOUR) {

                        wildCard(PlayCard);
                    }

                }
            }


            if (playcurrentPlayer.getHand().getSize() == 0) {

                System.out.println(playcurrentPlayer + " Won this round!");

                System.out.println(getTotalPoints());


            }

            i = (i + 1) % players.size();


        }
    }




    public int askUser(Player playcurrentPlayer){

        Scanner user_card = new Scanner(System.in);
        System.out.println(playcurrentPlayer.getHand().toString());
        System.out.println("Card at top of the pile:");
        System.out.println(discard.peek());
        System.out.println("Input a card: ");
        int Card_to_play = user_card.nextInt();

        return Card_to_play;


    }

    public void Draw(int n){

        int i = 0;
        while (i < n+1) {

            players.get((players.indexOf(playcurrentPlayer)+1) % players.size()).getHand().addCard(discard.pop());

            i++;

        }
    }

    public Card playCard(int user){


        Card addCard;

        addCard = playcurrentPlayer.getHand().getCard(user);

        discard.push(addCard);


        return addCard;
    }



    public void displayCard(){}

    public boolean checkCard(Card card1, Card card2){


        if (card1.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR) || card1.getTypeLight().equals(Card.TypeLight.WILDTWO)) {

            return true;
        }
        return card1.getColorLight().equals(card2.getColorLight()) || card1.getTypeLight().equals(card2.getTypeLight());
    }

    public boolean takeCardFromDeck(Player player, int n){

        int i = 0;

        if (n > 0) {

            while (i <= n) {

                player.getHand().addCard(deck.pop());

                i++;
            }
            return true;
        }

        return false;
    }

    public void skipPlayer(){
        // get current plr's index in the arraylist
        int currentPlayerIndex = players.indexOf(playcurrentPlayer);
        if(currentPlayerIndex >= (players.size()-1))
            // reset to first player in list
            playcurrentPlayer = players.get(0);
        else
            // move to next player
            playcurrentPlayer = players.get(currentPlayerIndex+1);
    }

    public void reverse(){
        Collections.reverse(players);
    }

    public void wildCard(Card card){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What colour would you like? (enter an integer) \nAvailable Colours, Red (0), Blue (1), Yellow (2), Green (3): ");
        int colour = scanner.nextInt();
        Boolean valid = false;
        while (colour < 0 || colour > 3 ) {
            System.out.println("What colour would you like? (enter an integer) \nAvailable Colours, Red (0), Blue (1), Yellow (2), Green (3): ");
            colour = scanner.nextInt();
            if (colour == 0) {card.setColorLight("Red");return;}
            if (colour == 1) {card.setColorLight("Blue");return;}
            if (colour == 2) {card.setColorLight("Yellow");return;}
            if (colour == 3) {card.setColorLight("Green");return;}
        }
    }

    public boolean checkWinner(){
        for (Player player: players) {
            if(player.getHand().getSize() == 0) {
                return true;
            }
        }
        return false;
    }

    public int getTotalPoints(){
        int totalPoint = 0;
        for(Player plr : players){
            if(plr != playcurrentPlayer){
                Hand hand = plr.getHand();
                for(Card card : hand.getHandList()){
                    totalPoint += card.getValue();
                }
            }
        }
        System.out.println("The round's total points is: " + totalPoint);
        return totalPoint;
    }


    public static void main (String args[]){


        Player AJ = new Player("AJ");
        Player Jason = new Player("Jason");
        Player Zarif = new Player("Zarif");
        Player Arun = new Player("Arun");

        ArrayList<Player> players1 = new ArrayList<>();
        players1.add(AJ);
        players1.add(Jason);
        players1.add(Zarif);
        players1.add(Arun);


        Round round = new Round(players1);
        round.distributeHand();
        round.playRound();



    }




}
