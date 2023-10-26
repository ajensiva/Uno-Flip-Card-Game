import java.util.ArrayList;
import java.util.Stack;

import javax.swing.text.StyledEditorKit;

import java.util.Scanner;
import java.util.Collections;

/**
 * The `Round` class represents a round of Uno card game.
 * It manages player actions, card interactions, and gameplay rules for a single round.
 *
 * @author Ajen, Jason, Zarif, Arun
 * @version 1.0
 */

public class Round {

    private static ArrayList<Player> players; // array to hold players
    private Deck deck; // main deck of the game
    private Stack<Card> discard; // discard cards stack

    public static boolean darkmode = false; // if true then we're playing dark sides of card

    private Player currentPlayer; // current player thats playing
    private final int DEALTCARDS = 7; // max number of cards to be delt

    /**
     * Constructor for the `Round` class.
     * Initializes the round with a list of players, a deck, and a discard pile.
     *
     * @param players The list of players participating in the round.
     */
    public Round(ArrayList<Player> players){
        // set players, create a new deck and discard stack... then distribute the cards to the players
        this.players = players;
        deck = new Deck();
        discard = new Stack<Card>();
        distributeHand();
    }

    /**
     * Distributes a fixed number of cards to each player's hand at the beginning of the round.
     */
    public void distributeHand(){
        // loop for all "DELTCARDS" # of cards and give each player that many cards
        for(int i = 0; i < DEALTCARDS; i++){
            for (Player player : players){
                player.getHand().addCard(deck.pop());
            }
        }
    }


    /**
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Plays a round of the Uno game.
     * Manages player turns, card plays, special card effects, and checks for a winner.
     */
    public void playRound() {
        // playerIndex holds the current player's index
        int playerIndex = 0;
        currentPlayer = players.get(playerIndex);

        // at the start of the round take 1 card from deck and place on discard stack
        discard.add(deck.pop());

        // loop until a winner for the round has been found
        while (!(checkWinner())) {
            // update the current player at the start each player's turn
            System.out.println();
            currentPlayer = players.get(playerIndex);
            boolean validInput = false;
            while (!(validInput)) {
                System.out.println("------------------------------");
                System.out.println("[" + currentPlayer.getName() + "] playing:");
                System.out.println("------------------------------");
                System.out.println("Cards you can play:");


                int cardToPlay = 0; // index of the card that's going to be played
                // loop until a valid card has been played
                while(true){
                    cardToPlay = askUser(currentPlayer);
                    // card index must be greater than 0 and less than length of hand to move on
                    if((0 <= cardToPlay && cardToPlay <= currentPlayer.getHand().getSize())){
                        break;
                    }
                }

             // retrieve from hand

            // draw one card from the deck and reset loop if index == size of deck
            if (cardToPlay == currentPlayer.getHand().getSize()){
                // take 1 from the deck and add to player's hand
                currentPlayer.getHand().addCard(deck.pop());

            } else {

                // the card that's going to be played
                Card playCard;
                playCard = currentPlayer.getHand().getCard(cardToPlay);
                // check to see if the card can be played or not by checking the discard stack
                if ((checkCard(playCard, discard.peek()))) {
                    validInput = true;
                    System.out.println(playCard(cardToPlay));

                    System.out.println("Card has been played!");
                    // take the card from the player
                    currentPlayer.getHand().removeCard(playCard);

                    // light type cards only
                    if (!(darkmode)) {

                    /*
                     Below are just checks to handle each type of 'special' card.
                     The effects they have on the game. A lot of repeat comments.
                    */

                        // handle wild 2 cards
                        if (playCard.getTypeLight() == (Card.TypeLight.WILDTWO)) {
                            wildCard(playCard); // call function to handle wild cards
                            drawCard(2); // give 2 new cards
                            playerIndex = (playerIndex + 1) % players.size(); // move to the next player
                        }
                        if (playCard.getTypeLight() == Card.TypeLight.REVERSE) {
                            // reverse collection and decrement player index to get player before
                            playerIndex -= 1;
                            reverse();
                        }
                        if (playCard.getTypeLight() == Card.TypeLight.SKIP) {
                            // move to the next player
                            playerIndex = (playerIndex + 1) % players.size();
                        }
                        if (playCard.getTypeLight() == Card.TypeLight.FLIP) {
                            // if on light then go dark, vice versa
                            darkmode = !(darkmode);
                        }
                        if (playCard.getTypeLight() == Card.TypeLight.DRAW_TWO) {
                            // give 2 cards to the next player
                            drawCard(2);
                        }
                        // same as WILD2 but draw 2 more
                        if (playCard.getTypeLight() == Card.TypeLight.WILD_DRAW_FOUR) {
                            wildCard(playCard);
                            drawCard(4);
                            playerIndex = (playerIndex + 1) % players.size();
                        }


                        /*DarkSide implementation*/


                        if (playCard.getTypeDark() == Card.TypeDark.DRAW_FIVE) {

                            drawCard(5); // give 2 new cards
                        }
                        if (playCard.getTypeDark() == Card.TypeDark.SKIP_EVERYONE) {
                            playerIndex -= 1;
                        }
                        if (playCard.getTypeDark() == Card.TypeDark.REVERSE) {
                            // reverse collection and decrement player index to get player before
                            playerIndex -= 1;
                            reverse();
                        }

                        if (playCard.getTypeDark() == Card.TypeDark.WILD_CARD){
                            wildCard(playCard);
                        }
                        if (playCard.getTypeDark() == Card.TypeDark.WILD_DRAW_COLOR) {
                            wildDrawColor(currentPlayer);
                        }

                        if (playCard.getTypeDark() == Card.TypeDark.FLIP) {

                            darkmode = !(darkmode);

                        }


                    }
                }
            }
            if (!validInput) {
                System.out.println("Invalid card choice please enter a valid card index or draw card!\n");
            }
            }

            // if player's hand is 0 then they won the round
            if (currentPlayer.getHand().getSize() == 0) {
                System.out.println(currentPlayer + " won this round! They will receive " + getTotalPoints() + " points.");
                currentPlayer.setScore(currentPlayer.getScore() + getTotalPoints()); // update player's score
            }

            // update to next player
            playerIndex = (playerIndex + 1) % players.size();
        }
    }

    /**
     * Asks the current player for a card to play.
     *
     * @param currentPlayer The player whose turn it is.
     * @return The index of the selected card to play or draw (last index).
     */
    public int askUser(Player currentPlayer){
        Scanner userInput = new Scanner(System.in);
        // print the player's current hand to see
        System.out.println(currentPlayer.getHand().toString());
        System.out.println(" [" + currentPlayer.getHand().getSize() + "] Draw one card!\n");
        System.out.println("---------------------------------------------- \n");
        System.out.println("Top of discard pile: " + discard.peek() + "\n");
        System.out.println("----------------------------------------------");
        System.out.println("Input a card or draw a card (last index): ");
        int cardToPlay = userInput.nextInt(); // index of the card to play
        //myDraw(cardToPlay); // if they type the last index, then handle draw one
        return cardToPlay;
    }

    /**
     * Handles drawing a specified number of cards for a player.
     *
     * @param n The number of cards to draw.
     */
    public void drawCard(int n){
        // loop for n times (draw n cards)
        int nextPlayerIndex = (players.indexOf(currentPlayer)+1) % players.size(); // player that will draw cards
        for(int i = 0; i < (n+1); i++){
            // give cards to the next player
            players.get(nextPlayerIndex).getHand().addCard(deck.pop());
        }
    }

    public void wildDrawColor(Player currentPlayer){
        int nextPlayerIndex = (players.indexOf(currentPlayer)+1) % players.size(); // player that will draw cards

        while ((players.get(nextPlayerIndex).getHand().getCard(players.get(nextPlayerIndex).getHand().getSize()-1)) != discard.peek()) {
            players.get(nextPlayerIndex).getHand().addCard(deck.pop());
        }

    }

    /**
     * Plays a card from the player's hand and adds it to the discard pile.
     *
     * @param cardToPlay The index of the card to be played from the player's hand.
     * @return The card that was played.
     */
    public Card playCard(int cardToPlay){
        // find the card that's about to be played in player's hand
        Card addCard = currentPlayer.getHand().getCard(cardToPlay);
        discard.push(addCard); // add the card to the discard stack
        return addCard;
    }

    /**
     * Checks if a card can be played based on Uno card game rules.
     *
     * @param card1 The card to be checked for playability.
     * @param card2 The card at the top of the discard pile.
     * @return True if the card can be played, false otherwise.
     */
    public boolean checkCard(Card card1, Card card2){
        // let wild cards be played regardless
        if (card1.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR) || card1.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
            return true;
        }
        // if the color or the number/type matches
        return card1.getColorLight().equals(card2.getColorLight()) || card1.getTypeLight().equals(card2.getTypeLight());
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
        // wild card will change the colour of the cards that can be played
        Scanner userInput = new Scanner(System.in);
        System.out.println("What color would you like? (enter an integer)\nAvailable Colors: Red (0), Blue (1), Yellow (2), Green (3): ");
        int color = userInput.nextInt();
        while (color < 0 || color > 3){
            System.out.println("Invalid color choice. Enter an integer (0-3) to choose a color: ");
            color = userInput.nextInt();
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
        // loop all players and check hand size
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
        int totalPoint = 0; //  sum of all player's cards' values
        for(Player plr : players){
            if(plr != currentPlayer){ // skip winner as a safe measure
                Hand hand = plr.getHand();
                // get each invidiual card's value and add to sum
                for(Card card : hand.getHandList()){
                    totalPoint += card.getValue();
                }
            }
        }
        return totalPoint;
    }



}