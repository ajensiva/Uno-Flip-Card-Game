package UnoModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller {


    private View view;

    private Round round;

    Controller(View view, Round round) {

        this.view = view;
        this.round = round;


//        view.addUpdateHandListener(new UpdateHandListener);
//        view.addupdateDeckListener(new UpdateDeckListener);
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
