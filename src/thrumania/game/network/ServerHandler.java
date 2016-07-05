package thrumania.game.network;

import thrumania.gui.PlayPanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by mohamadamin on 7/5/16.
 */
public class ServerHandler implements Runnable {

    private Socket socket;
    private PlayPanel playPanel;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Stack<HashMap<Integer, Object>> sendStack;

    public ServerHandler(Socket socket, PlayPanel panel) {
        this.socket = socket;
        this.playPanel = panel;
        this.sendStack = new Stack<>();
    }

    public void addToSendStack(HashMap<Integer, Object> data) {
        sendStack.add(data);
    }

    private void parseHashMap(HashMap<Integer, Object> map) {
        switch ((Integer) map.get(0)) {
            case Network.HUMAN_MOVE:
                break;
            case Network.SHIP_MOVE:
                break;
        }
    }

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                while (!sendStack.isEmpty()) {
                    output.writeObject(sendStack.get(0));
                }
                Object inObject;
                while ((inObject = input.readObject()) != null) {
                    HashMap<Integer, Object> map = (HashMap) inObject;
                    parseHashMap(map);
                }
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
