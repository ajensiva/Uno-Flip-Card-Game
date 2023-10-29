import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;

import UnoModel.Uno;

public class Controller {

    private Uno unoModel;
    private View view;

    public Controller(Uno uno, View view){
        this.unoModel = uno;
        this.view = view;
    }

    // updateDeckListener
    private class updateDeckListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            System.out.println();
        }
    }

    // updateHandListener
    private class updateHandListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){

        }
    }

    public static void main(String args[]) {
        Controller controller = new Controller(new Uno(), new View());
        System.out.println("poggers");
    }
}