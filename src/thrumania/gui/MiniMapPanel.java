package thrumania.gui;

import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Map;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by sina on 5/18/16.
 */

public class MiniMapPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Dimension d = new Dimension(this.getToolkit().getScreenSize().width - Constants.DRAWER_WIDTH * Constants.CELL_SIZE, this.getToolkit().getScreenSize().height - Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private Map map;
    private int cellSize;
    private Cell[][] cells;
    private Coordinate focus;
    private GamePanel gamePanel;
    private PlayPanel playPanel;

    public MiniMapPanel(Map map) {
        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.map = map;
        this.setSize(d);
        this.setLayout(null);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel;
    }
    public void setPlayPanel(PlayPanel panel) {
        this.playPanel = panel;
    }

    public void updateMap() {
        this.cells = map.getCells();
        this.cellSize = getWidth()/cells[0].length;
        repaint();
    }

    public void updateFocus(Coordinate focus) {
        this.focus = focus;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (cells != null) {
            for (int i=0; i<cells.length; i++) {
                for (int j=0; j<cells[0].length; j++) {
                    g.drawImage(
                            ImageUtils.getImage(cells[i][j].getPictureName()),
                            j*cellSize, i*cellSize, cellSize, cellSize, null
                    );
                }
            }
        }
        if (focus != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(255, 0, 0));
            g2.drawRect(
                    focus.getColumn()*cellSize, focus.getRow()*cellSize,
                    getBoxWidth(), getBoxHeight()
            );
            g.setColor(new Color(255, 0, 0, 60));
            g.fillRect(
                    focus.getColumn()*cellSize, focus.getRow()*cellSize,
                    getBoxWidth(), getBoxHeight()
            );
        }
    }

    private int getBoxWidth() {
        return Constants.DRAWER_WIDTH*cellSize;
    }

    private int getBoxHeight() {
        return Constants.Drawer_HIGHT*cellSize;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x -= getBoxWidth()/2;
        y -= getBoxHeight()/2;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > getWidth()-getBoxWidth()) x = getWidth()-getBoxWidth();
        if (y > getHeight()-getBoxHeight()) y = getHeight()-getBoxHeight();
        x /= cellSize;
        y /= cellSize;
        updateFocus(new Coordinate(y, x));
        if (gamePanel != null) gamePanel.setStart(new Coordinate(y, x));
        else if (playPanel != null) playPanel.setStart(new Coordinate(y, x));
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = (int) e.getPoint().getX();
        int y = (int) e.getPoint().getY();
        System.out.println("Here with " + new Coordinate(x ,y));
        x -= getBoxWidth()/2;
        y -= getBoxHeight()/2;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > getWidth()-getBoxWidth()) x = getWidth()-getBoxWidth();
        if (y > getHeight()-getBoxHeight()) y = getHeight()-getBoxHeight();
        x /= cellSize;
        y /= cellSize;
        updateFocus(new Coordinate(y, x));
        if (gamePanel != null) gamePanel.setStart(new Coordinate(y, x));
        else if (playPanel != null) playPanel.setStart(new Coordinate(y, x));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
