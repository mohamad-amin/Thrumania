package thrumania.game.network;

import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by mohamadamin on 7/5/16.
 */
public class ClientNode extends Network {

    private String machineName;

    public ClientNode(PlayPanel playPanel, int numberOfPlayers, HashMap<Integer, Object> map, String machineName) {
        super(playPanel, numberOfPlayers, map);
        this.machineName = machineName;
        connect();
    }

    @Override
    void connect() {
        try {
            this.socket = new Socket(machineName, Constants.NETWORK_PORT);
            new Thread(new ServerHandler(socket, panel)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
