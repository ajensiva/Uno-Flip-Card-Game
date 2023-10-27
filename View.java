import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View {

    private final int FRAME_SIZE_WIDTH = 500;
    private final int FRAME_SIZE_HEIGHT = 500;

    private JFrame rootFrame;
    private JPanel mainPanel, handPanel;

    private JButton deckButton, discardButton;
    private ArrayList<JButton> playerCards; // holds player's hand; array of cards

    public View() {

        playerCards = new ArrayList<JButton>();

        // root frame
        rootFrame = new JFrame();
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rootFrame.setResizable(false);

        // main panel
        mainPanel = new JPanel();
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

    // 
    public void addCard(){
        JButton card = new JButton();
        card.setName("[ CARD TEST ]");
        handPanel.add(card);
        playerCards.add(card);
    }

    public void buildDeck() {
        JButton button = new JButton("Deck");
        button.setSize(100, 50);
        button.setLocation(100,100);

        mainPanel.add(button);
    }

    public void buildDiscard() {
        JButton button = new JButton("Discard");
        button.setSize(100, 50);
        button.setLocation(250,100);

        mainPanel.add(button);
    }

    public static int numCards = 17;

    public void buildHand() {
        handPanel = new JPanel();
        //handPanel.setBackground(Color.BLACK);
        handPanel.setSize(FRAME_SIZE_WIDTH, 125);
        handPanel.setLocation(0,275);
        mainPanel.add(handPanel);

        handPanel.setLayout(new GridLayout(0, 7));        
    }
    
    public static void main(String args[]) {
        View view = new View();

        for(int i = 0; i < numCards; i++){
            view.addCard();
        }
    }
}
