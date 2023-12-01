import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * UnoGUI class represents the graphical user interface for the UNO game.
 *
 * @author Zarif, Ajen, Arun, Jason
 * @version 3.0
 */
public class UnoGUI {


    //----------------------------------BASIC GUI------------------------------------

    protected int numFields;
    protected JButton nextPlayer = new JButton("Next Player");
    protected JButton UnoButton = new JButton("UNO!!");
    protected JButton buildDeckbutton = new JButton();
    protected JButton buildDiscardbutton = new JButton();
    protected JButton playGame = new JButton("PLAY GAME");
    protected JButton addPlayer = new JButton("ADD PLAYER");
    protected JButton addBot = new JButton("ADD BOT");
    private String[] botNames = {
            "Malik", "Jasmine", "Jamal", "Keisha", "Marcus",
            "LaToya", "Andre", "Aaliyah", "Tyrone", "Ebony","Emily", "Michael", "Emma", "Christopher", "Olivia",
            "Jacob", "Ava", "Matthew", "Sophia", "Nicholas"
    };
    protected JPanel startMenuPanel = new JPanel();
    private final int FRAME_SIZE_WIDTH = 700;
    private final int FRAME_SIZE_HEIGHT = 700;
    protected JFrame startMenuFrame;
    private JFrame rootFrame;
    protected JPanel mainPanel, handPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    private JPanel displayCurrentPlayerPanel = new JPanel();

    private JPanel nextPlayerButtonPanel = new JPanel();

    private JPanel leaderboardPanel = new JPanel();

    private JFrame leaderboardFrame = new JFrame("Leaderboard");

    private JLabel leaderboardLabelTitle;


    protected ArrayList<JTextField> playerInputFields; // Array to store user inputs
    protected ArrayList<JButton> playerCards = new ArrayList<>(); // holds player's hand; array of cards

    private JScrollPane Scrollpane;

    private JLabel display_current_player = new JLabel("this is where the players should be displayed!");

    private JLabel roundPoints;

    //---------------WILD CARD GUI-----------------

    protected Dialog wildCardDialog;

    protected JPanel wildPanel;

    protected JButton blue = new JButton();
    protected JButton red = new JButton();

    protected JButton yellow = new JButton();

    protected JButton green = new JButton();

//-------------------------------------------DISCARD INFO-----------------------------------------

    protected JLabel discardLabel = new JLabel();

    private JPanel discardPanel = new JPanel();


    //----------------------------------------COMPONENTS TO SAVE INFORMATION------------------------------

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");

    private JMenuItem save = new JMenuItem("Save");

    private JMenuItem undo = new JMenuItem("Undo");

    private JMenuItem redo = new JMenuItem("Redo");

    private JMenuItem load = new JMenuItem("Load");



    /**
     * Constructor for UnoGUI class.
     * Initializes the start menu and makes it visible.
     */

    public UnoGUI() {

        startMenu();
        setStartMenuVisible(true);
    }
    /**
     * Sets up the start menu frame and components.
     */
    public void startMenu() {

        numFields = 2;
        startMenuFrame = new JFrame("UNO Game Start Menu");
        startMenuFrame.setSize(500, 500);
        startMenuFrame.setLocationRelativeTo(null); // Center the frame
        startMenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startMenuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        startMenuPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel title_label = new JLabel("UNO", SwingConstants.CENTER);
        title_label.setFont(new Font("Arial", Font.BOLD, 36));
        constraints.gridy = 0;
        startMenuPanel.add(title_label, constraints);

        // Initial 2 player fields
        playerInputFields = new ArrayList<>();
        for (int i = 0; i < numFields; i++) {
            constraints.gridy = i + 1;
            constraints.gridwidth = 1;

            JLabel label = new JLabel("Player " + (i + 1) + ": ", SwingConstants.RIGHT);
            startMenuPanel.add(label, constraints);

            JTextField textField = new JTextField(10);
            constraints.gridx = 1;
            startMenuPanel.add(textField, constraints);
            playerInputFields.add(textField);

            constraints.gridx = 0;
        }

        // Play game button
        constraints.gridy = numFields + 2;
        constraints.gridwidth = 2;
        playGame.setPreferredSize(new Dimension(200, 40)); // Make the play button wider and taller
        playGame.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font for the button
        startMenuPanel.add(playGame, constraints);

        // Add player button
        constraints.gridy = numFields + 3;
        constraints.gridwidth = 2;
        addPlayer.setPreferredSize(new Dimension(200, 40)); // Make the play button wider and taller
        addPlayer.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font for the button
        startMenuPanel.add(addPlayer, constraints);

        // add bot player button

        constraints.gridy = numFields + 4;
        constraints.gridwidth = 2;
        addBot.setPreferredSize(new Dimension(200, 40)); // Make the play button wider and taller
        addBot.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font for the button
        startMenuPanel.add(addBot, constraints);



        startMenuFrame.add(startMenuPanel);
    }

    /**
     * Adds a new player field to the start menu.
     */

    public void addPlayerField() {
        if (numFields < 4) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets = new Insets(10, 10, 10, 10);

            // Add the label
            constraints.gridx = 0;
            constraints.gridy = numFields + 1; // Set the y position for the new label
            constraints.gridwidth = 1;
            JLabel label = new JLabel("Player " + (numFields + 1) + ":", SwingConstants.RIGHT);
            startMenuPanel.add(label, constraints);

            // Add the text field
            JTextField textField = new JTextField(10);
            constraints.gridx = 1;
            startMenuPanel.add(textField, constraints);
            playerInputFields.add(textField);

            // Increment the number of fields to account for the new player
            numFields++;

            // Update the y position for "Play Game" button
            constraints.gridx = 0;
            constraints.gridy = numFields + 1;
            constraints.gridwidth = 2;
            startMenuPanel.add(playGame, constraints);

            // update y position for "add bot" button
            constraints.gridy = numFields + 3;
            startMenuPanel.add(addBot, constraints);


            // Update the y position for "Add Player" button
            constraints.gridy = numFields + 2;
            startMenuPanel.add(addPlayer, constraints);

            startMenuPanel.revalidate();
            startMenuPanel.repaint();
        }
    }

    /**
     * adds the bot textfield option when starting the game
     */
    public void addBotField() {
        if (numFields < 4) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets = new Insets(10, 10, 10, 10);

            // Add the label
            constraints.gridx = 0;
            constraints.gridy = numFields + 1; // Set the y position for the new label
            constraints.gridwidth = 1;
            JLabel label = new JLabel("Bot " + (numFields + 1) + ":", SwingConstants.RIGHT);
            startMenuPanel.add(label, constraints);

            // Add the text field
            JTextField textField = new JTextField(10);
            textField.setName(label.getText());
            Random random = new Random();

            // Define the range (e.g., between 1 and 100)
            int minRange = 0;
            int maxRange = botNames.length-1;

            // Generate a random integer within the specified range
            int randomInRange = random.nextInt(maxRange - minRange + 1) + minRange;
            textField.setText(botNames[randomInRange]);
            constraints.gridx = 1;
            startMenuPanel.add(textField, constraints);
            playerInputFields.add(textField);

            // Increment the number of fields to account for the new player
            numFields++;

            // Update the y position for "Play Game" button
            constraints.gridx = 0;
            constraints.gridy = numFields + 1;
            constraints.gridwidth = 2;
            startMenuPanel.add(playGame, constraints);

            // Update the y position for "Add Player" button
            constraints.gridy = numFields + 2;
            startMenuPanel.add(addPlayer, constraints);

            // Update the y position for "Add Player" button
            constraints.gridy = numFields + 3;
            startMenuPanel.add(addBot, constraints);

            startMenuPanel.revalidate();
            startMenuPanel.repaint();
        }
    }

    /**
     * Sets the visibility of the start menu frame.
     *
     * @param flag true to make the start menu visible, false otherwise.
     */
    public void setStartMenuVisible(boolean flag) {

        startMenuFrame.setVisible(flag);

    }

    /**
     * Starts the UNO game by initializing the game frame and components.
     */
    public void startGame() {
        playerCards = new ArrayList<JButton>();

        // root frame
        rootFrame = new JFrame();
        rootFrame.setLayout(new BorderLayout());
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rootFrame.setResizable(true);

        // main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(new Dimension(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT));
        rootFrame.add(mainPanel, BorderLayout.CENTER);

        // set up current player display

        displayCurrentPlayerPanel.add(display_current_player);


        // Calculate round points
        roundPoints = new JLabel(" ::: Round Cumulative Points: 0");
        roundPoints.setHorizontalAlignment(SwingConstants.CENTER);
        displayCurrentPlayerPanel.add(roundPoints);

        mainPanel.add(displayCurrentPlayerPanel, BorderLayout.SOUTH);


        // build hand ui and add cards
        buildDeck();

        buildDiscard();

        buildMenuBar();
        buildHand();
        nextPlayer();
        displayCurrentPlayer(0);


        leaderboardFrame.setPreferredSize(new Dimension(FRAME_SIZE_WIDTH / 2, FRAME_SIZE_HEIGHT / 2));
        leaderboardFrame.setVisible(true);
        leaderboardFrame.pack();

        leaderboardFrame.setLocationRelativeTo(rootFrame);

        // clean up root frame
        rootFrame.pack();
        rootFrame.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        setstartGameVisible(true);
    }




    /**
     * updates the running leaderboard in the current round of Uno
     * @param players
     */
    public void Leaderboard(ArrayList<Player> players) {

        leaderboardPanel.removeAll();

        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardLabelTitle = new JLabel("LEADERBOARD", SwingConstants.CENTER);
        leaderboardLabelTitle.setFont(new Font("Arial", Font.BOLD, 16));
        leaderboardLabelTitle.setForeground(Color.DARK_GRAY);

        leaderboardPanel.add(leaderboardLabelTitle);

        for (Player player : players) {
            JLabel label = new JLabel("Player: " + player.getName() + "  Score: " + player.getScore());
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            leaderboardPanel.add(label);
        }

        leaderboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leaderboardFrame.setLayout(new BorderLayout());
        leaderboardFrame.add(leaderboardPanel, BorderLayout.CENTER);
        leaderboardFrame.setSize(300, 400);
        leaderboardFrame.setLocationRelativeTo(null); // Center the frame on the screen
        leaderboardFrame.setVisible(true);

    }

    /**
     * Creates a panel for the wild card selection.
     */
    public void wildCardGui() {
        // test create wildcard pane
        wildCardDialog = new JDialog();
        wildCardDialog.setTitle("Pick a color");
        wildCardDialog.setResizable(false);

        Dimension buttonSize = new Dimension(90, 25);

        red.setPreferredSize(buttonSize);
        blue.setPreferredSize(buttonSize);
        yellow.setPreferredSize(buttonSize);
        green.setPreferredSize(buttonSize);


        wildPanel = new JPanel(new GridLayout(1, 4));
        wildPanel.add(red);
        wildPanel.add(blue);
        wildPanel.add(yellow);
        wildPanel.add(green);

        wildCardDialog.add(wildPanel);
        wildCardDialog.pack();
        wildCardDialog.setLocationRelativeTo(null);
        wildCardDialog.setVisible(true);

    }
    /**
     *
     */

    public void setstartGameVisible(boolean flag){

        rootFrame.setVisible(flag);


    }

    /**
     * Updates the discard information panel with the specified card.
     *
     * @param card The card to display in the discard information panel.
     */

    public void discardInfo(Card card, boolean isDarkMode){
        if (isDarkMode) {
            discardLabel.setText("Colour at top of discard: " + card.getColorDark().toString());
        } else {
            discardLabel.setText("Colour at top of discard: " + card.getColorLight().toString());
        }
        discardPanel.add(discardLabel);
        mainPanel.add(discardPanel);

        mainPanel.revalidate();
        mainPanel.repaint();

    }

    /**
     * Clears the player cards from the hand panel.
     */
    public void clearPlayerCards() {
        handPanel.removeAll();
        handPanel.revalidate();
        handPanel.repaint();
        playerCards.clear();
    }



    /**
     * Adds the "Next Player" button to the user interface.
     */

    public void nextPlayer() {

        nextPlayer.setSize(20, 50);
        nextPlayerButtonPanel.setSize(20, 50);

        nextPlayerButtonPanel.add(nextPlayer);

        mainPanel.add(nextPlayerButtonPanel);

        nextPlayer.setEnabled(false);

        mainPanel.revalidate();
        mainPanel.repaint();


    }

    public void updatePoints(int points) {
        roundPoints.setText(" ::: Round Cumulative Points: " + points);

    }


    /**
     * Displays the name of the current player in the user interface.
     *
     * @param currentPlayer The index of the current player.
     */
    public void displayCurrentPlayer(int currentPlayer){
        display_current_player.setFont(new Font("Arial", Font.BOLD, 12));
        display_current_player.setText("The current player: " + playerInputFields.get(currentPlayer).getText());
    }

    /**
     * Builds the deck button in the user interface.
     */

    public void buildDeck() {
        // Set a fixed size for the discard button
        buildDeckbutton.setSize(100, 125);

        ImageIcon image = new ImageIcon(Card.DECK_FILE_NAME);
        buildDeckbutton.setIcon(image);



        // Retrieve the size after the button has been added
        int width = buildDeckbutton.getWidth();
        int height = buildDeckbutton.getHeight();

        // Scale the image to fit the button
        ImageIcon resizedIcon = new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        buildDeckbutton.setIcon(resizedIcon);

        // Make only images visible
        buildDeckbutton.setOpaque(false);
        buildDeckbutton.setContentAreaFilled(false);
        buildDeckbutton.setBorderPainted(false);





        buttonPanel.add(buildDeckbutton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }


    /**
     * Builds the discard button in the user interface.
     */
    public void buildDiscard() {
        buildDiscardbutton.setSize(100, 125);


        updateDiscard(Card.DECK_FILE_NAME);

        buttonPanel.add(buildDiscardbutton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.revalidate();
        mainPanel.repaint();



    }
    /**
     * Updates the discard button with the specified file path.
     *
     * @param file_path The file path of the card to display on the discard button.
     */
    public void updateDiscard(String file_path){
        buildDiscardbutton.setSize(100, 125); // Set a fixed size for the discard button


        ImageIcon image = new ImageIcon(file_path);
        buildDiscardbutton.setIcon(image);

        // Scale the image to fit the button
        int width = buildDiscardbutton.getWidth();
        int height = buildDiscardbutton.getHeight();

        ImageIcon resizedIcon = new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        buildDiscardbutton.setIcon(resizedIcon);

        // make only images visible
        buildDiscardbutton.setOpaque(false);
        buildDiscardbutton.setContentAreaFilled(false);
        buildDiscardbutton.setBorderPainted(false);
    }
    /**
     * Builds the hand panel in the user interface.
     */
    public void buildHand() {

        handPanel = new JPanel();
        handPanel.setSize(FRAME_SIZE_WIDTH, 100);
        handPanel.setLocation(0, 275);
        mainPanel.add(handPanel);


        handPanel.setLayout(new BoxLayout(handPanel, BoxLayout.X_AXIS));


        Scrollpane = new JScrollPane(handPanel);

        Scrollpane.setSize(new Dimension(1000, 100));

        Scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        rootFrame.add(Scrollpane, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();



    }


    private void buildMenuBar(){

        menu.add(save);
        menu.add(undo);
        menu.add(redo);
        menu.add(load);
        menuBar.add(menu);

        mainPanel.add(menuBar);

        rootFrame.setJMenuBar(menuBar);

        mainPanel.revalidate();
        mainPanel.repaint();

    }

    /**
     * Adds a card to the hand panel.
     *
     * @param card The card to add to the hand panel.
     * @return The button representing the added card.
     */
    public JButton addCard(Card card) {

        ImageIcon image = new ImageIcon(card.getImageFilePath());
        JButton cardButton = new JButton(image);
        cardButton.setPreferredSize(new Dimension(100, 125));
        int width = cardButton.getWidth();
        int height = cardButton.getHeight();
        if (width == 0) {width = 100;}
        if (height == 0) {height = 125;}
        ImageIcon resizedIcon = new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        cardButton.setIcon(resizedIcon);

        handPanel.add(cardButton);
        playerCards.add(cardButton);

        // make only images visible
        cardButton.setOpaque(false);
        cardButton.setContentAreaFilled(false);
        cardButton.setBorderPainted(false);

        return cardButton;
    }

    /**
     * Removes the specified button representing a card from the user interface.
     *
     * @param buttonToRemove The number representing the card to remove.
     * @param hand          The hand from which the card is removed.
     */
    public void updatePlayerCardsRemove(int buttonToRemove, Hand hand) {

        for (int i = 0; i < hand.getSize(); i++) {
            JButton button = playerCards.get(i);
            button.setName(Integer.toString(i));
        }

        handPanel.remove(playerCards.get(buttonToRemove));
        //playerCards.remove(buttonToRemove);
        playerCards.remove(buttonToRemove);
    }


    //-----------------------------------ACTION LISTENERS---------------------------------------------------------------



    public void addNextPlayerListener(ActionListener listenforNextPlayer) {
        nextPlayer.addActionListener(listenforNextPlayer);
    }

    public void redWildCardButtonListener(ActionListener listenforPlayColourRed) {
        red.addActionListener(listenforPlayColourRed);

    }

    public void blueWildCardButtonListener(ActionListener listenforPlayColourBlue) {
        blue.addActionListener(listenforPlayColourBlue);
    }

    public void yellowWildCardButtonListener(ActionListener listenforPlayColourYellow) {
        yellow.addActionListener(listenforPlayColourYellow);
    }

    public void greenWildCardButtonListener(ActionListener listenforPlayColourGreen) {
        green.addActionListener(listenforPlayColourGreen);
    }

    public void addBuildDeckListener(ActionListener listenforBuildDeck) {

        buildDeckbutton.addActionListener(listenforBuildDeck);
    }

    public void addPlayers(ActionListener listenforPlayersAdded) {

        addPlayer.addActionListener(listenforPlayersAdded);

    }

    public void addStartGameListener(ActionListener listenforStartGame) {

        playGame.addActionListener(listenforStartGame);

    }


    public void addBotListener(ActionListener listenforBot) {

        addBot.addActionListener(listenforBot);

    }


    //-------------------------------------LISTENER ACTIONERS FOR MENUBAR----------------------------------

    public void addFileSaveMenu(ActionListener listenforSave){
        save.addActionListener(listenforSave);
    }

    public void addFileUndo(ActionListener listenforUndo){
        redo.addActionListener(listenforUndo);
    }
    public void addFileRedo(ActionListener listenforRedo){
        undo.addActionListener(listenforRedo);
    }

    public void addFileLoad (ActionListener listenforLoad){ load.addActionListener(listenforLoad);}


    public void addPlayCardListener(Hand hand, ActionListener listenforCardtoPlay) {
        for (int i = 0; i < hand.getSize(); i++) {
            JButton button = playerCards.get(i);
            button.setName(Integer.toString(i));
            button.addActionListener(listenforCardtoPlay);
        }
    }

    public void updatePlayerInputFields(ArrayList<Player> players){
        for(int i = 0; i < players.size(); i++){
            playerInputFields.get(i).setText(players.get(i).getName());
        }
    }

}