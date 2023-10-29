import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller {


    private UnoGUI view;
    private Uno unoModel;

    public Controller(UnoGUI view, Uno uno) {
        this.view = view;
        this.unoModel = uno;
        view.buildDeck(new updateDeckListener());
        uno.playGame();
    }

    private class updateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //handle playing card
            System.out.println("Removes Card");


            System.out.println(unoModel.currentRound.deck.peek());
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