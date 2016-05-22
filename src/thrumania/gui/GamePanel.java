package thrumania.gui;

import thrumania.board.item.MapItems.Map;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;
import thrumania.utils.IntegerUtils;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

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

//    private int CellSize_Zero_Scale
    public void setSelectedElelements(Constants.Elements selectedElelements) {
        this.selectedElelements = selectedElelements;
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
    }

    public Constants.ZoomScales getZoomScale() {
        return zoomScale;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int r = 0; r < Constants.Drawer_HIGHT; r++) {
            for (int c = 0; c < Constants.DRAWER_WIDTH; c++) {
                g.drawImage(
                        ImageUtils.getImage("ocean1.jpg"),
                        c * Constants.CELL_SIZE,
                        r * Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        null);
                g.drawImage(
                        ImageUtils.getImage(map.getCells()[r + start.getRow()][c + start.getColumn()].getPictureName()),
                        c * Constants.CELL_SIZE,
                        r * Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        null);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = (e.getY() / Constants.CELL_SIZE) + start.getRow();
        int column = (e.getX() / Constants.CELL_SIZE) + start.getColumn();
        if (this.selectedElelements == Constants.Elements.ZOOM_IN){
            zoomScale=Constants.incScale(zoomScale);
            fixingStartInZoom(row,column);
            repaint();
            this.miniMap.updateFocus(start);
        } else if (this.selectedElelements == Constants.Elements.ZOOM_OUT){
            zoomScale=Constants.decScale(zoomScale);
            fixingStartInZoom(row,column);
            repaint();
            this.miniMap.updateFocus(start);
        } else if(this.selectedElelements == Constants.Elements.LOW_ALTITTUDE_LAND) {
            changingMap(row,column);
        } else if (this.selectedElelements == Constants.Elements.TREE) {

        } else if (this.selectedElelements == Constants.Elements.FISH) {

        } else if (this.selectedElelements == Constants.Elements.GOLD_MINE) {

        } else if (this.selectedElelements == Constants.Elements.STONE_MINE) {

        } else if (this.selectedElelements == Constants.Elements.FARM) {

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
        if(this.selectedElelements == Constants.Elements.LOW_ALTITTUDE_LAND) {
            int row = (e.getY() / Constants.CELL_SIZE) + start.getRow();
            int column = (e.getX() / Constants.CELL_SIZE) + start.getColumn();
            changingMap(row,column);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if((start.getColumn()<Constants.MATRIX_WIDTH- Constants.DRAWER_WIDTH) &&
                IntegerUtils.isInRange((Constants.DRAWER_WIDTH-1)*Constants.CELL_SIZE,
                        (Constants.DRAWER_WIDTH)*Constants.CELL_SIZE,
                        e.getX()))
            start.addColumn(1);
        if((start.getRow()<Constants.MATRIX_HEIGHT- Constants.Drawer_HIGHT) &&
                IntegerUtils.isInRange((Constants.Drawer_HIGHT-1)*Constants.CELL_SIZE,
                        (Constants.Drawer_HIGHT)*Constants.CELL_SIZE,
                        e.getY()))
            start.addRow(1);
        if((start.getColumn()>0) &&
                IntegerUtils.isInRange(0,Constants.CELL_SIZE, e.getX()))
            start.addColumn(-1);
        if((start.getRow()>0) &&
                IntegerUtils.isInRange(0,Constants.CELL_SIZE, e.getY()))
            start.addRow(-1);
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollUp(){
        if(start.getRow()>0) {
            start.addRow(-1);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollDown(){
        if(start.getRow()<Constants.MATRIX_HEIGHT- Constants.Drawer_HIGHT) {
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

    public void scrollLeft(){
        if(start.getColumn()>0) start.addColumn(-1);
        repaint();
        this.miniMap.updateFocus(start);
    }

    public void changingMap(int row,int column){
            boolean repaint = map.changeMap(row, column);
            if (repaint) repaint();
    }

    public void fixingStartInZoom(int x,int y){
        int startX=x-((Constants.Drawer_HIGHT/2)+(Constants.Drawer_HIGHT%2));
        int startY=y-((Constants.DRAWER_WIDTH/2)+(Constants.DRAWER_WIDTH%2));
        if(startX<0) startX=0;
        if(startY<0) startY=0;
        if(startX+Constants.Drawer_HIGHT>Constants.MATRIX_HEIGHT) startX = Constants.MATRIX_HEIGHT-Constants.Drawer_HIGHT;
        if(startY+Constants.DRAWER_WIDTH>Constants.MATRIX_WIDTH) startY = Constants.MATRIX_WIDTH-Constants.DRAWER_WIDTH;
        start = new Coordinate(startX,startY);
    }

}