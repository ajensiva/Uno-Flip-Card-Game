import java.util.ArrayList;
import java.util.Stack;

import java.util.Collections;

/**
 * The `Round` class represents a round of Uno card game.
 * It manages player actions, card interactions, and gameplay rules for a single
 * round.
 *
 * @author Ajen, Jason, Zarif, Arun
 * @version 3.0
 */

public class Round {

    private static ArrayList<Player> players; // array to hold players

    protected Deck deck; // main deck of the game
    protected Stack<Card> discard; // discard cards stack

    public static boolean darkmode = true; // if true then we're playing dark sides of card

    protected Player currentPlayer; // current player that's playing
    private final int DEALTCARDS = 7; // max number of cards to be delt

    protected int playCardIndex;

    protected int playerIndex = 0;

    protected Card removeCard;

    protected Player roundWinner;

    /**
     * Constructor for the `Round` class.
     * Initializes the round with a list of players, a deck, and a discard pile.
     *
     * @param players The list of players participating in the round.
     */
    public Round(ArrayList<Player> players) {
        // set players, create a new deck and discard stack... then distribute the cards
        // to the players
        this.players = players;
        deck = new Deck();
        discard = new Stack<Card>();
        this.roundWinner = null;
        // clear old hand if it exists
        for (Player plr : players) {
            plr.getHand().clearHand();

        }
        distributeHand();
        makeDiscard();
    }

    /**
     * Distributes a fixed number of cards to each player's hand at the beginning of
     * the round.
     */
    public void distributeHand() {
        // loop for all "DELTCARDS" # of cards and give each player that many cards
        for (int i = 0; i < DEALTCARDS; i++) {
            for (Player player : players) {
                player.getHand().addCard(deck.pop());
            }
        }
    }

    /**
     * returns the models player's list
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayCardIndex(int PlayCardIndex) {

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
    public Card getPlayCard() {

        return currentPlayer.getHand().getCard(getCardtoPlayIndex());
    }
    /**
     * make discard stack
     */
    public void makeDiscard() {
        if (deck.peek().getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)) {

            deck = new Deck();
            discard.add(deck.pop());
        } else {
            discard.add(deck.pop());

        }
    }

    /**
     * traverse to next player
     */
    public void nextPlayer() {

        playerIndex = (playerIndex + 1) % players.size();
        currentPlayer = players.get(playerIndex);
    }

    /**
     * Implements a series of checks for cards other than wild cards and performs the necessary actions to the game
     * @return
     */

    public boolean cardPlayedLogic() {
        if ((checkCard(getPlayCard(), discard.peek()))) {

            // light type cards only
            if (!(darkmode)) {
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
            if (darkmode) {

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
                if (getPlayCard().getTypeDark() == Card.TypeDark.WILD_DRAW_COLOR) {

                    boolean flag = true;

                    while(flag){

                        if (deck.peek().getColorDark() != null && discard.peek().getColorDark().equals(players.get(((players.indexOf(currentPlayer) + 1) % players.size())).getHand().getCard(players.get(((players.indexOf(currentPlayer) + 1) % players.size())).getHand().getSize()-1).getColorDark())) {

                            flag = false;
                        }
                        else {

                            drawCard(1);
                        }

                    }

                }
            }

            removeCard = currentPlayer.getHand().removeCard(getPlayCard());

            discard.add(removeCard);

            return true;
        }
        return false;
    }

    /**
     * Sets the current Player to first index of the player list
     */

    public void setCurrentPlayertoFirstIndex() {
        currentPlayer = players.get(0);
    }


    /**
     * Draws a Card for the current player playing
     */
    public void drawCurrPlayer() {

        currentPlayer.getHand().addCard(deck.pop());

    }

    /**
     * Handles drawing a specified number of cards for a player.
     *
     * @param n The number of cards to draw.
     */
    public void drawCard(int n) {
        // loop for n times (draw n cards)
        int nextPlayerIndex = (players.indexOf(currentPlayer) + 1) % players.size(); // player that will draw cards
        for (int i = 0; i < n; i++) {
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
    public Card playCard(int cardToPlay) {
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
        
        // for light side checks
        if (darkmode == false) {

            // CHECK IF THEY ARE WILD_DRAW_4 OR DARK_WILD_CARD
            if(card1.getColorLight() == null || card2.getColorLight() == null) {return true;}

            // checks
            boolean check_colour_light = card1.getColorLight().equals(card2.getColorLight());
            boolean check_type_light = card1.getTypeLight().equals(card2.getTypeLight());
            // check to see if it's a wild card that was played
            boolean is_light_wildcard = card1.getTypeLight().equals(Card.TypeLight.WILDTWO) || card1.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR);

            // if its a normal card, do a normal OR check; else check for wild card logic
            if (is_light_wildcard) {
                // WILDTWO
                if (card2.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
                    return check_colour_light && check_type_light;
                } else {
                    return check_colour_light || check_type_light;
                }

            }
            // CHECKS NORMAL LIGHT MODE CARDS
            else {
                return check_colour_light || check_type_light;
            }
        } else {

            // CHECK IF THEY ARE WILD_DRAW_4 OR DARK_WILD_CARD
            if(card1.getColorDark() == null || card2.getColorDark() == null) {return true;}

            // checks
            boolean check_colour_dark = card1.getColorDark().equals(card2.getColorDark());
            boolean check_type_dark = card1.getTypeDark().equals(card2.getTypeDark());
            // check to see if it's a wild card that was played
            boolean is_dark_wildcard = card1.getTypeDark().equals(Card.TypeDark.DARK_WILD_CARD) || card1.getTypeDark().equals(Card.TypeDark.WILD_DRAW_COLOR);

            // if its a normal card, do a normal OR check; else check for wild card logic
            if (is_dark_wildcard) {

                // WILD TWO LOGIC - ONLY PLAYABLE, IF SAME TYPE AND SAME COLOUR
                if (card2.getTypeDark().equals(Card.TypeDark.WILD_DRAW_COLOR)) {
                    return check_colour_dark && check_type_dark;
                } else {
                    return check_colour_dark || check_type_dark;
                }
            }
            // CHECKS NORMAL DARK MODE CARDS
            else {
                return check_colour_dark || check_type_dark;
            }
        }

    }

    /**
     * Reverses the order of players in the game.
     */
    public void reverse() {
        Collections.reverse(players);
        playerIndex = players.indexOf(currentPlayer);
    }

    /**
     * Checks if any player has won the round by emptying their hand.
     *
     * @return True if a player has won, false otherwise.
     */
    public boolean checkWinner() {
        // loop all players and check hand size
        for (Player player : players) {
            if (player.getHand().getSize() == 0) {
                roundWinner = player;
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the total points for the round based on the remaining cards in
     * players' hands.
     *
     * @return The total points for the round.
     */
    public int getTotalPoints() {
        int totalPoint = 0; // sum of all player's cards' values

        if (!darkmode) {
            for (Player plr : players) {
                Hand hand = plr.getHand();
                // get each invidiual card's value and add to sum
                for (Card card : hand.getHandList()) {
                    totalPoint += card.getTypeLight().getValue();
                }
            }
        } else {
            for (Player plr : players) {
                Hand hand = plr.getHand();
                // get each invidiual card's value and add to sum
                for (Card card : hand.getHandList()) {
                    totalPoint += card.getTypeDark().getValue();
                }

            }

        }
        return totalPoint;
    }
}