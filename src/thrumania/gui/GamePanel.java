package thrumania.gui;

import thrumania.board.item.MapItems.*;
import thrumania.board.item.MapItems.Map;
import thrumania.messages.Messages;
import thrumania.utils.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.Timer;

/**
 * Created by sina on 5/18/16.
 */

public class GamePanel extends JPanel implements MouseInputListener {

    private Map map;
    private Coordinate start = new Coordinate(0, 0);
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private Constants.ZoomScales zoomScale = Constants.ZoomScales.ZERO_SCALE;
    private Constants.Elements selectedElelements = Constants.Elements.EMPTY;
    private MiniMapPanel miniMap;
    private Constants.Seasons season;
    private Constants.DayTime dayTime;
    int yStart =0;
    int xStart =0;

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
        this.addMouseMotionListener(this);
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
        for (int r = 0; r < Constants.Drawer_HIGHT; r++) {
            for (int c = 0; c < Constants.DRAWER_WIDTH; c++) {

               this.drawingOcean(r, c, g);
//                if( this.season == Constants.Seasons.WINTER){
//                    this.drawingSnowFlake(xStart , yStart ,g);
//                    this.xStart +=10;
//                    this.yStart+=20;
//                }
                if (map.getCells()[r + start.getRow()][c + start.getColumn()].getCode() == 1) {
                    System.out.println(Integer.toString(Integer.parseInt(map.getCells()[r + start.getRow()][c + start.getColumn()].getPictureNameWithoutExtension()) + seasonnum * 16) + ".png");
                    g.drawImage(
                            ImageUtils.getImage(Integer.toString(Integer.parseInt(map.getCells()[r + start.getRow()][c + start.getColumn()].getPictureNameWithoutExtension()) + seasonnum * 16) + ".png"),
                            c * Constants.CELL_SIZE,
                            r * Constants.CELL_SIZE,
                            Constants.CELL_SIZE,
                            Constants.CELL_SIZE,
                            null);
                }

                if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn() != null) {
                    System.out.println("Nooo");
                    System.out.println(map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn());
                    if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn().getClass().getSimpleName().equals("Tree"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                    else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn().getClass().getSimpleName().equals("StoneMine"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE + 10, null);
//                    g.fillRect( c * Constants.CELL_SIZE , r * Constants.CELL_SIZE ,Constants.CELL_SIZE * 2 , Constants.CELL_SIZE * 2 );

                    else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn().getClass().getSimpleName().equals("Agliculture"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                    else {
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideMapElemetn())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
                                Constants.CELL_SIZE + 15, Constants.CELL_SIZE + 15, null);
                    }
                }
            }
        }
    }

    private void saveMapToFile() {
        Cell[][] cells = map.getCells();
        byte[][] ids = new byte[cells.length][cells[0].length];
        for (int i=0; i<ids.length; i++) {
            for (int j=0; j<ids[0].length; j++) {
                ids[i][j] = cells[i][j].getId();
            }
        }
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(0, start.getRow());
        map.put(1, start.getColumn());
        this.miniMap.updateFocus(start);
        map.put(2, ids);
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
            byte[][] ids = (byte[][]) loadedMap.get(2);
            for (int i=0; i<ids.length; i++) {
                for (int j=0; j<ids[0].length; j++) {
                    if (ids[i][j] < 3) {
                        changingMap(i, j, "lowland");
                    } else if (ids[i][j] < 6) {
//                        Todo: @Amirhosein: // FIXME: 6/5/16 with highland
//                        changingMap(i, j, "highland");
                        changingMap(i, j, "lowland");
                    } else if (ids[i][j] < 8) {
                        changingMap(i, j, "sea");
                    } else {
                        // Todo: handle deep sea maybe?
                    }
                    switch (ids[i][j]) {
                        case Constants.AGRICULTURE_ID:
                            agricultureSetterToCell(i, j);
                            break;
                        case Constants.TREE_ID:
                            treeSetterToCell(i, j);
                            break;
                        case Constants.STONE_ID:
                            stoneSetterToCell(i, j);
                            break;
                        case Constants.GOLD_ID:
                            goldSetterToCell(i, j);
                            break;
                        case Constants.FISH_ID:
                            fishSetterToCell(i, j);
                            break;
                        default:
                            break;
                    }
                }
            }
            repaint();
        }
        JOptionPane.showMessageDialog(this,
                "Map loaded successfully :D", "Load Map", JOptionPane.INFORMATION_MESSAGE);
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
        System.out.println(i);
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
            changingMap(row, column, "lowland");
        } else if (this.selectedElelements == Constants.Elements.DEEP_SEA || this.selectedElelements == Constants.Elements.SHALLOW_SEA) {
            changingMap(row, column, "sea");
        } else if (this.selectedElelements == Constants.Elements.TREE) {
            this.treeSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.FISH) {
            this.fishSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.GOLD_MINE) {
            this.goldSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.STONE_MINE) {
            this.stoneSetterToCell(row, column);
        } else if (this.selectedElelements == Constants.Elements.AGRICULTURE) {
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

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int row = (e.getY() / Constants.CELL_SIZE) + start.getRow();
        int column = (e.getX() / Constants.CELL_SIZE) + start.getColumn();
        if (this.selectedElelements == Constants.Elements.LOW_ALTITTUDE_LAND)
            changingMap(row, column, "lowland");
        else if (this.selectedElelements == Constants.Elements.DEEP_SEA || this.selectedElelements == Constants.Elements.SHALLOW_SEA)
            changingMap(row, column, "sea");
        else if (this.selectedElelements == Constants.Elements.TREE)
            this.treeSetterToCell(row, column);
        else if (this.selectedElelements == Constants.Elements.AGRICULTURE)
            this.agricultureSetterToCell(row, column);
        else if (this.selectedElelements == Constants.Elements.FISH)
            this.fishSetterToCell(row, column);
        else if (this.selectedElelements == Constants.Elements.GOLD_MINE)
            this.goldSetterToCell(row, column);
        else if (this.selectedElelements == Constants.Elements.STONE_MINE)
            this.stoneSetterToCell(row, column);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if ((start.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) &&
                IntegerUtils.isInRange((Constants.DRAWER_WIDTH - 1) * Constants.CELL_SIZE,
                        (Constants.DRAWER_WIDTH) * Constants.CELL_SIZE,
                        e.getX()))
            start.addColumn(1);
        if ((start.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) &&
                IntegerUtils.isInRange((Constants.Drawer_HIGHT - 1) * Constants.CELL_SIZE,
                        (Constants.Drawer_HIGHT) * Constants.CELL_SIZE,
                        e.getY()))
            start.addRow(1);
        if ((start.getColumn() > 0) &&
                IntegerUtils.isInRange(0, Constants.CELL_SIZE, e.getX()))
            start.addColumn(-1);
        if ((start.getRow() > 0) &&
                IntegerUtils.isInRange(0, Constants.CELL_SIZE, e.getY()))
            start.addRow(-1);
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollUp() {
        if (start.getRow() > 0) {
            start.addRow(-1);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollDown() {
        if (start.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) {
            start.addRow(1);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollRight() {
        if (start.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) start.addColumn(1);
        repaint();
        this.miniMap.updateFocus(start);
    }

    public void scrollLeft() {
        if (start.getColumn() > 0) start.addColumn(-1);
        repaint();
        this.miniMap.updateFocus(start);
    }

    public void changingMap(int row, int column, String type) {
        if ("sea".equals(type)) map.changeMap(row, column, 0);
        if ("lowland".equals(type)) map.changeMap(row, column, 1);
        repaint();
    }

    public void fixingStartInZoom(int x, int y) {
        int startX = x - ((Constants.Drawer_HIGHT / 2) + (Constants.Drawer_HIGHT % 2));
        int startY = y - ((Constants.DRAWER_WIDTH / 2) + (Constants.DRAWER_WIDTH % 2));
        if (startX < 0) startX = 0;
        if (startY < 0) startY = 0;
        if (startX + Constants.Drawer_HIGHT > Constants.MATRIX_HEIGHT)
            startX = Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT;
        if (startY + Constants.DRAWER_WIDTH > Constants.MATRIX_WIDTH)
            startY = Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH;
        start = new Coordinate(startX, startY);
    }

    private String getPictureNameAccordingToSeason(Constants.Seasons season, MapElement mapElement) {
        if (season.equals(Constants.Seasons.SPRING))
            return mapElement.getSpringPictureName();
        else if (season.equals(Constants.Seasons.SUMMER))
            return mapElement.getSummerPictureName();
        else if (season.equals(Constants.Seasons.AUTMN))
            return mapElement.getAutmnPictureName();
        else
            return mapElement.getWinterPictureName();
    }

    private void treeSetterToCell(int row, int column) {
        Cell temp;
        Tree tempTree = new Tree();
        temp = map.getCell(row, column);
        if (temp.isCompeleteLand() && temp.getInsideMapElemetn() == null && temp.getCode() == 1) {

            temp.setInsideMapElemetn(tempTree);
            repaint();

        }

    }

    private void fishSetterToCell(int row, int column) {
        Cell temp;
        SmallFish smallFishTemp = new SmallFish();
        temp = map.getCell(row, column);

        if (!temp.isLand() && temp.getInsideMapElemetn() == null) {


            temp.setInsideMapElemetn(smallFishTemp);
            repaint();
        }
    }

    private void goldSetterToCell(int row, int col) {
        Cell temp;
        GoldMine goldMineTemp = new GoldMine();
        temp = map.getCell(row, col);
        if (temp.isLand() && temp.getInsideMapElemetn() == null && temp.getCode() == 1) {
            temp.setInsideMapElemetn(goldMineTemp);
            repaint();
        }
    }

    public void stoneSetterToCell(int row, int col) {
        Cell currentTempCell, rightTempCell, downRightTempCell, downTempCell;
        StoneMine stoneMineTemp = new StoneMine();
        currentTempCell = map.getCell(row, col);

        if (currentTempCell.getCode() == 1 && currentTempCell.getInsideMapElemetn() == null) {

            currentTempCell.setInsideMapElemetn(stoneMineTemp);


            repaint();

        }
    }


    private void agricultureSetterToCell(int row, int col) {
        Cell temp;
        Agliculture aglicultureTemp = new Agliculture();
        temp = map.getCell(row, col);
        if (temp.isLand() && temp.getCode() == 1 && temp.getInsideMapElemetn() == null) {
            temp.setInsideMapElemetn(aglicultureTemp);

            repaint();
        }

    }

    private void drawingOcean(int row, int column, Graphics g) {
        if (this.dayTime == Constants.DayTime.MORNING) {
            g.drawImage(ImageUtils.getImage("ocean1.jpg"), column * Constants.CELL_SIZE,
                    row * Constants.CELL_SIZE,
                    Constants.CELL_SIZE,
                    Constants.CELL_SIZE,
                    null);
        } else {
            g.drawImage(ImageUtils.getImage("ocean1Night.jpg"), column * Constants.CELL_SIZE,
                    row * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE, null);
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
}