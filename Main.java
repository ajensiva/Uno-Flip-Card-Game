import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JFrame with JPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create an empty panel as a spacer
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // Set the preferred height for the spacer panels
        int frameHeight = frame.getSize().height;
        int panelHeight = (int) (frameHeight * 0.1);
        topPanel.setPreferredSize(new Dimension(0, panelHeight));
        bottomPanel.setPreferredSize(new Dimension(0, panelHeight));

        // Create the central panel
        JPanel centralPanel = new JPanel();
        centralPanel.setBackground(Color.CYAN);

        // Add the central panel to the main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centralPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}
