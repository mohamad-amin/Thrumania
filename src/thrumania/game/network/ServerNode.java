package thrumania.game.network;

import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * Created by mohamadamin on 7/5/16.
 */
public class ServerNode extends Network {

    private String machineName;

    public ServerNode(PlayPanel panel, int numberOfPlayers, HashMap<String, Object> map, String machineName) {
        super(panel, numberOfPlayers, map);
        this.machineName = machineName;
        connect();
    }

    @Override
    void connect() {
        try {
            ServerSocket server = new ServerSocket(Constants.NETWORK_PORT);
            socket = server.accept();
            new Thread(new ServerHandler(socket, panel)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
