package thrumania.game.network;

import thrumania.utils.Constants;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by mohamadamin on 7/5/16.
 */
public class ClientNode extends Network {

    private String machineName;
    private int s;
    public ClientNode(int numberOfPlayers, String machineName) {
        super(null, numberOfPlayers, null);
        s = numberOfPlayers;
        this.machineName = machineName;
        connect();
    }

    @Override
    void connect() {
        try {
            this.socket = new Socket(machineName, Constants.NETWORK_PORT);
            ServerHandler handler = new ServerHandler(socket, null);
            handler.setPlayers(s);
            new Thread(handler).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
