package thrumania.gui;

import thrumania.board.item.MapItems.Map;
import thrumania.board.item.MapItems.Cell;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 5/18/16.
 */

public class MiniMapPanel extends JPanel {

    private Dimension d = new Dimension(this.getToolkit().getScreenSize().width - Constants.DRAWER_WIDTH * Constants.CELL_SIZE, this.getToolkit().getScreenSize().height - Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private Map map;
    private int cellSize;
    private Cell[][] cells;
    private Coordinate focus;

    public MiniMapPanel(Map map) {
        this.map = map;
        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.setSize(d);
        this.setLayout(null);
    }

    public void updateMap() {
        this.cells = map.getCells();
        System.out.println(getWidth());
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
                    Constants.DRAWER_WIDTH*cellSize, Constants.Drawer_HIGHT*cellSize
            );
            g.setColor(new Color(255, 0, 0, 60));
            g.fillRect(
                    focus.getColumn()*cellSize, focus.getRow()*cellSize,
                    Constants.DRAWER_WIDTH*cellSize, Constants.Drawer_HIGHT*cellSize
            );
        }
    }
}
