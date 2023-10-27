import UnoModel.Uno;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller {


    private View view;

    private Uno uno;

    Controller(View view, Uno uno) {

        this.view = view;
        this.uno = uno;


        uno.playGame();

        view.addUpdateHandListener(new UpdateHandListener);
        view.addupdateDeckListener(new UpdateDeckListener);
    }

    private class UpdateHandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){



            //handle playing card
        }

    }
    private class UpdateDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){


            //Handle adding Card to deck
        }

    }
}
