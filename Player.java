// working on it: karki

public class Player {
    
    // variables
    private Hand hand;
    private String name;
    private int playerScore;

    // constructor
    public Player(String name, Hand hand){
        this.name = name;
        this.hand = hand;
    }

    // getScore()
    public int getScore(){
        return this.playerScore;
    }
    
    // setScore()
    public void setScore(int score){
        if(score >= 0){
            this.playerScore = score;
        }
    }

    // getHand()
    public Hand getHand(){
        return this.hand;
    }

    // getName()
    public String getName(){
        return this.name;
    }
}
