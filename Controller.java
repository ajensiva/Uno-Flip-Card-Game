import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class Controller {


    private UnoGUI unoGUI;
    private Uno unoModel;

    public Controller(UnoGUI gui, Uno uno) {
        this.unoGUI = gui;
        this.unoModel = uno;


        this.unoGUI.addBuildDeckListener(new updateDeckListener());

        this.unoModel.testRound();


        this.unoGUI.addStartGameListener(new playGameButtonListener());
        this.unoGUI.addPlayers(new addPlayersListener());
        this.unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new listenForCardPlayed());
    }

    public class addPlayersListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String[] userInputs = new String[unoGUI.numFields];
            for (int i = 0; i < unoGUI.numFields; i++) {
                userInputs[i] = unoGUI.inputFields.get(i).getText();
            }

            // Display the collected inputs (you can change this to your desired action)
            for (int i = 0; i < unoGUI.numFields; i++) {
                System.out.println("Player " + (i + 1) + ": " + userInputs[i]);

            }
        }
    }

    public class playGameButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            unoGUI.startGame();


        }
    }


    private class updateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //handle playing card
            System.out.println(unoModel.currentRound.deck.peek());
            System.out.println("Removes Card From Deck");
            unoModel.currentRound.drawCurrPlayer();
            System.out.println(unoModel.currentRound.deck.peek());

            //unoModel.currentRound.currentPlayer.getHand();

        }
    }

    public class listenForCardPlayed implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            System.out.println("Card clicked: " + button.getName());
            for (int i = 0; i < unoGUI.playerCards.size(); i++){
                if (button == unoGUI.playerCards.get(i)){

                    unoModel.currentRound.setPlayCardIndex(i);

                }
            }
        }
    }

    private class updateHandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){

            //Handle adding Card to deck
        }

    }


    public static void main(String args[]) {
        Controller controller = new Controller(new UnoGUI(), new Uno());

    }
}