import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

// change by zarif
public class Uno{
    // working: Jason

    private final int MAXSCORE = 500;

    private static ArrayList<Player> players;

    public Uno() {
        // init list of players
        players = new ArrayList<Player>();

        boolean flag = true;


            // split by space and create new player
            Scanner num_of_players = new Scanner(System.in);
            System.out.println("Enter number of players (2-4): ");
            int num = num_of_players.nextInt();

            Scanner name = new Scanner(System.in);

            for (int i = 0; i < num; i++){

                System.out.println("Enter player (" + (i + 1) + ") name: ");

                String name_player = name.nextLine();
                Player newPlayer = new Player(name_player);
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
                System.out.println("winner is: " + plr  + "!");
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

        Uno newGame = new Uno();

        newGame.printPlayers();

        newGame.playGame();

    }
}
