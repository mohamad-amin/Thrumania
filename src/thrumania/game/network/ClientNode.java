package thrumania.game.network;

import thrumania.utils.Constants;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by mohamadamin on 7/5/16.
 */
public class ClientNode extends Network {

    private String machineName;

    public ClientNode(int numberOfPlayers, HashMap<String, Object> map, String machineName) {
        super(numberOfPlayers, map);
        this.machineName = machineName;
        connect();
    }

    @Override
    void connect() {
        try {
            this.socket = new Socket(machineName, Constants.NETWORK_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
