package thrumania.gui;

import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.board.item.MapItems.MapElement;
import thrumania.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by mohamadamin on 6/24/16.
 */
public class PlayPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Map map;
    private Coordinate focus = new Coordinate(0, 0);
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private Constants.ZoomScales zoomScale = Constants.ZoomScales.ZERO_SCALE;
    private Constants.Elements selectedElelements = Constants.Elements.EMPTY;
    private MiniMapPanel miniMap;
    private Constants.Seasons season;
    private Constants.DayTime dayTime;
    int yfocus =0;
    int xfocus =0;

    public PlayPanel(Map map, MiniMapPanel panel) {
        this.miniMap = panel;
        this.map = map;
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setSize(d);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.miniMap.setPlayPanel(this);
        miniMap.updateMap();
        this.season = Constants.Seasons.SPRING;
        this.dayTime = Constants.DayTime.MORNING;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int seasonnum = giveMeSeasonNum();
        for (int r = 0; r < Constants.Drawer_HIGHT; r++) {
            for (int c = 0; c < Constants.DRAWER_WIDTH; c++) {
                this.drawingOcean(r, c, g);
//                if( this.season == Constants.Seasons.WINTER){
//                    this.drawingSnowFlake(xfocus , yfocus ,g);
//                    this.xfocus +=10;
//                    this.yfocus+=20;
//                }
                if (map.getCells()[r + focus.getRow()][c + focus.getColumn()] instanceof LowLand) {
                    g.drawImage(
                            ImageUtils.getImage(Integer.toString(Integer.parseInt(map.getCells()[r + focus.getRow()][c + focus.getColumn()].getPictureNameWithoutExtension()) + seasonnum * 16) + ".png"),
                            c * Constants.CELL_SIZE,
                            r * Constants.CELL_SIZE,
                            Constants.CELL_SIZE,
                            Constants.CELL_SIZE,
                            null);
                }

                if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems() != null) {
                    if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Tree"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                    else if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("StoneMine"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE + 10, null);
//                    g.fillRect( c * Constants.CELL_SIZE , r * Constants.CELL_SIZE ,Constants.CELL_SIZE * 2 , Constants.CELL_SIZE * 2 );

                    else if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Agliculture"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                    else {
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
                                Constants.CELL_SIZE + 15, Constants.CELL_SIZE + 15, null);
                    }
                }
            }
        }
    }

    private String getPictureNameAccordingToSeason(Constants.Seasons season, InsideElementsItems mapElement) {
        if (season.equals(Constants.Seasons.SPRING))
            return ( (MapElement)mapElement ).getSpringPictureName();
        else if (season.equals(Constants.Seasons.SUMMER))
            return ( (MapElement)mapElement ).getSummerPictureName();
        else if (season.equals(Constants.Seasons.AUTMN))
            return ((MapElement)mapElement).getAutmnPictureName();
        else
            return ((MapElement)mapElement).getWinterPictureName();
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
    
    public void setFocus(Coordinate position) {
        this.focus = position;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if ((focus.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) &&
                IntegerUtils.isInRange((Constants.DRAWER_WIDTH - 1) * Constants.CELL_SIZE,
                        (Constants.DRAWER_WIDTH) * Constants.CELL_SIZE,
                        e.getX()))
            focus.addColumn(1);
        if ((focus.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) &&
                IntegerUtils.isInRange((Constants.Drawer_HIGHT - 1) * Constants.CELL_SIZE,
                        (Constants.Drawer_HIGHT) * Constants.CELL_SIZE,
                        e.getY()))
            focus.addRow(1);
        if ((focus.getColumn() > 0) &&
                IntegerUtils.isInRange(0, Constants.CELL_SIZE, e.getX()))
            focus.addColumn(-1);
        if ((focus.getRow() > 0) &&
                IntegerUtils.isInRange(0, Constants.CELL_SIZE, e.getY()))
            focus.addRow(-1);
        this.miniMap.updateFocus(focus);
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


}
