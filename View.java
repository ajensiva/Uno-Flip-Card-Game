import javax.swing.*;
import java.awt.*;

public class View {

    private final int FRAME_SIZE_WIDTH = 500;
    private final int FRAME_SIZE_HEIGHT = 500;

    private JFrame rootFrame;
    private JPanel mainPanel;
    private JPanel handPanel;

    private Container contentPanel;

    public View() {

        // root frame
        rootFrame = new JFrame();
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rootFrame.setResizable(false);

        // main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(null); // Consider using LayoutManager for more complex layouts
        mainPanel.setPreferredSize(new Dimension(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT)); // Set preferred size
        rootFrame.add(mainPanel);

        buildHand();

        rootFrame.pack();
        rootFrame.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        rootFrame.setVisible(true); // Should be called at the end
    }

    public void buildDeck() {
        Button button = new Button("Deck");
        mainPanel.add(button);
    }

    public void buildDiscard() {
        Button button = new Button("Discard");
        mainPanel.add(button);
    }

    public void buildHand() {
        handPanel = new JPanel();
        handPanel.setBackground(Color.BLACK);
        handPanel.setSize(FRAME_SIZE_WIDTH, 125);
        handPanel.setLocation(0,275);
        mainPanel.add(handPanel); // Placing the handPanel at the center
    }


    public static void main(String args[]) {
        View view = new View();
    }
}
