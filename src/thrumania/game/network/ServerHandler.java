package thrumania.game.network;

import thrumania.board.item.GameItems.buildings.Barrack;
import thrumania.board.item.GameItems.buildings.Castle;
import thrumania.board.item.GameItems.buildings.Port;
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
            case Network.HUMAN_ACTION:
                playPanel.findAndSetElementSelected((Integer) map.get(1), (Integer) map.get(2));
                playPanel.setHumanAction((Integer) map.get(3), (Integer) map.get(4));
                break;
            case Network.SHIP_ACTION:
                playPanel.findAndSetElementSelected((Integer) map.get(1), (Integer) map.get(2));
                playPanel.setShipAction((Integer) map.get(3), (Integer) map.get(4));
                break;
            case Network.ADD_CONTAINER_SHIP:
                Port port = (Port) playPanel.selectElementAt((Integer) map.get(2), (Integer) map.get(3));
                playPanel.buildContainerShip(port);
                break;
            case Network.ADD_FISHER_SHIP:
                Port prt = (Port) playPanel.selectElementAt((Integer) map.get(2), (Integer) map.get(3));
                playPanel.buildFisherShip(prt);
                break;
            case Network.ADD_SOLDIER:
                Barrack barrack = (Barrack) playPanel.selectElementAt((Integer) map.get(2), (Integer) map.get(3));
                playPanel.buildSoldier(barrack);
                break;
            case Network.ADD_WORKER:
                Castle castle = (Castle) playPanel.selectElementAt((Integer) map.get(2), (Integer) map.get(3));
                playPanel.buildWorker(castle);
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
