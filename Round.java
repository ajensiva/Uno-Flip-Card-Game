import java.util.ArrayList;
import java.util.Stack;

import java.util.Scanner;
import java.util.Collections;

/**
 * The `Round` class represents a round of Uno card game.
 * It manages player actions, card interactions, and gameplay rules for a single round.
 *
 * @author Ajen, Jason, Zarif, Arun
 * @version 2.0
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

    protected Card Remove_card;

    protected Player roundWinner;


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



    /**
     * Card to play index
     * @return
     */

    public int getCardtoPlayIndex() {
        return playCardIndex;
    }

    /**
     * Retreive the Card that was played
     * @return
     */
    public Card getPlayCard(){
        //System.out.println(currentPlayer.getHand().getCard(getCardtoPlayIndex()));

//        System.out.println(currentPlayer.getHand().getCard(getCardtoPlayIndex()));
        return currentPlayer.getHand().getCard(getCardtoPlayIndex());
    }

    /**
     * make discard stack
     */

    public void makeDiscard(){
        if (deck.peek().getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)){

            deck = new Deck();
            discard.add(deck.pop());
        }
        else{
            discard.add(deck.pop());

        }
    }


    public void nextPlayer(){

        playerIndex = (playerIndex + 1) % players.size();
        currentPlayer = players.get(playerIndex);
    }



    public boolean cardPlayedLogic() {
        if ((checkCard(getPlayCard(), discard.peek()))) {
            //System.out.println(currentPlayer.getHand().getCard(getCardtoPlayIndex()));

            // light type cards only
            if (!(darkmode)) {
                System.out.println("i said: " + getPlayCard().getTypeLight());
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


                if (getPlayCard().getTypeDark() == Card.TypeDark.FLIP) {

                    darkmode = !(darkmode);

                }


            }

            Remove_card = currentPlayer.getHand().removeCard(getPlayCard());



            discard.add(Remove_card);


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
        boolean check_colour_light;
        boolean check_colour_dark;

        if (card1.getColorLight() == null || card1.getColorDark() == null){
            check_colour_light = true;
            check_colour_dark = true;

            return check_colour_light || check_colour_dark;
        }

        else {

            check_colour_light = card1.getColorLight().equals(card2.getColorLight()); // false
            check_colour_dark = card1.getColorDark().equals(card2.getColorDark()); // false

            boolean check_type_light = card1.getTypeLight().equals(card2.getTypeLight());
            boolean check_type_dark = card1.getTypeDark().equals(card2.getTypeDark());

            // check to see if it's a wild card that was played
            boolean is_light_wildcard = card1.getTypeLight().equals(Card.TypeLight.WILDTWO) || card1.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR);
            boolean is_dark_wildcard = card1.getTypeDark().equals(Card.TypeDark.DARK_WILD_CARD) || card1.getTypeDark().equals(Card.TypeDark.WILD_DRAW_COLOR);

            // for light side checks
            if (darkmode == false) {
                // if its a normal card, do a normal OR check; else check for wild card logic
                if (is_light_wildcard) {

                    //WILDTWO
                    if (card2.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
                        return check_colour_light && check_type_light;
                    } else {
                        return check_colour_light || check_type_light;

                    }

                } else {
                    return check_colour_light || check_type_light;
                }
            } else {
                // if its a normal card, do a normal OR check; else check for wild card logic
                if (is_dark_wildcard) {

                    //WILD TWO LOGIC - ONLY PLAYABLE, IF SAME TYPE AND SAME COLOUR
                    if (card2.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
                        return check_colour_dark && check_type_dark;
                    } else {
                        return check_colour_dark || check_type_dark;
                    }
                } else {
                    return check_colour_light || check_type_light;
                }
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
     * Checks if any player has won the round by emptying their hand.
     *
     * @return True if a player has won, false otherwise.
     */
    public boolean checkWinner(){
        // loop all players and check hand size
        for (Player player: players) {
            if(player.getHand().getSize() == 0) {
                roundWinner = player;
                return true;
            }
        }
        return false;
    }

    public int calculateRoundScore() {
        int roundScore = 0;

        if (roundWinner == null) {
            return roundScore;
        }

        for (Player player : players) {
            player.calculateHandScore();
            roundScore += player.getHandScore();
        }
        roundWinner.setScore(roundScore);
        return roundScore;
    }

}