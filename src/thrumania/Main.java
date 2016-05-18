package thrumania;

import thrumania.board.Map;
import thrumania.gui.GameFrame;
import thrumania.utils.Constants;

/**
 * Created by mohamadamin on 5/17/16.
 */

public class Main {

    public static void main(String[] args) {
        Constants.initializeConstants();
        new GameFrame(new Map(55, 100));
    }

}
