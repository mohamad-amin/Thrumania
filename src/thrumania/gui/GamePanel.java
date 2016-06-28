package thrumania.gui;

import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.*;
import thrumania.messages.Messages;
import thrumania.utils.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.TimerTask;

/**
 * Created by sina on 5/18/16.
 */

public class GamePanel extends JPanel implements MouseInputListener {

    private Map map;
    private FixedQueue<HashMap<Integer, Object>> stateQueue;
    private Coordinate start = new Coordinate(0, 0);
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private Constants.ZoomScales zoomScale = Constants.ZoomScales.ZERO_SCALE;
    private Constants.Elements selectedElelements = Constants.Elements.EMPTY;
    private MiniMapPanel miniMap;
    private Constants.Seasons season;
    private Constants.DayTime dayTime;
    int yStart =0;
    int xStart =0;
    private boolean isScoralling= false;
    int scoralSide=4;
    FloatingCoordinate continuousMovement = new FloatingCoordinate(0,0);

    //    private int CellSize_Zero_Scale
    public void setSelectedElelements(Constants.Elements selectedElelements) {
        this.selectedElelements = selectedElelements;
        switch (selectedElelements) {
            case SAVE:
                saveMapToFile();
                break;
            case LOAD:
                loadMapFromFile();
                break;
            case UNDO:
                undo();
                break;
            case REDO:
                redo();
                break;
            default:
                break;
        }
    }

    public void setStart(Coordinate start) {
        this.start = start;
        repaint();
    }

    public GamePanel(Map map, MiniMapPanel panel) {
        this.miniMap = panel;
        this.map = map;
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setSize(d);
        this.addMouseListener(this);
        this.miniMap.setGamePanel(this);
        miniMap.updateMap();
        this.addMouseMotionListener(this);
        stateQueue = new FixedQueue<>(Constants.STATE_QUEUE_SIZE);
        this.season = Constants.Seasons.SPRING;
        this.dayTime = Constants.DayTime.MORNING;

    }

    public Constants.ZoomScales getZoomScale() {
        return zoomScale;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int seasonnum = giveMeSeasonNum();
        int re = Constants.Drawer_HIGHT+1; if (start.getRow()==Constants.MATRIX_HEIGHT-Constants.Drawer_HIGHT) re= Constants.Drawer_HIGHT;
        int ce = Constants.DRAWER_WIDTH+1; if (start.getColumn()==Constants.MATRIX_WIDTH-Constants.DRAWER_WIDTH) ce= Constants.DRAWER_WIDTH;
        int r=-1; if (start.getRow()==0) r=0;
        for (; r < re; r++) {
            int c=-1; if (start.getColumn()==0) c=0;
            for (; c < ce; c++) {
               this.drawingOcean(r, c, g);
                    if (map.getCells()[r + start.getRow()][c + start.getColumn()] instanceof LowLand || map.getCells()[r + start.getRow()][c + start.getColumn()] instanceof HighLand) {
                        g.drawImage(
                                ImageUtils.getImage(Integer.toString(Integer.parseInt(map.getCells()[r + start.getRow()][c + start.getColumn()].getPictureNameWithoutExtension()) + seasonnum * 16) + ".png"),
                                c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE,
                                Constants.CELL_SIZE,
                                null);
                    }
                if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems() != null) {
                    if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Tree"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE+(int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE+(int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);

                    else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("StoneMine"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE+(int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE+(int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE, Constants.CELL_SIZE + 10, null);
//                    g.fillRect( c * Constants.CELL_SIZE , r * Constants.CELL_SIZE ,Constants.CELL_SIZE * 2 , Constants.CELL_SIZE * 2 );

                    else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Agliculture"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE+(int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE+(int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                    else {
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE+(int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE + 15, Constants.CELL_SIZE + 15, null);
                    }
                }
            }
        }
    }

    private void saveState() {
        stateQueue.add(getStateHashMap());
    }

    private HashMap<Integer, Object> getStateHashMap() {
        Cell[][] cells = map.getCells();
        byte[][] ids = new byte[cells.length][cells[0].length];
        String[][] pictureNames = new String[cells.length][cells[0].length];
        for (int i=0; i<ids.length; i++) {
            for (int j=0; j<ids[0].length; j++) {
                ids[i][j] = cells[i][j].getId();
                pictureNames[i][j] = cells[i][j].getPictureName();
            }
        }
        HashMap<Integer, Object> state = new HashMap<>();
        state.put(2, ids);
        state.put(3, pictureNames);
        return state;
    }

    private void undo() {
        HashMap<Integer, Object> hashMap = stateQueue.getPrevious(getStateHashMap());
        if (hashMap == null) {
            JOptionPane.showMessageDialog(this, "Previous step isn't available :(", "Undo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            loadMapFromHash(hashMap);
        }
    }

    private void redo() {
        HashMap<Integer, Object> hashMap = stateQueue.getNext(getStateHashMap());
        if (hashMap == null) {
            JOptionPane.showMessageDialog(this, "No next step is available :(", "Redo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            loadMapFromHash(hashMap);
        }
    }

    private void saveMapToFile() {
        Cell[][] cells = map.getCells();
        byte[][] ids = new byte[cells.length][cells[0].length];
        String[][] pictureNames = new String[cells.length][cells[0].length];
        for (int i=0; i<ids.length; i++) {
            for (int j=0; j<ids[0].length; j++) {
                ids[i][j] = cells[i][j].getId();
                pictureNames[i][j] = cells[i][j].getPictureName();
            }
        }
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(0, start.getRow());
        map.put(1, start.getColumn());
        map.put(2, ids);
        map.put(3, pictureNames);
        this.miniMap.updateFocus(start);
        String fileName = "";
        fileName = JOptionPane.showInputDialog(this, "Please enter your map's name:", "Save Map",
                JOptionPane.INFORMATION_MESSAGE);
        if (fileName == null || fileName.trim().length()==0) {
            JOptionPane.showMessageDialog(this, "Not a valid file name:(", "Save Map", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (FileUtils.saveHashMapToFile(map, fileName + ".tmap")) {
                JOptionPane.showMessageDialog(this, "Map saved successfully :)", "Save Map", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Couldn't save map :(", "Save Map", JOptionPane.INFORMATION_MESSAGE);
            }
        }


    }

    private void loadMapFromFile() {
        String mapFilePath = "";
        JOptionPane.showMessageDialog(this, "Choose map file", "Load Map", JOptionPane.INFORMATION_MESSAGE);
        mapFilePath = FileUtils.chooseFile(this, "data/map");
        if  (!FileUtils.isMapFile(mapFilePath)) {
            JOptionPane.showMessageDialog(this,
                    "The selected file isn't a valid file :(", "Load Map", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        HashMap<Integer, Object> loadedMap = FileUtils.getHashMapFromFile(mapFilePath);
        if (loadedMap == null) {
            JOptionPane.showMessageDialog(this,
                    "Corrupted map, couldn't load :(", "Load Map", JOptionPane.INFORMATION_MESSAGE);
        } else {
            start.setRow((Integer) loadedMap.get(0));
            start.setColumn((Integer) loadedMap.get(1));
            loadMapFromHash(loadedMap);
        }
        JOptionPane.showMessageDialog(this,
                "Map loaded successfully :D", "Load Map", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadMapFromHash(HashMap<Integer, Object> hashMap) {

        byte[][] ids = (byte[][]) hashMap.get(2);
        String[][] pictureNames = (String[][]) hashMap.get(3);
        Cell cell = null;
        Coordinate position;
        Cell[][] cells = new Cell[ids.length][ids[0].length];
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
        miniMap.updateMap();
        repaint();
    }

    private int giveMeSeasonNum() {
        if (season.equals(Constants.Seasons.SPRING)) return 0;
        else if (season.equals(Constants.Seasons.SUMMER)) return 1;
        else if (season.equals(Constants.Seasons.AUTMN)) return 2;
        else if (season.equals(Constants.Seasons.WINTER)) return 3;
        return 4;
    }


    private void nextSeason() {
        int x = giveMeSeasonNum();
        season = giveMeSeasonConstant(x + 1);
        repaint();
    }

    private Constants.Seasons giveMeSeasonConstant(int i) {
        i = i % 4;
        if (i == 0) return Constants.Seasons.SPRING;
        else if (i == 1) return Constants.Seasons.SUMMER;
        else if (i == 2) return Constants.Seasons.AUTMN;
        else return Constants.Seasons.WINTER;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = (e.getY() / Constants.CELL_SIZE) + start.getRow();
        int column = (e.getX() / Constants.CELL_SIZE) + start.getColumn();
        if (this.selectedElelements == Constants.Elements.ZOOM_IN) {
            zoomScale = Constants.incScale(zoomScale);
            fixingStartInZoom(row, column);
            repaint();
            this.miniMap.updateFocus(start);
        } else if (this.selectedElelements == Constants.Elements.ZOOM_OUT) {
            zoomScale = Constants.decScale(zoomScale);
            fixingStartInZoom(row, column);
            repaint();
            this.miniMap.updateFocus(start);
        } else if (this.selectedElelements == Constants.Elements.LOW_ALTITTUDE_LAND) {
            saveState();
            changingMap(row, column, "lowland");
        } else if (this.selectedElelements == Constants.Elements.DEEP_SEA || this.selectedElelements == Constants.Elements.SHALLOW_SEA) {
            saveState();
            changingMap(row, column, "sea");
        }else if (this.selectedElelements == Constants.Elements.HIGH_ALTITTUDE_LAND){
            changingMap(row, column, "highland");
        } else if (this.selectedElelements == Constants.Elements.TREE) {
            saveState();
            this.treeSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.FISH) {
            saveState();
            this.fishSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.GOLD_MINE) {
            saveState();
            this.goldSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.STONE_MINE) {
            saveState();
            this.stoneSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.AGRICULTURE) {
            saveState();
            this.agricultureSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.PREVIEW) {
            nextSeason();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        isScoralling = false ;
        scoralSide = 4;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int row = (e.getY() / Constants.CELL_SIZE) + start.getRow();
        int column = (e.getX() / Constants.CELL_SIZE) + start.getColumn();
        if(row<start.getRow()+Constants.Drawer_HIGHT && column < start.getColumn()+Constants.DRAWER_WIDTH) {
            if (this.selectedElelements == Constants.Elements.LOW_ALTITTUDE_LAND) {
                saveState();
                changingMap(row, column, "lowland");
            } else if (this.selectedElelements == Constants.Elements.DEEP_SEA || this.selectedElelements == Constants.Elements.SHALLOW_SEA) {
                saveState();
                changingMap(row, column, "sea");
            } else if (this.selectedElelements == Constants.Elements.TREE) {
                saveState();
                this.treeSetterToCell(row, column);
            } else if (this.selectedElelements == Constants.Elements.HIGH_ALTITTUDE_LAND) {
                saveState();
                changingMap(row, column, "highland");
            } else if (this.selectedElelements == Constants.Elements.AGRICULTURE) {
                saveState();
                this.agricultureSetterToCell(row, column);
            } else if (this.selectedElelements == Constants.Elements.FISH) {
                saveState();
                this.fishSetterToCell(row, column);
            } else if (this.selectedElelements == Constants.Elements.GOLD_MINE) {
                saveState();
                this.goldSetterToCell(row, column);
            } else if (this.selectedElelements == Constants.Elements.STONE_MINE) {
                saveState();
                this.stoneSetterToCell(row, column);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int scoralSidetemp ;
        if ((start.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) &&
                IntegerUtils.isInRange((Constants.DRAWER_WIDTH  * Constants.CELL_SIZE)-Constants.RANGEOFSCROLL,
                        (Constants.DRAWER_WIDTH) * Constants.CELL_SIZE,
                        e.getX()))
            scoralSidetemp = 2;
        else if ((start.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) &&
                IntegerUtils.isInRange((Constants.Drawer_HIGHT * Constants.CELL_SIZE)-Constants.RANGEOFSCROLL,
                        (Constants.Drawer_HIGHT) * Constants.CELL_SIZE,
                        e.getY()))
            scoralSidetemp = 1;
        else if ((start.getColumn() > 0) &&
                IntegerUtils.isInRange(0, Constants.RANGEOFSCROLL, e.getX()))
            scoralSidetemp = 0;
        else if ((start.getRow() > 0) &&
                IntegerUtils.isInRange(0, Constants.RANGEOFSCROLL, e.getY()))
            scoralSidetemp = 3;
        else scoralSidetemp =4;
        if (scoralSide ==4){
            if(scoralSidetemp!=4) {
                scoralSide = scoralSidetemp;
                isScoralling = true;
                scoral();
            }
        }else {
            if(scoralSidetemp==4) {
                scoralSide = 4;
                isScoralling = false;
            }
            else if(scoralSidetemp!=scoralSide){
                scoralSide = scoralSidetemp;
                continuousMovement.setColumn(0);
                continuousMovement.setRow(0);
            }
        }
    }



    public void changingMap(int row, int column, String type) {
        if ("sea".equals(type)) map.changeMap(row, column, 0);
        if ("lowland".equals(type)) map.changeMap(row, column, 1);
        if ("highland".equals(type)) map.changeMap (row, column , 2);
        repaint();
    }

    public void fixingStartInZoom(int x, int y) {
        int startX = x - ((Constants.Drawer_HIGHT / 2) + (Constants.Drawer_HIGHT % 2));
        int startY = y - ((Constants.DRAWER_WIDTH / 2) + (Constants.DRAWER_WIDTH % 2));
        if (startX < 0)
            startX = 1;
        if (startY < 0)
            startY = 1;
        if (startX + Constants.Drawer_HIGHT > Constants.MATRIX_HEIGHT)
            startX = Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT-1;
        if (startY + Constants.DRAWER_WIDTH > Constants.MATRIX_WIDTH)
            startY = Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH-1;
        start = new Coordinate(startX, startY);
    }

    private String getPictureNameAccordingToSeason(Constants.Seasons season, InsideElementsItems mapElement) {
        if (season.equals(Constants.Seasons.SPRING))
            return ( (MapElement)mapElement ).  getSpringPictureName();
        else if (season.equals(Constants.Seasons.SUMMER))
            return ( (MapElement)mapElement ).getSummerPictureName();
        else if (season.equals(Constants.Seasons.AUTMN))
            return ( (MapElement)mapElement ).getAutmnPictureName();
        else
            return ( (MapElement)mapElement ).getWinterPictureName();
    }

    private void treeSetterToCell(int row, int column) {
        Cell temp;
        Tree tempTree = new Tree();
        temp = map.getCell(row, column);
        if (temp.isCompeleteLand() && temp.getInsideElementsItems() == null && temp instanceof LowLand) {

            temp.setInsideElementsItems(tempTree);
            repaint();

        }

    }

    private void fishSetterToCell(int row, int column) {
        Cell temp;
        SmallFish smallFishTemp = new SmallFish();
        temp = map.getCell(row, column);
        System.out.println(temp.isLand());
        if (! ( temp.getId() < 6) && temp.getInsideElementsItems() == null) {

            System.out.println("here3");
            temp.setInsideElementsItems(smallFishTemp);
            repaint();
        }
    }

    private void goldSetterToCell(int row, int col) {
        Cell temp;
        GoldMine goldMineTemp = new GoldMine();
        temp = map.getCell(row, col);
        if (temp.isLand() && temp.getInsideElementsItems() == null && temp instanceof HighLand) {
            temp.setInsideElementsItems(goldMineTemp);
            repaint();
        }
    }

    public void stoneSetterToCell(int row, int col) {
        Cell currentTempCell ;
        StoneMine stoneMineTemp = new StoneMine();
        currentTempCell = map.getCell(row, col);
        if (currentTempCell instanceof HighLand && currentTempCell.getInsideElementsItems() == null) {
            currentTempCell.setInsideElementsItems(stoneMineTemp);
            repaint();
        }
    }


    private void agricultureSetterToCell(int row, int col) {
        Cell temp;
        Agliculture aglicultureTemp = new Agliculture();
        temp = map.getCell(row, col);
        if (temp.isLand() && temp instanceof LowLand && temp.getInsideElementsItems() == null) {
            temp.setInsideElementsItems(aglicultureTemp);
            repaint();
        }

    }

    private void drawingOcean(int row, int column, Graphics g) {
        if (this.dayTime == Constants.DayTime.MORNING) {
            g.drawImage(ImageUtils.getImage("ocean1.jpg"),
                    column * Constants.CELL_SIZE + (int)(continuousMovement.getColumn()*Constants.CELL_SIZE),
                    row * Constants.CELL_SIZE + (int)(continuousMovement.getRow()*Constants.CELL_SIZE),
                    Constants.CELL_SIZE,
                    Constants.CELL_SIZE,
                    null);
        } else {
            g.drawImage(ImageUtils.getImage("ocean1Night.jpg"), column * Constants.CELL_SIZE + (int)(continuousMovement.getColumn()*Constants.CELL_SIZE),
                    row * Constants.CELL_SIZE + (int)(continuousMovement.getRow()*Constants.CELL_SIZE) , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
        }
    }

    private void drawingSnowFlake(int xStart , int yStart , Graphics g) {
        if (xStart + 30 < Constants.DRAWER_WIDTH * Constants.CELL_SIZE && yStart + 30 < Constants.Drawer_HIGHT * Constants.CELL_SIZE) {
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    g.drawImage(ImageUtils.getImage("snowFlake.png"), xStart, yStart, 30, 30, null);
                }
            };
        }
        else {
            this.xStart =0;
            this.yStart=0;
        }


    }

    public Constants.Seasons getSeason() {
        return season;
    }

    public void setSeason(Constants.Seasons season) {
        this.season = season;
    }

    public Constants.DayTime getDayTime() {
        return dayTime;
    }

    public void setDayTime(Constants.DayTime dayTime) {
        this.dayTime = dayTime;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        if (e.getID() == Messages.REPAINT) this.repaint();
        super.processComponentEvent(e);
    }

    public void scoral(){
        new Thread(() -> {
            while (isScoralling) {
                changeColOrRow();
            }}).start();
    }

    private void changeColOrRow() {
        switch (scoralSide) {
            case 0:
                scrollLeft();
                break;
            case 1:
                scrollDown();
                break;
            case 2:
                scrollRight();
                break;
            case 3:
                scrollUp();
                break;
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollUp(){
        if (start.getRow() > 0) {
            for (int j = 1 ; j < Constants.RATEOFSCROLL & isScoralling; j++) {
                continuousMovement.addRow(((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            start.addRow(-1);
            continuousMovement.setRow(0);
            continuousMovement.setColumn(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollDown() {
        if (start.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) {
            for (int j =1 ; j < Constants.RATEOFSCROLL && isScoralling;j++) {
                continuousMovement.addRow(-((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            start.addRow(1);
            continuousMovement.setRow(0);
            continuousMovement.setColumn(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollRight() {
        if (start.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) {
                for (int j =1 ;j<Constants.RATEOFSCROLL & isScoralling;j++) {
                    continuousMovement.addColumn(-((float)1/(float)Constants.RATEOFSCROLL));
                    try {
                        Thread.sleep(Constants.scrollSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();
                }
                start.addColumn(1);
                continuousMovement.setColumn(0);
                continuousMovement.setRow(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollLeft() {
        if (start.getColumn() > 0) {
            for (int j = 1; j < Constants.RATEOFSCROLL & isScoralling; j++) {
                continuousMovement.addColumn(((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            start.addColumn(-1);
            continuousMovement.setColumn(0);
            continuousMovement.setRow(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }
}