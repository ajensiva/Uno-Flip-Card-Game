
import java.io.*;
import java.util.ArrayList;

/**
 * Uno Class: Used to start and manage Uno card game.
 * This class initializes the game, allows players to enter their names, and
 * manages rounds.
 * 
 * @author Jason, Ajen, Arun, Zarif
 * @version 4.0
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

    /**
     * Builds XML String with current round information
     * @return
     */

    public String unoToXML() {
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<root>\n");
        xmlBuilder.append("\t<Uno>\n");

        xmlBuilder.append(this.currentRound.roundToXML());
        xmlBuilder.append("\t</Uno>\n");
        xmlBuilder.append("</root>\n");

        return xmlBuilder.toString();
    }

    /**
     * A function to write a current instance of the game to an XML in the appropriate format
     * @throws IOException
     */

    public void saveGame() throws IOException {
        try (FileWriter writer = new FileWriter("saveGameXML.xml")) {
            writer.write(unoToXML());
        }
    }

    /**
     * A function to write a current instance of the game after a player has made a move to an XML in the appropriate format
     * @throws IOException
     */

    public void savePlayerMove() throws IOException {
        try (FileWriter writer = new FileWriter("savePlayerMoveXML.xml")) {
            writer.write(unoToXML());
        }
    }
    }