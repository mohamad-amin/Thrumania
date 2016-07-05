package thrumania.gui;

import thrumania.game.network.Network;
import thrumania.game.network.ServerNode;

import java.util.HashMap;

/**
 * Created by mohamadamin on 6/24/16.
 */
public class ServerFrame extends PlayFrame {

    private Network network;

    public ServerFrame(HashMap<Integer, Object> loadedMap, int players) {
        super(loadedMap, players, true);
        network = new ServerNode(playPanel, players, loadedMap);
        playPanel.setNetwork(network);
    }

}
