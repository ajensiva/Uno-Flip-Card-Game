import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


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



    }

    public class addPlayersListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (unoGUI.numFields < 4) {
                unoGUI.addPlayerField();
            }
            if (unoGUI.numFields >= 4) {
                unoGUI.addPlayer.setEnabled(false); // Disable the add player button
            }
        }
    }

    public class playGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /*
            // Check if all player fields have been filled out
            boolean allFieldsFilled = true;
            for (JTextField textField : unoGUI.inputFields) {
                if (textField.getText().trim().isEmpty()) {
                    allFieldsFilled = false;
                    break;
                }
            }

            if (!allFieldsFilled) {
                // Show error alert
                JOptionPane.showMessageDialog(unoGUI.startMenuFrame,
                        "Please enter names for all players.",
                        "Missing Player Names",
                        JOptionPane.ERROR_MESSAGE);
            } else {*/
                // All fields are filled, proceed with starting the game
                unoGUI.startGame();

                // Clear existing cards/buttons from the GUI
                unoGUI.clearPlayerCards();

                // Add new cards to the GUI
                for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                    unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
                }

                unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new listenForCardPlayed());
            }
        }
    //}


    private class updateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //handle playing card
            System.out.println("Removes Card From Deck");
            unoModel.currentRound.drawCurrPlayer();
            System.out.println("Deck Size: " + unoModel.currentRound.deck.getSize());
            System.out.println("HandSize: " + unoModel.currentRound.currentPlayer.getHand().getSize());

            //unoModel.currentRound.currentPlayer.getHand();
            unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(unoModel.currentRound.currentPlayer.getHand().getSize()-1));
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


    public static void main(String args[]) {
        Controller controller = new Controller(new UnoGUI(), new Uno());

    }
}