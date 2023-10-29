import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller {


    private Uno_View view;
    private Uno unoModel;

    public Controller(Uno_View view, Uno uno) {
        this.view = view;
        this.unoModel = uno;

        view.buildDeck(new updateDeckListener());
        
    }

    private class updateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //handle playing card
            System.out.println("ok something happened");
        }

    }
    private class updateHandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){

            //Handle adding Card to deck
        }

    }

    // asdasdas
    public static void main(String args[]) {
        Controller controller = new Controller(new Uno_View(), null);

    }
}