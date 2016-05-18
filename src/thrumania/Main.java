package thrumania;

import thrumania.board.Map;
import thrumania.gui.GameFrame;

/**
 * Created by mohamadamin on 5/17/16.
 */

public class Main {

    public static void main(String[] args) {
        new GameFrame(new Map(55, 100));
    }

}
