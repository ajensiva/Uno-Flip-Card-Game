import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * The Controller class handles user input and manages interactions between the GUI and the Uno game model.
 * @author Zarif, Ajen, Arun, Jason
 * @version 2.0
 */
public class Controller {

    private UnoGUI unoGUI;
    private Uno unoModel;

    private boolean isPlayerLocked = false;

    /**
     * Constructor for the Controller class.
     *
     * @param gui  The UnoGUI instance representing the graphical user interface.
     * @param uno  The Uno instance representing the Uno game model.
     */
    public Controller(UnoGUI gui, Uno uno) {
        this.unoGUI = gui;
        this.unoModel = uno;

        this.unoGUI.addBuildDeckListener(new UpdateDeckListener());
        this.unoGUI.addStartGameListener(new PlayGameButtonListener());
        this.unoGUI.addPlayers(new AddPlayersListener());
        this.unoGUI.addNextPlayerListener(new NextPlayerButtonListener());
    }

    private void setHandPanelInteractable(boolean interactable) {
        for (JButton button : unoGUI.playerCards) {
            button.setEnabled(interactable);
        }
    }

    /**
     * ActionListener for the "Add Players" button in the GUI.
     */
    public class AddPlayersListener implements ActionListener {
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

    /**
     * ActionListener for the "Play Game" button in the GUI.
     */
    public class PlayGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean hasEmptyPlayerName = false;
            for (int i = 0; i < unoGUI.playerInputFields.size(); i++) {
                String playerName = unoGUI.playerInputFields.get(i).getText().trim();
                if (playerName.isEmpty()) {
                    hasEmptyPlayerName = true;
                    break;
                }
            }

            if (hasEmptyPlayerName) {
                JOptionPane.showMessageDialog(null, "Please fill out all player names.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < unoGUI.playerInputFields.size(); i++) {
                unoModel.addPlayer(unoGUI.playerInputFields.get(i).getText());
            }

            unoModel.round();
            unoGUI.startGame();
            unoGUI.clearPlayerCards();

            for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
            }

            unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());
            unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());
            unoGUI.setStartMenuVisible(false);
        }
    }

    /**
     * ActionListener for the "Update Deck" button in the GUI.
     */
    private class UpdateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isPlayerLocked) {
                unoModel.currentRound.drawCurrPlayer();
                unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(unoModel.currentRound.currentPlayer.getHand().getSize() - 1));
                unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());

                isPlayerLocked = true;
                setHandPanelInteractable(false);
                unoGUI.nextPlayer.setEnabled(true);
            }
        }
    }

    /**
     * ActionListener for playing a card in the GUI.
     */
    public class ListenForCardPlayed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int buttonIndex = Integer.parseInt(button.getName());
            
            unoModel.currentRound.setPlayCardIndex(buttonIndex);
            System.out.println(unoModel.currentRound.getPlayCard());
            if (unoModel.currentRound.cardPlayedLogic() && !isPlayerLocked) {
                isPlayerLocked = true;
                setHandPanelInteractable(false);
                unoGUI.nextPlayer.setEnabled(true);


                if(unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)
                        || unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILDTWO)) {

                    if (unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
                        unoModel.currentRound.drawCard(1);
                        unoGUI.wildCardGui();
                        unoGUI.redWildCardButtonListener(new PlayRedWildCard());
                        unoGUI.blueWildCardButtonListener(new PlayBlueWildCard());
                        unoGUI.yellowWildCardButtonListener(new PlayYellowWildCard());
                        unoGUI.greenWildCardButtonListener(new PlayGreenWildCard());
                        unoGUI.discardLabel.setVisible(false);
                    } else if (unoModel.currentRound.Remove_card.getTypeLight()
                            .equals(Card.TypeLight.WILD_DRAW_FOUR)) {
                        unoModel.currentRound.drawCard(3);
                        unoGUI.wildCardGui();
                        unoGUI.redWildCardButtonListener(new PlayRedWildCard());
                        unoGUI.blueWildCardButtonListener(new PlayBlueWildCard());
                        unoGUI.yellowWildCardButtonListener(new PlayYellowWildCard());
                        unoGUI.greenWildCardButtonListener(new PlayGreenWildCard());
                        unoGUI.discardLabel.setVisible(false);
                    }
                }

                try {
                    unoGUI.updatePlayerCardsRemove(button, unoModel.currentRound.currentPlayer.getHand());
                    unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());
                    if (unoModel.currentRound.checkWinner()){

                        JOptionPane.showMessageDialog(null, unoModel.currentRound.currentPlayer.getName(), "WINNER!!!", JOptionPane.INFORMATION_MESSAGE);
                        unoGUI.setstartGameVisible(false);
                    }
                } catch (IndexOutOfBoundsException index) {
                    System.out.println("Warning index is volatile" + index);
                }
            }
        }
    }

    /**
     * ActionListener for playing the red wild card.
     */
    public class PlayRedWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            unoModel.currentRound.discard.peek().setColorLight("Red");
            unoGUI.wildCardFrame.setVisible(false);
            unoGUI.discardInfo(unoModel.currentRound.discard.peek());
            unoGUI.discardLabel.setVisible(true);
        }
    }

    /**
     * ActionListener for playing the blue wild card.
     */
    public class PlayBlueWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            unoModel.currentRound.discard.peek().setColorLight("Blue");
            unoGUI.wildCardFrame.setVisible(false);
            unoGUI.discardInfo(unoModel.currentRound.discard.peek());
            unoGUI.discardLabel.setVisible(true);
        }
    }

    /**
     * ActionListener for playing the yellow wild card.
     */
    public class PlayYellowWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            unoModel.currentRound.discard.peek().setColorLight("Yellow");
            unoGUI.wildCardFrame.setVisible(false);
            unoGUI.discardInfo(unoModel.currentRound.discard.peek());
            unoGUI.discardLabel.setVisible(true);
        }
    }

    /**
     * ActionListener for playing the green wild card.
     */
    public class PlayGreenWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            unoModel.currentRound.discard.peek().setColorLight("Green");
            unoGUI.wildCardFrame.setVisible(false);
            unoGUI.discardInfo(unoModel.currentRound.discard.peek());
            unoGUI.discardLabel.setVisible(true);
        }
    }

    /**
     * ActionListener for the "Next Player" button in the GUI.
     */
    public class NextPlayerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isPlayerLocked = false;
            setHandPanelInteractable(true);

                if ((unoModel.currentRound.Remove_card !=  null) && unoModel.currentRound.Remove_card.getTypeLight() == Card.TypeLight.REVERSE) {
                     Collections.reverse(unoGUI.playerInputFields);
                }


            unoModel.currentRound.nextPlayer();
            unoGUI.displayCurrentPlayer(unoModel.currentRound.getPlayers().indexOf(unoModel.currentRound.currentPlayer));
            unoGUI.clearPlayerCards();

            for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
            }

            unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());
            unoGUI.nextPlayer.setEnabled(false);
        }
    }

    /**
     * The main method to run the Uno game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Controller controller = new Controller(new UnoGUI(), new Uno());
    }
}

