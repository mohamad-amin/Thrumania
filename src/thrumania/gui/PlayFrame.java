package thrumania.gui;

import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.buildings.Castle;
import thrumania.board.item.GameItems.people.Worker;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Cells.Sea;
import thrumania.board.item.MapItems.Inside.*;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.managers.AIManager;
import thrumania.managers.HumanManagers;
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
    private Dimension d = new Dimension(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
    private PlayPanel playPanel;
    private PlayBottomPanel playBottomPanel;
    private MiniMapPanel miniMapPanel;
    private PlayRightPanel playRightPanel;
    AIManager virtals;

    public AIManager getVirtals() {
        return virtals;
    }

    public void setVirtals(AIManager virtals) {
        this.virtals = virtals;
    }

    public PlayFrame(HashMap<Integer, Object> loadedMap, int players) {
        Constants.NUMBER_OF_PLAYERS = players;
        Side.setNumberOfPlayers(players);
        loadFrame(loadMapFromHash(loadedMap));
    }

    private void loadFrame(Map map) {
        this.map = map;
        this.setSize(d);
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setFocusable(false);
        Constants.mouseInitializer(this);

        miniMapPanel = new MiniMapPanel(map);
        this.add(miniMapPanel);
        map.setMiniMap(miniMapPanel);

        //Todo should handle playernumber
        playBottomPanel = new PlayBottomPanel();
        playPanel = new PlayPanel(map, miniMapPanel,0);
        playBottomPanel.setPlayPanel(playPanel);
        playPanel.setPlayBottomPanel(playBottomPanel);
        loadStrongholds();
        Thread playPanelThread = new Thread(playPanel);
        playPanelThread.start();
        playRightPanel = new PlayRightPanel(playPanel);
        Thread plaRightPanelThread = new Thread(playRightPanel);
        plaRightPanelThread.start();


        this.add(playPanel);
        this.add(playRightPanel);
        this.add(playBottomPanel);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);


        //Todo handle player number
        virtals = new AIManager(Constants.NUMBER_OF_PLAYERS-1,playPanel);

    }

    private void loadStrongholds() {
        MapProcessor processor = new MapProcessor(map.getCells());
        processor.newInitializeStrongholds();
        List<Cell> strongholdPositions = processor.findCastlePositions(Side.getNumberOfPlayers());
        int count = 0;
        for (Cell cell : strongholdPositions) {
            Castle castle = new Castle(cell.getPosition(), cell.getNeighborLand(map.getCells()).getPosition(), count,playBottomPanel,playPanel,this,map);
            castle.setWaterStartingPoint(cell.getNeighbourSea(map.getCells()).getPosition());
            cell.setInsideElementsItems(castle);
            this.initializingHumans(castle);
            count++;
        }
    }

    private void initializingHumans(Castle castle) {
        // each human will go back to it's starting point if its needed to go back to its origin such as castle or troops building
        // TODO : set the right number of humans for each team and castle and also use the method random number

        Worker worker = new Worker(playPanel, map, castle.getStartingPoint().getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10, castle.getStartingPoint().getRow() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10, castle.getSide().getNumberOfPlayer(), playBottomPanel);
        worker.setHomeCastleCoordinate(castle.getStartingPoint());
        HumanManagers.getSharedInstance().getHumans()[worker.getPlayerNumber()].add(worker);

        if(! worker.isExecuted())
        {
            worker.setExecuted(true);
            HumanManagers.getSharedInstance().getThreadPoolExecutor().execute(worker);
        }

    }


    // TODO : private int randomNumber

    private Map loadMapFromHash(HashMap<Integer, Object> hashMap) {

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
        return map;
    }

}
