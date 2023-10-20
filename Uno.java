import java.util.LinkedList;
import java.util.Scanner;

// change by zarif
public class Uno{
    // working: Jason

    private final int MAXSCORE = 500;
    private Player winner;
    LinkedList<Player> players;

    public Uno(String playerList) {
        System.out.print("Enter all players: ");
        players = new LinkedList<Player>();
        String[] names = playerList.split(" ");
        for (String name : names) {
            // Player newPlayer = new Player(name, hand);
            // players.add(player);

        }

    }

    public void playGame() {}
    // public Player winner() {}

    public static void main(String args[]) {}



}
