package thrumania.gui;

import thrumania.game.network.ClientNode;
import thrumania.game.network.Network;
import thrumania.game.network.ServerNode;

import java.util.HashMap;

/**
 * Created by mohamadamin on 6/24/16.
 */
public class NetworkFrame extends PlayFrame {

    private Network network;

    public NetworkFrame(HashMap<Integer, Object> loadedMap, int players, boolean isServer, String machineName) {
        super(loadedMap, players);
        if (isServer) network = new ServerNode(playPanel, players, loadedMap, machineName);
        else network = new ClientNode(playPanel, players, loadedMap, machineName);
        playPanel.setNetwork(network);
    }

}
