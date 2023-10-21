// working on it: karki

public class Player {
    
    // variables
    private Hand hand;
    private String name;
    private int playerScore;

    // constructor
    public Player(String name){
        this.name = name;
        this.hand = new Hand();
    }

    // getScore()
    public int getScore(){
        return this.playerScore;
    }


    public Card playCard(String user){

        return null;
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

    // equals override
    @Override
    public boolean equals(Object obj){
        Player plr = (Player) obj;
        if(this.getName() == plr.getName()){
            return true;
        }
        return false;
    }
}
