import javax.swing.*;
import javax.swing.JFrame.*;
import javax.swing.border.Border;
import java.awt.*;


public class View {


    private final int FRAME_SIZE = 1000;
    private JFrame rootFrame;

    private JPanel handPanel;

    private Container contentPanel;

    public View(){

        rootFrame = new JFrame();

        rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        rootFrame.setResizable(false);
        rootFrame.pack();

        rootFrame.setSize(FRAME_SIZE, FRAME_SIZE);

        contentPanel = rootFrame.getContentPane();


        buildHand();
        rootFrame.setVisible(true);





//
//        buildPanel(); //Jpanel
//        buildHand(); //Hand //Make Jpanel
//        buildDeck(); //Make Deck
//        buildDiscard(); //Make Discard
//        buildPopup(); //Played unplayable Card
//        buildPlayerName();
//


    }
    public void buildDeck(){

        Button button = new Button("Deck");
        contentPanel.add(button);


    }

    public void buildDiscard(){

        Button button = new Button("Discard");
        contentPanel.add(button);
    }


    public void buildHand(){
        handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
//        handPanel.setSize(50, 50);
        rootFrame.add(handPanel, BorderLayout.CENTER);
        handPanel.setBackground(Color.BLACK);

        handPanel.setVisible(true);



    }









    public static void main(String args[]){

        View view = new View();


    }








}




