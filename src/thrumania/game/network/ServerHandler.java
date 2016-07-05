package thrumania.game.network;

import thrumania.board.item.GameItems.buildings.Barrack;
import thrumania.board.item.GameItems.buildings.Castle;
import thrumania.board.item.GameItems.buildings.Port;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Cells.Sea;
import thrumania.board.item.MapItems.Inside.*;
import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

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

    private boolean tryParseMap(HashMap<Integer, Object> hashMap) {
        try {
            byte[][] ids = (byte[][]) hashMap.get(2);
            String[][] pictureNames = (String[][]) hashMap.get(3);
            Cell cell = null;
            Coordinate position;
            Cell[][] cells = new Cell[ids.length][ids[0].length];
            Map map = new Map(ids.length, ids[0].length);
            for (int i = 0; i < ids.length; i++) {
                for (int j = 0; j < ids[0].length; j++) {
                    position = new Coordinate(i, j);
                    switch (ids[i][j]) {
                        case Constants.LOW_LAND_ID:
                            cell = new LowLand(position);
                            cell.setPictureName(pictureNames[i][j]);
                            break;
                        case Constants.HIGH_LAND_ID:
                            cell = new HighLand(position);
                            cell.setPictureName(pictureNames[i][j]);
                            break;
                        case Constants.SEA_ID:
                            cell = new Sea(position);
                            cell.setPictureName(pictureNames[i][j]);
                            break;
                        case Constants.DEEP_SEA_ID:
                            // Todo: deep sea
                            break;
                        case Constants.AGRICULTURE_ID:
                            cell = new LowLand(position);
                            cell.setInsideElementsItems(new Agliculture());
                            cell.setPictureName(pictureNames[i][j]);
                            break;
                        case Constants.TREE_ID:
                            cell = new LowLand(position);
                            cell.setInsideElementsItems(new Tree());
                            cell.setPictureName(pictureNames[i][j]);
                            break;
                        case Constants.STONE_ID:
                            cell = new HighLand(position);
                            cell.setInsideElementsItems(new StoneMine());
                            cell.setPictureName(pictureNames[i][j]);
                            break;
                        case Constants.GOLD_ID:
                            cell = new HighLand(position);
                            cell.setInsideElementsItems(new GoldMine());
                            cell.setPictureName(pictureNames[i][j]);
                            break;
                        case Constants.FISH_ID:
                            cell = new Sea(position);
                            cell.setInsideElementsItems(new SmallFish());
                            cell.setPictureName(pictureNames[i][j]);
                            break;
                    }
                    cells[i][j] = cell;
                }
            }
            map.setCells(cells);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean checkedMap = false;

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
                    if (!checkedMap) {
                        if (tryParseMap(map)) checkedMap = true;
                    }
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
