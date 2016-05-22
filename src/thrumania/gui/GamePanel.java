package thrumania.gui;

import thrumania.board.Map;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;
import thrumania.utils.IntegerUtils;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private Constants.Elements selectedElelements = Constants.Elements.LOW_ALTITTUDE_LAND;
    MiniMapPanel miniMap;

//    private int CellSize_Zero_Scale
    public void setSelectedElelements(Constants.Elements selectedElelements) {
        this.selectedElelements = selectedElelements;
    }

    public GamePanel(Map map, MiniMapPanel panel) {
        this.miniMap = panel;
        this.map = map;
        this.setLayout(null);
        this.setLocation(0, 0);

        this.setSize(d);
        this.addMouseListener(this);
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
        if (this.selectedElelements == Constants.Elements.ZOOM_IN){
            zoomScale=Constants.incScale(zoomScale);
            repaint();
        }
        if(this.selectedElelements == Constants.Elements.ZOOM_OUT){
            zoomScale=Constants.decScale(zoomScale);
            repaint();
        }
        if(this.selectedElelements == Constants.Elements.LOW_ALTITTUDE_LAND){
            changingMap(e);
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
            changingMap(e);
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

    public void changingMap(MouseEvent e){
            int row = (e.getY() / Constants.CELL_SIZE) + start.getRow();
            int column = (e.getX() / Constants.CELL_SIZE) + start.getColumn();
            boolean repaint = map.changeMap(row, column);
            if (repaint) repaint();
    }

}