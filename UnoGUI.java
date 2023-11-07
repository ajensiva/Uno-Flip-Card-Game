import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UnoGUI {



    protected int numFields;

    protected JButton nextPlayer = new JButton("next Player");

    protected JButton UnoButton = new JButton("UNO!!");

    protected String userInputs[];

    protected JButton buildDeckbutton = new JButton("Deck");

    protected JLabel darkside = new JLabel();

    protected JButton playGame = new JButton("PLAY GAME");
    protected ArrayList<JTextField> inputFields; // Array to store user inputs

    protected JButton collectButton = new JButton();

    private int drewCard;

    private final int FRAME_SIZE_WIDTH = 600;
    private final int FRAME_SIZE_HEIGHT = 600;

    private JFrame startMenuFrame, rootFrame;
    private JPanel mainPanel, handPanel;

    private JButton deckButton, discardButton;
    protected ArrayList<JButton> playerCards; // holds player's hand; array of cards

    public UnoGUI() {
//        startMenu();
//        setStartMenuVisible(true);
        startGame();
    }

    public void startMenu(){


        numFields = 2;

        startMenuFrame = new JFrame();
        startMenuFrame.setSize(500, 500);
        JPanel panel = new JPanel();
        startMenuFrame.add(panel);

        // Create a GridBagLayout for the panel
        panel.setLayout(new GridBagLayout());

        // Create constraints for centering
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;

        // Create a title label
        JLabel title_label = new JLabel("UNO");
        Font title_font = new Font("Arial", Font.PLAIN, 24); // You can adjust the font size (24 in this example)
        title_label.setFont(title_font); // Set the font
        panel.add(title_label, constraints);


        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        //JTextField to ask for how many players want to play?
        JTextField num_play = new JTextField(10);
        panel.add(num_play, constraints);


        // Reset constraints for the input label
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        inputFields = new ArrayList<>();

        // Create input labels and text fields
        for (int i = 0; i < numFields; i++) {
            constraints.gridy = i + 1;


            inputFields.add(i, new JTextField("enter user " + (i + 1)));
            panel.add(inputFields.get(i), constraints);
        }

        // Create a button to collect user inputs
        constraints.gridy = numFields + 2;
        panel.add(collectButton, constraints);


        constraints.gridy = numFields + 3;
        panel.add(playGame, constraints);



        startMenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setStartMenuVisible(boolean flag){

        startMenuFrame.setVisible(flag);

    }

    public void startGame(){
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

        // clean up root frame
        rootFrame.pack();
        rootFrame.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        rootFrame.setVisible(true);

        for(int i = 0; i < 7; i++){
            addCard();
        }
    }


    public void currPlaySide(){

        mainPanel.add(darkside);
    }


    public void nextPlayer(){

        mainPanel.add(nextPlayer);

    }

    public void unoPressed(){

        mainPanel.add(UnoButton);

    }


    // 
    public void addCard(){
        JButton card = new JButton();
        card.setName("[ CARD TEST ]");
        handPanel.add(card);
        playerCards.add(card);
    }

    public void buildDeck() {
        System.out.println("test");
        buildDeckbutton.setSize(100, 100);
        buildDeckbutton.setLocation(100,100);


        mainPanel.add(buildDeckbutton);
    }


    public void buildDiscard() {
        JButton button = new JButton("Discard");
        button.setSize(100, 100);
        button.setLocation(380,100);

        mainPanel.add(button);
    }

    public void buildHand() {
        handPanel = new JPanel();
//        handPanel.setBackground(Color.WHITE);
        handPanel.setSize(FRAME_SIZE_WIDTH, 125);
        handPanel.setLocation(0,275);
        mainPanel.add(handPanel);

        handPanel.setLayout(new GridLayout(0, 7));

    }
    /*Button press calls function in Controller to handle logic for that button
    currently we have buttons for:

    if someone presses Uno
    if someone presses nextPlayer
    if someone is finshed inputting players, and presses "collect players"
    if someone presses "PlayGame" which starts the game
    if someone plays a card
    if someone draws a card from the deck

     */


    public void addUnoButtonListener(ActionListener listenforUnoPressed){
        UnoButton.addActionListener(listenforUnoPressed);
    }

    public void addNextPlayerListener(ActionListener listenforNextPlayer){
        nextPlayer.addActionListener(listenforNextPlayer);
    }

    public void addBuildDeckListener(ActionListener listenforBuildDeck){

        buildDeckbutton.addActionListener(listenforBuildDeck);
    }


    public void addPlayers(ActionListener listenforPlayersAdded){

        collectButton.addActionListener(listenforPlayersAdded);

    }
    public void addStartGameListener(ActionListener listenforStartGame){

        playGame.addActionListener(listenforStartGame);

    }


    public void addPlayCardListener(Hand hand, ActionListener listenforCardtoPlay){
        //private ArrayList<JButton> playerCards; // holds player's hand; array of cards
        System.out.println("handSize: " + hand.getSize());
        System.out.println("JButtonCards: " + playerCards.size());
        for(int i = 0; i < hand.getSize(); i++){
            Card card = hand.getCard(i);
            JButton button = playerCards.get(i);
            button.setName("card" + i);
            button.addActionListener(listenforCardtoPlay);
        }
    }

    public void addDrawCardListener(ActionListener listenforCardtoDraw){
        deckButton.addActionListener(listenforCardtoDraw);
    }



    public static void main(String args[]) {
        UnoGUI view = new UnoGUI();
        //view.startMenu();
    }
}