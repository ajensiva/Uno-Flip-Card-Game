import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View {


    private ArrayList<JTextField> inputFields; // Array to store user inputs

    private int drewCard;

    private final int FRAME_SIZE_WIDTH = 600;
    private final int FRAME_SIZE_HEIGHT = 600;

    private JFrame rootFrame;
    private JPanel mainPanel, handPanel;

    private JButton deckButton, discardButton;
    private ArrayList<JButton> playerCards; // holds player's hand; array of cards

    public View() {
        startGame();
    }

    public void startMenu(){


        int numFields = 0;

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        JPanel panel = new JPanel();
        frame.add(panel);

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
        JButton collectButton = new JButton("Collect Inputs");
        constraints.gridy = numFields + 2;
        panel.add(collectButton, constraints);

        collectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve user inputs from text fields
                String[] userInputs = new String[numFields];
                for (int i = 0; i < numFields; i++) {
                    userInputs[i] = inputFields.get(i).getText();
                }

                // Display the collected inputs (you can change this to your desired action)
                for (int i = 0; i < numFields; i++) {
                    System.out.println("Player " + (i + 1) + ": " + userInputs[i]);
                }
            }
        });

        constraints.gridy = numFields + 3;
        JButton playGame = new JButton("PLAY GAME");
        panel.add(playGame, constraints);

        playGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startGame();

            }
        });


        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        //buildDeck();
        buildDiscard();

        // clean up root frame
        rootFrame.pack();
        rootFrame.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        rootFrame.setVisible(true);

        // clean up root frame
        rootFrame.pack();
        rootFrame.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        rootFrame.setVisible(true);

        addCard();
        addCard();
    }


    // 
    public void addCard(){
        JButton card = new JButton();
        card.setName("[ CARD TEST ]");
        handPanel.add(card);
        playerCards.add(card);
    }

    public void buildDeck(ActionListener action) {
        System.out.println("test");
        JButton button = new JButton("Deck");
        button.setSize(100, 100);
        button.setLocation(100,100);

        button.addActionListener(action);
        mainPanel.add(button);
    }


    public void buildDiscard() {
        JButton button = new JButton("Discard");
        button.setSize(100, 100);
        button.setLocation(380,100);

        mainPanel.add(button);
    }

    public static int numCards = 7;

    public void buildHand() {
        handPanel = new JPanel();
//        handPanel.setBackground(Color.WHITE);
        handPanel.setSize(FRAME_SIZE_WIDTH, 125);
        handPanel.setLocation(0,275);
        mainPanel.add(handPanel);

        handPanel.setLayout(new GridLayout(0, 7));



    }
    
    public static void main(String args[]) {
        View view = new View();
        //view.startMenu();
    }
}