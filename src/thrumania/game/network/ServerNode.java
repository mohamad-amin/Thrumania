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

    public ServerNode(PlayPanel panel, int numberOfPlayers, HashMap<Integer, Object> map) {
        super(panel, numberOfPlayers, map);
        System.out.println("Here1");
        connect();
        super.sendData(map);
    }

    @Override
    void connect() {
        try {
            ServerSocket server = new ServerSocket(Constants.NETWORK_PORT);
            socket = server.accept();
            ServerHandler handler = new ServerHandler(socket, panel);
            handler.setServer(true);
            new Thread(handler).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
