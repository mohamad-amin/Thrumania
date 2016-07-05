package thrumania.gui;

import thrumania.game.network.Network;

import java.util.HashMap;

/**
 * Created by mohamadamin on 6/24/16.
 */
public class ClientFrame extends PlayFrame {

    public ClientFrame(HashMap<Integer, Object> loadedMap, int players, Network network) {
        super(loadedMap, players, true);
        playPanel.setNetwork(network);
    }

}
