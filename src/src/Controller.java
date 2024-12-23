package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The Controller class handles user input and manages interactions between the
 * GUI and the Uno game model.
 * 
 * @author Zarif, Ajen, Arun, Jason
 * @version 4.0
 */
public class Controller {

    private UnoGUI unoGUI;
    private Uno unoModel;

    private boolean isPlayerLocked = false;

    private FileOutputStream playerMoveFileUndo =
            new FileOutputStream("playerMoveUndoRepository.ser");

    private FileOutputStream playerMoveFileRedo =
            new FileOutputStream("playerMoveRedoRepository.ser");

    private FileInputStream playerUndo =
            new FileInputStream("playerMoveUndoRepository.ser");

    private FileInputStream playerRedo =
            new FileInputStream("playerMoveRedoRepository.ser");

    private FileOutputStream saveGameFile =
            new FileOutputStream("saveGameRepository.ser");

    private FileInputStream loadGameFile =
            new FileInputStream("saveGameRepository.ser");

    private Card saveCardPLayed;



    /**
     * Constructor for the Controller class.
     *
     * @param gui The UnoGUI instance representing the graphical user interface.
     * @param uno The Uno instance representing the Uno game model.
     */
    public Controller(UnoGUI gui, Uno uno) throws FileNotFoundException {
        this.unoGUI = gui;
        this.unoModel = uno;

        this.unoGUI.addBuildDeckListener(new UpdateDeckListener());
        this.unoGUI.addStartGameListener(new PlayGameButtonListener());
        //-----------------------------------------
        this.unoGUI.addFileSaveMenu(new saveFileSave());
        this.unoGUI.addFileUndo(new saveFileUndo());
        this.unoGUI.addFileRedo(new saveFileRedo());

        //-----------------------------------------
        this.unoGUI.addPlayers(new AddPlayersListener());
        this.unoGUI.addNextPlayerListener(new NextPlayerButtonListener());
        this.unoGUI.addBot.addActionListener(new addbotListener());
        this.unoGUI.addFileLoad(new loadSaveListener());

    }

    /**
     * sets the current players hand to be interactable or not interactable
     * depending on if they used their turn or not
     * 
     * @param interactable
     */
    private void setHandPanelInteractable(boolean interactable) {
        for (JButton button : unoGUI.playerCards) {
            button.setEnabled(interactable);
        }
    }

    /**
     * Performs a series of checks to see what actions should be performed according
     * to the type; plays card if card is playable
     */
    public void controllerPlayCardLogic() {

        if (unoModel.currentRound.cardPlayedLogic() && !isPlayerLocked) {
            isPlayerLocked = true;
            setHandPanelInteractable(false);
            unoGUI.nextPlayer.setEnabled(true);

            if (!Round.darkmode) {

                if (unoModel.currentRound.removeCard.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)
                        || unoModel.currentRound.removeCard.getTypeLight().equals(Card.TypeLight.WILDTWO)) {

                    if (unoModel.currentRound.removeCard.getTypeLight().equals(Card.TypeLight.WILDTWO)) {

                        unoModel.currentRound.drawCard(2);
                        unoGUI.wildCardGui();
                        unoGUI.red.setText("Red");
                        unoGUI.blue.setText("Blue");
                        unoGUI.yellow.setText("Yellow");
                        unoGUI.green.setText("Green");
                        unoGUI.redWildCardButtonListener(new PlayRedWildCard());
                        unoGUI.blueWildCardButtonListener(new PlayBlueWildCard());
                        unoGUI.yellowWildCardButtonListener(new PlayYellowWildCard());
                        unoGUI.greenWildCardButtonListener(new PlayGreenWildCard());
                        unoGUI.discardLabel.setVisible(false);
                    } else if (unoModel.currentRound.removeCard.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)) {

                        unoModel.currentRound.drawCard(4);
                        unoGUI.wildCardGui();
                        unoGUI.red.setText("Red");
                        unoGUI.blue.setText("Blue");
                        unoGUI.yellow.setText("Yellow");
                        unoGUI.green.setText("Green");
                        unoGUI.redWildCardButtonListener(new PlayRedWildCard());
                        unoGUI.blueWildCardButtonListener(new PlayBlueWildCard());
                        unoGUI.yellowWildCardButtonListener(new PlayYellowWildCard());
                        unoGUI.greenWildCardButtonListener(new PlayGreenWildCard());
                        unoGUI.discardLabel.setVisible(false);
                    }
                }

            } else {
                if (unoModel.currentRound.removeCard.getTypeDark().equals(Card.TypeDark.DARK_WILD_CARD)) {
                    unoModel.currentRound.drawCard(1);
                    unoGUI.wildCardGui();
                    unoGUI.red.setText("Orange");
                    unoGUI.blue.setText("Teal");
                    unoGUI.yellow.setText("Purple");
                    unoGUI.green.setText("Pink");

                    unoGUI.redWildCardButtonListener(new PlayRedWildCard());
                    unoGUI.blueWildCardButtonListener(new PlayBlueWildCard());
                    unoGUI.yellowWildCardButtonListener(new PlayYellowWildCard());
                    unoGUI.greenWildCardButtonListener(new PlayGreenWildCard());
                    unoGUI.discardLabel.setVisible(false);

                }

            }
            System.out.println("UPDATING PLAYER HAND AND DISCARD");

            unoGUI.updatePlayerCardsRemove(unoModel.currentRound.getCardtoPlayIndex(), unoModel.currentRound.currentPlayer.getHand());
            unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());

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
                unoGUI.addBot.setEnabled(false); // Disable the add bot button
                unoGUI.addPlayer.setEnabled(false); // Disable the add player button
            }
        }
    }

    /**
     * adds a Listener to the "ADD BOT" button in the GUI
     */

    public class addbotListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (unoGUI.numFields < 4) {
                unoGUI.addBotField();
            }
            if (unoGUI.numFields >= 4) {
                unoGUI.addBot.setEnabled(false); // Disable the add bot button
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
                JOptionPane.showMessageDialog(null, "Please fill out all player names.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < unoGUI.playerInputFields.size(); i++) {
                JTextField field = unoGUI.playerInputFields.get(i);
                boolean isBot = false;
                if (field.getName() != null && field.getName().substring(0, 3).equals("Bot")) {
                    isBot = true;
                }
                unoModel.addPlayer(unoGUI.playerInputFields.get(i).getText(), isBot);
            }

            unoModel.round();
            unoGUI.startGame();
            unoGUI.clearPlayerCards();
            unoGUI.Leaderboard(unoModel.currentRound.getPlayers());

            for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
            }

            unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());
            unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());
            unoGUI.setStartMenuVisible(false);
            unoGUI.updatePoints(unoModel.currentRound.getTotalPoints());
        }
    }

    /**
     * ActionListener for the "Update Deck" button in the GUI.
     */
    private class UpdateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            //Saving what the player just did:

            try {
                unoModel.savePlayerMove();

                ObjectOutputStream out = new ObjectOutputStream(playerMoveFileUndo);

                out.writeObject(unoModel.currentRound);
                System.out.println("SAVED PLAYER MOVE");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (!isPlayerLocked) {
                unoModel.currentRound.drawCurrPlayer();
                unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand()
                        .getCard(unoModel.currentRound.currentPlayer.getHand().getSize() - 1));
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

            //Saving what the player just did:

            try {
                unoModel.savePlayerMove();

                ObjectOutputStream out = new ObjectOutputStream(playerMoveFileUndo);

                out.writeObject(unoModel.currentRound);
                System.out.println("SAVED PLAYER MOVE UNDO");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }



            JButton button = (JButton) e.getSource();
            int buttonIndex = Integer.parseInt(button.getName());

            unoModel.currentRound.setPlayCardIndex(buttonIndex);

            saveCardPLayed = unoModel.currentRound.currentPlayer.getHand().getCard(buttonIndex);
            controllerPlayCardLogic();

            if (unoModel.currentRound.checkWinner()) {
                JOptionPane.showMessageDialog(null, unoModel.currentRound.currentPlayer.getName(), "Won Round! ",
                        JOptionPane.INFORMATION_MESSAGE);

                unoModel.currentRound.roundWinner.setScore(
                        unoModel.currentRound.roundWinner.getScore() + unoModel.currentRound.getTotalPoints());
                System.out.println(unoModel.currentRound.roundWinner.getScore());
                unoGUI.Leaderboard(unoModel.players);

                // PERSON WON GAME
                if (unoModel.checkGameWon()) {

                    JOptionPane.showMessageDialog(null, unoModel.gameWinner.getName(), "Won The Game! ",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (unoGUI.wildCardDialog != null) {
                        unoGUI.wildCardDialog.dispose();
                    }
                    unoModel.round();
                    unoGUI.clearPlayerCards();
                    for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                        unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
                    }
                    unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());
                    unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());
                    unoGUI.setStartMenuVisible(false);
                    unoGUI.updatePoints(unoModel.currentRound.getTotalPoints());

                    unoGUI.nextPlayer.doClick();

                }

            }

            //Saving what the player just did:

            try {
                unoModel.savePlayerMove();

                ObjectOutputStream out = new ObjectOutputStream(playerMoveFileRedo);

                out.writeObject(unoModel.currentRound);

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        }
    }

    /**
     * ActionListener for playing the red wild card.
     */
    public class PlayRedWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (Round.darkmode) {

                unoModel.currentRound.discard.peek().setColorDark("Orange");
                unoGUI.wildCardDialog.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                unoGUI.discardLabel.setVisible(true);

            } else {
                unoModel.currentRound.discard.peek().setColorLight("Red");
                unoGUI.wildCardDialog.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                unoGUI.discardLabel.setVisible(true);
            }

        }
    }

    /**
     * ActionListener for playing the blue wild card.
     */
    public class PlayBlueWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (Round.darkmode) {

                unoModel.currentRound.discard.peek().setColorDark("Teal");
                unoGUI.wildCardDialog.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                unoGUI.discardLabel.setVisible(true);

            } else {
                unoModel.currentRound.discard.peek().setColorLight("Blue");
                unoGUI.wildCardDialog.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                unoGUI.discardLabel.setVisible(true);
            }
        }
    }

    /**
     * ActionListener for playing the yellow wild card.
     */
    public class PlayYellowWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (Round.darkmode) {

                unoModel.currentRound.discard.peek().setColorDark("Purple");
                unoGUI.wildCardDialog.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                unoGUI.discardLabel.setVisible(true);

            } else {

                unoModel.currentRound.discard.peek().setColorLight("Yellow");
                unoGUI.wildCardDialog.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                unoGUI.discardLabel.setVisible(true);

            }

        }
    }

    /**
     * ActionListener for playing the green wild card.
     */
    public class PlayGreenWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (Round.darkmode) {

                unoModel.currentRound.discard.peek().setColorDark("Pink");
                unoGUI.wildCardDialog.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                unoGUI.discardLabel.setVisible(true);

            } else {

                unoModel.currentRound.discard.peek().setColorLight("Green");
                unoGUI.wildCardDialog.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                unoGUI.discardLabel.setVisible(true);
            }
        }
    }

    /**
     * ActionListener for the "Next Player" button in the GUI, also handles AI logic
     */
    public class NextPlayerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<Player> playersList = unoModel.currentRound.getPlayers();
            int currentIndex = unoModel.currentRound.playerIndex;
            int nextIndex = (currentIndex + 1) % playersList.size();

            if (playersList.get(nextIndex) instanceof AllenAI) {

                unoModel.currentRound.nextPlayer();
                currentIndex = unoModel.currentRound.playerIndex;
                AllenAI bot = (AllenAI) playersList.get(unoModel.currentRound.playerIndex);
                unoGUI.displayCurrentPlayer(currentIndex);

                unoGUI.clearPlayerCards();
                for (int i = 0; i < bot.getHand().getSize(); i++) {
                    unoGUI.addCard(bot.getHand().getCard(i));
                }
                setHandPanelInteractable(false);

                if (bot.allenPlayCard(unoModel.currentRound, bot.getHand())) {
                    if (bot.allenCardPlayed.getTypeLight().equals(Card.TypeLight.WILDTWO) || bot.allenCardPlayed.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR) || bot.allenCardPlayed.getTypeDark().equals(Card.TypeDark.DARK_WILD_CARD)){
                        unoGUI.discardInfo(unoModel.currentRound.discard.peek(), unoModel.currentRound.darkmode);
                    }
                    unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());
                    unoGUI.updatePlayerCardsRemove(unoModel.currentRound.getCardtoPlayIndex(), bot.getHand());
                } else {
                    unoModel.currentRound.drawCurrPlayer();
                    unoGUI.addCard(bot.getHand().getCard(bot.getHand().getSize() - 1));
                    setHandPanelInteractable(false);
                    unoGUI.nextPlayer.setEnabled(true);
                }

                unoGUI.updatePoints(unoModel.currentRound.getTotalPoints());
                return;
            }


            isPlayerLocked = false;
            setHandPanelInteractable(true);


            unoGUI.updatePlayerInputFields(unoModel.currentRound.getPlayers());
            unoModel.currentRound.nextPlayer();
            unoGUI.displayCurrentPlayer(unoModel.currentRound.playerIndex);

            unoGUI.clearPlayerCards();
            for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
            }

            unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());
            unoGUI.nextPlayer.setEnabled(false);
            unoGUI.updatePoints(unoModel.currentRound.getTotalPoints());
        }
    }

    /**
     * ActionListener to listen to JMenuItem "Save"
     */
    public class saveFileSave implements ActionListener{

        public void actionPerformed(ActionEvent e){



            try {

                // Update XML
                unoModel.saveGame();

                //Update SAVE GAME output stream
                ObjectOutputStream out = new ObjectOutputStream(saveGameFile);
                out.writeObject(unoModel.currentRound);


            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }

    }

    /**
     * ActionListener to listen to JMenuItem "Load"
     */
    public class loadSaveListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                ObjectInputStream in = new ObjectInputStream(loadGameFile);
                unoModel.currentRound = (Round) in.readObject();
                unoModel.saveGame();

                unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());

                unoGUI.displayCurrentPlayer(unoModel.currentRound.getPlayers().indexOf(unoModel.currentRound.currentPlayer));

                for (Player player : unoModel.currentRound.getPlayers()) {
                    unoGUI.clearPlayerCards();

                    for (int i = 0; i < player.getHand().getSize(); i++) {
                        unoGUI.addCard(player.getHand().getCard(i));
                    }
                }

                unoGUI.clearPlayerCards();
                for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                    unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
                }
                unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());
                unoGUI.nextPlayer.setEnabled(false);
                unoGUI.updatePoints(unoModel.currentRound.getTotalPoints());



            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * ActionListener to listen to JMenuItem "Save"
     */

    public class saveFileUndo implements ActionListener{

        public void actionPerformed(ActionEvent e){

            try {
                int curr_index = unoModel.currentRound.playerIndex;

                ObjectInputStream in = new ObjectInputStream(playerUndo);
                unoModel.currentRound = (Round) in.readObject();

                unoModel.currentRound.currentPlayer = unoModel.currentRound.getPlayers().get(curr_index);

                unoModel.currentRound.playerIndex = curr_index;

                unoModel.currentRound.currentPlayer.getHand().addCard(saveCardPLayed);

                System.out.println(unoModel.currentRound.playerIndex);
                unoModel.savePlayerMove();
                unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());

                unoGUI.displayCurrentPlayer(unoModel.currentRound.playerIndex);

                unoGUI.clearPlayerCards();
                for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                    unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
                }

                unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());
                isPlayerLocked = false;
                unoGUI.nextPlayer.setEnabled(false);



            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }


        }
    }

    /**
     * ActionListener to listen to JMenuItem "Redo"
     */

    public class saveFileRedo implements ActionListener {

        public void actionPerformed(ActionEvent e) {


            try {


                ObjectInputStream in = new ObjectInputStream(playerRedo);
                unoModel.currentRound = (Round) in.readObject();

                unoModel.savePlayerMove();

                unoModel.currentRound.currentPlayer.getHand().removeCard(saveCardPLayed);


                unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());

                unoGUI.displayCurrentPlayer(unoModel.currentRound.playerIndex);

                unoGUI.clearPlayerCards();
                for (int i = 0; i < unoModel.currentRound.currentPlayer.getHand().getSize(); i++) {
                    unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(i));
                }

                unoGUI.addPlayCardListener(unoModel.currentRound.currentPlayer.getHand(), new ListenForCardPlayed());
                isPlayerLocked = false;
                unoGUI.nextPlayer.setEnabled(true);


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }


    /**
     * The main method to run the Uno game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) throws FileNotFoundException {
        Controller controller = new Controller(new UnoGUI(), new Uno());
    }
}