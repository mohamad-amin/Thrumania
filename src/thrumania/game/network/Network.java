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
    private ServerHandler serverHandler;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private HashMap<Integer, Object> gameMap;

    public final static int HUMAN_ACTION = 0,
            SHIP_ACTION = 1,
            ADD_WORKER = 2,
            ADD_SOLDIER = 3,
            ADD_CONTAINER_SHIP = 4,
            ADD_FISHER_SHIP = 5;

    public Network(PlayPanel panel, int numberOfPlayers, HashMap<Integer, Object> map) {
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

    public void sendData(HashMap<Integer, Object> data) {
        serverHandler.addToSendStack(data);
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
