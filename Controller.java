import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private UnoGUI unoGUI;
    private Uno unoModel;

    public Controller(UnoGUI gui, Uno uno) {
        this.unoGUI = gui;
        this.unoModel = uno;

        // testing area
        this.unoGUI.buildDeck(new updateDeckListener());
        this.unoModel.testRound();
        
        System.out.println("Player's Hand: " + unoModel.currentRound.currentPlayer.getHand());
    }
    
    private class updateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //handle playing card
            System.out.println("Top of deck: " + unoModel.currentRound.deck.peek());
        }
    }

    private class updateHandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){

            //Handle adding Card to deck
        }

    }

    // asdasdas
    public static void main(String args[]) {
        Controller controller = new Controller(new UnoGUI(), new Uno());

    }
}