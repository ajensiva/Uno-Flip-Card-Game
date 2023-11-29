
import java.io.*;
import java.util.ArrayList;

/**
 * Uno Class: Used to start and manage Uno card game.
 * This class initializes the game, allows players to enter their names, and
 * manages rounds.
 * 
 * @author Jason, Ajen, Arun, Zarif
 * @version 3.0
 */

public class Uno implements Serializable {

    private final int MAXSCORE = 500; // max score to win game
    protected static ArrayList<Player> players = new ArrayList<>(); // store all players in an array
    protected Round currentRound;

    protected Player gameWinner;

    /**
     * Constructor for the Uno class.
     * Initializes the list of players by allowing them to enter their names.
     */
    public Uno() {
        this.gameWinner = null;
    }

    /**
     * Checks if a player has won the game.
     *
     * @return True if a player has a score greater than or equal to the maximum
     *         score; otherwise, false.
     */
    public boolean checkGameWon() {
        // loop all players and check their score and compare with maxscore
        for (Player plr : players) {
            if (plr.getScore() >= MAXSCORE) {
                gameWinner = plr; // player that won the game
                return true;
            }
        }
        return false;
    }

    /**
     * begins a round a Uno
     */
    public void round() {
        currentRound = new Round(players);
        currentRound.setCurrentPlayertoFirstIndex();
    }

    /**
     * Adds a player to the game model
     * 
     * @param name
     */
    public void addPlayer(String name, boolean isBot) {
        if (isBot) {
            AllenAI bot = new AllenAI(name);
            players.add(bot);
        } else {
            Player player = new Player(name);
            players.add(player);
        }
    }

    public String unoToXML() {
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<root>\n");
        xmlBuilder.append("\t<Uno>\n");

        System.out.println("IM HERE FIRST");
        xmlBuilder.append(this.currentRound.roundToXML());
        xmlBuilder.append("\t</Uno>\n");
        xmlBuilder.append("</root>\n");

        return xmlBuilder.toString();
    }


    public void saveGame() throws IOException {
        try (FileWriter writer = new FileWriter("saveGameXML.xml")) {
            writer.write(unoToXML());
        }
    }


        public static void main (String [] args) throws IOException {

            Player player1 = new Player("Juan");

            Player player2 = new Player("Georgio");

            Player player3 = new Player( "DLo");



            Uno uno = new Uno();

            uno.addPlayer(player1.getName(), false);
            uno.addPlayer(player2.getName(), false);
            uno.addPlayer(player2.getName(), false);

            uno.round();


            uno.saveGame();


            //saving load 1 of Uno game


            System.out.println("IM HERE ABOUT TO WRITE BYTES TO UNOGAME!");
            FileOutputStream file = new FileOutputStream("UnoGame1.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(uno);
            out.close();




        }
    }