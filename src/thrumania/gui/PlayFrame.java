package thrumania.gui;

import thrumania.board.item.GameItems.buildings.Castle;
import thrumania.board.item.MapItems.*;
import thrumania.game.MapProcessor;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mohamadamin on 6/24/16.
 */
public class PlayFrame extends JFrame {

    private Map map;
    private int players;
    private Dimension d = new Dimension(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
    private PlayPanel playPanel;
    private MiniMapPanel miniMapPanel;

    public PlayFrame(HashMap<Integer, Object> loadedMap, int players) {
        this.players = players;

        loadFrame(loadMapFromHash(loadedMap));

    }

    private void loadFrame(Map map) {

        this.map = map;
        loadStrongholds();

        this.setSize(d);
        this.setLayout(null);
        this.setLocation(0,0);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setFocusable(false);
        Constants.mouseInitializer(this);

        miniMapPanel = new MiniMapPanel(map);
        this.add(miniMapPanel);
        map.setMiniMap(miniMapPanel);

        playPanel = new PlayPanel(map, miniMapPanel);
        this.add(playPanel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    private void loadStrongholds() {
        MapProcessor processor = new MapProcessor(map.getCells());
        processor.newInitializeStrongholds();
        List<Cell> strongholdPositions = processor.findCastlePositions(players);
        for (Cell cell : strongholdPositions) {
            cell.setInsideElementsItems(new Castle());
        }
        // Todo: add strongholds
    }

    private Map loadMapFromHash(HashMap<Integer, Object> hashMap) {

        byte[][] ids = (byte[][]) hashMap.get(2);
        String[][] pictureNames = (String[][]) hashMap.get(3);
        Cell cell = null;
        Coordinate position;
        Cell[][] cells = new Cell[ids.length][ids[0].length];
        Map map = new Map(ids.length, ids[0].length);
        for (int i=0; i<ids.length; i++) {
            for (int j=0; j<ids[0].length; j++) {
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
                        // Todo: fix it when highland is added
                        cell = new LowLand(position);
                        cell.setInsideElementsItems(new StoneMine());
                        cell.setPictureName(pictureNames[i][j]);
                        break;
                    case Constants.GOLD_ID:
                        cell = new LowLand(position);
                        cell.setInsideElementsItems(new GoldMine());
                        cell.setPictureName(pictureNames[i][j]);
                        break;
                    case Constants.FISH_ID:
                        cell = new LowLand(position);
                        cell.setInsideElementsItems(new SmallFish());
                        cell.setPictureName(pictureNames[i][j]);
                        break;
                }
                cells[i][j] = cell;
            }
        }
        map.setCells(cells);
        return map;

    }

}
