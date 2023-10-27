import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View {

    private final int FRAME_SIZE_WIDTH = 500;
    private final int FRAME_SIZE_HEIGHT = 500;

    private JFrame rootFrame;
    private JPanel mainPanel, handPanel;


    private JButton deckButton, discardButton;

    public View() {

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


        //if card cannot be played call this function
//        buildLabelField();

        // clean up root frame
        rootFrame.pack();
        rootFrame.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        rootFrame.setVisible(true);
    }

    public void buildLabelField(){

        JLabel LabelField = new JLabel("Error");
        LabelField.setBounds(220,100,80,25);
        mainPanel.add(LabelField);
    }

    public void buildDeck() {
        JButton button = new JButton("Deck");
        button.setSize(100, 50);
        button.setLocation(50,100);

        mainPanel.add(button);
    }


    // make this into an image textlabel
    public void buildDiscard() {
        JLabel label = new JLabel("Discard");
        label.setSize(100, 50);
        label.setLocation(330,100);

        mainPanel.add(label);
    }

    public void buildHand() {
        handPanel = new JPanel();
        handPanel.setBackground(Color.DARK_GRAY);
        handPanel.setSize(FRAME_SIZE_WIDTH, 125);
        handPanel.setLocation(0,275);
        mainPanel.add(handPanel);
    }
    
    public static void main(String args[]) {
        View view = new View();
    }
}
