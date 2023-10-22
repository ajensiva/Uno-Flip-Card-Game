import java.util.ArrayList;
import java.util.Scanner;

/**
 * Uno Class: Used to start and manage an Uno card game.
 * This class initializes the game, allows players to enter their names, and manages rounds.
 *
 * @version 1.0
 */
public class Uno {
    private final int MAXSCORE = 500;
    private static ArrayList<Player> players;

    /**
     * Constructor for the Uno class.
     * Initializes the list of players by allowing them to enter their names.
     */
    public Uno() {
        // Initialize the list of players
        players = new ArrayList<Player>();

        boolean flag = true;

        // Obtain the number of players
        Scanner num_of_players = new Scanner(System.in);
        System.out.println("Enter the number of players (2-4): ");
        int num = num_of_players.nextInt();

        Scanner name = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            System.out.println("Enter player " + (i + 1) + "'s name: ");
            String name_player = name.nextLine();
            Player newPlayer = new Player(name_player);
            players.add(newPlayer);
        }
    }

    /**
     * Starts and manages the Uno card game.
     * Creates and plays rounds until a player wins the game.
     */
    public void playGame() {
        Round newRound = new Round(players);
        while (!this.winner()) {
            newRound.playRound();
            // Start a new round after the first round is complete
            newRound = new Round(players);
        }
    }

    /**
     * Checks if a player has won the game.
     *
     * @return True if a player has a score greater than or equal to the maximum score; otherwise, false.
     */
    public boolean winner() {
        for (Player plr : players) {
            if (plr.getScore() >= MAXSCORE) {
                System.out.println("The winner is: " + plr + "!");
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
