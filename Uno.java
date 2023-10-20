import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

// change by zarif
public class Uno{
    // working: Jason

    private final int MAXSCORE = 500;
    private Player winner;
    private static LinkedList<Player> players;

    public Uno(String playerList) {
        System.out.print("Enter all players: ");
        players = new LinkedList<Player>();
        String[] names = playerList.split(" ");
        for (String name : names) {

            Player newPlayer = new Player(name);
            players.add(newPlayer);

        }

    }

    public void playGame() {}

    public void winner(int score) {}


    public static void main(String args[]) {

        Round newRound = new Round(players);
        while (newRound.checkWinner())  {
            newRound.playRound();
            newRound = new Round(players);
        }
    }



}
