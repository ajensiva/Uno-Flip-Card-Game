import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

// change by zarif
public class Uno{
    // working: Jason

    private final int MAXSCORE = 500;
    private Player winner;
    private static ArrayList<Player> players;

    public Uno(String playerList) {
        // init list of players
        players = new ArrayList<Player>();
        
        // split by space and create new player
        String[] names = playerList.split(" ");
        for (String name : names) {
            Player newPlayer = new Player(name);
            players.add(newPlayer);
        }
    }

    public void playGame() {
        // create new game until someone wins the game
        Round newRound = new Round(players);
        while(!this.winner()){
            newRound.playRound();
            // new round after first round done
            newRound = new Round(players);
        }
    }

    // return if someone won
    public boolean winner(){
        for(Player plr : players){
            if(plr.getScore() >= MAXSCORE){
                return true;
            }
        }
        return false;
    }

    public void printPlayers() {
        for(Player player: players) {
            System.out.println(player.getName());
        }
    }


    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter all players: ");
        String userInput = scanner.nextLine();
        Uno newGame = new Uno(userInput);

        newGame.printPlayers();

        newGame.playGame();

    }
}
