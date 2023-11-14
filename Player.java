/**
 * Player class represents a player in the Uno game.
 *
 * @author Arun, Jason, Ajen, Zarif
 * @version 2.0
 */
public class Player {

    // attributes
    private Hand hand;        // The player's hand of Uno cards
    private String name;      // The name of the player
    private int playerScore;  // The player's score in the game

    /**
     * Constructor to create a new player with the given name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.playerScore = 0;
        this.hand = new Hand();
    }
  
    /**
     * Get the player's score.
     *
     * @return The player's score.
     */
    public int getScore() {
        return this.playerScore;
    }
  
    /**
     * Set the player's score to the specified value.
     *
     * @param score The new score for the player.
     */
    public void setScore(int score) {
        if (score >= 0) {
            this.playerScore = score;
        }
    }
  
    /**
     * Get the player's hand.
     *
     * @return The player's hand of Uno cards.
     */
    public Hand getHand() {
        return this.hand;
    }
  
    /**
     * Get the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Override the equals method to compare players based on name and score.
     *
     * @param obj The object to compare with this player.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        Player plr = (Player) obj;
        if (this.getName().equals(plr.getName()) && this.getScore() == plr.getScore()) {
            return true;
        }
        return false;
    }
}
