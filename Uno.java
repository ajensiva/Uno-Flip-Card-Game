import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Uno Class: Used to start and manage an Uno card game.
 * This class initializes the game, allows players to enter their names, and manages rounds.
 * @author Jason, Ajen, Arun, Zarif
 * @version 1.0
 */
public class Uno {


    private static int count = 0;

    private final int MAXSCORE = 500; // max score to win game
    protected static ArrayList<Player> players = new ArrayList<>(); // store all players in an array

    protected Round currentRound;
    /**
     * Constructor for the Uno class.
     * Initializes the list of players by allowing them to enter their names.
     */
    public Uno() {


    }

    /**
     * Starts and manages the Uno card game.
     * Creates and plays rounds until a player wins the game.
     */
    public void playGame() {
        // start a new round, first round
        currentRound = new Round(players);
        count ++;
        while (!this.winner()) {
            // repeat rounds until a winner is found
            currentRound.playRound();
            // start a new round after the previous round is complete
            currentRound = new Round(players);
        }
    }

    /**
     * Checks if a player has won the game.
     *
     * @return True if a player has a score greater than or equal to the maximum score; otherwise, false.
     */
    public boolean winner() {
        // loop all players and check their score and compare with maxscore
        for (Player plr : players) {
            if (plr.getScore() >= MAXSCORE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the names of the players in the game.
     */
    public void printPlayers() {
        for (Player player : players) {
            System.out.println(player.getName());
        }
    }

    public void round(){
        currentRound = new Round(players);
        currentRound.playRound();
    }

    public void addPlayer(String name){

        Player player = new Player(name);

        players.add(player);



    }
    /*
    public static void main(String args[]) {
        Uno newGame = new Uno();

        newGame.printPlayers();

        newGame.playGame();

    }
    */
}