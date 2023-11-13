import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UnoGUI {

    protected int numFields;
    protected JButton nextPlayer = new JButton("next Player");
    protected JButton UnoButton = new JButton("UNO!!");
    protected JButton buildDeckbutton = new JButton("Deck");
    protected JButton playGame = new JButton("PLAY GAME");
    protected JButton addPlayer = new JButton("ADD PLAYER");
    protected String userInputs[];
    protected JPanel startMenuPanel = new JPanel();
    protected JLabel darkside = new JLabel();
    private final int FRAME_SIZE_WIDTH = 600;
    private final int FRAME_SIZE_HEIGHT = 600;
    protected JFrame startMenuFrame;
    private JFrame rootFrame;
    private JPanel mainPanel, handPanel = new JPanel();
    private JButton deckButton, discardButton;
    protected ArrayList<JTextField> inputFields; // Array to store user inputs
    protected ArrayList<JButton> playerCards = new ArrayList<>(); // holds player's hand; array of cards
    protected boolean addingbuttons = true;

    //---------------WILD CARD GUI-----------------
    protected ArrayList<JButton> wildColours = new ArrayList<JButton>();

    private JFrame wildCardFrame;

    private JPanel wildPanel = new JPanel();

    private JButton blue, red, yellow, green;


    public UnoGUI() {
        startMenu();
        setStartMenuVisible(true);
//        startGame();
    }

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
        inputFields = new ArrayList<>();
        for (int i = 0; i < numFields; i++) {
            constraints.gridy = i + 1;
            constraints.gridwidth = 1;

            JLabel label = new JLabel("Player " + (i + 1) + ": ", SwingConstants.RIGHT);
            startMenuPanel.add(label, constraints);

            JTextField textField = new JTextField(10);
            constraints.gridx = 1;
            startMenuPanel.add(textField, constraints);
            inputFields.add(textField);

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

        startMenuFrame.add(startMenuPanel);
        System.out.println(startMenuFrame.getContentPane());
    }

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
            inputFields.add(textField);

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

            startMenuPanel.revalidate();
            startMenuPanel.repaint();
        }
    }

    public void setStartMenuVisible(boolean flag) {

        startMenuFrame.setVisible(flag);

    }

    public void startGame() {
        playerCards = new ArrayList<JButton>();


        // root frame
        rootFrame = new JFrame();
        rootFrame.setLayout(new BorderLayout());
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rootFrame.setResizable(true);

        // main panel
        mainPanel = new JPanel(new FlowLayout());
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT));
        rootFrame.add(mainPanel);

        // build hand ui and add cards
        buildHand();
        buildDeck();
        buildDiscard();

        // clean up root frame
        rootFrame.pack();
        rootFrame.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        rootFrame.setVisible(true);
    }

    public void wildCardGui() {
        // test create wildcard pane
        wildCardFrame = new JFrame();
        wildCardFrame.setSize(100, 100);

        wildCardFrame.add(wildPanel);

        wildPanel.setLayout(new GridBagLayout());

        JLabel wild_label = new JLabel("Pick a color?");
        wildPanel.add(wild_label);

        red = new JButton("Red");
        blue = new JButton("Blue");
        yellow = new JButton("Yellow");
        green = new JButton("Green");

        wildPanel.add(red);
        wildPanel.add(blue);
        wildPanel.add(yellow);
        wildPanel.add(green);

        wildCardFrame.pack();
        wildCardFrame.setVisible(true);

        wildColours.add(blue);
        wildColours.add(red);
        wildColours.add(yellow);
        wildColours.add(green);

    }


    public void WildCardClose() {
        wildCardFrame.setVisible(false);
    }

    public void clearPlayerCards() {
        handPanel.removeAll();
        handPanel.revalidate();
        handPanel.repaint();
        playerCards.clear();
    }


    public void currPlaySide() {

        mainPanel.add(darkside);
    }


    public void nextPlayer() {

        mainPanel.add(nextPlayer);

    }

    public void unoPressed() {

        mainPanel.add(UnoButton);

    }

    public void buildDeck() {
        System.out.println("test");
        buildDeckbutton.setSize(100, 100);
        buildDeckbutton.setLocation(100, 100);


        mainPanel.add(buildDeckbutton);
    }


    public void buildDiscard() {
        JButton button = new JButton("Discard");
        button.setSize(100, 100);
        button.setLocation(380, 100);

        mainPanel.add(button);
    }

    public void buildHand() {
        handPanel = new JPanel();
//        handPanel.setBackground(Color.WHITE);
        handPanel.setSize(FRAME_SIZE_WIDTH, 125);
        handPanel.setLocation(0, 275);
        mainPanel.add(handPanel);

        handPanel.setLayout(new GridLayout(0, 7));
    }

    public JButton addCard(Card card) {

        ImageIcon image = new ImageIcon(card.getImageFilePath());
        JButton cardButton = new JButton(image);


        JButton card_button = new JButton();

        handPanel.add(card_button);
        playerCards.add(card_button);

        return card_button;
    }


    public void updatePlayerCardsAdd(Card card) {
        JButton card_button = addCard(card);

        System.out.println("new card added to hand");
    }

    public void updatePlayerCardsRemove(JButton buttonClicked, Hand hand) {


        System.out.println(buttonClicked.getWidth());

        buttonClicked.setText("");

        playerCards.remove(playerCards.indexOf(buttonClicked));
        System.out.println("BEFORE: HandSize: " + hand.getSize() + " #Buttons: " + playerCards.size());
        handPanel.remove(buttonClicked);
        System.out.println("AFTER: HandSize: " + hand.getSize() + " #Buttons: " + playerCards.size());

        //System.out.println("Hand Size: " + hand.getSize());
        //System.out.println("# Buttons: " + playerCards.size());
        for (int i = 0; i < hand.getSize(); i++) {
            JButton button = playerCards.get(i);
            //button.setName("card" + i);
            button.setName(Integer.toString(i));
            button.setText(button.getName());
        }

        //
    }


    //-----------------------------------ACTION LISTENERS---------------------------------------------------------------


    public void addUnoButtonListener(ActionListener listenforUnoPressed) {
        UnoButton.addActionListener(listenforUnoPressed);
    }

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

    public void addPlayCardListener(Hand hand, ActionListener listenforCardtoPlay) {
        System.out.println("Hand Size: " + hand.getSize());
        System.out.println("# Buttons: " + playerCards.size());
        for (int i = 0; i < hand.getSize(); i++) {
            JButton button = playerCards.get(i);
            //button.setName("card" + i);
            button.setName(Integer.toString(i));
            button.setText(button.getName());
            button.addActionListener(listenforCardtoPlay);
        }
    }
}