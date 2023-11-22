import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

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
        this.unoGUI.addBot.addActionListener(new addbotListener());
    }


    private void setHandPanelInteractable(boolean interactable) {
        for (JButton button : unoGUI.playerCards) {
            button.setEnabled(interactable);
        }
    }


    public void wildCardLogic() {

        if (unoModel.currentRound.cardPlayedLogic() && !isPlayerLocked) {
            isPlayerLocked = true;
            setHandPanelInteractable(false);
            unoGUI.nextPlayer.setEnabled(true);

            if (!Round.darkmode) {
                System.out.println("light mode");

                if (unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR) 
                    || unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
                         
                    if (unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILDTWO)) {
                         
                        unoModel.currentRound.drawCard(1);
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
                    } else if (unoModel.currentRound.Remove_card.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)) {
                        
                        unoModel.currentRound.drawCard(3);
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

            } else{
                System.out.println("dark mode");
                if (unoModel.currentRound.Remove_card.getTypeDark().equals(Card.TypeDark.DARK_WILD_CARD)) {
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
        System.out.println("index: " + unoModel.currentRound.getCardtoPlayIndex());
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
            if (unoGUI.numFields < 8) {
                unoGUI.addPlayerField();
            }
            if (unoGUI.numFields >= 8) {
                unoGUI.addPlayer.setEnabled(false); // Disable the add player button
            }
        }
    }

    public class addbotListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (unoGUI.numFields < 8) {
                unoGUI.addBotField();
            }
            if (unoGUI.numFields >= 8) {
                unoGUI.addBot.setEnabled(false); // Disable the add player button
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
                JTextField field = unoGUI.playerInputFields.get(i);
                boolean isBot = false;
                if(field.getName() != null && field.getName().substring(0, 3).equals("Bot")){
                    isBot = true;
                }
                unoModel.addPlayer(unoGUI.playerInputFields.get(i).getText(), isBot);
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
            unoGUI.updatePoints(unoModel.currentRound.getTotalPoints());
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

            wildCardLogic();

//                try {

            //PERSON WON ROUND
                    if (unoModel.currentRound.checkWinner()){
                        JOptionPane.showMessageDialog(null, unoModel.currentRound.currentPlayer.getName(), "Won Round! ", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(unoModel.currentRound.roundWinner.getName() + " won the round, awarding pts...");
                        unoModel.currentRound.roundWinner.setScore(unoModel.currentRound.roundWinner.getScore() + unoModel.currentRound.getTotalPoints());

                        //PERSON WON GAME
                        if(unoModel.checkGameWon()){

                            JOptionPane.showMessageDialog(null, unoModel.gameWinner.getName(), "Won The Game! ", JOptionPane.INFORMATION_MESSAGE);
                            unoGUI.setstartGameVisible(false);
                        }
                        else{
                            System.out.println("Starting new round...");
                            unoModel.round();
                        }



                    }
//                } catch (IndexOutOfBoundsException index) {
//                    System.out.println("Warning index is volatile" + index);
//                }
            }
        }

    /**
     * ActionListener for playing the red wild card.
     */
    public class PlayRedWildCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(Round.darkmode){


                unoModel.currentRound.discard.peek().setColorDark("Orange");
                unoGUI.wildCardFrame.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                unoGUI.discardLabel.setVisible(true);

            }
            else{
                unoModel.currentRound.discard.peek().setColorLight("Red");
                unoGUI.wildCardFrame.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
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
                unoGUI.wildCardFrame.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                unoGUI.discardLabel.setVisible(true);

            } else {
                unoModel.currentRound.discard.peek().setColorLight("Blue");
                unoGUI.wildCardFrame.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
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

            if(Round.darkmode){


                unoModel.currentRound.discard.peek().setColorDark("Purple");
                unoGUI.wildCardFrame.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                unoGUI.discardLabel.setVisible(true);

            }
            else{

                unoModel.currentRound.discard.peek().setColorLight("Yellow");
                unoGUI.wildCardFrame.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
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

            if(Round.darkmode){


                unoModel.currentRound.discard.peek().setColorDark("Pink");
                unoGUI.wildCardFrame.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                unoGUI.discardLabel.setVisible(true);

            }else {

                unoModel.currentRound.discard.peek().setColorLight("Green");
                unoGUI.wildCardFrame.setVisible(false);
                unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                unoGUI.discardLabel.setVisible(true);
            }
        }
    }

    /**
     * ActionListener for the "Next Player" button in the GUI.
     */
    public class NextPlayerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<Player> playersList = unoModel.currentRound.getPlayers();
            int currentIndex = unoModel.currentRound.playerIndex;
            int nextIndex = (currentIndex + 1) % playersList.size();

            if(playersList.get(nextIndex) instanceof AllenAI){
                unoModel.currentRound.nextPlayer();
                currentIndex = unoModel.currentRound.playerIndex;
                AllenAI bot = (AllenAI) playersList.get(unoModel.currentRound.playerIndex);
                System.out.println(bot.getHand());
                unoGUI.displayCurrentPlayer(currentIndex);
                if (bot.allenPlayCard(unoModel.currentRound, bot.getHand())){
                    unoGUI.updatePlayerCardsRemove(unoModel.currentRound.getCardtoPlayIndex(), unoModel.currentRound.currentPlayer.getHand());

                    if (bot.allenCardPlayed.getTypeLight().equals(Card.TypeLight.WILDTWO) || bot.allenCardPlayed.getTypeLight().equals(Card.TypeLight.WILD_DRAW_FOUR)) {
                        System.out.println("TEST THIS");
                        unoGUI.discardInfo(unoModel.currentRound.discard.peek());
                        System.out.println("I LOVED HER");
                    }
                    if ((bot.allenCardPlayed !=  null) && bot.allenCardPlayed.getTypeLight() == Card.TypeLight.REVERSE || (bot.allenCardPlayed !=  null) && bot.allenCardPlayed.getTypeDark() == Card.TypeDark.REVERSE) {
                        Collections.reverse(unoGUI.playerInputFields);
                    }

                    System.out.println("ALLEN PLAYED " + bot.getAllenPlayCard());
                    System.out.println("Discard " + unoModel.currentRound.discard.peek());
                    unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());

                }
                else{
                    System.out.println("allen drew a card: " + unoModel.currentRound.deck.peek());
                    unoModel.currentRound.drawCurrPlayer();
                    unoGUI.addCard(unoModel.currentRound.currentPlayer.getHand().getCard(unoModel.currentRound.currentPlayer.getHand().getSize() - 1));
                    setHandPanelInteractable(false);
                    unoGUI.nextPlayer.setEnabled(true);
                }


//                unoGUI.updateDiscard(unoModel.currentRound.discard.peek().getImageFilePath());
                unoGUI.updatePoints(unoModel.currentRound.getTotalPoints());
                return;
            }

            isPlayerLocked = false;
            setHandPanelInteractable(true);

            if ((unoModel.currentRound.Remove_card !=  null) && unoModel.currentRound.Remove_card.getTypeLight() == Card.TypeLight.REVERSE || (unoModel.currentRound.Remove_card !=  null) && unoModel.currentRound.Remove_card.getTypeDark() == Card.TypeDark.REVERSE) {
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
            unoGUI.updatePoints(unoModel.currentRound.getTotalPoints());
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

