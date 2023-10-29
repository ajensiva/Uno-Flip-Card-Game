package UnoModel;


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
    private static ArrayList<Player> players; // store all players in an array

    /**
     * Constructor for the Uno class.
     * Initializes the list of players by allowing them to enter their names.
     */
    public Uno() {
        // Initialize the list of players
        players = new ArrayList<Player>();
        boolean flag = true;

        while (flag) {
            // scanner to get userinput (# players and player names)
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter the number of players (2-4): ");

            if (userInput.hasNextInt()) {

                int num = userInput.nextInt();
                userInput.nextLine(); // consume the newline left by nextInt()

                if (num < 2 || num > 4) {
                    System.out.println("Enter a number between 2 or 4 please!");

                } else {
                    // loop for # of players
                    for (int i = 0; i < num; i++) {
                        // get plr name and create new player instance and add to array
                        System.out.println("Enter player " + (i + 1) + "'s name: ");
                        String name_player = userInput.nextLine();
                        Player newPlayer = new Player(name_player);
                        players.add(newPlayer);
                    }

                    flag = false;
                }
            } else {

                String input = userInput.next(); // Read the non-integer input as a string
                System.out.println("Invalid input: " + input + " is not an integer.");
            }

        }
    }

    /**
     * Starts and manages the Uno card game.
     * Creates and plays rounds until a player wins the game.
     */
    public void playGame() {
        // start a new round, first round
        Round newRound = new Round(players);
        count ++;
        while (!this.winner()) {
            // repeat rounds until a winner is found
            newRound.playRound();
            // start a new round after the previous round is complete
            newRound = new Round(players);
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


    public static void main(String args[]) {

        Uno newGame = new Uno();

        newGame.printPlayers();

        newGame.playGame();

    }
}
