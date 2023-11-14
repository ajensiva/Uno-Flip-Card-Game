import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;


public class Controller {
    // baaaaah,

    private UnoGUI unoGUI;
    private Uno unoModel;

    private boolean isPlayerLocked = false;

    public Controller(UnoGUI gui, Uno uno) {

        this.unoGUI = gui;
        this.unoModel = uno;


        this.unoGUI.addBuildDeckListener(new updateDeckListener());




        this.unoGUI.addStartGameListener(new playGameButtonListener());


        this.unoGUI.addPlayers(new addPlayersListener());

        this.unoGUI.addNextPlayerListener(new nextPlayerButtonListener());


        // initially update discard
        //this.unoGUI.updateDiscard(unoModel.currentRound.discard.peek());
    }

    private void setHandPanelInteractable(boolean interactable) {
        for (JButton button : unoGUI.playerCards) {
            button.setEnabled(interactable);
        }
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

            // Start the game

            boolean hasEmptyPlayerName = false;
            for (int i = 0; i < unoGUI.playerInputFields.size(); i++) {
                String playerName = unoGUI.playerInputFields.get(i).getText().trim();
                if (playerName.isEmpty()) {
                    hasEmptyPlayerName = true;
                    break;
                }
            }

            if (hasEmptyPlayerName) {
                // Display an alert
                JOptionPane.showMessageDialog(null, "Please fill out all player names.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
                for (int i = 0; i < unoGUI.playerInputFields.size(); i++){
                unoModel.addPlayer(unoGUI.playerInputFields.get(i).getText());
                }
                unoModel.round();

                unoGUI.startGame();
                // Clear existing cards/buttons from the GUI
                unoGUI.clearPlayerCards();
                // Add new cards to the GUI
                for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                    unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
                }
                unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new listenForCardPlayed());
                // update discard ui
                unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());
                unoGUI.setStartMenuVisible(false);

        }
    }

        private class updateDeckListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!isPlayerLocked){
                    //handle playing card
                    System.out.println("Removes Card From Deck");
                    unoModel.currentRound.drawCurrPlayer();
                    System.out.println("Deck Size: " + unoModel.currentRound.deck.getSize());
                    System.out.println("HandSize: " + unoModel.currentRound.currentPlayer.getHand().getSize());

                    //unoModel.currentRound.currentPlayer.getHand();
                    unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(unoModel.currentRound.currentPlayer.getHand().getSize() - 1));
                    unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new listenForCardPlayed());

                    isPlayerLocked = true;
                    setHandPanelInteractable(false);
                    unoGUI.nextPlayer.setEnabled(true);

                }
            }
        }

        public class listenForCardPlayed implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                JButton button = (JButton) e.getSource();
                int buttonIndex = Integer.parseInt(button.getName());
                System.out.println("Card Clicked: " + unoModel.currentRound.currentPlayer.getHand().getCard(buttonIndex));
                unoModel.currentRound.setPlayCardIndex(buttonIndex);
                if (unoModel.currentRound.cardPlayedLogic() && !isPlayerLocked) {


                    isPlayerLocked = true;
                    setHandPanelInteractable(false);
                    unoGUI.nextPlayer.setEnabled(true);
                    if (unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR) || unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILDTWO)) {

                        if (unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
                            unoModel.currentRound.drawCard(1);
                            unoGUI.wildCardGui();
                            unoGUI.redWildCardButtonListener(new playRedWildCard());
                            unoGUI.blueWildCardButtonListener(new playBlueWildCard());
                            unoGUI.yellowWildCardButtonListener(new playYellowWildCard());
                            unoGUI.greenWildCardButtonListener(new playGreenWildCard());

                            unoGUI.discardLabel.setVisible(false);
                        }
                        else if (unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)) {
                            unoModel.currentRound.drawCard(3);
                            unoGUI.wildCardGui();
                            unoGUI.redWildCardButtonListener(new playRedWildCard());
                            unoGUI.blueWildCardButtonListener(new playBlueWildCard());
                            unoGUI.yellowWildCardButtonListener(new playYellowWildCard());
                            unoGUI.greenWildCardButtonListener(new playGreenWildCard());


                            unoGUI.discardLabel.setVisible(false);

                        }
                    }



                    try {
                        unoGUI.updatePlayerCardsRemove(button, unoModel.currentRound.currentPlayer.getHand());

                        // update discard ui
                        unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());
                    }
                    catch (IndexOutOfBoundsException index){

                        System.out.println("Warning index is volatile" + index);

                    }


                }
            }

        }


        public class playRedWildCard implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {


                unoModel.currentRound.discard.peek().setColorLight("Red");

                unoGUI.wildCardFrame.setVisible(false);

                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                unoGUI.discardLabel.setVisible(true);




            }
        }

        public class playBlueWildCard implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                unoModel.currentRound.discard.peek().setColorLight("Blue");

                unoGUI.wildCardFrame.setVisible(false);

                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                unoGUI.discardLabel.setVisible(true);

            }
        }

        public class playYellowWildCard implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                unoModel.currentRound.discard.peek().setColorLight("Yellow");

                unoGUI.wildCardFrame.setVisible(false);

                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                unoGUI.discardLabel.setVisible(true);

            }
        }

        public class playGreenWildCard implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                unoModel.currentRound.discard.peek().setColorLight("Green");

                unoGUI.wildCardFrame.setVisible(false);

                unoGUI.discardInfo(unoModel.currentRound.discard.peek());

                unoGUI.discardLabel.setVisible(true);

            }
        }

        public class nextPlayerButtonListener implements  ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerLocked = false;
                setHandPanelInteractable(true);
                System.out.println("player turn changed");
                unoModel.currentRound.nextPlayer();
                unoGUI.displayCurrentPlayer(unoModel.currentRound.getPlayers().indexOf(unoModel.currentRound.currentPlayer));
                unoGUI.clearPlayerCards();
                // Add new cards to the GUI
                for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                    unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
                }
                unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new listenForCardPlayed());
                unoGUI.nextPlayer.setEnabled(false);

            }
        }


        public static void main(String args[]) {
            Controller controller = new Controller(new UnoGUI(), new Uno());

        }
    }
