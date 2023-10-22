import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.util.Collections;

/**
 * The `Round` class represents a round of an Uno card game.
 * It manages player actions, card interactions, and gameplay rules for a single round.
 *
 * @author Ajen, Jason, Zarif, Arun
 * @version 1.0
 */
public class Round {

    private static ArrayList<Player> players;
    private Deck deck;
    private Stack<Card> discard;

    public static boolean darkmode = false;

    private Player playcurrentPlayer;
    private final int DEALTCARDS = 7;

    /**
     * Constructor for the `Round` class.
     * Initializes the round with a list of players, a deck, and a discard pile.
     *
     * @param players The list of players participating in the round.
     */
    Round(ArrayList<Player> players){
        this.players = players;
        deck = new Deck();
        discard = new Stack<Card>();
        distributeHand();
    }

    /**
     * Gets the deck of cards used in the round.
     *
     * @return The deck of cards.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Distributes a fixed number of cards to each player's hand at the beginning of the round.
     */
    public void distributeHand(){
        int i = 0;
        while (i < DEALTCARDS) {
            for (Player player : players){
                player.getHand().addCard(deck.pop());
            }
            i++;
        }
    }

    /**
     * Plays a round of the Uno game.
     * Manages player turns, card plays, special card effects, and checks for a winner.
     */
    public void playRound() {
        playcurrentPlayer = players.get(0);
        int i = 0;

        discard.add(deck.pop());

        while (!(checkWinner())) {
            if (darkmode) {
                System.out.println("on dark side!\n");
            } else {
                System.out.println("on light side!\n");
            }

            System.out.println();
            playcurrentPlayer = players.get(i);

            System.out.println(playcurrentPlayer.getName());

            int Card_to_play = 0;
            while(true){
                Card_to_play = askUser(playcurrentPlayer);
                System.out.println(discard.peek());
                if((0 <= Card_to_play && Card_to_play < playcurrentPlayer.getHand().getSize())){
                    System.out.println("done");
                    break;
                }
            }

            Card PlayCard;
            PlayCard = playcurrentPlayer.getHand().getCard(Card_to_play);

            if ((checkCard(PlayCard, discard.peek()))) {
                playCard(Card_to_play);
                System.out.println("Card has been played!");
                playcurrentPlayer.getHand().removeCard(PlayCard);

                if (!(darkmode)) {
                    if (PlayCard.getTypeLight() == (Card.TypeLight.WILDTWO)) {
                        wildCard(PlayCard);
                        Draw(2);
                        i = (i + 1) % players.size();
                    }
                    if (PlayCard.getTypeLight() == Card.TypeLight.REVERSE) {
                        i -= 1;
                        reverse();
                    }
                    if (PlayCard.getTypeLight() == Card.TypeLight.SKIP) {
                        i = (i + 1) % players.size();
                    }
                    if (PlayCard.getTypeLight() == Card.TypeLight.FLIP) {
                        darkmode = !(darkmode);
                    }
                    if (PlayCard.getTypeLight() == Card.TypeLight.DRAW_TWO) {
                        Draw(2);
                    }
                    if (PlayCard.getTypeLight() == Card.TypeLight.WILD_DRAW_FOUR) {
                        wildCard(PlayCard);
                        Draw(4);
                        i = (i + 1) % players.size();
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

    /**
     * Asks the current player for a card to play.
     *
     * @param playcurrentPlayer The player whose turn it is.
     * @return The index of the selected card to play or draw (last index).
     */
    public int askUser(Player playcurrentPlayer){
        Scanner user_card = new Scanner(System.in);
        System.out.println(playcurrentPlayer.getHand().toString());
        System.out.println(" [" + playcurrentPlayer.getHand().getSize() + "] draw one card! ");
        System.out.println("Card at the top of the pile:");
        System.out.println(discard.peek());
        System.out.println("Input a card or draw a card (last index): ");
        int Card_to_play = user_card.nextInt();
        myDraw(Card_to_play);
        return Card_to_play;
    }

    /**
     * Handles drawing a card from the deck based on the player's choice.
     *
     * @param Card_to_play The player's choice for playing or drawing a card.
     */
    public void myDraw(int Card_to_play){
        if (Card_to_play == playcurrentPlayer.getHand().getSize()){
            playcurrentPlayer.getHand().addCard(deck.pop());
        }
    }

    /**
     * Handles drawing a specified number of cards for a player.
     *
     * @param n The number of cards to draw.
     */
    public void Draw(int n){
        int i = 0;
        while (i < n+1) {
            players.get((players.indexOf(playcurrentPlayer)+1) % players.size()).getHand().addCard(deck.pop());
            i++;
        }
    }


    /**
     * Plays a card from the player's hand and adds it to the discard pile.
     *
     * @param user The index of the card to be played from the player's hand.
     * @return The card that was played.
     */
    public Card playCard(int user){
        Card addCard;
        addCard = playcurrentPlayer.getHand().getCard(user);
        discard.push(addCard);
        return addCard;
    }

    /**
     * Placeholder method for displaying cards (no implementation provided).
     */
    public void displayCard(){}

    /**
     * Checks if a card can be played based on Uno card game rules.
     *
     * @param card1 The card to be checked for playability.
     * @param card2 The card at the top of the discard pile.
     * @return True if the card can be played, false otherwise.
     */
    public boolean checkCard(Card card1, Card card2){
        if (card1.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR) || card1.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
            return true;
        }
        return card1.getColorLight().equals(card2.getColorLight()) || card1.getTypeLight().equals(card2.getTypeLight());
    }

    /**
     * Attempts to take a specified number of cards from the deck and add them to a player's hand.
     *
     * @param player The player who is taking the cards.
     * @param n The number of cards to take from the deck.
     * @return True if the cards were successfully taken, false otherwise.
     */
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

    /**
     * Reverses the order of players in the game.
     */
    public void reverse(){
        Collections.reverse(players);
    }

    /**
     * Handles the selection of a color for a Wild card.
     *
     * @param card The Wild card for which the color is selected.
     */
    public void wildCard(Card card){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What color would you like? (enter an integer)\nAvailable Colors: Red (0), Blue (1), Yellow (2), Green (3): ");
        int color = scanner.nextInt();
        while (color < 0 || color > 3) {
            System.out.println("Invalid color choice. Enter an integer (0-3) to choose a color: ");
            color = scanner.nextInt();
        }
        if (color == 0) {card.setColorLight("Red");}
        if (color == 1) {card.setColorLight("Blue");}
        if (color == 2) {card.setColorLight("Yellow");}
        if (color == 3) {card.setColorLight("Green");}
    }

    /**
     * Checks if any player has won the round by emptying their hand.
     *
     * @return True if a player has won, false otherwise.
     */
    public boolean checkWinner(){
        for (Player player: players) {
            if(player.getHand().getSize() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the total points for the round based on the remaining cards in players' hands.
     *
     * @return The total points for the round.
     */
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


//    public static void main (String args[]){
//
//
//        Player AJ = new Player("AJ");
//        Player Jason = new Player("Jason");
//        Player Zarif = new Player("Zarif");
//        Player Arun = new Player("Arun");
//
//
//        // Arun
//        //Zarif (1)
//        //Jason
//        //AJ
//
//        ArrayList<Player> players1 = new ArrayList<>();
//        players1.add(AJ);
//        players1.add(Jason);
//        players1.add(Zarif);
//        players1.add(Arun);
//
//
//        Round round = new Round(players1);
//        round.distributeHand();
//        round.playRound();
//
//
//
//    }




}
