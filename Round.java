import java.util.ArrayList;
import java.util.Stack;

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

    protected Deck deck; // main deck of the game
    protected Stack<Card> discard; // discard cards stack

    public static boolean darkmode = false; // if true then we're playing dark sides of card

    protected Player currentPlayer; // current player that's playing
    private final int DEALTCARDS = 7; // max number of cards to be delt

    protected int playCardIndex;

    protected int playerIndex = 0;

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
        makeDiscard();
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

    public void setPlayCardIndex(int PlayCardIndex){
        //System.out.println("IN ROUND:" + PlayCardIndex);
        this.playCardIndex = PlayCardIndex;

    }

    public int getCardtoPlayIndex() {
        return playCardIndex;
    }

    public Card getPlayCard(){
        //System.out.println(currentPlayer.getHand().getCard(getCardtoPlayIndex()));

        System.out.println("getPlayCard: " + currentPlayer.getHand().getCard(getCardtoPlayIndex()));
        return currentPlayer.getHand().getCard(getCardtoPlayIndex());
    }

    public void makeDiscard(){
        discard.add(deck.pop());
    }


    public void currentPlayer(){
        currentPlayer = players.get(playerIndex);
    }


    public void nextPlayer(){
        currentPlayer = players.get(players.indexOf(currentPlayer)+1);
    }


    public void unoFunction(){
        checkWinner();
    }

    public boolean cardPlayedLogic() {
        if ((checkCard(getPlayCard(), discard.peek()))) {
            //System.out.println(playCard(getCardtoPlayIndex()));

            System.out.println("Card has been played!");

            // take the card from the player
            discard.add(currentPlayer.getHand().removeCard(getPlayCard()));

            // light type cards only
            if (!(darkmode)) {

                    /*
                     Below are just checks to handle each type of 'special' card.
                     The effects they have on the game. A lot of repeat comments.
                    */

                // handle wild 2 cards
                if (getPlayCard().getTypeLight() == (Card.TypeLight.WILDTWO)) {
//                    wildCard(getPlayCard()); // call function to handle wild cards
//                    drawCard(2); // give 2 new cards
                    playerIndex = (playerIndex + 1) % players.size(); // move to the next player
                }
                if (getPlayCard().getTypeLight() == Card.TypeLight.REVERSE) {
                    // reverse collection and decrement player index to get player before
                    playerIndex -= 1;
                    reverse();
                }
                if (getPlayCard().getTypeLight() == Card.TypeLight.SKIP) {
                    // move to the next player
                    playerIndex = (playerIndex + 1) % players.size();
                }
                if (getPlayCard().getTypeLight() == Card.TypeLight.FLIP) {
                    // if on light then go dark, vice versa
                    darkmode = !(darkmode);
                }
                if (getPlayCard().getTypeLight() == Card.TypeLight.DRAW_TWO) {
                    // give 2 cards to the next player
                    drawCard(2);
                }
                // same as WILD2 but draw 2 more
                if (getPlayCard().getTypeLight() == Card.TypeLight.WILD_DRAW_FOUR) {
//                    wildCard(getPlayCard());
//                    drawCard(4);
                    playerIndex = (playerIndex + 1) % players.size();
                }
            }
            if (darkmode){

                if (getPlayCard().getTypeDark() == Card.TypeDark.DRAW_FIVE) {

                    drawCard(5); // give 2 new cards
                }
                if (getPlayCard().getTypeDark() == Card.TypeDark.SKIP_EVERYONE) {
                    playerIndex -= 1;
                }
                if (getPlayCard().getTypeDark() == Card.TypeDark.REVERSE) {
                    // reverse collection and decrement player index to get player before
                    playerIndex -= 1;
                    reverse();
                }


                if (getPlayCard().getTypeDark() == Card.TypeDark.WILD_DRAW_COLOR) {
                    wildDrawColor(currentPlayer);
                }

                if (getPlayCard().getTypeDark() == Card.TypeDark.FLIP) {

                    darkmode = !(darkmode);

                }


            }
            return true;
        }
    return false;
}



    public void playRound() {
        currentPlayer = players.get(0);
    }


    public String darkmode(boolean darkmode){
        if (darkmode){
            return "Darkmode!";
        }
        else{
            return  "Lightmode!";
        }
    }

    // draws one card from the deck and gives to current player! and RETURN the card that was just popped
    public void drawCurrPlayer(){
        //
        currentPlayer.getHand().addCard(deck.pop());


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
    public boolean checkCard(Card card1, Card card2) {

        boolean check_colour_light = card1.getColorLight().equals(card2.getColorLight()); // false
        boolean check_colour_dark = card1.getColorDark().equals(card2.getColorDark()); // false 

        boolean check_type_light = card1.getTypeLight().equals(card2.getTypeLight());
        boolean check_type_dark = card1.getTypeDark().equals(card2.getTypeDark());

        // check to see if its a wild card that was played
        boolean is_light_wildcard = card1.getTypeLight().equals(Card.TypeLight.WILDTWO) || card1.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR);
        boolean is_dark_wildcard = card1.getTypeDark().equals(Card.TypeDark.WILD_CARD) || card1.getTypeDark().equals(Card.TypeDark.WILD_DRAW_COLOR);

        // for light side checks
        if (darkmode == false) {
            System.out.println("She's my light");
            // if its a normal card, do a normal OR check; else check for wild card logic
            if (is_light_wildcard) {
                if (card2.getTypeLight().equals(Card.TypeLight.WILDTWO) || card2.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)) {
                    return check_colour_light && check_type_light;
                } else {
                    return check_colour_light || check_type_light;

                }

            } else {
                return check_colour_light || check_type_light;
            }
        } else {
            System.out.println("Dark Mode like her hair <3");
            // if its a normal card, do a normal OR check; else check for wild card logic
            if (is_dark_wildcard) {
                if (card2.getTypeLight().equals(Card.TypeLight.WILDTWO) || card2.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)) {
                    return check_colour_dark && check_type_dark;
                } else {
                    return check_colour_dark || check_type_dark;
                }
            }
            else{
                return check_colour_light || check_type_light;
            }
        }
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




}