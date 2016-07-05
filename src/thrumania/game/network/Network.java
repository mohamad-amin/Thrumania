package thrumania.game.network;

import thrumania.gui.PlayPanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by mohamadamin on 7/5/16.
 */
public abstract class Network {

    protected Socket socket;
    protected PlayPanel panel;
    private int numberOfPlayers;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private HashMap<String, Object> gameMap;

    public static int HUMAN_MOVE = 0,
            SHIP_MOVE = 1;

    public Network(PlayPanel panel, int numberOfPlayers, HashMap<String, Object> map) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameMap = map;
        this.panel = panel;
    }

    abstract void connect();

    protected void initializeStreams() {
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
