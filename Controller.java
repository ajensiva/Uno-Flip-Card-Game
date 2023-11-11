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

        unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new listenForCardPlayed());


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

    public class playGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


            // Start the game
            unoGUI.startGame();

            // Clear existing cards/buttons from the GUI
            unoGUI.clearPlayerCards();

            // Add new cards to the GUI
            for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i), i);

            }


        }
    }


    private class updateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //handle playing card
            System.out.println("Removes Card From Deck");
            unoModel.currentRound.drawCurrPlayer();
            System.out.println("Deck Size: " + unoModel.currentRound.deck.getSize());
            System.out.println("HandSize: " + unoModel.currentRound.currentPlayer.getHand().getSize());

            //unoModel.currentRound.currentPlayer.getHand();
            unoGUI.addCard(unoModel.currentRound.drawCurrPlayer(), unoModel.currentRound.currentPlayer.getHand().getSize());
            unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new listenForCardPlayed());

        }
    }

    public class listenForCardPlayed implements ActionListener{

        @Override 
        public void actionPerformed(ActionEvent e) {

            JButton button = (JButton) e.getSource();
            int buttonIndex = Integer.parseInt(button.getName());
            System.out.println("Card Clicked: " + button.getName());

            System.out.println(unoModel.currentRound.currentPlayer.getHand().getCard(buttonIndex));
            System.out.println(unoModel.currentRound.deck.peek());

            unoModel.currentRound.setPlayCardIndex(buttonIndex);
            if(unoModel.currentRound.cardPlayedLogic()){
                unoGUI.updatePlayerCardsRemove(button, unoModel.currentRound.currentPlayer.getHand());
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